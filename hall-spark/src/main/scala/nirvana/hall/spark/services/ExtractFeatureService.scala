package nirvana.hall.spark.services

import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.extractor.jni.JniLoader
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.{ExtractResponse, ExtractRequest}
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.SparkFunctions.StreamEvent
import SparkFunctions.{StreamError, StreamEvent}
import org.apache.commons.io.FileUtils

import scala.annotation.tailrec
import scala.util.control.NonFatal

/**
 * extract feature service
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-09
 */
object ExtractFeatureService {
  private lazy val extractor = new FeatureExtractorImpl
  case class ExtractError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "E|"+message
  }
  def requestExtract(parameter:NirvanaSparkConfig,event:StreamEvent,originalImg:GAFISIMAGESTRUCT):Option[(StreamEvent,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT)]= {
    if (event.personId != null && event.personId.length > 0) {
      try {
        SparkFunctions.loadExtractorJNI()
        //FileUtils.writeByteArrayToFile(new File("/home/gafis/spark/"+event.path+".img"),originalImg.toByteArray())
        val featureTryVersion = if (parameter.isNewFeature) ExtractProto.NewFeatureTry.V2 else ExtractProto.NewFeatureTry.V1
        val mnt = extractor.extractByGAFISIMG(originalImg, event.position, event.featureType, featureTryVersion)
        Some(event,mnt._1,mnt._2)
        /*val data = extractor.extractByGAFISIMGBinary(ByteString.copyFrom(originalImg.toByteArray()).newInput(), event.position, event.featureType, featureTryVersion)
        val gafisImg = new GAFISIMAGESTRUCT
        val is = ByteString.copyFrom(data.get._1).newInput()
        gafisImg.fromStreamReader(is)
        Some((event, gafisImg,ByteString.copyFrom(data.get._2)))*/
        /*val width = originalImg.stHead.nWidth
        val height = originalImg.stHead.nHeight
        if (width * height != originalImg.stHead.nImgSize)
          FileUtils.writeStringToFile(new File("~/spark/whImgSize.txt"),"width:"+width+"|height:"+height+"|nImgSize:"+originalImg.stHead.nImgSize+"|"+event.personId+"-"+event.featureType.getNumber+"-"+originalImg.stHead.bIsPlain+"/\r/\n",true)

        val rpcHttpClient = SparkFunctions.httpClient
        val request = ExtractRequest.newBuilder()
        request.setImgData(ByteString.copyFrom(originalImg.toByteArray()))
        request.setFeatureTry(featureTryVersion)
        request.setMntType(event.featureType)
        request.setPosition(event.position)
        val baseResponse = rpcHttpClient.call(parameter.extractorServer, ExtractRequest.cmd, request.build())
        baseResponse.getStatus  match{
          case CommandStatus.OK =>
            if(baseResponse.hasExtension(ExtractResponse.cmd)) {
              val response = baseResponse.getExtension(ExtractResponse.cmd)
              val imgData = response.getMntData
              val binData = response.getBinData

              val gafisImg = new GAFISIMAGESTRUCT
              val is = imgData.newInput()
              gafisImg.fromStreamReader(is)

              /*val gafisBin = new GAFISIMAGESTRUCT
              val bin = binData.newInput()
              gafisBin.fromStreamReader(bin)*/

              Some((event, gafisImg,binData))
            }else{
              throw new IllegalAccessException("response haven't ExtractResponse")
            }
          case CommandStatus.FAIL =>
            throw new IllegalAccessException("fail to extractor,server message:%s".format(baseResponse.getMsg))
        }*/
      } catch {
        case NonFatal(e) =>
          e.printStackTrace(System.err)
          SparkFunctions.reportError(parameter, ExtractError(event, e.toString))
          None
      }
    } else if (event.caseId != null && event.caseId.length > 0){ //Latent type
      Some((event, originalImg,null))
    } else {
      SparkFunctions.reportError(parameter, ExtractError(event, "personId and caseId are null!"))
      None
    }
  }
}
