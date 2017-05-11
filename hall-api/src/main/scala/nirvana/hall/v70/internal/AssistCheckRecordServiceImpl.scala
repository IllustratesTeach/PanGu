package nirvana.hall.v70.internal

import java.util.UUID
import javax.persistence.EntityManager

import nirvana.hall.api.internal.AssistCheckConstant
import nirvana.hall.api.services.AssistCheckRecordService
import nirvana.hall.v70.jpa.HallAssistCheck

import scala.collection.mutable.{HashMap, ListBuffer}
import scala.collection.JavaConverters._

/**
  * Created by yuchen on 2017/4/21.
  */
class AssistCheckRecordServiceImpl(entityManager: EntityManager) extends AssistCheckRecordService{
  override def recordAssistCheck(queryId: String, oraSid: String, caseId: String, personId: String, source: String): Unit = {
    new HallAssistCheck(UUID.randomUUID.toString.replace("-",""),queryId,oraSid,caseId,personId,source,AssistCheckConstant.NO_REPORTED).save
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

  override def updateAssistcheckStatus(status:String, id:String): Unit = {
    HallAssistCheck.update.set(status = status).where(HallAssistCheck.id === id).execute
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

  override def saveXcReport(serviceid:String, msg:String): Unit = {
    val uuid = UUID.randomUUID().toString.replace("-","")
    var sql = "insert into hall_xc_report(id,serviceid,typ,msg) values(?,?,?,?)"
    entityManager.createNativeQuery(sql)
                 .setParameter(1, uuid)
                 .setParameter(2, serviceid)
                 .setParameter(3, "checkin")
                 .setParameter(4, msg)
                 .executeUpdate
  }

  private def getStringVal(obj : Object) = {
    if(obj != null){
      obj.toString
    } else {
      ""
    }
  }

}