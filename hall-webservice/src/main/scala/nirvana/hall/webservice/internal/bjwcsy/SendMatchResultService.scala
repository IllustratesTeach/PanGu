package nirvana.hall.webservice.internal.bjwcsy

import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import javax.activation.DataHandler
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.fpt.FPTFileBuilder
import nirvana.hall.api.services.sync.FetchQueryService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, QueryService, TPCardService}
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.bjwcsy.union4pfmip
import org.apache.axiom.attachments.ByteArrayDataSource
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient

/**
  * 互查系统返回比对结果定时任务
  */
class SendMatchResultService(hallWebserviceConfig: HallWebserviceConfig,
                             tPCardService: TPCardService,
                             lPCardService: LPCardService,
                             caseInfoService: CaseInfoService,
                             queryService: QueryService,
                             fetchQueryService: FetchQueryService, implicit val dataSource: DataSource) extends LoggerSupport{

  /**
    * 互查系统返回比对结果
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    //1. 查DB比对完成任务 （status >= 2, seq = 1 ）
    //2. 数据转FPT
    //3. 调用webservice上报比对结果数据
    //4. 发送成功后更新任务状态 （ seq = 2 ）
    if(hallWebserviceConfig.union4pfmip.cron != null)
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "sendMatchResult-cron", new Runnable {
        override def run(): Unit = {
          val url = hallWebserviceConfig.union4pfmip.url
          val targetNamespace = hallWebserviceConfig.union4pfmip.targetNamespace
          val userid = hallWebserviceConfig.union4pfmip.user
          val password = hallWebserviceConfig.union4pfmip.password
          try{
            var resultMap = findMatchTask()
            if(resultMap.size > 0) {
              var queryId = resultMap("queryId").asInstanceOf[Long]
              var oraSid = resultMap("oraSid").asInstanceOf[Long]
              var oraUuid = resultMap("oraUuid").asInstanceOf[String]
              var queryType = resultMap("queryType").asInstanceOf[Long]
              var keyId = resultMap("keyId").asInstanceOf[String]
              var dataHandler:DataHandler = null
              val matchResult = queryService.getMatchResult(oraSid).get
              var fpt4File:FPT4File = null
              //查重
              if(queryType == 0) {
                fpt4File = FPTFileBuilder.buildLogic11RecFpt(matchResult,queryId)
              //正查
              } else if(queryType == 2) {
                val ajid = findAjid(keyId)
                fpt4File = FPTFileBuilder.buildLogic09RecFpt(matchResult,ajid,queryId)
              }
              if(fpt4File != null){
                //debug保存 fpt
                saveFpt(fpt4File,queryId+"")
                dataHandler = new DataHandler(new ByteArrayDataSource(fpt4File.toByteArray(AncientConstants.GBK_ENCODING)))
                val service = StarkWebServiceClient.createClient(classOf[union4pfmip], url, targetNamespace)
                val flag: Int = service.setSearchResult(userid, password, dataHandler)
                if (flag == 1) {
                  updateMatchResultStatus(oraSid)
                } else {
                  error("call setSearchResult faild!inputParam:" + true + " returnVal:" + flag)
                }
              }
            }
          }catch{
              case e:Exception=> error("SendMatchResultService-error:" + e.getMessage)
                e.printStackTrace()
          }
        }
      })
  }

  /**
    * 更新任务状态
    * @param oraSid
    */
  def updateMatchResultStatus(oraSid:Long): Unit = {
    val sql = "update normalquery_queryque set seq = '2' where status >= '2' and seq = '1' and ora_sid = ?"
    JdbcDatabase.update(sql) { ps =>
      ps.setLong(1, oraSid)
    }
  }

  /**
    * 查询远程任务号和本地任务号任务uuid和查询类型
    */
  def findMatchTask(): scala.collection.mutable.HashMap[String,Any] = {
    var resultMap = new scala.collection.mutable.HashMap[String,Any]
    val sql = "select * from " +
               "(select queryid,ora_sid,ora_uuid,querytype,keyid from normalquery_queryque where seq = ? and status >= '2' and rownum = 1 order by queryid asc) " +
               "where rownum = 1 "
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setLong(1,1)
    }{rs=>
      resultMap += ("queryId" -> rs.getLong("queryid"))
      resultMap += ("oraSid" -> rs.getLong("ora_sid"))
      resultMap += ("oraUuid" -> rs.getString("ora_uuid"))
      resultMap += ("queryType" -> rs.getLong("querytype"))
      resultMap += ("keyId" -> rs.getString("keyid"))
    }
    resultMap
  }

  /**
    * 查询案件id
    * @param keyId
    */
  def findAjid(keyId:String): String = {
    var ajid:String = ""
    val sql = "select c.ajid from normallp_latfinger l, normallp_case c " +
              "where l.caseid = c.caseid and fingerid = ? "
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setString(1,keyId)
    }{rs=>
      ajid = rs.getString("ajid")
    }
    ajid
  }

  /**
    * 保存debug fpt
    * @param fpt4
    */
  def saveFpt(fpt4:FPT4File,taskId: String): Unit = {
    val dirPath = "E:/debugTaskFpt"
    val now = new Date()
    val sdf:SimpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS")
    val nowStr = sdf.format(now)
    var dir = new java.io.File(dirPath)
    if(!dir.exists()){
      dir.mkdirs()
    }
    try{
      var out = new FileOutputStream(dir+"/"+taskId+"_"+nowStr+".fpt")
      val fpt4ByteArray :Array[Byte] = fpt4.toByteArray(AncientConstants.GBK_ENCODING)
      out.write(fpt4ByteArray)
      out.flush()
      out.close()
    } catch {
      case e:Exception=> error("saveFpt-error:" + e.getMessage)
        e.printStackTrace()
    }
  }

}