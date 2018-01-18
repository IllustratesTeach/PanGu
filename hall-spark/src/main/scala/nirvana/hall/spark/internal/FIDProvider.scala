package nirvana.hall.spark.internal

import java.io.ByteArrayInputStream

import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.{PersonConvert, TemplateFingerConvert}
import nirvana.hall.spark.services.SparkFunctions.{StreamEvent, _}
import nirvana.hall.spark.services._

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

/**
  * Created by wangjue on 2017/12/23.
  */
class FIDProvider extends ImageProvider{
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
  override def requestImageByBMP(parameter: NirvanaSparkConfig, message: String): Option[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = null

  /**
    * FID处理入库
    *
    * @param parameter
    * @param message
    * @return
    */
  override def requestData(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = {

    def fetchBMP(seq:Int) : Seq[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = {
      try {
        var path = message
        if (!message.startsWith("http")) path = imageFileServer.get + message
        val event = new StreamEvent(message,message, FeatureType.PalmTemplate, getFingerPosition(1),"","","")
        var seq = ArrayBuffer[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]()
        /**
          * 文件名为人员编号的情况，下载解析前先判断是否入库，已入库则不在下载解析数据
          */
        val fileName = path.substring(path.lastIndexOf("/")+1,path.lastIndexOf("."))
        val listPalms : List[Array[Int]] = GafisPartitionRecordsBjsjSave.queryPalmByPersonId(fileName)
        if (listPalms.size >= 4) return seq

        val data = SparkFunctions.httpClient.download(path)
        val fid = FIDFile.parseFromInputStream(new ByteArrayInputStream(data))

        if (fid.palms.length > 0) {
          val personId = fid.personNo
          /*FID 是否存在左右全掌信息*/
          val hasFullPalm = fid.palms.filter(t => "1".equals(t.fgp) || "2".equals(t.fgp))
          if (hasFullPalm.length == 0) {
            reportError(parameter, RequestRemoteFileError(event, "has not full palm!"))
            return seq
          }
          /* 根据人员编号判断掌纹图像和特征是否已经入库 */
          val list : List[Array[Int]] = GafisPartitionRecordsBjsjSave.queryPalmByPersonId(personId)
          fid.palms.foreach{ t =>
            if ("1".equals(t.fgp) || "2".equals(t.fgp)) {
              val gafisImgByteArray = fpt4code.FPTFingerDataToGafisImage(t).toByteArray()
              val fgp = t.fgp.toInt+10
              val hasPerson = GafisPartitionRecordsBjsjSave.queryPersonById(personId)
              if (hasPerson.isEmpty) {
                val person = new PersonConvert
                person.personId = personId
                person.fptPath = path
                try {
                  GafisPartitionRecordsDakuSaver.savePersonInfo(person)
                } catch {
                    case e: Throwable =>
                      if (e.toString.indexOf("PK_GAFIS_PERSON_PERSONID") == -1)
                        throw e
                  }
              }
              val imageIsExists = list.exists(x=> x(0) == fgp && x(1) == 1)
              val mntIsExists = list.exists(x=> x(0) == fgp && x(1) == 0)
              if (imageIsExists && mntIsExists) return seq
              /* 提取特征 */
              val feature = new GAFISIMAGESTRUCT
              if (!mntIsExists) {
                val mnt = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(gafisImgByteArray), FingerPosition.FINGER_R_THUMB, FeatureType.PalmTemplate).get._1
                feature.fromByteArray(mnt)
                fgp match {
                  case 11 => feature.stHead.szName = "PalmRMnt"
                  case 12 => feature.stHead.szName = "PalmLMnt"
                }
              }

              /* 压缩图像 */
              var wsq = new GAFISIMAGESTRUCT
              if (!imageIsExists) {
                val gafisImg = extractor.readByteArrayAsGAFISIMAGE(new ByteArrayInputStream(gafisImgByteArray))
                gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
                wsq = encoder.encodeWSQ(gafisImg)
              }

              /* 保存图像所需其它项 */
              val template = new TemplateFingerConvert
              template.personId = personId
              template.gatherData = wsq.toByteArray()
              template.groupId = FingerConstants.GROUP_IMAGE
              template.lobType = FingerConstants.LOBTYPE_DATA
              template.fgp = fgp.toString
              template.path = path

              val result = (event,template,feature,new GAFISIMAGESTRUCT)
              seq += result
            }
          }
        }
        seq
      } catch{
        case e:CallRpcException=>
          //try 4 times to fetch fpt file
          if(seq == 4)  reportException(e)  else fetchBMP(seq+1)
        case NonFatal(e)=>
          reportException(e)
      }
    }


    def reportException(e: Throwable) = {
      //e.printStackTrace(System.err)
      val event = StreamEvent(message, "",  FeatureType.FingerTemplate, FingerPosition.FINGER_UNDET,"","","")
      reportError(parameter, RequestRemoteFileError(event, e.toString))
      ArrayBuffer[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]()
    }
    fetchBMP(1)
  }

}
