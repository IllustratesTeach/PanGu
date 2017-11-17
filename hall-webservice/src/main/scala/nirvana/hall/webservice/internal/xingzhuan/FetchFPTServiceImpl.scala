package nirvana.hall.webservice.internal.xingzhuan

import java.io.File
import javax.sql.DataSource

import nirvana.hall.api.services._
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.services.xingzhuan.SendQueryService
import nirvana.hall.webservice.services.xingzhuan.FetchFPTService
import org.apache.commons.io.FileUtils
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.webservice.HallWebserviceConstants

import scala.collection.mutable

/**
  * Created by Administrator on 2017/5/9.
  */
class FetchFPTServiceImpl(queryService: QueryService,
                          fPTService: FPTService,
                          assistCheckRecordService: AssistCheckRecordService,
                          caseInfoService: CaseInfoService,
                          tPCardService: TPCardService,
                          sendQueryService: SendQueryService,
                          implicit val dataSource: DataSource) extends FetchFPTService with LoggerSupport{

  val BATCH_SIZE = 100

  def fetchAssistTask(): mutable.ListBuffer[mutable.HashMap[String,Any]] = {
    val sql = s"SELECT t.id,t.fpt_path,t.custom1,executetimes " +
      s"FROM xc_task t " +
      s"WHERE t.status = '0' AND EXECUTETIMES < 3 AND t.id IS NOT NULL AND rownum<= ? "
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      ps.setInt(1, BATCH_SIZE)
    } { rs =>
      var map = new scala.collection.mutable.HashMap[String,Any]
      map += ("id" -> rs.getString("id"))
      map += ("fpt_path" -> rs.getString("fpt_path"))
      map += ("custom1" -> rs.getString("custom1"))
      map += ("executetimes" -> rs.getInt("executetimes"))
      resultList.append(map)
    }
    resultList
  }

  def fetchFPT(): Unit = {
      fetchAssistTask.foreach{
        t => sendQuery(t.get("fpt_path").get.toString,t.get("id").get.toString,t.get("custom1").get.toString,t.get("executetimes").get.asInstanceOf[Int])
      }
  }

  private def sendQuery(fptPath:String,id:String,custom1:String,executetimes: Int): Unit = {
    try {
      val is = FileUtils.openInputStream(new File(fptPath))
      var fptFile:FPT4File = null
        try{
        fptFile = FPTFile.parseFromInputStream(is, AncientConstants.GBK_ENCODING).right.get
      }
      catch{
        case e: Exception =>
          var exceptionInfo = ExceptionUtil.getStackTraceInfo(e)
          assistCheckRecordService.updateXcTask(id, HallWebserviceConstants.FILEParseErrorStatus,exceptionInfo,"FPT文件解析异常", "","")
          return
      }
      sendQueryService.sendQuery(fptFile, id, custom1,executetimes)
    } catch {
      case e: Exception =>
        var exceptionInfo = ExceptionUtil.getStackTraceInfo(e)
        var msg = e.getMessage()
        if(msg == null) {
          var index = exceptionInfo.indexOf("\r\n")
          if(index == -1) {
            if(exceptionInfo.length() < 100) index = exceptionInfo.length()
            index = 100
          }
          msg = exceptionInfo.substring(0, index)
        }
        else{
          if(msg.length>100)
            msg = msg.substring(0,100)
        }
        assistCheckRecordService.updateXcTask(id, HallWebserviceConstants.FILEStatus,exceptionInfo,msg, "","")
    }
  }
}