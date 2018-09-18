package nirvana.hall.webservice.internal.survey.gz

import java.io.{File, FileInputStream}
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import monad.support.services.{LoggerSupport, XmlLoader}
import nirvana.hall.api.internal.fpt.FPT5Utils
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.gz.vo.{ListNode, OriginalList}
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import nirvana.hall.webservice.survey.gz.client.FPT50HandprintServiceService
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.apache.tools.zip.ZipFile


/**
  * Created by ssj on 2017/11/9.
  */
class HandprintServiceCronService(hallWebserviceConfig: HallWebserviceConfig,
                                  surveyRecordService: SurveyRecordService) extends LoggerSupport{
  val url = hallWebserviceConfig.handprintService.url
  val targetNamespace = hallWebserviceConfig.handprintService.targetNamespace
  val userID = hallWebserviceConfig.handprintService.user
  val passwordStr = hallWebserviceConfig.handprintService.password
//  val unitCode = hallWebserviceConfig.handprintService.unitCode
  val password = DigestUtils.md5Hex(passwordStr)

  val fpt50handprintServiceService = new FPT50HandprintServiceService
  val fpt50handprintServicePort = fpt50handprintServiceService.getFPT50HandprintServicePort
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
    val hxdate =fpt50handprintServicePort.getSystemDateTime()
    info("海鑫当前时间：---"+hxdate)
    surveyRecordService.getSurveyConfig().foreach{
      m =>
        val checkVal = surveyRecordService.isSleep(new SimpleDateFormat(Constant.DATETIME_FORMAT).parse(hxdate).getTime()
                                                    , m.get("starttime").get.asInstanceOf[Date].getTime
                                                    , m.get("increments").get.toString)
        if(!checkVal._1){
          //获取数量
          val num = fpt50handprintServicePort.getFingerPrintCount(userID
            , password
            , m.get("unitCode").get.toString
            , "A"
            , Constant.EMPTY
            , checkVal._2.startTime
            , checkVal._2.endTime)
          surveyRecordService.saveSurveyLogRecord(Constant.GET_FINGER_PRINT_COUNT
            ,Constant.EMPTY
            ,Constant.EMPTY
            ,CommonUtil.appendParam("userID:"+userID
              ,"password:"+password
              ,"asjfsdd_xzqhdm:"+ m.get("unitCode").get.toString
              ,"xckybh:"
              ,"zzhwlx:"+"A"
              ,"kssj:"+ checkVal._2.startTime
              ,"jssj:"+ checkVal._2.endTime)
            ,num
            ,Constant.EMPTY)
          info("获取现堪的数量：---"+num)
          if (num.toInt > 0) {
            //获取现勘号列表
            val dataList = fpt50handprintServicePort.getFingerPrintList(userID
              , password
              , m.get("unitCode").get.toString
              , "A"
              , Constant.EMPTY
              , checkVal._2.startTime
              , checkVal._2.endTime
              , 1
              , num.toInt)
            //zip保存路径
            val path = hallWebserviceConfig.localXKFingerListPath + "/" + new Date().getTime.toString
    //        if(hallWebserviceConfig.saveFPTFlag.equals("1")){
              FileUtils.writeByteArrayToFile(new File(path + ".zip"), IOUtils.toByteArray(dataList.getInputStream))
    //        }
            FPT5Utils.unzipFile(new ZipFile(path + ".zip"), path + ".xml")
            val fileInputStream = new FileInputStream(path + ".xml")
            val dataListStr = new String(IOUtils.toByteArray(fileInputStream))
            surveyRecordService.saveSurveyLogRecord(Constant.GET_FINGER_PRINT_LIST
              ,Constant.EMPTY
              ,Constant.EMPTY
              ,CommonUtil.appendParam("userID:"+userID
                ,"password:"+password
                ,"asjfsdd_xzqhdm:"+ m.get("unitCode").get.toString
                ,"xckybh:"
                ,"zzhwlx:"+"A"
                ,"kssj:"+ checkVal._2.startTime
                ,"jssj:"+ checkVal._2.endTime
                ,"ks:" + 1
                ,"js:" + num)
              , dataListStr
              ,Constant.EMPTY)
            val original = XmlLoader.parseXML[OriginalList](dataListStr)
            val kNoList = original.k.iterator
            var kNoObj:ListNode = null
            while(kNoList.hasNext){
              kNoObj = kNoList.next
              //通过现场物证编号判断数据是否存在，已存在则更新数据
              if(surveyRecordService.isSurvey(kNoObj.XCWZBH)){
                surveyRecordService.updateRecordStateByXCWZBH(Constant.SURVEY_CODE_INIT ,kNoObj.XCWZBH)
              }else{
                surveyRecordService.saveSurveyRecord(kNoObj.AJMC
                  ,kNoObj.XCKYBH
                  ,kNoObj.XCWZBH)
              }
            }
          }
          surveyRecordService.updateSurveyConfig(new Timestamp(DateConverter.convertString2Date(checkVal._2.endTime,Constant.DATETIME_FORMAT).getTime) , m.get("unitCode").get.toString)
        }
    }
  }

}
