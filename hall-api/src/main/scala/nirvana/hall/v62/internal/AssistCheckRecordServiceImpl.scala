package nirvana.hall.v62.internal


import java.util.UUID
import javax.sql.DataSource

import nirvana.hall.api.internal.AssistCheckConstant
import nirvana.hall.api.services.AssistCheckRecordService
import nirvana.hall.support.services.JdbcDatabase

import scala.collection.mutable.{HashMap, ListBuffer}

/**
  * Created by yuchen on 2017/4/21.
  */
class AssistCheckRecordServiceImpl(implicit val dataSource: DataSource) extends AssistCheckRecordService{
  override def recordAssistCheck(queryId: String, oraSid: String, caseId: String, personId: String, source: String): Unit = {

    val sql = "insert into HALL_ASSISTCHECK(id,queryid,orasid,caseid,personid,createtime,source，status) VALUES(?,?,?,?,?,sysdate,?,?)"
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1,UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,queryId)
      ps.setString(3,oraSid)
      ps.setString(4,caseId)
      ps.setString(5,personId)
      ps.setString(6,source)
      ps.setLong(7,0)
    }
  }

  /**
    * 查询协查任务
    */
  override def findAssistcheck(size: String): ListBuffer[HashMap[String,Any]] = {
    var resultList = new ListBuffer[HashMap[String,Any]]
    val sql = "select a.id, a.queryid, a.orasid, a.caseid, a.personid, q.querytype, q.keyid " +
              "from hall_assistcheck a, normalquery_queryque q " +
              "where a.queryid = q.queryid and a.orasid = q.ora_sid and a.status = '7' and ((q.querytype = 0 and q.status = 7) or (q.querytype = 2 and q.status = 11)) " +
              "and rownum <= ?"
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setString(1,size)
    }{rs=>
      var resultMap = new HashMap[String,Any]
      resultMap += ("id" -> rs.getString("id"))
      resultMap += ("queryid" -> rs.getString("queryid"))
      resultMap += ("orasid" -> rs.getString("orasid"))
      resultMap += ("caseid" -> rs.getString("caseid"))
      resultMap += ("personid" -> rs.getString("personid"))
      resultMap += ("querytype" -> rs.getLong("querytype"))
      resultMap += ("keyid" -> rs.getString("keyid"))
      resultList += resultMap
    }
    resultList
  }

  /**
    * 更新协查任务状态
    * @param status
    * @param id
    */
  override def updateAssistcheckStatus(status:Long, id:String): Unit = {
    val sql = "update hall_assistcheck set status = ?, updatetime = sysdate where id = ? "
    JdbcDatabase.update(sql) { ps =>
      ps.setLong(1, status)
      ps.setString(2, id)
    }
  }

  /**
    * 更新协查任务
    * @param status
    * @param id
    * @param fptPath
    */
  override def updateAssistcheck(status:Long, id:String, fptPath:String): Unit = {
    val sql = "update hall_assistcheck set status = ?, updatetime = sysdate, fpt_path = ?  where id = ? "
    JdbcDatabase.update(sql) { ps =>
      ps.setLong(1, status)
      ps.setString(2, fptPath)
      ps.setString(3, id)
    }
  }

  /**
    * 查询未上报正查查重比对关系
    */
  override def findUploadCheckin(uploadTime:String, size: String): ListBuffer[HashMap[String,Any]] = {
    var resultList = new ListBuffer[HashMap[String,Any]]
    val sql = "select q.queryid, q.ora_uuid, q.ora_sid, q.querytype,q.keyid from normalquery_queryque q " +
              "where not exists (select serviceid from hall_xc_report where serviceid = q.ora_uuid) " +
              "and ((q.querytype = 0 and q.status = 7) or (q.querytype = 2 and q.status = 11) or (q.querytype = 1 and q.status = 11) or (q.querytype = 3 and q.status = 7)) and queryid = 0 " +
              "and to_char(q.ora_createtime,'yyyy') = ? and rownum <= ?"
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setString(1,uploadTime)
      ps.setString(2,size)
    }{rs=>
      var resultMap = new HashMap[String,Any]
      resultMap += ("queryid" -> rs.getString("queryid"))
      resultMap += ("oraUuid" -> rs.getString("ora_uuid"))
      resultMap += ("oraSid" -> rs.getString("ora_sid"))
      resultMap += ("querytype" -> rs.getLong("querytype"))
      resultMap += ("keyid" -> rs.getString("keyid"))
      resultList += resultMap
    }
    resultList
  }

  /**
    * 保存上报记录
    */
  override def saveXcReport(serviceid:String, typ:String, status:Long, fptPath:String): Unit = {
    val uuid = UUID.randomUUID().toString.replace("-","")
    val sql = "insert into hall_xc_report(id,serviceid,typ,status,fpt_path,create_time,update_time) values(?,?,?,?,?,sysdate,sysdate) "
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1, uuid)
      ps.setString(2, serviceid)
      ps.setString(3, typ)
      ps.setLong(4, status)
      ps.setString(5, fptPath)
    }
  }

}
