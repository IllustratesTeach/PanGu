package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.api.services.sync.SyncCaseInfoService
import nirvana.hall.protocol.api.SyncDataProto.SyncCaseResponse
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/18.
 */
class SyncCaseInfoServiceImpl(v62Config: HallV62Config, caseInfoService: CaseInfoService, implicit val dataSource: DataSource) extends SyncDataFetcher with SyncCaseInfoService{
  override val KEY_NAME: String = "caseid"
  /**
   * 同步Case数据
   * @param timestamp
   * @param size
   * @return
   */
  override def syncCaseInfo(responseBuilder: SyncCaseResponse.Builder, timestamp: Long, size: Int, dbId: Option[String]): Unit = {
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()

    val tableName = getTableName(getDBID(dbId), V62Facade.TID_CASE)
    doFetcher(cardIdBuffer, timestamp, size, tableName)
    cardIdBuffer.foreach{cardId=>
      val syncCaseInfo = responseBuilder.addSyncCaseBuilder()
      syncCaseInfo.setCaseInfo(caseInfoService.getCaseInfo(cardId._1, dbId))
      syncCaseInfo.setTimestamp(cardId._2)
    }
  }
  /**
   * 获取DBID
   * @param dbId
   */
  private def getDBID(dbId: Option[String]):Short={
    if(dbId == None){
      v62Config.caseTable.dbId.toShort
    }else{
      dbId.get.toShort
    }
  }
}
