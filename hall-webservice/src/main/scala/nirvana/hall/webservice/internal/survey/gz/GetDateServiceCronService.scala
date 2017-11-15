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
  * Created by ssj on 2017/11/13.
  */
class GetDateServiceCronService(hallWebserviceConfig: HallWebserviceConfig, implicit val dataSource: DataSource) extends LoggerSupport{
  val url = hallWebserviceConfig.union4pfmip.url
  val targetNamespace = hallWebserviceConfig.union4pfmip.targetNamespace
  val userId = hallWebserviceConfig.union4pfmip.user
  val password = hallWebserviceConfig.union4pfmip.password
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
    if(hallWebserviceConfig.union4pfmip.cron!= null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin GetDateServiceCronService")
          val caseNolist = getCaseNo()
          if(caseNolist.size > 0){
            for(i <- 0 until caseNolist.size){
              val kno = caseNolist(i).get("kno").get.asInstanceOf[String]
              //获取没有处理过的现勘编号的对应的案件编号
              val caseid = client.getCaseNo(userId, password, kno)
              info("hx  getCaseNo -- 案件编号：" + caseid)
              if(caseid.equals("")||caseid == null){
                return
              }else{
                //TODO 执行获取案件信息入库操作
              }
            }
          }else{
            info("没有需要获取的现勘数据...")
          }
          info("end GetDateServiceCronService")
        }
      })
    }
  }

  def getCaseNo() : ListBuffer[mutable.HashMap[String,Any]] ={
    val sql = "select t.kno from survey_xkcode_record t where state=0"
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

 }
