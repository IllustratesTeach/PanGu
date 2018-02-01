package nirvana.hall.webservice.internal.survey.gafis62

import java.util.Date

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.c.services.gloclib.survey.{SURVEYCONFIG, SURVEYRECORD}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.SurveyConstant
import nirvana.hall.webservice.services.survey.{SurveyConfigService, SurveyRecordService}
import org.apache.commons.lang.StringUtils
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * Created by T430 on 2/1/2018.
  */
class FPT50HandprintGetLatentListCron(hallWebserviceConfig: HallWebserviceConfig,
                                      fPT50HandprintServiceClient: FPT50HandprintServiceClient,
                                      surveyConfigService: SurveyConfigService,
                                      surveyRecordService: SurveyRecordService,
                                      hallV62Config: HallV62Config) extends LoggerSupport{
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit ={

    //初始化配置信息
    initSurveyConfig

    if(hallWebserviceConfig.handprintService.cron!= null) {
      //获取现场指纹列表定时
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.handprintService.cron, StartAtDelay), "survey-cron-getLatentList", new Runnable {
        override def run(): Unit = {
          try {
            info("begin getLatentList")
            getLatentList
            info("end  getLatentList")
          } catch {
            case ex: Exception =>
              error("getLatentList-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,SurveyConstant.DATETIME_FORMAT)
              )
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
    if(latentCount.toInt > 0){
      var nIndex = surveyConfig.nSeq
      val step = 9
      Range(nIndex, latentCount,10).foreach{i =>
        val ks = i
        var js = ks + step
        if(js > latentCount)
          js = latentCount
        val fingerPrintListResponse = fPT50HandprintServiceClient.getLatentList(surveyConfig.szUnitCode, FPT50HandprintServiceConstants.ZZHWLX_ALL, "", kssj, jssj, ks, js)
        if(fingerPrintListResponse.nonEmpty){
          info("单位代码{} 现场列表大小{}",surveyConfig.szUnitCode,fingerPrintListResponse.get.list.length)
          try {
            FPT50HandprintUtils.modifyHallV62ConfigAppSvr(surveyConfig.szConfig,hallV62Config)
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
    }else{
      //没有获取到数据，设置开始时间为结束时间, 开始位置0，结束时间为空,下次任务会以系统时间为结束时间
      surveyConfig.nSeq = 1
      surveyConfig.szStartTime = jssj
      surveyConfig.szEndTime = ""

      surveyConfigService.updateSurveyConfig(surveyConfig)
    }

  }
}
