package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.internal.SqlUtils
import nirvana.hall.api.services.LPCardService
import nirvana.hall.api.services.sync.SyncLPCardService
import nirvana.hall.protocol.api.SyncDataProto.SyncLPCardResponse

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/18.
 */
class SyncLPCardServiceImpl(lPCardService: LPCardService, implicit val dataSource: DataSource) extends SyncDataFetcher with SyncLPCardService{
  override val MAX_SEQ_SQL: String = s"select ${SqlUtils.wrapModTimeAsLong(Some("max"))} from normallp_latfinger_mod t "
  override val MIN_SEQ_SQL: String = s"select ${SqlUtils.wrapModTimeAsLong(Some("min"))} from normallp_latfinger_mod t where ${SqlUtils.wrapModTimeAsLong()}  >"
  override val SYNC_SQL =  s"select tp.fingerid as cardid, ${SqlUtils.wrapModTimeAsLong()} as seq from normallp_latfinger_mod t left join normallp_latfinger tp on tp.ora_sid= t.ora_sid where ${SqlUtils.wrapModTimeAsLong()} >? and ${SqlUtils.wrapModTimeAsLong()} <=? order by t.modtime"

  /**
   * 同步LPCard数据
   * @param timestamp
   * @param size
   * @return
   */
  override def syncLPCard(responseBuilder: SyncLPCardResponse.Builder, timestamp: Long, size: Int): Unit = {
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    doFetcher(cardIdBuffer, timestamp, size)
    cardIdBuffer.foreach{cardId=>
      val syncLPCard = responseBuilder.addSyncLPCardBuilder()
      syncLPCard.setTimestamp(cardId._2)
      syncLPCard.setLpCard(lPCardService.getLPCard(cardId._1))
    }
  }
}
