package nirvana.hall.webservice.internal.survey.gafis62

import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.c.services.gloclib.survey.{SURVEYCONFIG, SURVEYRECORD}
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyHitResultRecordService, SurveyRecordService}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * Created by songpeng on 2017/12/24.
  * 现场勘验系统接口定时服务
  */
class FPT50HandprintServiceCron(hallWebserviceConfig: HallWebserviceConfig,
                                fPT50HandprintServiceClient: FPT50HandprintServiceClient,
                                surveyConfigService: SurveyConfigService,
                                surveyRecordService: SurveyRecordService,
                                surveyHitResultRecordService: SurveyHitResultRecordService,
                                fPT5Service: FPT5Service) extends LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    //初始化配置信息
    initSurveyConfig

    if(hallWebserviceConfig.handprintService.cron!= null){
      //获取现场指纹列表定时
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "survey-cron", new Runnable {
        override def run(): Unit = {
          try {
            info("begin getLatentList")
            getLatentList
            info("end  getLatentList")
          } catch {
            case e: Exception =>
          }
        }
      })
      //根据现勘列表获取现场指纹FPT5数据
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "survey-cron", new Runnable {
        override def run(): Unit = {
          try {
            info("begin getLatentPackage")
              getLatentPackage
            info("end  getLatentPackage")
          } catch {
            case e: Exception =>
          }
        }
      })
      //获取接警编号
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "survey-cron", new Runnable {
        override def run(): Unit = {
          try {
            info("begin getReceptionNo")
            getReceptionNo
            info("end getReceptionNo")
          } catch {
            case e: Exception =>
          }
        }
      })
      //推送比对任务
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          try {
            info("begin sendHitResult")
              sendHitResult
            info("end sendHitResult")
          } catch {
            case e: Exception =>
          }
        }
      })
    }
  }

  def initSurveyConfig: Unit ={
    if(hallWebserviceConfig.handprintService.surveyConfig != null){
      val surveyConfigList = surveyConfigService.getSurveyConfigList()
      hallWebserviceConfig.handprintService.surveyConfig.foreach{surveyConfig=>
        if(!surveyConfigList.exists(p=>surveyConfig.unitCode.equals(p.szUnitCode))){
          val config = new SURVEYCONFIG
          config.szUnitCode = surveyConfig.unitCode
          config.szStartTime = surveyConfig.startTime
          config.szEndTime = surveyConfig.endTime
          config.nSeq = 0
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
    //获取系统时间
    val systemDateTime = fPT50HandprintServiceClient.getSystemDateTime()
    info("系统时间{}",systemDateTime)
    //获取配置信息列表，并循环获取数据
    surveyConfigService.getSurveyConfigList().filter(_.nFlages == 1).foreach(getLatentListBySurveyConfig)
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
    val latentCount = fPT50HandprintServiceClient.getLatentCount(surveyConfig.szUnitCode, "", FPT50HandprintServiceConstants.ZZHWLX_ALL, kssj, jssj)
    if(latentCount.toInt > 0){
      var nIndex = surveyConfig.nSeq
      val step = 10
      Range(nIndex, latentCount, step).foreach{i =>
        val ks = i + 1
        var js = ks + step
        if(js > latentCount)
          js = latentCount

        val fingerPrintListResponse = fPT50HandprintServiceClient.getLatentList(surveyConfig.szUnitCode, "", FPT50HandprintServiceConstants.ZZHWLX_ALL, "", kssj, jssj, ks, js)
        if(fingerPrintListResponse.nonEmpty){
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

              surveyRecordService.addSurveyRecord(surveyRecord)
              nIndex += 1
            }
          }catch {
            case e: Exception=>
              error(e.getMessage)
          }finally {
            surveyConfig.nSeq = nIndex
            surveyConfigService.updateSurveyConfig(surveyConfig)
          }
        }
      }
    }else{
      //没有获取到数据，设置开始时间为结束时间, 开始位置0，结束时间为空,下次任务会以系统时间为结束时间
      surveyConfig.nSeq = 0
      surveyConfig.szStartTime = jssj
      surveyConfig.szEndTime = ""

      surveyConfigService.updateSurveyConfig(surveyConfig)
    }

  }

  /**
    * 读取没有获取fpt数据的现堪记录，根据物证编号获取数据并保存
    */
  def getLatentPackage: Unit ={
    val recordList = surveyRecordService.getSurveyRecordListByState(survey.SURVEY_STATE_DEFAULT, 10)
    recordList.foreach{record=>
      val latentPackageOp = fPT50HandprintServiceClient.getLatentPackage(record.szPhyEvidenceNo)
      if(latentPackageOp.nonEmpty){
        //保存数据
        fPT5Service.addLatentPackage(latentPackageOp.get)

        //更新状态
        record.nState = survey.SURVEY_STATE_SUCCESS
        surveyRecordService.updateSurveyRecord(record)

        fPT50HandprintServiceClient.sendFBUseCondition(record.szPhyEvidenceNo, FPT50HandprintServiceConstants.RESULT_TYPE_ADD)
      }else{
        fPT50HandprintServiceClient.sendFBUseCondition(record.szPhyEvidenceNo, FPT50HandprintServiceConstants.RESULT_TYPE_ERROR)
      }
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
    }else{
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
        //TODO
        val xckybh = ""
        val hitResultDh: DataHandler = null
        fPT50HandprintServiceClient.sendHitResult(xckybh, hitResult.nQueryType, hitResultDh)
        hitResult.nState = survey.SURVEY_STATE_SUCCESS
        surveyHitResultRecordService.updateSurveyHitResultRecord(hitResult)
      }
    }else{
      sendHitResult
    }
  }

}
