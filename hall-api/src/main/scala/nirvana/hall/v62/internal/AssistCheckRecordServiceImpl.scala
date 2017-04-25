package nirvana.hall.v62.internal


import java.util.UUID
import javax.sql.DataSource

import nirvana.hall.api.services.AssistCheckRecordService
import nirvana.hall.api.webservice.util.AFISConstant
import nirvana.hall.support.services.JdbcDatabase

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
      ps.setString(7,AFISConstant.NO_REPORTED)
    }
  }
}
