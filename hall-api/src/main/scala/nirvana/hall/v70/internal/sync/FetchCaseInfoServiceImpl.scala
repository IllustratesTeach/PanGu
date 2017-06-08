package nirvana.hall.v70.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.FetchCaseInfoService

import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 16/8/23.
  */
class FetchCaseInfoServiceImpl(implicit dataSource: DataSource) extends SyncDataFetcher with FetchCaseInfoService {
  override val SYNC_SQL: String = "select t.case_id as sid, t.seq seq from gafis_case t left join gafis_logic_db_case db on t.case_id=db.case_pkid where db.logic_db_pkid =? and t.seq >=? and t.seq <=?"
  //override val SYNC_SQL: String = "select t.case_id as sid, t.seq seq from gafis_case t left join gafis_logic_db_case db on t.case_id=db.case_pkid where db.logic_db_pkid =? and t.seq >=? and t.seq <=? and t.case_id not in (select h.serviceid from HALL_DS_CASE h where h.status != '0')"

  /**
    * 获取最大的seq值
    * @return
    */
  override def getMaxSeq(dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select max(t.seq) from gafis_case t left join gafis_logic_db_case db on t.case_id=db.case_pkid where db.logic_db_pkid='${dbId.get}'")
  }

  /**
    * 获取最小的seq值, 大于from
    * @param from
    * @return
    */
  override def getMinSeq(from: Long, dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select min(t.seq) from gafis_case t  left join gafis_logic_db_case db on t.case_id=db.case_pkid where db.logic_db_pkid='${dbId.get}' and t.seq >${from}")
    //getSeqBySql(s"select min(t.seq) from gafis_case t  left join gafis_logic_db_case db on t.case_id=db.case_pkid where db.logic_db_pkid='${dbId.get}' and t.seq >${from}")
  }

  /**
    * 获取案件列表
    * @param seq
    * @param size
    * @param dbId
    */
  override def fetchCaseId(seq: Long, size: Int, dbId: Option[String]): Seq[(String, Long)] = {
    val cardIdList = new ArrayBuffer[(String, Long)]()
    doFetcher(cardIdList, seq, size, dbId)
    cardIdList
  }
}
