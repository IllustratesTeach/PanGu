package nirvana.hall.webservice.internal.survey.gz

import java.sql.Timestamp
import java.util.Date

import monad.support.services.{LoggerSupport, XmlLoader}
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.gz.vo.{ListNode, OriginalList}
import nirvana.hall.webservice.services.survey.HandprintService
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient


/**
  * Created by ssj on 2017/11/9.
  */
class HandprintServiceCronService(hallWebserviceConfig: HallWebserviceConfig,surveyRecordService: SurveyRecordService) extends LoggerSupport{
  val url = hallWebserviceConfig.handprintService.url
  val targetNamespace = hallWebserviceConfig.handprintService.targetNamespace
  val userId = hallWebserviceConfig.handprintService.user
  val password = hallWebserviceConfig.handprintService.password
  val unitCode = hallWebserviceConfig.handprintService.unitCode
  val client = StarkWebServiceClient.createClient(classOf[HandprintService],
    url,
    targetNamespace,
    classOf[HandprintService].getSimpleName,
    classOf[HandprintService].getSimpleName + "HttpPort")

  /**
    * 定时器，调用海鑫现勘接口
    * 获取现勘时间段内符合条件数量  和  列表
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.handprintService.cron!= null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          try {
            info("begin HandprintServiceCronService Cron")
            doWork
            info("end HandprintServiceCronService Cron")
          } catch {
            case e: Exception =>
              error("HandprintServiceCronService-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(e),DateConverter.convertDate2String(new Date,Constant.DATETIME_FORMAT)
              )
          }
        }
      })
    }
  }

  private def doWork: Unit ={

    val checkVal = surveyRecordService.isSleep(new Date(client.getSystemDateTime).getTime)
    if(!checkVal._1){
      //获取数量
      val num = client.getOriginalDataCount(userId
        , password
        , unitCode
        , Constant.EMPTY
        , checkVal._2.startTime
        , checkVal._2.endTime)
      surveyRecordService.saveSurveyLogRecord(Constant.GET_ORIGINAL_DATA_COUNT
        ,Constant.EMPTY
        ,Constant.EMPTY
        ,CommonUtil.appendParam("userId:"+userId
          ,"password:"+password
          ,"unitCode:"+unitCode
          ,"kNo:"
          ,"startTime:"+ checkVal._2.startTime
          ,"endTime:"+ checkVal._2.endTime)
        ,num.toString
        ,Constant.EMPTY)
      if (num > 0) {
        //获取现勘号列表
        val dataList = new String(client.getOriginalDataList(userId
          , password
          , unitCode
          , Constant.EMPTY
          , checkVal._2.startTime
          , checkVal._2.endTime
          , 1
          , num))
        surveyRecordService.saveSurveyLogRecord(Constant.GET_ORIGINAL_DATA_LIST
          ,Constant.EMPTY
          ,Constant.EMPTY
          ,CommonUtil.appendParam("userId:"+userId
            ,"password:"+password
            ,"unitCode:"+unitCode
            ,"kNo:"
            ,"startTime:"+ checkVal._2.startTime
            ,"endTime:"+ checkVal._2.endTime
            ,"startNum:" + 1
            ,"endNum:" + num)
          ,dataList
          ,Constant.EMPTY)
        val original = XmlLoader.parseXML[OriginalList](dataList)
        val kNoList = original.K.iterator
        var kNoObj:ListNode = null
        while(kNoList.hasNext){
          kNoObj = kNoList.next
          surveyRecordService.saveSurveySnoRecord(kNoObj.K_No
            ,kNoObj.S_No
            ,kNoObj.card_type
            ,kNoObj.CASE_NAME)
          if(!surveyRecordService.isKno(kNoObj.K_No)){
            surveyRecordService.saveSurveyKnoRecord(kNoObj.K_No)
          }
        }
      }
      surveyRecordService.updateSurveyConfig(new Timestamp(DateConverter.convertString2Date(checkVal._2.endTime,Constant.DATETIME_FORMAT).getTime))
    }
  }
}
