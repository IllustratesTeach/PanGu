package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.services.TPCardService
import nirvana.hall.api.services.sync.SyncTPCardService
import nirvana.hall.protocol.api.SyncDataProto.SyncTPCardResponse
import nirvana.hall.v62.config.HallV62Config

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/18.
 */
class SyncTPCardServiceImpl(v62Config: HallV62Config,tPCardService: TPCardService,implicit val dataSource: DataSource) extends SyncDataFetcher with SyncTPCardService{
  override val KEY_NAME: String = "cardid"

  /**
   * 同步TPCard数据
   * @param responseBuilder
   * @param timestamp
   * @param size
   * @return
   */
  override def syncTPCard(responseBuilder: SyncTPCardResponse.Builder, timestamp: Long, size: Int, dBConfig: DBConfig): Unit = {
    val dbConfig = if(dBConfig != null){
      dBConfig
    }else{
      DBConfig(Left(v62Config.templateTable.dbId.toShort), Option(v62Config.templateTable.tableId.toShort))
    }
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    doFetcher(cardIdBuffer, timestamp, size, getTableName(dbConfig))
    cardIdBuffer.foreach{cardId=>
      val syncTPCard = responseBuilder.addSyncTPCardBuilder()
      val tpCard = tPCardService.getTPCard(cardId._1, dbConfig)
      syncTPCard.setTpCard(tpCard)
      syncTPCard.setTimestamp(cardId._2)
    }
  }
}
