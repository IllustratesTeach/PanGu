package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.services.LPCardService
import nirvana.hall.api.services.sync.SyncLPCardService
import nirvana.hall.protocol.api.SyncDataProto.SyncLPCardResponse
import nirvana.hall.v62.config.HallV62Config

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
  override def syncLPCard(responseBuilder: SyncLPCardResponse.Builder, timestamp: Long, size: Int, dBConfig: DBConfig): Unit = {
    val dbConfig = if(dBConfig != null){
      dBConfig
    }else{
      DBConfig(Left(v62Config.latentTable.dbId.toShort), Option(v62Config.latentTable.tableId.toShort))
    }
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    val tableName = getTableName(dbConfig)
    doFetcher(cardIdBuffer, timestamp, size, tableName)
    cardIdBuffer.foreach{cardId=>
      val syncLPCard = responseBuilder.addSyncLPCardBuilder()
      syncLPCard.setTimestamp(cardId._2)
      syncLPCard.setLpCard(lPCardService.getLPCard(cardId._1))
    }
  }
}
