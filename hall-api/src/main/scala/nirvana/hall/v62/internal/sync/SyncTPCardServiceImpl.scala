package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.TPCardService
import nirvana.hall.api.services.sync.SyncTPCardService
import nirvana.hall.protocol.api.SyncDataProto.SyncTPCardResponse
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

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
  override def syncTPCard(responseBuilder: SyncTPCardResponse.Builder, timestamp: Long, size: Int, dbId: Option[String]): Unit = {
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    val tableName = getTableName(getDBID(dbId), V62Facade.TID_TPCARDINFO)
    doFetcher(cardIdBuffer, timestamp, size, tableName)
    cardIdBuffer.foreach{cardId=>
      val syncTPCard = responseBuilder.addSyncTPCardBuilder()
      val tpCard = tPCardService.getTPCard(cardId._1, dbId)
      syncTPCard.setTpCard(tpCard)
      syncTPCard.setTimestamp(cardId._2)
    }
  }
  /**
   * 获取DBID
   * @param dbId
   */
  private def getDBID(dbId: Option[String]):Short={
    if(dbId == None){
      v62Config.templateTable.dbId.toShort
    }else{
      dbId.get.toShort
    }
  }
}
