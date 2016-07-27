package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.LPPalmService
import nirvana.hall.api.services.sync.SyncLPPalmService
import nirvana.hall.protocol.api.SyncDataProto.SyncLPPalmResponse
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

import scala.collection.mutable.ArrayBuffer

/**
 * 同步现场掌纹
 */
class SyncLPPalmServiceImpl(v62Config: HallV62Config, lPPalmService: LPPalmService, implicit val dataSource: DataSource) extends SyncDataFetcher with SyncLPPalmService{
  override val KEY_NAME: String = "palmid"
  /**
   * 同步LPCard数据
   * @param timestamp
   * @param size
   * @return
   */
  override def syncLPCard(responseBuilder: SyncLPPalmResponse.Builder, timestamp: Long, size: Int, dbId: Option[String]): Unit = {
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    val tableName = getTableName(getDBID(dbId), V62Facade.TID_LATPALM)
    doFetcher(cardIdBuffer, timestamp, size, tableName)
    cardIdBuffer.foreach{cardId=>
      val syncLPCard = responseBuilder.addSyncLPCardBuilder()
      syncLPCard.setLpCard(lPPalmService.getLPCard(cardId._1, dbId))
      syncLPCard.setTimestamp(cardId._2)
    }
  }
  /**
   * 获取DBID
   * @param dbId
   */
  private def getDBID(dbId: Option[String]):Short={
    if(dbId == None){
      v62Config.latentTable.dbId.toShort
    }else{
      dbId.get.toShort
    }
  }
}
