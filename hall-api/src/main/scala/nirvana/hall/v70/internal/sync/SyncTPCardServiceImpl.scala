package nirvana.hall.v70.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.TPCardService
import nirvana.hall.api.services.sync.SyncTPCardService
import nirvana.hall.protocol.api.SyncDataProto.SyncTPCardResponse

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/8/1.
 */
class SyncTPCardServiceImpl(tPCardService: TPCardService, implicit val dataSource: DataSource) extends SyncDataFetcher with SyncTPCardService{
  override val SYNC_SQL: String = "select t.personid cardid, t.seq seq from gafis_person t where t.seq >=? and t.seq <=?"

  /**
   * 同步TPCard数据
   * @param responseBuilder
   * @param timestamp
   * @param size
   * @return
   */
  override def syncTPCard(responseBuilder: SyncTPCardResponse.Builder, timestamp: Long, size: Int, dbId: Option[String]): Unit = {
    val cardIdBuffer = new ArrayBuffer[(String, Long)]()
    doFetcher(cardIdBuffer, timestamp, size, dbId)
    cardIdBuffer.foreach{cardId =>
      val syncTPCard = responseBuilder.addSyncTPCardBuilder()
      val tpCard = tPCardService.getTPCard(cardId._1, dbId)
      syncTPCard.setTpCard(tpCard)
      syncTPCard.setTimestamp(cardId._2)
    }
  }

  /**
   * 获取最大的seq值
   * @return
   */
  override def getMaxSeq(dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"select max(t.seq) from gafis_person t left join gafis_logic_db_fingerprint db on t.personid=db.fingerprint_pkid and db.logic_db_pkid='${dbId.get}'")
  }


  /**
   * 获取最小的seq值, 大于from
   * @param from
   * @return
   */
  override def getMinSeq(from: Long, dbId: Option[String])(implicit dataSource: DataSource): Long = {
    val sql = s"select min(t.seq) from gafis_person t left join gafis_logic_db_fingerprint db on t.personid=db.fingerprint_pkid and db.logic_db_pkid='${dbId.get}' where t.seq >${from}"
    getSeqBySql(sql)
  }
}
