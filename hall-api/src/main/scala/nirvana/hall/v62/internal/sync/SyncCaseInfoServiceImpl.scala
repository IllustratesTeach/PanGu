package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.api.services.sync.SyncCaseInfoService
import nirvana.hall.protocol.api.SyncDataProto.SyncCaseResponse
import nirvana.hall.v62.config.HallV62Config

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
  override def syncCaseInfo(responseBuilder: SyncCaseResponse.Builder, timestamp: Long, size: Int, dBConfig: DBConfig = null): Unit = {
    val dbConfig = if(dBConfig != null){
      dBConfig
    }else{
      DBConfig(Left(v62Config.caseTable.dbId.toShort), Option(v62Config.caseTable.tableId.toShort))
    }
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    val tableName = getTableName(dbConfig)
    doFetcher(cardIdBuffer, timestamp, size, tableName)
    cardIdBuffer.foreach{cardId=>
      val syncCaseInfo = responseBuilder.addSyncCaseBuilder()
      syncCaseInfo.setCaseInfo(caseInfoService.getCaseInfo(cardId._1, dbConfig))
      syncCaseInfo.setTimestamp(cardId._2)
    }
  }
}
