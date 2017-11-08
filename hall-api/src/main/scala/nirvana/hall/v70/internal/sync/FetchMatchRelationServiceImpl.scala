package nirvana.hall.v70.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.FetchMatchRelationService
import nirvana.hall.protocol.fpt.MatchRelationProto.MatchSysInfo
import nirvana.hall.support.services.JdbcDatabase

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
    * 同步比中关系
    * @param PkIdBuffer
    * @param seq
    * @param size
    * @param dbId
    */
  private def doFetcherMatchRelation(PkIdBuffer: ArrayBuffer[(String, Long)], seq: Long, size: Int, dbId: Option[String]): Unit ={
    val from = getMinSeq(seq, dbId)
    if(from >0 && from <= getMaxSeq(dbId)){
      JdbcDatabase.queryWithPsSetter2(SYNC_SQL){ps=>
        ps.setLong(1, from)
        ps.setLong(2, from + FETCH_BATCH_SIZE)
      }{rs=>
        while (rs.next()){
          val pk_id = rs.getString("PK_ID")
          val seq = rs.getLong("SEQ")
          if(PkIdBuffer.length >= size){
            val lastSeq = PkIdBuffer.last._2
            if(lastSeq < seq){
              return
            }
          }
          PkIdBuffer += (pk_id -> seq)
        }
        if(PkIdBuffer.length < size){
          doFetcher(PkIdBuffer, from + FETCH_BATCH_SIZE, size, dbId)
        }
      }

    }
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
