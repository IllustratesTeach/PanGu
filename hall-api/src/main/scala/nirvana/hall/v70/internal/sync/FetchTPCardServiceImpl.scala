package nirvana.hall.v70.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.FetchTPCardService

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/8/18.
 */
class FetchTPCardServiceImpl(implicit dataSource: DataSource) extends SyncDataFetcher with FetchTPCardService{
  override val SYNC_SQL: String = "select t.sid, t.seq from gafis_person_mod t left join gafis_logic_db_fingerprint db on t.sid=db.fingerprint_pkid where db.logic_db_pkid =? and t.seq >=? and t.seq <=?"

  /**
   * 获取捺印同步卡号列表
   * @param seq
   * @param size
   * @param dbId
   */
  override def fetchCardId(seq: Long, size: Int, dbId: Option[String]): Seq[(String, Long)] = {
    val cardIdList = new ArrayBuffer[(String, Long)]()
    doFetcher(cardIdList, seq, size, dbId)
    cardIdList
  }

  /**
   * 获取最大的seq值
   * @return
   */
  override def getMaxSeq(dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select max(seq) from gafis_person_mod t left join gafis_logic_db_fingerprint db on t.sid=db.fingerprint_pkid where db.logic_db_pkid='${dbId.get}' ")
  }

  /**
   * 获取最小的seq值, 大于from
   * @param from
   * @return
   */
  override def getMinSeq(from: Long, dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select min(seq) from gafis_person_mod t left join gafis_logic_db_fingerprint db on t.sid=db.fingerprint_pkid where db.logic_db_pkid='${dbId.get}' and t.seq >${from} ")
  }
}
