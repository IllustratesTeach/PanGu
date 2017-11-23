package nirvana.hall.webservice.internal.survey.gz


import java.io.{ByteArrayInputStream, File}
import java.util.Date

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.services.AncientData.AncientDataException
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.c.services.gfpt4lib.FPTFile.FPTParseException
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.survey.HandprintService
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import org.apache.commons.io.FileUtils
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient


/**
  * Created by ssj on 2017/11/13.
  */
class GetDateServiceCronService(hallWebserviceConfig: HallWebserviceConfig,
                                surveyRecordService: SurveyRecordService,
                                fPTService: FPTService) extends LoggerSupport{
  val BATCH_SIZE=10

  val url = hallWebserviceConfig.handprintService.url
  val targetNamespace = hallWebserviceConfig.handprintService.targetNamespace
  val userId = hallWebserviceConfig.handprintService.user
  val password = hallWebserviceConfig.handprintService.password
  val unitCode = hallWebserviceConfig.handprintService.unitCode
  val client = StarkWebServiceClient.createClient(classOf[HandprintService], url, targetNamespace)

  /**
    * 定时器，调用海鑫现勘接口
    * 获取现勘记录表中的 文字信息 和 指掌纹数据 和 接警编号
    * 1 判断是否有 案件编号 使用获取案事件编号接口 getCaseNo
    * 2 如果有案事件编号则调用获取文字信息的接口 getOriginalData 传入参数 T，然后将获取的数据入库，现勘记录表
    * 根据现勘号，一个一个获取指掌纹数据。直接入库操作，更新指掌纹记录表和现勘记录表
    * 如果没有则跳出当前定时
    * 3 入库成功后，调用获取接警编号接口，存入oracle case表中并更新记录表
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if (hallWebserviceConfig.handprintService.cron != null) {
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          try{
            info("begin Cron")
            doWork
            info("end Cron")
          }catch{
            case ex:Exception =>
              error("GetDateServiceCronService-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,Constant.DATETIME_FORMAT)
              )
          }
        }
      })
    }
  }



  def doWork {
    surveyRecordService.getXkcodebyState(Constant.INIT, BATCH_SIZE).foreach {
      kNo =>
        if(saveCaseTextInfo(kNo)){
          surveyRecordService.getSurveySnoRecord(kNo).foreach {
            m =>
              getPFTPackage(kNo, m.get("sno").toString, m.get("cardType").toString)
              getReceprtionNO(kNo)
          }
        }
    }
  }

  /**
    * 获得案件文字信息
    * @param kNo
    * return 是否有案件的文字信息,true-有；false-没有
    */
  private def saveCaseTextInfo(kNo:String):Boolean= {
    var bStr = false
    val caseNo = client.getCaseNo (userId, password, kNo)
    surveyRecordService.saveSurveyLogRecord (Constant.GET_CASE_NO
      , Constant.EMPTY
      , Constant.EMPTY
      , CommonUtil.appendParam ("userId:" + userId, "password:" + password, "kNo:" + kNo)
      , caseNo
      , Constant.EMPTY)
    if (! CommonUtil.isNullOrEmpty (caseNo) ) {
      bStr = true
      val caseInfo = client.getOriginalData (userId
        , password
        , kNo
        , Constant.EMPTY
        , Constant.GET_TEXT_INFO)
      surveyRecordService.saveSurveyLogRecord (Constant.GET_ORIGINAL_DATA
        , kNo
        , Constant.EMPTY
        , CommonUtil.appendParam ("userId:" + userId
          , "password:" + password
          , "kNo:" + kNo
          , "sNo:"
          , "cardType:" + Constant.GET_TEXT_INFO)
        , new String (caseInfo)
        , Constant.EMPTY)
      surveyRecordService.saveSurveycaseInfo (kNo, new String (caseInfo))
      surveyRecordService.updateXkcodeState (Constant.SURVEY_CODE_KNO_SUCCESS, kNo)
    }
    bStr
  }

  private def getPFTPackage(kNo:String,sNo:String,cardType:String): Unit ={
    val fpt = client.getOriginalData(userId,password,kNo,sNo,cardType)
    val path = hallWebserviceConfig.localTenprintPath + kNo+sNo + ".FPT"
    surveyRecordService.saveSurveyLogRecord(Constant.GET_ORIGINAL_DATA
                                            ,Constant.EMPTY
                                            ,Constant.EMPTY
                                            ,CommonUtil.appendParam("userId:" + userId
                                                                  , "password:" + password
                                                                  , "kNo:" + kNo
                                                                  , "sNo:" + sNo
                                                                  , "cardType:" + cardType)
                                            ,path
                                            ,Constant.EMPTY)
    if(hallWebserviceConfig.saveFPTFlag.equals("1")){
      FileUtils.writeByteArrayToFile(new File(path), fpt)
    }
    try{
      cardType match {
        case Constant.GET_FINGER_FPT =>
          val fptFile = FPTFile.parseFromInputStream(new ByteArrayInputStream(fpt))
          fptFile match {
            case Right(fpt4) =>
              fpt4.logic03Recs.foreach{ logic03Rec =>
                fPTService.addLogic03Res(logic03Rec)
              }
              surveyRecordService.updateSnoState(Constant.SNO_SUCCESS,kNo,sNo)
          }
        case Constant.GET_PALM_FPT =>
          FileUtils.writeByteArrayToFile(new File(path), fpt)
          surveyRecordService.savePalmpath(kNo,sNo,path)
          surveyRecordService.updateSnoState(Constant.SNO_SUCCESS,kNo,sNo)
      }
    }catch {
      case ex:AncientDataException =>
        callFBUseCondition(kNo,sNo,cardType,ExceptionUtil.getStackTraceInfo(ex))
      case exx:FPTParseException =>
        callFBUseCondition(kNo,sNo,cardType,ExceptionUtil.getStackTraceInfo(exx))
    }
  }

  /**
    *  获得接警编号
    * @param kNo
    */
  private def getReceprtionNO(kNo:String) = {

    val receprtionNO = client.getReceptionNo(userId,password,kNo)
    surveyRecordService.saveSurveyLogRecord(Constant.GET_RECEPTION_NO,kNo
      ,Constant.EMPTY
      ,CommonUtil.appendParam("userId:"+userId,"password:"+password,"kNo:"+kNo)
      ,receprtionNO,Constant.EMPTY)
    if(CommonUtil.isNullOrEmpty(receprtionNO)){
      surveyRecordService.updateXkcodeState(Constant.SURVEY_CODE_CASEID_ERROR,kNo)
    }else{
      surveyRecordService.updateCasePeception(receprtionNO,kNo)
      surveyRecordService.updateXkcodeState(Constant.SURVEY_CODE_CASEID_SUCCESS,kNo)
    }
  }

  private def callFBUseCondition(kNo:String,sNo:String,cardType:String,exceptionInfo:String): Unit ={
    val responses = client.FBUseCondition(userId,password,kNo,sNo,"",cardType,Constant.FPT_PARSE_FAIL)
    surveyRecordService.saveSurveyLogRecord(Constant.FBUSECONDITION
      ,kNo
      ,sNo
      ,CommonUtil.appendParam("userId:" + userId
        , "password:" + password
        , "kNo:" + kNo
        , "sNo:" + sNo
        , "cardNo:"
        , "cardType:" + cardType
        ,"resultType:"+Constant.FPT_PARSE_FAIL)
      ,responses
      ,exceptionInfo)
  }
}



