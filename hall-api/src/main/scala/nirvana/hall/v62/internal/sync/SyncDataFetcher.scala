package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.support.services.JdbcDatabase

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/18.
 */
abstract class SyncDataFetcher(implicit dataSource: DataSource) extends LoggerSupport{
  val MAX_SEQ_SQL: String
  val MIN_SEQ_SQL: String
  val SYNC_SQL: String
  val FETCH_BATCH_SIZE = 100

  def doFetcher(cardIdBuffer: ArrayBuffer[(String, Long)], timestamp: Long, size: Int): Unit ={
    val from = getMinSeq(timestamp)
    if(from > 0 && getMaxSeq >= from){
      JdbcDatabase.queryWithPsSetter2(SYNC_SQL){ps=>
        ps.setLong(1, from)
        ps.setLong(2, from + FETCH_BATCH_SIZE)
      }{rs=>
        while (rs.next()){
          if(cardIdBuffer.length >= size){
            val seq = rs.getLong("seq")
            val lastSeq = cardIdBuffer.last._2
            if(lastSeq < seq){
              return
            }
          }
          cardIdBuffer += (rs.getString("cardid") -> rs.getLong("seq"))
        }
        if(cardIdBuffer.length < size){
          doFetcher(cardIdBuffer, from + FETCH_BATCH_SIZE, size)
        }
      }
    }
  }
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
}
