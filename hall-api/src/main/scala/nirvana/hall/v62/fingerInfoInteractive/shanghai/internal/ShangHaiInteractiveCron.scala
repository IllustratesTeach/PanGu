package nirvana.hall.v62.fingerInfoInteractive.shanghai.internal

import java.io.File

import com.google.protobuf.ByteString
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.fpt.FPTFileHandler
import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gfpt4lib.FPT4File
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic02Rec
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.api.FPTProto.TPCard.Builder
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, ImageType, TPCard}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.v62.fingerInfoInteractive.shanghai.services.DataService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.slf4j.LoggerFactory

/**
  * Created by ssj on 2017/03/09.
  */
class ShangHaiInteractiveCron(hallApiConfig: HallApiConfig
                              ,dataService:DataService
                             , tPCardService: TPCardService) {

  val logger = LoggerFactory getLogger getClass

  /**
    * 定时器
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    periodicExecutor.addJob(new CronSchedule("0/10 * * * * ?"), "sync-cron", new Runnable {
      override def run(): Unit = {
        try {
          doWork
        } catch {
          case e: Exception => logger.error("input to appserver fail: " + e.getMessage + e.getStackTrace)
        }
      }
    })
  }
  /**
    * 定时任务调用方法
    */
  def doWork(): Unit = {
    //加载jni
    loadJni()

    logger.info("shanghai renkou sync start")
    var result = new scala.collection.mutable.HashMap[String,Any]
    result= dataService.getDataInfo(1)
    if (result.size<=0) {
      logger.info("shanghai renkou sync end")
      return
    }
    try{
      tPCardService.addTPCard(convertToProtobuf(result))
      logger.info("info appserver success ")
    }catch{
        case e:Exception => logger.error("info appserver error: " + e.getMessage + e.getStackTrace)
        dataService.insertCard(result.get("JMSFZSLH").get.toString,1)
        return
    }
    val res= dataService.insertCard(result.get("JMSFZSLH").get.toString,0)
    //判断插入6.2是否成功
    if(res == 1 ){
      logger.info("savecompile card: " + result.get("JMSFZSLH").get.toString)
    }
  }


  /**
    *  转换为protobuf
    *
    */
  private def convertToProtobuf (result : scala.collection.mutable.HashMap[String,Any]): TPCard ={
    val logic02Rec = new Logic02Rec
    val tpCard = TPCard.newBuilder()
    val textBuilder = tpCard.getTextBuilder
    tpCard.setStrCardID(result.get("JMSFZSLH").get.toString)
    textBuilder.setStrName(result.get("XM").get.toString)
    textBuilder.setStrAliasName(IsEmptyChar(logic02Rec.alias))
    textBuilder.setNSex(0)
    textBuilder.setStrBirthDate(IsEmptyChar(logic02Rec.birthday))
    textBuilder.setStrIdentityNum(result.get("GMSFHM").get.toString)
    textBuilder.setStrBirthAddrCode(IsEmptyChar(logic02Rec.addressDetail))
    textBuilder.setStrBirthAddr(IsEmptyChar(logic02Rec.address))
    textBuilder.setStrAddrCode(IsEmptyChar(logic02Rec.doorDetail))
    textBuilder.setStrAddr(IsEmptyChar(logic02Rec.door))
    textBuilder.setStrPersonType(IsEmptyChar(logic02Rec.category))
    textBuilder.setStrCaseType1(IsEmptyChar(logic02Rec.caseClass1Code))
    textBuilder.setStrCaseType2(IsEmptyChar(logic02Rec.caseClass2Code))
    textBuilder.setStrCaseType3(IsEmptyChar(logic02Rec.caseClass3Code))
    textBuilder.setStrPrintUnitCode(result.get("CJDW_GAJGJGDM").get.toString)
    textBuilder.setStrPrintUnitName(result.get("CJDW_GAJGMC").get.toString)
    textBuilder.setStrPrinter(result.get("CJR_XM").get.toString)
    textBuilder.setStrPrintDate(IsEmptyChar(logic02Rec.gatherDate))
    textBuilder.setStrComment(IsEmptyChar(logic02Rec.remark))
    textBuilder.setStrNation(IsEmptyChar(logic02Rec.nation))
    textBuilder.setStrRace(IsEmptyChar(logic02Rec.nativeplace))
    textBuilder.setStrCertifType(IsEmptyChar(logic02Rec.certificateType))
    textBuilder.setStrCertifID(IsEmptyChar(logic02Rec.certificateNo))
    textBuilder.setBHasCriminalRecord(if (logic02Rec.isCriminal == 0) {
      logic02Rec.isCriminal == false
    } else {
      logic02Rec.isCriminal == true
    })
    textBuilder.setStrCriminalRecordDesc(IsEmptyChar(logic02Rec.criminalInfo))
    textBuilder.setStrPremium(IsEmptyChar(logic02Rec.assistUnitName))
    textBuilder.setNXieChaFlag(0)
    textBuilder.setStrXieChaRequestUnitName(IsEmptyChar(logic02Rec.assistUnitName))
    textBuilder.setStrXieChaRequestUnitCode(IsEmptyChar(logic02Rec.assistUnitCode))
    textBuilder.setNXieChaLevel(0)
    textBuilder.setStrXieChaForWhat(IsEmptyChar(logic02Rec.assistPurpose))
    textBuilder.setStrRelPersonNo(IsEmptyChar(logic02Rec.relatedPersonId))
    textBuilder.setStrRelCaseNo(IsEmptyChar(logic02Rec.relatedCaseId))
    textBuilder.setStrXieChaTimeLimit(IsEmptyChar(logic02Rec.assistTimeLimit))
    textBuilder.setStrXieChaDate(IsEmptyChar(logic02Rec.assistDate))
    textBuilder.setStrXieChaRequestComment(IsEmptyChar(logic02Rec.assistAskingInfo))
    textBuilder.setStrXieChaContacter(IsEmptyChar(logic02Rec.contact))
    textBuilder.setStrXieChaTelNo(IsEmptyChar(logic02Rec.contactPhone))
    textBuilder.setStrShenPiBy(IsEmptyChar(logic02Rec.approver))

    imgHandler(result.get("ZWY_ZWTXSJ"),result.get("ZWY_ZWDM"),tpCard)
    imgHandler(result.get("ZWE_ZWTXSJ"),result.get("ZWE_ZWDM"),tpCard)

    tpCard.build()
  }

  /**
    * 图像服务
    *
    * @param optionXT
    * @param optionZW
    * @param tPCard
    */
  private def imgHandler(optionXT: Option[Any],optionZW:Option[Any],tPCard:Builder): Unit ={
    val gafisImg = new GAFISIMAGESTRUCT
    val cprData = optionXT.get.asInstanceOf[Array[Byte]]

    if(cprData.length< 22) {
      return
    }else{
      val cprDataSub = new Array[Byte](cprData.length)
      Array.copy(cprData, 22, cprDataSub, 0, cprData.length - 22)


      val fdata = new FPT4File.FingerTData
      fdata.imgData = cprDataSub
      fdata.imgHorizontalLength = "640"
      fdata.imgVerticalLength = "640"
      fdata.dpi = "500"
      fdata.fgp = "1"
      fdata.imgCompressMethod = "1419"
      val tBuffer = FPTFileHandler.fingerDataToGafisImage(fdata)

      //图像解压   //http://192.168.1.214:9001
      val s = FPTFileHandler.callHallImageDecompressionImage("hallApiConfig.hallImageUrl",tBuffer)
      //提取特征
      val mntAndBin = FPTFileHandler.extractorFeature(s, fgpParesEnum(optionZW.get.toString), FeatureType.FingerTemplate)
      val blobBuilder = tPCard.addBlobBuilder()
      blobBuilder.setStMntBytes(ByteString.copyFrom(mntAndBin.get._1.toByteArray()))
      blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
      blobBuilder.setFgp(fgpParesProtoBuffer(optionZW.get.toString))
      blobBuilder.setStImageBytes(ByteString.copyFrom(tBuffer.toByteArray()))
      blobBuilder.setStBinBytes(ByteString.copyFrom(mntAndBin.get._2.toByteArray()))
    }
  }

  /**
    * 将解析出的指位翻译成系统中的枚举类型
    */
  def fgpParesEnum(fgp:String): FingerPosition ={
    fgp match {
      case "11" =>
        FingerPosition.FINGER_R_THUMB
      case "12" =>
        FingerPosition.FINGER_R_INDEX
      case "13" =>
        FingerPosition.FINGER_R_MIDDLE
      case "14" =>
        FingerPosition.FINGER_R_RING
      case "15" =>
        FingerPosition.FINGER_R_LITTLE
      case "16" =>
        FingerPosition.FINGER_L_THUMB
      case "17" =>
        FingerPosition.FINGER_L_INDEX
      case "18" =>
        FingerPosition.FINGER_L_MIDDLE
      case "19" =>
        FingerPosition.FINGER_L_RING
      case "20" =>
        FingerPosition.FINGER_L_LITTLE
      case other =>
        FingerPosition.FINGER_UNDET
    }
  }

  /**
    * 将解析出的指位翻译成系统中的枚举类型,ProtoBuffer
    */
  def fgpParesProtoBuffer(fgp:String): FingerFgp ={
    fgp match {
      case "11" =>
        FingerFgp.FINGER_R_THUMB
      case "12" =>
        FingerFgp.FINGER_R_INDEX
      case "13" =>
        FingerFgp.FINGER_R_MIDDLE
      case "14" =>
        FingerFgp.FINGER_R_RING
      case "15" =>
        FingerFgp.FINGER_R_LITTLE
      case "16" =>
        FingerFgp.FINGER_L_THUMB
      case "17" =>
        FingerFgp.FINGER_L_INDEX
      case "18" =>
        FingerFgp.FINGER_L_MIDDLE
      case "19" =>
        FingerFgp.FINGER_L_RING
      case "20" =>
        FingerFgp.FINGER_L_LITTLE
      case other =>
        FingerFgp.FINGER_UNDET
    }
  }

  /**
    * 判断String 是否为空
    * 为空则付给 一个string
    */
  def IsEmptyChar(fgp:String): String = {
    val ss = ""
    fgp match {
      case null =>
        ss
      case other =>
        fgp
    }
    ss
  }
  //加载jni
  def loadJni() {
    val file = new File("support")
    if (file.exists()){
      nirvana.hall.extractor.jni.JniLoader.loadJniLibrary("support", "stderr")
      nirvana.hall.image.jni.JniLoader.loadJniLibrary("support", "stderr")
    }
    else{
      nirvana.hall.extractor.jni.JniLoader.loadJniLibrary(".", "stderr")
      nirvana.hall.image.jni.JniLoader.loadJniLibrary(".", "stderr")
    }
  }
}
