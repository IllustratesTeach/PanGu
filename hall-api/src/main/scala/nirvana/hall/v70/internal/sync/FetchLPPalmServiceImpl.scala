package nirvana.hall.v70.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.FetchLPPalmService
import nirvana.hall.protocol.api.FPTProto.LPCard
import org.apache.tapestry5.json.JSONObject

import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 16/8/22.
  */
class FetchLPPalmServiceImpl(implicit dataSource: DataSource) extends SyncDataFetcher with FetchLPPalmService{
  override val SYNC_SQL: String = "select t.palm_id as sid, t.seq from gafis_case_palm t left join gafis_logic_db_case db on t.case_id=db.case_pkid where db.logic_db_pkid =? and t.seq >=? and t.seq <=?"
  //override val SYNC_SQL: String = "select t.palm_id as sid, t.seq from gafis_case_palm t left join gafis_logic_db_case db on t.case_id=db.case_pkid where db.logic_db_pkid =? and t.seq >= ? and t.seq <= ? and t.palm_id not in (select h.serviceid from HALL_DS_CASE_PALM h where h.status != '0')"

  /**
    * 获取最大的seq值
    *
    * @return
    */
  override def getMaxSeq(dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select max(t.seq) from gafis_case_palm t left join gafis_logic_db_case db on t.case_id=db.case_pkid where db.logic_db_pkid='${dbId.get}'")
  }

  /**
    * 获取最小的seq值, 大于from
    *
    * @param from
    * @return
    */
  override def getMinSeq(from: Long, dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select min(t.seq) from gafis_case_palm t left join gafis_logic_db_case db on t.case_id=db.case_pkid where db.logic_db_pkid='${dbId.get}' and t.seq > ${from}")
    //getSeqBySql(s"select min(t.seq) from gafis_case_palm t left join gafis_logic_db_case db on t.case_id=db.case_pkid where db.logic_db_pkid='${dbId.get}' and t.seq > ${from} and t.palm_id not in (select t1.serviceid from hall_ds_case_palm t1 where t1.status != '0')")
  }

  /**
    * 获取案件列表
    *
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
    * 验证读取策略
    *
    * @param lPCard
    * @param readStrategy
    * @return
    */
  override def validateByReadStrategy(lPCard: LPCard, readStrategy: String): Boolean = {
    val datasources=lPCard.getStrDataSource
    val strategy = new JSONObject(readStrategy)
    var isdatasource=if (strategy.has("dataSource")) true else false
    if (isdatasource){
      isdatasource=strategy.getString("dataSource").contains(datasources)
    }
    isdatasource
  }
}
