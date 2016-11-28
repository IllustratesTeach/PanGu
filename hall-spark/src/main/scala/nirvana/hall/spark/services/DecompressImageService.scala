package nirvana.hall.spark.services

import java.util.concurrent.atomic.AtomicLong

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.image.internal.FirmDecoderImpl
import nirvana.hall.protocol.image.FirmImageDecompressProto.{FirmImageDecompressRequest, FirmImageDecompressResponse}
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.SparkFunctions.{StreamError, StreamEvent}

import scala.util.control.NonFatal

/**
 * decompress image service
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-09
 */
object DecompressImageService extends LoggerSupport{
  private lazy val serverRandom = randomImageServer()
  private lazy val seq = new AtomicLong(0)
  private lazy val gfsDirect = SysProperties.getBoolean("decompress.gfs.direct",defaultValue = false)
  private lazy val wsqDirect = SysProperties.getBoolean("decompress.wsq.direct",defaultValue = false)
  private lazy val imageServers = SysProperties.getPropertyOption("decompress.server").get

  private lazy val decoder = new FirmDecoderImpl(".",null)
  case class DecompressError(streamEvent:StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "D|"+message
  }
  private def randomImageServer():() => String={
    val imageServerList = imageServers.split(",")
    val len = imageServerList.length

    if(len == 1){
      ()=>{
        imageServers
      }
    }else {
      () => {
        val i = seq.incrementAndGet()
        imageServerList.apply((i % len).toInt)
      }
    }
  }
  def requestDecompress(parameter:NirvanaSparkConfig,event:StreamEvent,compressedImg: GAFISIMAGESTRUCT):Option[(StreamEvent,GAFISIMAGESTRUCT)]= {
    if (event.personId != null && event.personId.length > 0) {
    //if (true) {

      if (compressedImg.stHead.bIsCompressed == 1) {
        //using direct decompress method for WSQ and GFS
        if (compressedImg.stHead.nCompressMethod == glocdef.GAIMG_CPRMETHOD_WSQ.toByte
        && wsqDirect){
          directDecode(parameter, event, compressedImg)
        }else if( gfsDirect && (compressedImg.stHead.nCompressMethod == glocdef.GAIMG_CPRMETHOD_GFS.toByte
          || compressedImg.stHead.nCompressMethod == glocdef.GAIMG_CPRMETHOD_XGW.toByte
          || compressedImg.stHead.nCompressMethod == glocdef.GAIMG_CPRMETHOD_XGW_EZW.toByte
          || compressedImg.stHead.nCompressMethod == glocdef.GAIMG_CPRMETHOD_DEFAULT.toByte)){
          if (compressedImg.stHead.nWidth != 640 || compressedImg.stHead.nHeight != 640) {//if width or height is not 640 , convert to 640
            doReportException(parameter, event,new RuntimeException("width or height is not 640,actual width is "+ compressedImg.stHead.nWidth + " height is "+ compressedImg.stHead.nHeight))
            compressedImg.stHead.nWidth = 640
            compressedImg.stHead.nHeight = 640
          }
          directDecode(parameter, event, compressedImg)
        }
        else
          decompressWithHttpService(parameter, event, compressedImg)
      } else {
        Some((event, compressedImg))
      }
    } else if (event.caseId != null && event.caseId.length > 0){ //Latent type
      Some((event, compressedImg))
    } else {
      doReportException(parameter, event,new RuntimeException("personId and caseId are null!"))
      None
    }
  }

  private def directDecode(parameter: NirvanaSparkConfig, event: StreamEvent, compressedImg: GAFISIMAGESTRUCT): Option[(StreamEvent, GAFISIMAGESTRUCT)] = {
    try {
      //load jni
      //          println("jni decompress code:"+compressedImg.stHead.nCompressMethod)
      SparkFunctions.loadImageJNI()
      //FileUtils.writeByteArrayToFile(new File("/home/gafis/spark/"+event.path+".data"),decoder.decode(compressedImg).toByteArray())
      val result = decoder.decode(compressedImg)
      Some((event, result))
    } catch {
      case NonFatal(e) =>
        doReportException(parameter, event,
          new scala.RuntimeException("fail to decompress,code:" + compressedImg.stHead.nCompressMethod + " " + e.toString, e))
    }
  }

  def doReportException(parameter:NirvanaSparkConfig, event:StreamEvent, e: Throwable) = {
    error(e.getMessage, e)
    SparkFunctions.reportError(parameter, DecompressError(event, e.toString))
    None
  }

  private def decompressWithHttpService(parameter: NirvanaSparkConfig, event: StreamEvent, compressedImg: GAFISIMAGESTRUCT): Option[(StreamEvent, GAFISIMAGESTRUCT)] = {
    val rpcHttpClient = SparkFunctions.httpClient
    val request = FirmImageDecompressRequest.newBuilder()
    request.setCprData(ByteString.copyFrom(compressedImg.toByteArray()))
    //    @tailrec
    def doDecompress(seq: Int): Option[(StreamEvent, GAFISIMAGESTRUCT)] = {
      try {
        val baseResponse = rpcHttpClient.call(serverRandom(), FirmImageDecompressRequest.cmd, request.build())
        baseResponse.getStatus match {
          case CommandStatus.OK =>
            if (baseResponse.hasExtension(FirmImageDecompressResponse.cmd)) {
              val response = baseResponse.getExtension(FirmImageDecompressResponse.cmd)
              val imgData = response.getOriginalData
              val gafisImg = new GAFISIMAGESTRUCT
              val is = imgData.newInput()
              gafisImg.fromStreamReader(is)
              if (gafisImg.stHead.nWidth != 640 || gafisImg.stHead.nHeight != 640) {//if width or height is not 640 , convert to 640
                doReportException(parameter, event,new RuntimeException("width or height is not 640,actual width is "+ gafisImg.stHead.nWidth + " height is "+ gafisImg.stHead.nHeight))
                gafisImg.stHead.nWidth = 640
                gafisImg.stHead.nHeight = 640
              }
              Some((event, gafisImg))
            } else {
              throw new IllegalAccessException("response haven't FirmImageDecompressResponse")
            }
          case CommandStatus.FAIL =>
            throw new IllegalAccessException("fail to decompress,server message:%s".format(baseResponse.getMsg))
        }
      } catch {
        case e: CallRpcException =>
          if (seq == 4) doReportException(parameter,event,e) else doDecompress(seq + 1)
        case NonFatal(e) =>
          doReportException(parameter,event,e)
      }
    }
    doDecompress(1)
  }
}
