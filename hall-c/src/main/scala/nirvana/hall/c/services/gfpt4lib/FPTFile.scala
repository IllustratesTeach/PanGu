package nirvana.hall.c.services.gfpt4lib

import java.io.{File, FileInputStream, InputStream}
import java.nio.charset.Charset

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.AncientData.{StreamReader, StreamWriter, _}
import nirvana.hall.c.services.gfpt4lib.FPT3File.FPT3File
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import org.apache.commons.io.IOUtils

import scala.language.reflectiveCalls
import scala.reflect._
import scala.util.control.NonFatal

/**
 * fpt file
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
object FPTFile {
  val FS:Byte= 28
  val GS:Byte= 29
  val FPT_FLAG="FPT"
  val FPT_V3="03"
  val FPT_V4="04"
  class FPTParseException(message:String,cause:Throwable) extends RuntimeException(message,cause){
    def this(message:String){
      this(message,null)
    }
  }

  /**
   * parse fpt file from InputStream
   * @param stream InputStream
   * @param encoding charset
   * @return FPT3File or FPT4File
   */
  def parseFromInputStream(stream:InputStream, encoding: Charset = AncientConstants.UTF8_ENCODING): Either[FPT3File,FPT4File]= {
    val streamReader:StreamReader = stream
    val head = new FPTHead
    if(stream.available() < head.getDataSize){
      throw new FPTParseException("file length(%s) too small ".format(stream.available()))
    }
    try {
      streamReader.markReaderIndex()
      head.fromStreamReader(streamReader, encoding)
      streamReader.resetReaderIndex()

      if (head.flag != FPT_FLAG)
        throw new IllegalArgumentException("FPT expected.actual :[%s]".format(head.flag))
      head.majorVersion match {
        case FPT_V3 =>
          Left(new FPT3File().fromStreamReader(streamReader, encoding))
        case FPT_V4 =>
          Right(new FPT4File().fromStreamReader(streamReader, encoding))
        case other =>
          throw new UnsupportedOperationException(other + " unsupported,only 03 or 04.")
      }
    }catch{
      case e:AncientDataException=>
        throw e
      case NonFatal(e)=>
        throw new FPTParseException(e.toString,e)
    }
  }
  private[gfpt4lib] def readLogic[T <:AncientData : ClassTag](strNum:String,dataSource:StreamReader,encoding:Charset): Array[T]={
    val num = if(strNum == null || strNum.isEmpty) 0 else strNum.toInt
    val seq = 0 until num map {i =>
      val value = classTag[T].runtimeClass.newInstance().asInstanceOf[T].fromStreamReader(dataSource, encoding)
      value
    }
    seq.toArray
  }
  class FPTHead extends AncientData{
    @Length(3)
    var flag:String = _
    @Length(2)
    var majorVersion:String = _
    @Length(2)
    var minorVersion:String = _
  }


  trait DynamicFingerData extends AncientData{
    @IgnoreTransfer
    private var logicEnd: Byte = FPTFile.FS // GS
    protected def getFingerDataCountString:String
    protected def getFingerDataCount:Int={
      val sendFingerCount = getFingerDataCountString
      if (sendFingerCount != null && sendFingerCount.nonEmpty)
        sendFingerCount.toInt
      else
        0  //如果数据为空的时候,则返回0
        //throw new AncientDataException("sendFingerCount field is "+sendFingerCount)
    }
    override def getDataSize: Int = {
      var count = super.getDataSize
      if (getFingerDataCount == 0)
        count += 1
      count
    }


    /**
     * serialize to channel buffer
     * @param stream netty channel buffer
     */
    override def writeToStreamWriter[T](stream: T, encoding: Charset)(implicit converter: (T) => StreamWriter): T = {
      super.writeToStreamWriter(stream,encoding)
      if (getFingerDataCount == 0){
        val dataSink = converter(stream)
        dataSink.writeByte(logicEnd)
      }
      stream
    }

    override def fromStreamReader(dataSource: StreamReader, encoding: Charset = AncientConstants.UTF8_ENCODING): this.type = {
      super.fromStreamReader(dataSource, encoding)
      if (getFingerDataCount == 0) {
        logicEnd = dataSource.readByte()
      }
      this
    }
  }

  private def getArrayLength[T](array: Array[T]): String ={
    if(array != null){
      array.length.toString
    }else{
      "0"
    }
  }
  def buildFPT4File(fpt4File: FPT4File): FPT4File ={
    fpt4File.head.flag = "FPT"
    fpt4File.head.majorVersion = "04"
    fpt4File.head.minorVersion = "00"
    fpt4File.sendUnitSystemType ="1900" //1900 东方金指
    fpt4File.fileLength = ""
    //设置逻辑记录长度
    fpt4File.tpCount = getArrayLength(fpt4File.logic02Recs)
    fpt4File.lpCount = getArrayLength(fpt4File.logic03Recs)
    fpt4File.tl_ltCount = getArrayLength(fpt4File.logic04Recs)
    fpt4File.ttCount = getArrayLength(fpt4File.logic05Recs)
    fpt4File.llCount = getArrayLength(fpt4File.logic06Recs)
    fpt4File.lpRequestCount = getArrayLength(fpt4File.logic07Recs)
    fpt4File.tpRequestCount = getArrayLength(fpt4File.logic09Recs)
    fpt4File.ltCandidateCount =  getArrayLength(fpt4File.logic09Recs)
    fpt4File.tlCandidateCount = getArrayLength(fpt4File.logic10Recs)
    fpt4File.ttCandidateCount = getArrayLength(fpt4File.logic11Recs)
    fpt4File.llCandidateCount = getArrayLength(fpt4File.logic12Recs)
    fpt4File.customCandidateCount = getArrayLength(fpt4File.logic99Recs)

    fpt4File
  }

  def main(args:Array[String]): Unit ={
    val fis = new FileInputStream(new File(args(0)))
    try {
      val fpt = FPTFile.parseFromInputStream(fis,AncientConstants.GBK_ENCODING)
      fpt match{
        case Left(ftp3)=>
          val head = ftp3.head
        case Right(ftp4)=>
          val head = ftp4.head
      }
    }finally{
      IOUtils.closeQuietly(fis)
    }
  }
}

