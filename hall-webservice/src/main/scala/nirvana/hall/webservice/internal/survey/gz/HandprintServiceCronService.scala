package nirvana.hall.webservice.internal.survey.gz

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.survey.HandprintService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient

import scala.collection.mutable


/**
  * Created by ssj on 2017/11/9.
  */
class HandprintServiceCronService(hallWebserviceConfig: HallWebserviceConfig,implicit val dataSource: DataSource) extends LoggerSupport{
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
          val config = getSurveyConfig()
          val startTime = config.get("starttime").get.asInstanceOf[Date].getTime
          val endTime = startTime + config.get("increments").get.asInstanceOf[String].toLong*60*1000  //increments 毫秒数
          info("begin HandprintServiceCronService")

          //获取海鑫系统时间
          //val hxtime = new Date(client.getSystemDateTime).getTime
          val hxtime = new Date().getTime
          if (hxtime <= endTime) {
            info("系统休眠...")
          } else {
            try {
              //获取数量
              val num = client.getOriginalDataCount(userId, password, "123456", "", dateToStr(new Date(startTime), "yyyy-MM-dd HH:mm:ss"), dateToStr(new Date(endTime), "yyyy-MM-dd HH:mm:ss"))
              info("hx  getOriginalDataCount --" + num)
              if (num > 0) {
                //获取现勘号列表
                val ss = client.getOriginalDataList(userId, password, "123456", "", dateToStr(new Date(startTime), "yyyy-MM-dd HH:mm:ss"), dateToStr(new Date(endTime), "yyyy-MM-dd HH:mm:ss"), 1, num)
                info("hx  getOriginalDataCount --" + ss.toString)
              }
            } catch {
              case e: Exception =>
                error("HandprintServiceCronService-error:" + e.getMessage)
            }
            updateSurveyConfig(new Timestamp(endTime))
          }
          info("end HandprintServiceCronService")
        }
      })
    }
  }


  /**
    * 获取现勘时间配置信息
    *
    * @return
    */
  def getSurveyConfig(): mutable.HashMap[String,Any] =  {
    val sql = s"SELECT * " +
      s"FROM SURVEY_CONFIG t where t.flags = '1'"
    var map = new scala.collection.mutable.HashMap[String,Any]
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
    }{rs=>
      if(rs.next()){
        map += ("starttime" -> rs.getTimestamp("START_TIME"))
        map += ("increments" -> rs.getString("INCREMENTS"))
      }
    }
    map
  }

  /**
    * 更新现勘时间配置表 开始时间字段
    *
    * @param endTime
    * @return
    */
  def updateSurveyConfig(endTime : Timestamp): Unit =  {
    val sql = s"update SURVEY_CONFIG " +
      s"set START_TIME = ? "
    JdbcDatabase.update(sql){ps=>
      ps.setTimestamp(1,endTime)
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
