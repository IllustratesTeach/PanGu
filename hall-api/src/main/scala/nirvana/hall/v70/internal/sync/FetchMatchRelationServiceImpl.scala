package nirvana.hall.v70.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.FetchMatchRelationService
import nirvana.hall.protocol.fpt.MatchRelationProto.MatchSysInfo

import scala.collection.mutable.ArrayBuffer

/**
  * Created by yuchen on 2017/6/27.
  */
class FetchMatchRelationServiceImpl(implicit dataSource: DataSource) extends SyncDataFetcher with  FetchMatchRelationService{
  override val SYNC_SQL: String = s"SELECT t.pk_id,t.seq FROM HALL_MATCHRELATION_READ_LIST t WHERE t.seq >=? AND t.seq <=?"

  /**
    * 获取最大的seq值
    *
    * @return
    */
  override def getMaxSeq(dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"SELECT MAX(seq) FROM  HALL_MATCHRELATION_READ_LIST")
  }

  /**
    * 获取最小的seq值, 大于from
    *
    * @param from
    * @return
    */
override def getMinSeq(from: Long, dbId: Option[String])(implicit dataSource: DataSource): Long = {
    getSeqBySql(s"SELECT MIN(seq) FROM HALL_MATCHRELATION_READ_LIST t WHERE t.seq > ${from}")
  }

  /**
    * 获取比中关系pkid列表
    *
    * @param seq
    * @param size
    * @param dbId
    */
  override def fetchPkId(seq: Long, size: Int, dbId: Option[String]): Seq[(String, Long)] = {
    val pKIdList = new ArrayBuffer[(String, Long)]()
    doFetcherMatchRelation(pKIdList, seq, size, dbId)
    pKIdList
  }

  /**
    * 验证读取策略
    *
    * @param matchSysInfo 比中信息
    * @param readStrategy
    * @return
    */
  override def validateByReadStrategy(matchSysInfo: MatchSysInfo, readStrategy: String): Boolean = {true}
}
