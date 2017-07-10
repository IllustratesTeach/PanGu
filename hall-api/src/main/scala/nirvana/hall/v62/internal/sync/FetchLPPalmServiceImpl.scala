package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.FetchLPPalmService
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v62.internal.V62Facade

import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 16/8/22.
  */
class FetchLPPalmServiceImpl(implicit dataSource: DataSource) extends SyncDataFetcher with FetchLPPalmService{
   override val KEY_NAME: String = "palmid"
   /**
    * 获取案件列表
     *
     * @param seq
    * @param size
    * @param dbId
    */
   override def fetchCardId(seq: Long, size: Int, dbId: Option[String]): Seq[(String, Long)] = {
     val cardIdList = new ArrayBuffer[(String, Long)]
     val tableName = getTableName(dbId.get.toShort, V62Facade.TID_LATPALM)
     doFetcher(cardIdList, seq, size, tableName)
     cardIdList
   }

  /**
    * 验证读取策略
    *
    * @param lPCard
    * @param readStrategy
    * @return
    */
  override def validateByReadStrategy(lPCard: LPCard, readStrategy: String): Boolean = {true}
}
