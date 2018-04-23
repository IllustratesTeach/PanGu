package nirvana.hall.spark.internal

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.{ImageProvider, SparkFunctions, WSQPropertiesConverter}
import nirvana.hall.spark.services.SparkFunctions.StreamEvent

import scala.collection.mutable.ArrayBuffer

/**
  * Created by yuchen on 2018/4/19.
  */
class WSQFileProvider extends ImageProvider{


  val REGEX = s"^[R][0-9]+(_)(\\d{2})(.wsq)"
  /**
    * 请求图像数据
    *
    * @param parameter nirvana相关配置
    * @param message   KAFKA中的消息
    * @return 消息序列
    */
  override def requestImage(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, GAFISIMAGESTRUCT)] = {
    def fetchWSQFile():Seq[(StreamEvent, GAFISIMAGESTRUCT)] = {
      var arrayBuffer = ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]()
      if(message.matches(REGEX)){
        val wsqFilePath = message
        val wsqFileData = SparkFunctions.httpClient.download(wsqFilePath)
        val personId = wsqFilePath.split("_")(0)
        val fgp = getFingerPosition(wsqFilePath.split("_")(1).toInt)
        val hasPerson = GafisPartitionRecordsDakuSaver.queryPersonById(personId)
        if (hasPerson.isEmpty) {
          val person = WSQPropertiesConverter.wsqInfoToPersonConvert(wsqFilePath)
          GafisPartitionRecordsDakuSaver.savePersonInfo(person)
        }
        val event = new StreamEvent(wsqFilePath,personId,FeatureType.FingerTemplate, fgp, "", "", "")
        val buffer= (event,wsqToGafisImage(wsqFileData))
        arrayBuffer += buffer
      }else{
        throw new UnsupportedOperationException("fileName is error: " + message)
      }
      arrayBuffer
    }
    fetchWSQFile
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
