package nirvana.hall.webservice.internal.survey.gz

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.survey.HandprintService
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
/**
  * Created by ssj on 2017/11/14.
  */
class GetReceptionNoServiceCronService (hallWebserviceConfig: HallWebserviceConfig, implicit val dataSource: DataSource) extends LoggerSupport{
  val url = hallWebserviceConfig.union4pfmip.url
  val targetNamespace = hallWebserviceConfig.union4pfmip.targetNamespace
  val userId = hallWebserviceConfig.union4pfmip.user
  val password = hallWebserviceConfig.union4pfmip.password
  val client = StarkWebServiceClient.createClient(classOf[HandprintService], url, targetNamespace)

  /**
    * 定时器，调用海鑫现勘接口
    * 定时获取接警编号为空的失败的现勘号的 接警编号
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.union4pfmip.cron!= null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin GetReceptionNoServiceCronService")
          try {
            val receptionNolist = getReceptionNo()
            if (receptionNolist.size <= 0) {
              info("没有需要获取的接警编号...")
            } else {
              for (i <- 0 until receptionNolist.size) {
                val kno = receptionNolist(i).get("kno").get.asInstanceOf[String]
                //获取接警编号
                val receptionid = client.getReceptionNo(userId, password, kno)
                info("hx  getReceptionNo -- 接警编号：" + receptionid)
                if (receptionid.equals("") || receptionid == null) {
                  return
                } else {
                  //TODO 接警编号入库操作
                  //更新获取成功接警编号
                  updateReceptionNo(kno)
                }
              }

            }
            info("end GetReceptionNoServiceCronService")
          } catch {
            case e: Exception =>
              error("GetReceptionNoServiceCronService-error:" + e.getMessage)
          }
        }
      })
    }
  }

  /**
    * 获取现勘记录表中没有获取到接警编号的现勘号
    *
    * @return
    */
  def getReceptionNo(): ListBuffer[mutable.HashMap[String,Any]] =  {
    val sql = "select t.kno from survey_xkcode_record t where state=-2"
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    JdbcDatabase.queryWithPsSetter(sql){ ps =>
    }
    { rs =>
      var map = new scala.collection.mutable.HashMap[String,Any]
      map += ("kno" -> rs.getString("kno"))
      resultList.append(map)
    }
    resultList
  }

  /**
    * 更新已经获取到接警编号的现勘号的状态
    *
    * @return
    */
  def updateReceptionNo(kno : String): Unit =  {
    val sql = s"update survey_xkcode_record " +
      s"set state = 2 where kno = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,kno)
    }
  }
}
