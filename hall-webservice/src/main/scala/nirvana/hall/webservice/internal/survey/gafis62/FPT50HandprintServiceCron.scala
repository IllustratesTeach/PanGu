package nirvana.hall.webservice.internal.survey.gafis62

import java.util.Date

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.c.services.gloclib.survey.{SURVEYCONFIG, SURVEYRECORD}
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.config.{HallWebserviceConfig, SurveyConfig}
import nirvana.hall.webservice.internal.survey.SurveyConstant
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyHitResultRecordService, SurveyRecordService}
import org.apache.commons.lang.StringUtils
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * Created by songpeng on 2017/12/24.
  * 现场勘验系统接口定时服务
  */
class FPT50HandprintServiceCron(hallWebserviceConfig: HallWebserviceConfig,
                                surveyConfigService: SurveyConfigService,
                                surveyRecordService: SurveyRecordService,
                                surveyHitResultRecordService: SurveyHitResultRecordService,
                                matchRelationService: MatchRelationService,
                                fPT5Service: FPT5Service) extends LoggerSupport{

  val fPT50HandprintServiceClient = new FPT50HandprintServiceClient(hallWebserviceConfig.handprintService)
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    if(hallWebserviceConfig.handprintService.cron!= null){
      //获取现场指纹列表定时
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "survey-cron-getLatentList", new Runnable {
        override def run(): Unit = {
          try {
            info("begin getLatentList")
            //根据配置里的62应用服务器地址，使用动态变量访问
            if(hallWebserviceConfig.handprintService.surveyV62ServiceConfig != null){
              hallWebserviceConfig.handprintService.surveyV62ServiceConfig.foreach{surveyV62ServiceConfig=>
                V62Facade.withConfigurationServer(surveyV62ServiceConfig.v62ServerConfig){
                  initSurveyConfig(surveyV62ServiceConfig.surveyConfig)
                  getLatentList
                }
              }
            }
            info("end  getLatentList")
          } catch {
            case ex: Exception =>
              error("getLatentList-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
              )
          }
        }
      })
      //根据现勘列表获取现场指纹FPT5数据
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "survey-cron-getLatentPackage", new Runnable {
        override def run(): Unit = {
          try {
            info("begin getLatentPackage")
            if(hallWebserviceConfig.handprintService.surveyV62ServiceConfig != null){
              hallWebserviceConfig.handprintService.surveyV62ServiceConfig.foreach{surveyV62ServiceConfig=>
                V62Facade.withConfigurationServer(surveyV62ServiceConfig.v62ServerConfig){
                  getLatentPackage
                }
              }
            }
            info("end  getLatentPackage")
          } catch {
            case ex: Exception =>
              error("getLatentPackage-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
              )
          }
        }
      })
      //获取接警编号
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "survey-cron-getReceptionNo", new Runnable {
        override def run(): Unit = {
          try {
            info("begin getReceptionNo")
            if(hallWebserviceConfig.handprintService.surveyV62ServiceConfig != null){
              hallWebserviceConfig.handprintService.surveyV62ServiceConfig.foreach{surveyV62ServiceConfig=>
                V62Facade.withConfigurationServer(surveyV62ServiceConfig.v62ServerConfig){
                  getReceptionNo
                }
              }
            }
            info("end getReceptionNo")
          } catch {
            case ex: Exception =>
              error("getReceptionNo-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
              )
          }
        }
      })
      //推送比对任务
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "sync-cron-sendHitResult", new Runnable {
        override def run(): Unit = {
          try {
            info("begin sendHitResult")
            if(hallWebserviceConfig.handprintService.surveyV62ServiceConfig != null){
              hallWebserviceConfig.handprintService.surveyV62ServiceConfig.foreach{surveyV62ServiceConfig=>
                V62Facade.withConfigurationServer(surveyV62ServiceConfig.v62ServerConfig){
                  sendHitResult
                }
              }
            }
            info("end sendHitResult")
          } catch {
            case ex: Exception =>
              error("sendHitResult-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
              )
          }
        }
      })
    }
  }

  /**
    * 初始化配置,如果本地有对应的单位代码则跳过，否则新增SurveyConfig配置
    * @param surveyConfigList SurveyConfig配置列表
    */
  def initSurveyConfig(surveyConfigList: Seq[SurveyConfig]): Unit ={
    if(surveyConfigList != null){
      val surveyConfigList2 = surveyConfigService.getSurveyConfigList()
      surveyConfigList.foreach{surveyConfig=>
        if(!surveyConfigList2.exists(p=>surveyConfig.unitCode.equals(p.szUnitCode))){
          val config = new SURVEYCONFIG
          config.szUnitCode = surveyConfig.unitCode
          config.szStartTime = surveyConfig.startTime
          config.szEndTime = surveyConfig.endTime
          config.nSeq = 1
          config.nFlages = 1 //启用
          config.szConfig = surveyConfig.config
          surveyConfigService.addSurveyConfig(config)
        }
      }
    }
  }

  /**
    * 获取现场指纹列表
    */
  def getLatentList: Unit ={
    //TODO 添加日志，异常判断和处理，完善业务逻辑,目前是完全理想状态下的代码逻辑
    try{
      //获取系统时间
      val systemDateTime = fPT50HandprintServiceClient.getSystemDateTime()
      info("系统时间{}",systemDateTime)
      //获取配置信息列表，并循环获取数据
      surveyConfigService.getSurveyConfigList().filter(_.nFlages == 1).foreach(getLatentListBySurveyConfig)
    }catch {
      case ex:Exception =>
        error("getLatentList-error:{},currentTime:{}"
          ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
        )
    }
  }

  def getLatentListBySurveyConfig(surveyConfig: SURVEYCONFIG): Unit ={
    val systemDateTime = fPT50HandprintServiceClient.getSystemDateTime()
    info("海鑫系统时间{}, 获取单位代码{}现勘数据",systemDateTime, surveyConfig.szUnitCode)
    val kssj = surveyConfig.szStartTime
    var jssj = surveyConfig.szEndTime
    //如果结束时间为空，设定当前系统时间为结束时间
    if("".equals(jssj)){
      jssj = systemDateTime
    }
    //获取待发送现场指掌纹数量
    val latentCount = fPT50HandprintServiceClient.getLatentCount(surveyConfig.szUnitCode, FPT50HandprintServiceConstants.ZZHWLX_ALL, "", kssj, jssj)
    info("latentCount number:{}",latentCount)
    if(latentCount.toInt > 1){
      var nIndex = surveyConfig.nSeq
      val step = 9
      Range(nIndex, latentCount,10).foreach{i =>
        val ks = i
        var js = ks + step
        if(js > latentCount)
          js = latentCount
        val fingerPrintListResponse = fPT50HandprintServiceClient.getLatentList(surveyConfig.szUnitCode, FPT50HandprintServiceConstants.ZZHWLX_ALL, "", kssj, jssj, ks, js)
        if(fingerPrintListResponse.nonEmpty){
          info("单位代码:{} 现场列表大小:{}",surveyConfig.szUnitCode,fingerPrintListResponse.get.list.length)
          try {
            fingerPrintListResponse.get.list.foreach { k =>
              val xckybh = k.xckybh
              //根据现堪编号获取接警编号
              val receptionNo = fPT50HandprintServiceClient.getReceptionNo(xckybh)
              //插入SURVEY_RECORD
              val surveyRecord = new SURVEYRECORD
              surveyRecord.szKNo = k.xckybh
              surveyRecord.szCaseName = k.ajmc
              surveyRecord.szPhyEvidenceNo = k.xcwzbh
              surveyRecord.szJieJingNo = receptionNo
              surveyRecord.nState = survey.SURVEY_STATE_DEFAULT
              surveyRecord.nJieJingState = if(StringUtils.isNotEmpty(receptionNo) && StringUtils.isNotBlank(receptionNo))
                survey.SURVEY_STATE_SUCCESS
              else survey.SURVEY_STATE_DEFAULT

              surveyRecordService.addSurveyRecord(surveyRecord)
              nIndex += 1
            }
          }catch {
            case ex: Exception=>
              error("getLatentList-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
              )
          }finally {
            surveyConfig.nSeq = nIndex
            surveyConfigService.updateSurveyConfig(surveyConfig)
          }
        }
      }
      try {
        surveyConfig.szStartTime = fPT50HandprintServiceClient.getSystemDateTime()
        surveyConfig.szEndTime = ""
        surveyConfigService.updateSurveyConfig(surveyConfig)
      } catch {
        case ex: Exception =>
          error("getSystemDateTime:{},currentTime:{}"
            , ExceptionUtil.getStackTraceInfo(ex), DateConverter.convertDate2String(new Date, SurveyConstant.DATETIME_FORMAT)
          )
      }
    }else if(latentCount == 1){
      val fingerPrintListResponse = fPT50HandprintServiceClient.getLatentList(surveyConfig.szUnitCode, FPT50HandprintServiceConstants.ZZHWLX_ALL, "", kssj, jssj, 1, 1)
      if(fingerPrintListResponse.nonEmpty){
        info("单位代码:{} 现场列表大小:{}",surveyConfig.szUnitCode,fingerPrintListResponse.get.list.length)
        try {
          fingerPrintListResponse.get.list.foreach { k =>
            val xckybh = k.xckybh
            //根据现堪编号获取接警编号
            val receptionNo = fPT50HandprintServiceClient.getReceptionNo(xckybh)
            //插入SURVEY_RECORD
            val surveyRecord = new SURVEYRECORD
            surveyRecord.szKNo = k.xckybh
            surveyRecord.szCaseName = k.ajmc
            surveyRecord.szPhyEvidenceNo = k.xcwzbh
            surveyRecord.szJieJingNo = receptionNo
            surveyRecord.nState = survey.SURVEY_STATE_DEFAULT
            surveyRecord.nJieJingState = if(StringUtils.isNotEmpty(receptionNo) && StringUtils.isNotBlank(receptionNo))
              survey.SURVEY_STATE_SUCCESS
            else survey.SURVEY_STATE_DEFAULT

            surveyRecordService.addSurveyRecord(surveyRecord)
            surveyConfig.szStartTime = fPT50HandprintServiceClient.getSystemDateTime()
            surveyConfig.szEndTime = ""
            surveyConfigService.updateSurveyConfig(surveyConfig)
          }
        }catch {
          case ex: Exception=>
            error("getLatentList-error:{},currentTime:{}"
              ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
            )
        }
      }
    } else{
      //没有获取到数据，设置开始时间为结束时间, 开始位置1，结束时间为空,下次任务会以系统时间为结束时间
      surveyConfig.nSeq = 1
      surveyConfig.szStartTime = jssj
      surveyConfig.szEndTime = ""

      surveyConfigService.updateSurveyConfig(surveyConfig)
    }

  }

  /**
    * 读取没有获取fpt数据的现堪记录，根据物证编号获取数据并保存
    */
  def getLatentPackage: Unit = {
    val recordList = surveyRecordService.getSurveyRecordListByState(survey.SURVEY_STATE_DEFAULT, 10)
    info("获取recordlist数量:{}",recordList.size)
    if (recordList.nonEmpty) {
      recordList.foreach { record =>
        try {
          val latentPackageOp = fPT50HandprintServiceClient.getLatentPackage(record.szPhyEvidenceNo)
          if (latentPackageOp.nonEmpty) {
            var resultType = FPT50HandprintServiceConstants.RESULT_TYPE_ADD
            //根据客户要求去掉按事件编号判断
//            if (null == latentPackageOp.get.caseMsg.caseId) {
//              //更新状态
//              warn("该现场物证编号{} 无案事件编号", record.szPhyEvidenceNo)
//              record.nState = survey.SURVEY_STATE_FAIL
//              resultType = FPT50HandprintServiceConstants.RESULT_TYPE_ERROR
//            } else {
              //保存数据
              fPT5Service.addLatentPackage(latentPackageOp.get)
              info("入库成功，现场物证编号：" + record.szPhyEvidenceNo)

              if(Option(latentPackageOp.get.latentFingers).nonEmpty){
                latentPackageOp.get.latentFingers.foreach { finger =>
                  if (record.szPhyEvidenceNo.equals(finger.latentFingerImageMsg.latentPhysicalId)) {
                    record.szFingerid = finger.latentFingerImageMsg.latentPhysicalId
                    //更新状态
                    record.nState = survey.SURVEY_STATE_SUCCESS
                }
                }
              }
              if (Option(latentPackageOp.get.latentPalms).nonEmpty) {
                latentPackageOp.get.latentPalms.foreach { palm =>
                  if (record.szPhyEvidenceNo.equals(palm.latentPalmImageMsg.latentPalmPhysicalId)) {
                    record.szFingerid = palm.latentPalmImageMsg.latentPalmPhysicalId
                    //更新状态
                    record.nState = survey.SURVEY_STATE_SUCCESS
                  }
                }
              }
            //}
            surveyRecordService.updateSurveyRecord(record)
            fPT50HandprintServiceClient.sendFBUseCondition(record.szPhyEvidenceNo, resultType)
          } else {
            fPT50HandprintServiceClient.sendFBUseCondition(record.szPhyEvidenceNo, FPT50HandprintServiceConstants.RESULT_TYPE_ERROR)
          }
      }catch {
          case ex: Exception=>
            error("fPT50HandprintServiceClient-getLatentPackage:{},currentTime:{}"
              ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
            )
            record.nState = survey.SURVEY_STATE_FAIL
            surveyRecordService.updateSurveyRecord(record)
        }
      }
      getLatentPackage
    }
  }

  /**
    * 获取接警编号
    */
  def getReceptionNo: Unit ={
    //读取SURVEY_RECORD表没有接警编号的数据,获取接警编号并保存
    val recordList = surveyRecordService.getSurveyRecordListByJieJingState(survey.SURVEY_STATE_DEFAULT, 10)
    if(recordList.nonEmpty){
      recordList.foreach{record=>
        fPT50HandprintServiceClient.getReceptionNo(record.szKNo)
        record.nJieJingState = survey.SURVEY_STATE_SUCCESS
        surveyRecordService.updateSurveyRecord(record)
      }
      getReceptionNo
    }
  }

  /**
    * 推送比对任务
    */
  def sendHitResult: Unit ={
    val hitResultList = surveyHitResultRecordService.getSurveyHitResultRecordList(survey.SURVEY_STATE_DEFAULT, 10)
    if(hitResultList.nonEmpty){
      hitResultList.foreach{hitResult=>
        info("hitResultList,szFingerID{}",hitResult.szFingerID)
        val xckybh = surveyRecordService.getPhyEvidenceNoByFingerId(hitResult.szFingerID)
        info("导出比中关系,现场勘验编号{}",xckybh.get)
        if (xckybh.nonEmpty) {
          info("获取比中关系包")
          val hitResultDhOp = surveyHitResultRecordService.getDataHandlerOfLtOrLlHitResultPackage(hitResult)
          info("获取hitResultDhOp对象{}",hitResultDhOp.get.getName)
          if(hitResultDhOp.nonEmpty){
            info("发送比中关系包")
            fPT50HandprintServiceClient.sendHitResult(xckybh.get, hitResult.nQueryType, hitResultDhOp.get)

            hitResult.nState = survey.SURVEY_STATE_SUCCESS
          }else{
            hitResult.nState = survey.SURVEY_STATE_FAIL
          }
          surveyHitResultRecordService.updateSurveyHitResultRecord(hitResult)
        }
      }
      sendHitResult
    }
  }
}
