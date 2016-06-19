package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.internal.SqlUtils
import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.api.services.sync.SyncCaseInfoService
import nirvana.hall.protocol.api.SyncDataProto.SyncCaseResponse

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/18.
 */
class SyncCaseInfoServiceImpl(caseInfoService: CaseInfoService, implicit val dataSource: DataSource) extends SyncDataFetcher with SyncCaseInfoService{
  override val MAX_SEQ_SQL: String = s"select ${SqlUtils.wrapModTimeAsLong(Some("max"))} from normallp_case_mod t "
  override val MIN_SEQ_SQL: String = s"select ${SqlUtils.wrapModTimeAsLong(Some("min"))} from normallp_case_mod t where ${SqlUtils.wrapModTimeAsLong()}  >"
  override val SYNC_SQL =  s"select tp.caseId as cardid, ${SqlUtils.wrapModTimeAsLong()} as seq from normallp_case_mod t left join normallp_case tp on tp.ora_sid= t.ora_sid where ${SqlUtils.wrapModTimeAsLong()} >? and ${SqlUtils.wrapModTimeAsLong()} <=? order by t.modtime"

  /**
   * 同步Case数据
   * @param timestamp
   * @param size
   * @return
   */
  override def syncCaseInfo(responseBuilder: SyncCaseResponse.Builder, timestamp: Long, size: Int): Unit = {
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    doFetcher(cardIdBuffer, timestamp, size)
    cardIdBuffer.foreach{cardId=>
      val syncCaseInfo = responseBuilder.addSyncCaseBuilder()
      syncCaseInfo.setTimestamp(cardId._2)
      syncCaseInfo.setCaseInfo(caseInfoService.getCaseInfo(cardId._1))
    }
  }
}
