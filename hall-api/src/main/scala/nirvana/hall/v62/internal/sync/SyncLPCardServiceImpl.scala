package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.LPCardService
import nirvana.hall.api.services.sync.SyncLPCardService
import nirvana.hall.protocol.api.SyncDataProto.SyncLPCardResponse
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/18.
 */
class SyncLPCardServiceImpl(v62Config: HallV62Config, lPCardService: LPCardService, implicit val dataSource: DataSource) extends SyncDataFetcher with SyncLPCardService{
  override val KEY_NAME: String = "fingerid"
  /**
   * 同步LPCard数据
   * @param timestamp
   * @param size
   * @return
   */
  override def syncLPCard(responseBuilder: SyncLPCardResponse.Builder, timestamp: Long, size: Int, dbId: Option[String]): Unit = {
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    val tableName = getTableName(getDBID(dbId), V62Facade.TID_LATFINGER)
    doFetcher(cardIdBuffer, timestamp, size, tableName)
    cardIdBuffer.foreach{cardId=>
      val syncLPCard = responseBuilder.addSyncLPCardBuilder()
      syncLPCard.setLpCard(lPCardService.getLPCard(cardId._1, dbId))
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
