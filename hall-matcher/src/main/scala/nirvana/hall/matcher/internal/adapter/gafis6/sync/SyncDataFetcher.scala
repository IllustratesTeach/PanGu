package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.sql.ResultSet
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.DataChecker
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData

/**
 * 抓取分库数据gafis6.2
 */
abstract class SyncDataFetcher(hallMatcherConfig: HallMatcherConfig , implicit val dataSource: DataSource) extends LoggerSupport{
  val MAX_SEQ_SQL: String
  val MIN_SEQ_SQL: String
  val SYNC_SQL: String
  final val FETCH_BATCH_SIZE = hallMatcherConfig.fetchBatchSize
  /**
   * 抓取同步数据
   * @param syncDataResponse
   * @param size
   * @param from
   */
  def doFetch(syncDataResponse: SyncDataResponse.Builder, size: Int, from: Long): Unit ={
    println(MIN_SEQ_SQL)
    val from_ = getMinSeq(from)
    if(from_ > 0 && from_ <= getMaxSeq){
      println(from_ +":"+getMaxSeq)
      JdbcDatabase.queryWithPsSetter2(SYNC_SQL){ps=>
        ps.setLong(1, from_)
      }{rs=>
        while (rs.next()){
          //如果下一条数据的seq与当前的最大值不一样，退出
          val count = syncDataResponse.getSyncDataCount
          val seq = rs.getLong("seq")
          if(count >= size){
            val preSeq = syncDataResponse.getSyncDataList.get(count - 1).getTimestamp
            if(preSeq < seq)
              return
          }
          readResultSet(syncDataResponse, rs, size)
        }
      }
    }
  }

  /**
   * 读取分库数据
   * @param syncDataResponse
   * @param rs
   * @param size
   */
  def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit

  /**
   * 获取最大的seq值
   * @return
   */
  private def getMaxSeq(implicit dataSource: DataSource): Long ={
    getSeqBySql(MAX_SEQ_SQL)
  }

  /**
   * 获取最小的seq值, 大于from
   * @param from
   * @return
   */
  private def getMinSeq(from: Long)(implicit dataSource: DataSource): Long ={
    getSeqBySql(MIN_SEQ_SQL + from)
  }

  private def getSeqBySql(sql: String)(implicit dataSource: DataSource):Long ={
    JdbcDatabase.queryFirst(sql){ps=>}{_.getLong(1)}.getOrElse(0)
  }

  /**
   * 数据校验
   * @param syncData
   * @param isLatent
   * @return
   */
  protected def validSyncData(syncData: SyncData, isLatent: Boolean): Boolean ={
    DataChecker.checkSyncData(hallMatcherConfig, syncData, isLatent)
  }
}
