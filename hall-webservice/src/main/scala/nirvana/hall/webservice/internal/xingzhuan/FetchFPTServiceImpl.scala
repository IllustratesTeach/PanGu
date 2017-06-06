package nirvana.hall.webservice.internal.xingzhuan

import java.io.File
import javax.sql.DataSource

import nirvana.hall.api.services._
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.services.SendQueryService
import nirvana.hall.webservice.services.xingzhuan.FetchFPTService
import org.apache.commons.io.FileUtils

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
                          implicit val dataSource: DataSource) extends FetchFPTService {

  val BATCH_SIZE = 10

  def fetchAssistTask(): mutable.ListBuffer[mutable.HashMap[String,Any]] = {
    val sql = s"SELECT t.id,t.fpt_path " +
      s"FROM HALL_ASSISTTASK t " +
      s"WHERE t.status = '0' AND t.id IS NOT NULL AND rownum<= ? "
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      ps.setInt(1, BATCH_SIZE)
    } { rs =>
      var map = new scala.collection.mutable.HashMap[String,Any]
      map += ("id" -> rs.getString("id"))
      map += ("fpt_path" -> rs.getString("fpt_path"))
      resultList.append(map)
    }
    resultList
  }

  def fetchFPT(): Unit = {
    fetchAssistTask.foreach{
      t => sendQuery(t.get("fpt_path").get.toString,t.get("id").get.toString)
    }
  }

  private def sendQuery(fptPath:String,id:String): Unit ={
    val is = FileUtils.openInputStream(new File(fptPath))
    val fptFile = FPTFile.parseFromInputStream(is, AncientConstants.GBK_ENCODING).right.get
    sendQueryService.sendQuery(fptFile,id)
  }
}