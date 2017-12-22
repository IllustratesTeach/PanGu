package nirvana.hall.spark.internal

import java.io.ByteArrayInputStream

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.{PersonConvert, TemplateFingerConvert}
import nirvana.hall.spark.services._
import nirvana.hall.spark.services.SparkFunctions._

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

/**
  * Created by wangjue on 2017/12/14.
  */
class BjsjPalmBmpProvider extends ImageProvider{
  private lazy val imageFileServer = SysProperties.getPropertyOption("fpt.file.server")
  private lazy val extractor = new FeatureExtractorImpl
  private lazy val decoder = new FirmDecoderImpl("support",null)
  private lazy val encoder = new ImageEncoderImpl(decoder)
  /**
    * 请求图像数据
    *
    * @param parameter nirvana相关配置
    * @param message   KAFKA中的消息
    * @return 消息序列
    */
  override def requestImage(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, GAFISIMAGESTRUCT)] = {ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]()}

  /**
    * 请求图像数据为BMP,不需要解压缩，获取数据直接提特征并入库
    *
    * @param parameter
    * @param message
    * @return
    */
  override def requestImageByBMP(parameter: NirvanaSparkConfig, message: String): Option[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = {
    SparkFunctions.loadExtractorJNI()
    SparkFunctions.loadImageJNI()
    def fetchBMP(seq:Int) : Option[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = {
      try {
        var path = message
        if (!message.startsWith("http")) path = imageFileServer.get + message
        //   guess  /bmp/P1212121212221212121_11.bmp
        /* 根据文件名称获取人员编号和掌位 */
        val personIdAndFgp = message.substring(message.lastIndexOf("/")+1,message.lastIndexOf(".")).split("_")
        val personId = personIdAndFgp(0)
        val fgp = personIdAndFgp(1)
        val hasPerson = GafisPartitionRecordsBjsjSave.queryPersonById(personId)
        if (hasPerson.isEmpty) {
          val person = new PersonConvert
          person.personId = personId
          person.fptPath = path
          GafisPartitionRecordsDakuSaver.savePersonInfo(person)
        }
        val event = new StreamEvent(message,message, FeatureType.PalmTemplate, getFingerPosition(1),"","","")
        /* 根据人员编号判断掌纹图像和特征是否已经入库 */
        val list : List[Array[Int]] = GafisPartitionRecordsBjsjSave.queryPalmByPersonId(personId)
        val imageIsExists = list.exists(x=> x(0) == fgp.toInt && x(1) == 1)
        val mntIsExists = list.exists(x=> x(0) == fgp.toInt && x(1) == 0)
        if (imageIsExists && mntIsExists) return Some(event,new TemplateFingerConvert,new GAFISIMAGESTRUCT,new GAFISIMAGESTRUCT)

        val data = SparkFunctions.httpClient.download(path)

        /* 提取特征 */
        val feature = new GAFISIMAGESTRUCT
        if (!mntIsExists) {
          val mnt = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(data), FingerPosition.FINGER_R_THUMB, FeatureType.PalmTemplate).get._1
          feature.fromByteArray(mnt)
        }

        /* 压缩图像 */
        var wsq = new GAFISIMAGESTRUCT
        if (!imageIsExists) {
          val gafisImg = extractor.readByteArrayAsGAFISIMAGE(new ByteArrayInputStream(data))
          gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
          wsq = encoder.encodeWSQ(gafisImg)
        }

        /* 保存图像所需其它项 */
        val template = new TemplateFingerConvert
        template.personId = personId
        template.gatherData = wsq.toByteArray()
        template.groupId = FingerConstants.GROUP_IMAGE
        template.lobType = FingerConstants.LOBTYPE_DATA
        template.fgp = fgp
        template.path = path

        Some(event,template,feature,new GAFISIMAGESTRUCT)
      } catch{
          case e:CallRpcException=>
            //try 4 times to fetch fpt file
            if(seq == 4)  reportException(e)  else fetchBMP(seq+1)
          case NonFatal(e)=>
            reportException(e)
        }
    }
    def reportException(e: Throwable) = {
      e.printStackTrace(System.err)
      val event = StreamEvent("", "",  FeatureType.FingerTemplate, FingerPosition.FINGER_UNDET,"","","")
      reportError(parameter, RequestRemoteFileError(event, e.toString))
      null
    }
    fetchBMP(1)
  }

}
