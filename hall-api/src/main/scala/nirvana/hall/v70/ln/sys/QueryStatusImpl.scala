package nirvana.hall.v70.ln.sys

import javax.sql.DataSource

import nirvana.hall.support.services.JdbcDatabase

/**
  * Created by Administrator on 2017/11/8.
  */
class QueryStatusImpl(implicit val dataSource: DataSource) extends QueryStatus{
  /**
    * 根据任务号sid获取比对状态 SQL查询方式
    *
    * @param oraSid
    */
  override def getStatusBySidSQL(oraSid: Long): Int = {
    val sql = s"select t.status from GAFIS_NORMALQUERY_QUERYQUE t where t.ora_sid = ?"
    var status = 0
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      ps.setInt(1,oraSid.toInt)
    } { rs =>
      status = rs.getInt("status")
    }
    status
  }

  /**
    * 更新任务表中对应这条认定的候选信息的候选状态
    *
    * @param oraSid
    * @param taskType
    * @param keyId
    * @param fgp
    * @return
    */
  override def updateCandListStatus(oraSid: String, taskType: Int, keyId: String, tCode: String, fgp: Int): Long = ???
}
