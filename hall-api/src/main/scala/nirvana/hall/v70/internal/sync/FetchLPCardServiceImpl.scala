package nirvana.hall.v70.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.FetchLPCardService

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/8/22.
 */
class FetchLPCardServiceImpl (implicit dataSource: DataSource) extends SyncDataFetcher with FetchLPCardService{
  override val SYNC_SQL: String = "select t.sid, t.seq from gafis_case_finger_mod t left join gafis_case_finger f on f.finger_id=t.sid left join gafis_logic_db_case db on f.case_id=db.case_pkid where db.logic_db_pkid =? and t.seq >=? and t.seq <=?"

  /**
   * 获取最大的seq值
   * @return
   */
  override def getMaxSeq(dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select max(t.seq) from gafis_case_finger_mod t left join gafis_case_finger f on f.finger_id=t.sid left join gafis_logic_db_case db on f.case_id=db.case_pkid where db.logic_db_pkid='${dbId.get}'")
  }

  /**
   * 获取最小的seq值, 大于from
   * @param from
   * @return
   */
  override def getMinSeq(from: Long, dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select min(t.seq) from gafis_case_finger_mod t left join gafis_case_finger f on f.finger_id=t.sid left join gafis_logic_db_case db on f.case_id=db.case_pkid where db.logic_db_pkid='${dbId.get}' and t.seq > ${from} ")
  }

  /**
   * 获取案件列表
   * @param seq
   * @param size
   * @param dbId
   */
  override def fetchCardId(seq: Long, size: Int, dbId: Option[String]): ArrayBuffer[(String, Long)] = {
    val cardIdList = new ArrayBuffer[(String, Long)]()
    doFetcher(cardIdList, seq, size, dbId)
    cardIdList
  }
}
