package nirvana.hall.webservice.internal.survey.gz

import java.io.ByteArrayInputStream
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import monad.support.services.{LoggerSupport, XmlLoader}
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.gz.vo.OriginalList
import nirvana.hall.webservice.services.survey.{HandprintService, SurveyRecord}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient



/**
  * Created by ssj on 2017/11/9.
  */
class HandprintServiceCronService(hallWebserviceConfig: HallWebserviceConfig,surveyRecord: SurveyRecord) extends LoggerSupport{
  val url = hallWebserviceConfig.union4pfmip.url
  val targetNamespace = hallWebserviceConfig.union4pfmip.targetNamespace
  val userId = hallWebserviceConfig.union4pfmip.user
  val password = hallWebserviceConfig.union4pfmip.password
  val client = StarkWebServiceClient.createClient(classOf[HandprintService], url, targetNamespace)

  /**
    * 定时器，调用海鑫现勘接口
    * 获取现勘时间段内符合条件数量  和  列表
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.union4pfmip.cron!= null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          try {
            val config = surveyRecord.getSurveyConfig()
            val startTime = config.get("starttime").get.asInstanceOf[Date].getTime
            val endTime = startTime + config.get("increments").get.asInstanceOf[String].toLong*60*1000  //increments 毫秒数
            info("begin HandprintServiceCronService")

            //获取海鑫系统时间
            //val hxtime = new Date(client.getSystemDateTime).getTime
            //surveyRecord.saveSurveyLogRecord("getSystemDateTime","","","",hxtime.toString,"")
            val hxtime = new Date().getTime
            if (hxtime <= endTime) {
              info("系统休眠...")
            } else {
              //拼接request msg
              val map = new scala.collection.mutable.HashMap[String,Any]
              map += ("a" -> userId)
              map += ("b" -> password)
              map += ("c" -> "123456")
              map += ("d" -> dateToStr(new Date(startTime), "yyyy-MM-dd HH:mm:ss"))
              map += ("e" -> dateToStr(new Date(endTime), "yyyy-MM-dd HH:mm:ss"))
              //获取数量
              val num = client.getOriginalDataCount(userId, password, "123456", "", dateToStr(new Date(startTime), "yyyy-MM-dd HH:mm:ss"), dateToStr(new Date(endTime), "yyyy-MM-dd HH:mm:ss"))
              val requestmsgs = surveyRecord.mapToSting("getOriginalDataCount",map)
              surveyRecord.saveSurveyLogRecord("getOriginalDataCount","","",requestmsgs,num.toString,"")
              info("hx  getOriginalDataCount --" + num)
              if (num > 0) {

                //拼接request msg
                val map1 = new scala.collection.mutable.HashMap[String,Any]
                map1 += ("a" -> userId)
                map1 += ("b" -> password)
                map1 += ("c" -> "123456")
                map1 += ("d" -> "")
                map1 += ("e" -> dateToStr(new Date(startTime), "yyyy-MM-dd HH:mm:ss"))
                map1 += ("f" -> dateToStr(new Date(endTime), "yyyy-MM-dd HH:mm:ss"))
                map1 += ("g" -> 1)
                map1 += ("h" -> num)
                //获取现勘号列表
                val datelist = client.getOriginalDataList(userId, password, "123456", "", dateToStr(new Date(startTime), "yyyy-MM-dd HH:mm:ss"), dateToStr(new Date(endTime), "yyyy-MM-dd HH:mm:ss"), 1, num)
                val requestmsgs1 = surveyRecord.mapToSting("getOriginalDataList",map1)
                surveyRecord.saveSurveyLogRecord("getOriginalDataList","","",requestmsgs1,"","")
                info("hx  getOriginalDataList --" + datelist.toString)
                val original = XmlLoader.parseXML[OriginalList](new String(datelist))
                for(i <- 0 until original.K.size()){
                  surveyRecord.saveSurveySnoRecord(original.K.get(i).K_No,original.K.get(i).S_No,original.K.get(i).card_type,original.K.get(i).CASE_NAME)
                  if(surveyRecord.isKno(original.K.get(i).K_No)<=0){
                    surveyRecord.saveSurveyKnoRecord(original.K.get(i).K_No)
                  }
                }
              }
              surveyRecord.updateSurveyConfig(new Timestamp(endTime))
            }
            info("end HandprintServiceCronService")
          } catch {
            case e: Exception =>
              error("HandprintServiceCronService-error:" + e.getMessage)
              surveyRecord.saveSurveyLogRecord("","","","","",e.getMessage)
          }
        }
      })
    }
  }
  /**
    *  date to string
    * @param dateDate
    * @param format
    * @return
    */
  def dateToStr(dateDate: Date, format: String): String = {
    var dateString: String = null
    try {
      val formatter: SimpleDateFormat = new SimpleDateFormat(format)
      dateString = formatter.format(dateDate)
    }
    catch {
      case e: Exception => {
        val formatter: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
        dateString = formatter.format(dateDate)
      }
    }
    return dateString
  }


 }
