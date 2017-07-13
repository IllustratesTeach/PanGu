package nirvana.hall.v70.internal

import java.sql.Timestamp
import java.util.UUID
import javax.persistence.EntityManager

import nirvana.hall.api.services.AssistCheckRecordService
import nirvana.hall.v70.jpa.HallAssistCheck

import scala.collection.mutable.{HashMap, ListBuffer}
import scala.collection.JavaConverters._

/**
  * Created by yuchen on 2017/4/21.
  */
class AssistCheckRecordServiceImpl(entityManager: EntityManager) extends AssistCheckRecordService{
  override def recordAssistCheck(queryId: String, oraSid: String, caseId: String, personId: String, source: String): Unit = {
    new HallAssistCheck(UUID.randomUUID.toString.replace("-",""),queryId,oraSid,caseId,personId,source,0).save
  }

  override def findAssistcheck(size: String): ListBuffer[HashMap[String,Any]] = {
    var sql = "select * from " +
      "(select a.id, a.queryid, a.orasid,a.caseid, a.personid, q.querytype, q.keyid  " +
      "from hall_assistcheck a, gafis_normalquery_queryque q " +
      "where a.orasid = q.ora_sid and a.status = '0' and q.querytype = 0 and q.status = 2 " +
      "union all " +
      "select distinct a.id, a.queryid, a.orasid, a.caseid, a.personid, q.querytype, q.keyid  " +
      "from hall_assistcheck a, gafis_normalquery_queryque q, gafis_checkin_info c, gafis_checkin_review r " +
      "where a.orasid = q.ora_sid " +
      "and a.status = '0' and q.querytype = 2 and q.status = 2 " +
      "and c.query_uuid = q.pk_id " +
      "and r.checkin_id = c.pk_id) " +
      "where rownum <= ?"
    val resultList = entityManager.createNativeQuery(sql).setParameter(1, size).getResultList
    var _resultList = new ListBuffer[HashMap[String,Any]]
    resultList.asScala.foreach { objs =>
      val objArray = objs.asInstanceOf[Array[Object]]
      var resultMap = new HashMap[String,Any]
      resultMap += ("id" -> getStringVal(objArray(0)))
      resultMap += ("queryid" -> getStringVal(objArray(1)))
      resultMap += ("orasid" -> getStringVal(objArray(2)))
      resultMap += ("caseid" -> getStringVal(objArray(3)))
      resultMap += ("personid" -> getStringVal(objArray(4)))
      resultMap += ("querytype" -> java.lang.Long.parseLong(getStringVal(objArray(5))))
      resultMap += ("keyid" -> getStringVal(objArray(6)))
      _resultList += resultMap
    }
    _resultList
  }

  override def updateAssistcheckStatus(status:Long, id:String): Unit = {
    val sql = "update hall_assistcheck set status = ?, updatetime = sysdate where id = ? "
    entityManager.createNativeQuery(sql)
      .setParameter(1, Integer.valueOf(status+""))
      .setParameter(2, id)
      .executeUpdate
  }

  override def updateAssistcheck(status:Long, id:String, fptPath:String): Unit= {
    val sql = "update hall_assistcheck set status = ?, updatetime = sysdate, fpt_path = ?  where id = ? "
    entityManager.createNativeQuery(sql)
      .setParameter(1, Integer.valueOf(status+""))
      .setParameter(2, fptPath)
      .setParameter(3, id)
      .executeUpdate
  }

  override def findUploadCheckin(uploadTime:String, size: String): ListBuffer[HashMap[String,Any]] = {
    var sql = "select * from " +
      "(select q.queryid, q.pk_id ora_uuid, q.ora_sid, q.querytype, q.keyid from gafis_normalquery_queryque q " +
      "where not exists (select serviceid from hall_xc_report where serviceid = q.pk_id) and q.querytype = 0 and q.queryid is null and q.status = 2  " +
      "and to_char(q.createtime,'yyyy') = ? " +
      "union all " +
      "select distinct q.queryid, q.pk_id ora_uuid, q.ora_sid, q.querytype, q.keyid " +
      "from gafis_normalquery_queryque q, gafis_checkin_info c, gafis_checkin_review r " +
      "where not exists (select serviceid from hall_xc_report where serviceid = q.pk_id) " +
      "and q.querytype = 2 and q.queryid is null and q.status = 2 " +
      "and to_char(q.createtime,'yyyy') = ? " +
      "and c.query_uuid = q.pk_id " +
      "and r.checkin_id = c.pk_id) " +
      "where rownum <= ? "
    val resultList = entityManager.createNativeQuery(sql)
      .setParameter(1, uploadTime)
      .setParameter(2, uploadTime)
      .setParameter(3, size)
      .getResultList
    var _resultList = new ListBuffer[HashMap[String,Any]]
    resultList.asScala.foreach { objs =>
      val objArray = objs.asInstanceOf[Array[Object]]
      var resultMap = new HashMap[String,Any]
      resultMap += ("queryid" -> getStringVal(objArray(0)))
      resultMap += ("oraUuid" -> getStringVal(objArray(1)))
      resultMap += ("oraSid" -> getStringVal(objArray(2)))
      resultMap += ("querytype" -> java.lang.Long.parseLong(getStringVal(objArray(3))))
      resultMap += ("keyid" -> getStringVal(objArray(4)))
      _resultList += resultMap
    }
    _resultList
  }

  override def saveXcReport(serviceid:String, typ:String, status:Long, fptPath:String): Unit = {
    val uuid = UUID.randomUUID().toString.replace("-","")
    val sql = "insert into hall_xc_report(id,serviceid,typ,status,fpt_path,create_time,update_time) values(?,?,?,?,?,sysdate,sysdate) "
    entityManager.createNativeQuery(sql)
      .setParameter(1, uuid)
      .setParameter(2, serviceid)
      .setParameter(3, typ)
      .setParameter(4, status)
      .setParameter(5, fptPath)
      .executeUpdate
  }

  private def getStringVal(obj : Object) = {
    if(obj != null){
      obj.toString
    } else {
      ""
    }
  }

  override def saveErrorReport(serviceid: String, typ: String, status: Long, msg: String): Unit = ???

  override def updateAssistcheckLT(queryId: String, oraSid: String, caseId: String, id: String, status: Int, msg: String): Unit = ???

  override def updateXcTask(id: String, status: Int, errorinfo: String, detail: String, serviceid: String, custom2: String): Unit = ???

  override def updateXcTask(id: String, status: Int, errorinfo: String, detail: String, serviceid: String, custom2: String, custom4: String): Unit = ???

  override def updateXcTask(id: String, executetimes: Int): Unit = ???

  override def updateAssistcheckTT(queryId: String, oraSid: String, personId: String, id: String, status: Int, msg: String): Unit = ???

  override def saveXcQuery(id: String, fingerid: String, typ: Int, status: Int, custom1: String, custom2: String, detail: String, errorinfo: String, date: Timestamp): Unit = ???

  override def updateXcQuery(id: String, fingerid: String, typ: Int, status: Int, oraSid: String, queryId: String, errorinfo: String, date: Timestamp): Unit = ???
}