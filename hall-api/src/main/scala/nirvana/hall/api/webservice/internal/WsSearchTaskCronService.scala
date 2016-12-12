package nirvana.hall.api.webservice.internal


import javax.sql.DataSource

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.services.sync.FetchQueryService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, QueryService, TPCardService}
import nirvana.hall.api.webservice.services.WsSearchTaskService
import nirvana.hall.api.webservice.util.FPTFileHandler
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, ImageType, TPCard}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient

/**
  * 互查系统定时任务
  */
class WsSearchTaskCronService(hallApiConfig: HallApiConfig,
                              tPCardService: TPCardService,
                              lPCardService: LPCardService,
                              caseInfoService: CaseInfoService,
                              queryService: QueryService,
                              fetchQueryService: FetchQueryService,
                              implicit val dataSource: DataSource) extends LoggerSupport{

  val cronSchedule = "0 0/5 * * * ? *" //TODO 可配置
  /**
    * 定时器，获取比对任务
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
      periodicExecutor.addJob(new CronSchedule(cronSchedule), "SearchTaskWsService-cron", new Runnable {
        override def run(): Unit = {
          //发送请求获取任务
          val url = "http://127.0.0.1:8080/ws/WsSearchTaskService?wsdl"
          val targetNamespace = "http://www.egfit.cn/"
          val userid = ""
          val password = ""
          var taskControlID = ""
          val searchTaskService = StarkWebServiceClient.createClient(classOf[WsSearchTaskService], url, targetNamespace)
          val taskDataHandler = searchTaskService.getSearchTask(userid, password)

            try{

              val taskFpt = FPTFile.parseFromInputStream(taskDataHandler.getInputStream)

              //解析比对任务FPT
              taskControlID = taskFpt.right.get.sid


              taskFpt match {
                case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
                case Right(fpt4) =>
                  if(fpt4.logic02Recs.length>0){
                    if(fpt4.logic02Recs.foreach(tp => tp.head.dataType) == "02"){
                      var tPCard:TPCard = null
                      tPCard = TPFPT2ProtoBuffer(fpt4)
                      tPCardService.addTPCard(tPCard)
                      //TODO 发查询
                      queryService.addMatchTask(null)
                    }
                  }else if(fpt4.logic03Recs.length>0){
                    if(fpt4.logic03Recs.foreach(tp => tp.head.dataType) == "03"){
                      // TODO 现场处理
                    }
                  }else{
                      throw new Exception("接收FPT逻辑描述记录类型不正确")
                  }
              }
              try{
                val flag:Int = searchTaskService.setSearchTaskStatus(userid, password, taskControlID, true)
//                if(flag!=1){
//                  error("call setSearchTaskStatus faild!inputParam:"+ true + " returnVal:" + flag)
//                }
              }catch{
                case e:Exception => error("setSearchTaskStatus-error:" + e.getMessage)
              }
          }catch{
              case e:Exception=> error("WsSearchTaskCronService-error:" + e.getMessage)
                try{
                  val flag:Int = searchTaskService.setSearchTaskStatus(userid, password, taskControlID, false)
//                  if(flag!=1){
//                    case e:Exception=> error("call setSearchTaskStatus faild!inputParam:"+ false + " returnVal:" + flag)
//                  }
                }catch{
                  case e:Exception=> error("setSearchTaskStatus-error:" + e.getMessage)
                }
          }
        }
      })
  }





  /**
    * 根据接口获得的FPT文件,构建TPCard的ProtoBuffer的对象
      * 在构建过程中,需要解压图片获得原图，提取特征
    * @param fpt4
    * @return
    */
  def TPFPT2ProtoBuffer(fpt4:FPT4File):TPCard ={
    val tpCard = TPCard.newBuilder()
    val textBuilder = tpCard.getTextBuilder
        tpCard.setStrCardID(fpt4.sid)
    fpt4.logic02Recs.foreach { tp =>
      textBuilder.setStrName(tp.personName)
      textBuilder.setStrAliasName (tp.alias)
      textBuilder.setNSex(tp.gender.toInt)
      textBuilder.setStrBirthDate (tp.birthday)
      textBuilder.setStrIdentityNum (tp.idCardNo)
      textBuilder.setStrBirthAddrCode (tp.addressDetail)
      textBuilder.setStrBirthAddr (tp.address)
      textBuilder.setStrAddrCode (tp.doorDetail)
      textBuilder.setStrAddr (tp.door)
      textBuilder.setStrPersonType (tp.category)
      textBuilder.setStrCaseType1 (tp.caseClass1Code)
      textBuilder.setStrCaseType2 (tp.caseClass2Code)
      textBuilder.setStrCaseType3 (tp.caseClass3Code)
      textBuilder.setStrPrintUnitCode (tp.gatherUnitCode)
      textBuilder.setStrPrintUnitName (tp.gatherName)
      textBuilder.setStrPrinter (tp.gatherName)
      textBuilder.setStrPrintDate (tp.gatherDate)
      textBuilder.setStrComment (tp.remark)
      textBuilder.setStrNation (tp.nation)
      textBuilder.setStrRace (tp.nativeplace)
      textBuilder.setStrCertifType (tp.certificateType)
      textBuilder.setStrCertifID (tp.certificateNo)
      textBuilder.setBHasCriminalRecord (tp.isCriminal.toBoolean)
      textBuilder.setStrCriminalRecordDesc (tp.criminalInfo)
      textBuilder.setStrPremium (tp.assistUnitName)
      textBuilder.setNXieChaFlag (tp.isAssist.toInt)
      textBuilder.setStrXieChaRequestUnitName (tp.assistUnitName)
      textBuilder.setStrXieChaRequestUnitCode (tp.assistUnitCode)
      textBuilder.setNXieChaLevel (tp.assistLevel.toInt)
      textBuilder.setStrXieChaForWhat (tp.assistPurpose)
      textBuilder.setStrRelPersonNo (tp.relatedPersonId)
      textBuilder.setStrRelCaseNo (tp.relatedCaseId)
      textBuilder.setStrXieChaTimeLimit (tp.assistTimeLimit)
      textBuilder.setStrXieChaDate (tp.assistDate)
      textBuilder.setStrXieChaRequestComment (tp.assistAskingInfo)
      textBuilder.setStrXieChaContacter (tp.contact)
      textBuilder.setStrXieChaTelNo (tp.contactPhone)
      textBuilder.setStrShenPiBy (tp.approver)


      tp.fingers.foreach { tData =>
        if (tData.imgData != null && tData.imgData.length > 0) {
          val tBuffer = FPTFileHandler.fingerDataToGafisImage(tData)
          //图像解压
          val s = FPTFileHandler.callHallImageDecompressionImage(tBuffer)
          //提取特征
          val mnt = FPTFileHandler.extractorFeature(s,fgpParesEnum(tData.fgp),ParseFeatureTypeEnum(fpt4.tpCount.toInt,fpt4.lpCount.toInt))
          val blobBuilder = tpCard.addBlobBuilder()
          mnt.foreach { blob =>
            blobBuilder.setStMntBytes(ByteString.copyFrom(blob.toByteArray()))
            blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
            blobBuilder.setFgp(fgpParesProtoBuffer(tData.fgp))
          }
        }
      }
      textBuilder.build()
    }
    tpCard.build()
  }

  /**
    * 将解析出的指位翻译成系统中的枚举类型
    */
  def fgpParesEnum(fgp:String): FingerPosition ={
    fgp match {
      case "01" =>
        FingerPosition.FINGER_R_THUMB
      case "02" =>
        FingerPosition.FINGER_R_INDEX
      case "03" =>
        FingerPosition.FINGER_R_MIDDLE
      case "04" =>
        FingerPosition.FINGER_R_RING
      case "05" =>
        FingerPosition.FINGER_R_LITTLE
      case "06" =>
        FingerPosition.FINGER_L_THUMB
      case "07" =>
        FingerPosition.FINGER_L_INDEX
      case "08" =>
        FingerPosition.FINGER_L_MIDDLE
      case "09" =>
        FingerPosition.FINGER_L_RING
      case "10" =>
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
      case "01" =>
        FingerFgp.FINGER_R_THUMB
      case "02" =>
        FingerFgp.FINGER_R_INDEX
      case "03" =>
        FingerFgp.FINGER_R_MIDDLE
      case "04" =>
        FingerFgp.FINGER_R_RING
      case "05" =>
        FingerFgp.FINGER_R_LITTLE
      case "06" =>
        FingerFgp.FINGER_L_THUMB
      case "07" =>
        FingerFgp.FINGER_L_INDEX
      case "08" =>
        FingerFgp.FINGER_L_MIDDLE
      case "09" =>
        FingerFgp.FINGER_L_RING
      case "10" =>
        FingerFgp.FINGER_L_LITTLE
      case other =>
        FingerFgp.FINGER_UNDET
    }
  }

  /**
    *
    * @param tpCount
    * @param lpCount
    * @return
    */
  def ParseFeatureTypeEnum(tpCount:Integer,lpCount:Integer): FeatureType ={
    var featureType:FeatureType = null
    if(tpCount>0){
      featureType = FeatureType.FingerTemplate
    }else if(lpCount>0){
      featureType = FeatureType.FingerLatent
    }
    featureType
  }
}
