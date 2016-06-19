package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.internal.SqlUtils
import nirvana.hall.api.services.TPCardService
import nirvana.hall.api.services.sync.SyncTPCardService
import nirvana.hall.protocol.api.SyncDataProto.SyncTPCardResponse

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/18.
 */
class SyncTPCardServiceImpl(tPCardService: TPCardService,implicit val dataSource: DataSource) extends SyncDataFetcher with SyncTPCardService{
  override val MAX_SEQ_SQL: String = s"select ${SqlUtils.wrapModTimeAsLong(Some("max"))} from normaltp_tpcardinfo_mod t "
  override val MIN_SEQ_SQL: String = s"select ${SqlUtils.wrapModTimeAsLong(Some("min"))} from normaltp_tpcardinfo_mod t where ${SqlUtils.wrapModTimeAsLong()}  >"
  override val SYNC_SQL =  s"select tp.cardid as cardid, ${SqlUtils.wrapModTimeAsLong()} as seq from normaltp_tpcardinfo_mod t left join normaltp_tpcardinfo tp on tp.ora_sid= t.ora_sid where ${SqlUtils.wrapModTimeAsLong()} >=? and ${SqlUtils.wrapModTimeAsLong()} <=? order by t.modtime"
  /**
   * 同步TPCard数据
   * @param responseBuilder
   * @param timestamp
   * @param size
   * @return
   */
  override def syncTPCard(responseBuilder: SyncTPCardResponse.Builder, timestamp: Long, size: Int): Unit = {
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    doFetcher(cardIdBuffer, timestamp, size)
    cardIdBuffer.foreach{cardId=>
      val syncTPCard = responseBuilder.addSyncTPCardBuilder()
      syncTPCard.setTpCard(tPCardService.getTPCard(cardId._1))
      syncTPCard.setTimestamp(cardId._2)
    }
  }

}
