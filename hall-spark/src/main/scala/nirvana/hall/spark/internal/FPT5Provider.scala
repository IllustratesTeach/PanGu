package nirvana.hall.spark.internal

import java.io.File

import nirvana.hall.c.services.gfpt5lib.{FingerMsg, fpt5util}
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.{FPT5MntConverter}
import nirvana.hall.image.internal.{FPT5ImageConverter}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services._
import nirvana.hall.spark.services.SparkFunctions.{StreamEvent, _}
import org.apache.commons.io.{FileUtils, IOUtils}

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

/**
  * Created by wangjue on 2018/1/5.
  */
class FPT5Provider extends ImageProvider{
  private lazy val imageFileServer = SysProperties.getPropertyOption("fpt.file.server")

  /**
    * 请求图像数据
    *
    * @param parameter nirvana相关配置
    * @param message   KAFKA中的消息
    * @return 消息序列
    */
  override def requestImage(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, GAFISIMAGESTRUCT)] = {
    def fetch(seq:Int) : Seq[(StreamEvent ,GAFISIMAGESTRUCT)] = {
      var path = message
      if (!message.startsWith("http") && !"None".equals(imageFileServer.get)) path = imageFileServer.get + message
      var buffer = ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]()
      val filePath : String = path
      var personId: String = null
      var caseId : String = null
      var seqNo : String = null
      var cardId : String = null
      var event = StreamEvent(filePath, personId,  FeatureType.FingerTemplate, FingerPosition.FINGER_UNDET,caseId,seqNo,cardId)
      try {
        /*val data = SparkFunctions.httpClient.download(filePath)
        val fpt5= fpt5util.parseFPT5(IOUtils.toString(data,"UTF-8"))*/
        var data : Array[Byte] = Array.emptyByteArray
        if ("None".equals(imageFileServer.get)) data = SparkFunctions.unzipByArray(FileUtils.readFileToByteArray(new File(filePath)))
        else data = SparkFunctions.unzipByURL(filePath)

        val fpt5= fpt5util.parseFPT5(IOUtils.toString(data,"UTF-8"))
        if (fpt5.fingerprintPackage != null) {
          fpt5.fingerprintPackage.foreach{ tp =>
            val descriptiveMsg = tp.descriptiveMsg
            val jzbh = descriptiveMsg.jingZongPersonId
            personId = descriptiveMsg.fingerPalmCardId
            assert(personId != null, "person id is null")
            event = StreamEvent(filePath, personId,  FeatureType.FingerTemplate, FingerPosition.FINGER_UNDET,caseId,seqNo,cardId)
            val hasPerson = GafisPartitionRecordsFullSaver.queryPersonById(personId)
            if (hasPerson.isEmpty) {
              //TODO 人员完整信息入库待完善
              val person = FptPropertiesConverter.fpt5ToPersonConvert(tp ,path)
              GafisPartitionRecordsFullSaver.saveFullPersonInfo(person)
              if (person.portrait.personId!=null)
                GafisPartitionRecordsFullSaver.savePortrait(person.portrait)
            }
            val list = GafisPartitionRecordsFullSaver.queryFingerFgpAndFgpCaseByPersonId(personId)
            assert(tp.fingers != null, "tp.fingers is null")
            tp.fingers.fingerMsg.foreach{ t =>
              if (t.fingerImageData != null && t.fingerImageData.length > 0) {
                val tBuffer = createImageEvent(path, personId, t, list)
                if (tBuffer != null)
                  buffer += tBuffer
              }
            }
          }
        } else if (fpt5.latentPackage != null) {
          //TODO 案件现场完整信息入库待完善
          SparkFunctions.loadExtractorJNI()
          fpt5.latentPackage.foreach{ lp =>
            val caseMsg = lp.caseMsg
            val ysxt_asjbh = caseMsg.originalSystemCaseId   //原始系统_案事件编号
            val xckybh = caseMsg.latentSurveyId             //现场勘验编号
            val asjbh = caseMsg.caseId                      //现勘案事件编号
            caseId = caseMsg.latentCardId                   //现场指掌纹卡编号
            val latentCase = FptPropertiesConverter.fpt5ToLatentCaseConvert(lp)
            lp.latentFingers.foreach{ l =>
                val gafisImage = FPT5ImageConverter.convertLPFingerImageData2GafisImage(l.latentFingerImageMsg)
                val xcwzbh = l.latentFingerImageMsg.latentPhysicalId                    //现场物证编号
                cardId = l.latentFingerImageMsg.originalSystemLatentFingerPalmId        //现场指掌纹编号
                assert(!xcwzbh.isEmpty && !cardId.isEmpty ,"lp.latentPhysicalId and originalSystemLatentFingerPalmId is null")
                if (!cardId.isEmpty) seqNo = cardId.substring(cardId.length-2)
                else {
                  seqNo = xcwzbh.substring(xcwzbh.length-2)
                  cardId = caseId + seqNo
                }
                event = StreamEvent(filePath, personId,  FeatureType.FingerTemplate, FingerPosition.FINGER_UNDET,caseId,seqNo,cardId)
                val latentFinger = FptPropertiesConverter.fpt5ToLatentFingerConvert(l.latentFingerImageMsg,path,caseId,cardId,seqNo)
                latentFinger.imgData = gafisImage.toByteArray()
                l.latentFingerFeatureMsg.foreach{ f =>
                  val gafisMnt = FPT5MntConverter.convertFingerLDataMnt2GafisMnt(l.latentFingerImageMsg,f)
                  val latentFingerFeature = FptPropertiesConverter.fptToLatentFingerFeatureConvert(cardId,gafisMnt.toByteArray(),f.latentFeatureExtractMethodCode)
                  latentFinger.LatentFingerFeatures =  latentFingerFeature :: Nil
                }
                if (latentCase.latentFingers == null)
                  latentCase.latentFingers = latentFinger :: Nil
                else
                  latentCase.latentFingers = latentCase.latentFingers ::: latentFinger :: Nil
              }
            GafisPartitionRecordsFullSaver.saveLatent(latentCase)
          }
        } else {
          reportError(parameter, RequestRemoteFileError(event, " ,fingerprintPackage and latentPackage is null!"))
        }
        buffer
      } catch{
        case e:CallRpcException=>
          //try 4 times to fetch fpt file
          if(seq == 4)  reportException(e ,event)  else fetch(seq+1)
        case NonFatal(e)=>
          reportException(e ,event)
      }
    }

    def reportException(e: Throwable ,event : StreamEvent) = {
      e.printStackTrace(System.err)
      reportError(parameter, RequestRemoteFileError(event, e.toString))
      ArrayBuffer[(StreamEvent ,GAFISIMAGESTRUCT)]()
    }
    fetch(1)
  }



  def createImageEvent(filePath: String, personId: String, fingerMsg: FingerMsg, list : List[Array[Int]]): (StreamEvent ,GAFISIMAGESTRUCT) = {
    val gafisImg = FPT5ImageConverter.convertTPFingerImageData2GafisImage(fingerMsg)
    val fgp = getFingerPosition(fingerMsg.fingerPositionCode.toInt)
    val fgpCase = gafisImg.stHead.bIsPlain
    val isExists = list.exists(x=> x(0) == fgp.getNumber && x(1) == fgpCase)
    val event = new StreamEvent(filePath, personId, FeatureType.FingerTemplate, fgp, "", "", "")
    if (!isExists) {
      (event, gafisImg)
    } else (event, new GAFISIMAGESTRUCT)
  }

  /**
    * 请求图像数据为BMP,不需要解压缩，获取数据直接提特征并入库
    *
    * @param parameter
    * @param message
    * @return
    */
  override def requestImageByBMP(parameter: NirvanaSparkConfig, message: String): Option[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = null

  /**
    * 请求数据,跳过图像解压和提特征服务类
    *
    * @param parameter
    * @param message
    * @return
    */
  override def requestData(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = null

}
