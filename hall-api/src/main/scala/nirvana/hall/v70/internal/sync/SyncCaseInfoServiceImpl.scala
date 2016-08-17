package nirvana.hall.v70.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.api.services.sync.SyncCaseInfoService
import nirvana.hall.protocol.api.SyncDataProto.SyncCaseResponse

/**
 * Created by songpeng on 16/8/4.
 */
class SyncCaseInfoServiceImpl(caseInfoService: CaseInfoService, implicit val dataSource: DataSource) extends SyncDataFetcher with SyncCaseInfoService{
  //TODO 没有seq
  override val SYNC_SQL: String = "select t.case_id cardid, t.seq seq from gafis_case t left join gafis_logic_db_case db on t.case_id=db.case_pkid where t.seq >=? and t.seq <=?"

  /**
   * 获取最大的seq值
   * @return
   */
  override def getMaxSeq(dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select max(seq) from gafis_case t left join gafis_logic_db_case db on t.case_id=db.case_pkid and db.logic_db_pkid='${dbId.get}'")
  }

  /**
   * 获取最小的seq值, 大于from
   * @param from
   * @return
   */
  override def getMinSeq(from: Long, dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select min(seq) from gafis_case t left join gafis_logic_db_case db on t.case_id=db.case_pkid and db.logic_db_pkid='${dbId.get}' and t.seq >${from}")
  }

  /**
   * 同步CaseInfo数据
   * @param timestamp
   * @param size
   * @return
   */
  override def syncCaseInfo(responseBuilder: SyncCaseResponse.Builder, timestamp: Long, size: Int, dbId: Option[String]): Unit = {
    throw new UnsupportedOperationException
/*    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    doFetcher(cardIdBuffer, timestamp, size, dbId)
    cardIdBuffer.foreach{cardId=>
      val syncCaseInfo = responseBuilder.addSyncCaseBuilder()
      syncCaseInfo.setCaseInfo(caseInfoService.getCaseInfo(cardId._1, dbId))
      syncCaseInfo.setTimestamp(cardId._2)
    }*/
  }
}
