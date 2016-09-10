package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.FetchLPCardService
import nirvana.hall.v62.internal.V62Facade

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/8/22.
 */
class FetchLPCardServiceImpl (implicit dataSource: DataSource) extends SyncDataFetcher with FetchLPCardService{
  override val KEY_NAME: String = "fingerid"
  /**
   * 获取案件列表
   * @param seq
   * @param size
   * @param dbId
   */
  override def fetchCardId(seq: Long, size: Int, dbId: Option[String]): Seq[(String, Long)] = {
    val cardIdList = new ArrayBuffer[(String, Long)]
    val tableName = getTableName(dbId.get.toShort, V62Facade.TID_LATFINGER)
    doFetcher(cardIdList, seq, size, tableName)
    cardIdList
  }

}
