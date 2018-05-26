package nirvana.hall.spark.internal

import java.io.File

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services._
import nirvana.hall.spark.services.SparkFunctions.{StreamEvent, reportError}

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

/**
  * Created by yuchen on 2018/4/19.
  */
class WSQFileProvider extends ImageProvider{

  private lazy val imageFileServer = SysProperties.getPropertyOption("fpt.file.server")
  /**
    * 请求图像数据
    *
    * @param parameter nirvana相关配置
    * @param message   KAFKA中的消息
    * @return 消息序列
    */
  override def requestImage(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, GAFISIMAGESTRUCT)] = {
    def fetchWSQFile(seq:Int):Seq[(StreamEvent, GAFISIMAGESTRUCT)] = {
      var arrayBuffer = ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]()
      try{
        if(!message.startsWith("http") && !"None".equals(imageFileServer.get)){
          val wsqFilePath =  message
          val wsqFileData = SparkFunctions.httpClient.download(imageFileServer.get + File.separator+ wsqFilePath)
          val personId = wsqFilePath.split("_")(0).split("/")(1)
          val fgp = getFingerPosition(wsqFilePath.split("_")(1).split("\\.")(0).toInt)
          val hasPerson = GafisPartitionRecordsDakuSaver.queryPersonById(personId)
          if (hasPerson.isEmpty) {
            val person = WSQPropertiesConverter.wsqInfoToPersonConvert(wsqFilePath)
            GafisPartitionRecordsDakuSaver.savePersonInfo(person)
          }
          val event = new StreamEvent(wsqFilePath,personId,FeatureType.FingerTemplate, fgp, "", "", "")
          val buffer= (event,wsqToGafisImage(wsqFileData))
          arrayBuffer += buffer
        }
      }catch{
        case e:CallRpcException=>
          if(seq == 4)  reportException(e)  else fetchWSQFile(seq+1)
        case NonFatal(e)=>
          reportException(e)
      }
      arrayBuffer
    }

    def reportException(e: Throwable) = {
      e.printStackTrace(System.err)
      val event = StreamEvent("", "",  FeatureType.FingerTemplate, FingerPosition.FINGER_UNDET,"","","")
      reportError(parameter, RequestRemoteFileError(event, e.toString))
      null
    }
    fetchWSQFile(1)
  }


  private def wsqToGafisImage(image:Array[Byte]): GAFISIMAGESTRUCT ={
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 1.toByte
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
    gafisImg.bnData = image
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg
  }



  /**
    * 请求数据完整信息
    *
    * @param parameter
    * @param message
    * @return
    */
  override def requestData(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = ???

  /**
    * 请求图像数据为BMP,不需要解压缩，获取数据直接提特征并入库
    *
    * @param parameter
    * @param message
    * @return
    */
  override def requestImageByBMP(parameter: NirvanaSparkConfig, message: String): Option[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = ???
}
