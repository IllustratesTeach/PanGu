package nirvana.hall.v70.internal.sync

import javax.sql.DataSource

import nirvana.hall.support.services.JdbcDatabase

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/8/4.
 */
abstract class SyncDataFetcher(implicit dataSource: DataSource) {
  val SYNC_SQL: String
  val FETCH_BATCH_SIZE = 10

  def doFetcher(cardIdBuffer: ArrayBuffer[(String, Long)], seq: Long, size: Int, dbId: Option[String]): Unit ={
    val from = getMinSeq(seq, dbId)
    if(from >0 && from <= getMaxSeq(dbId)){
      JdbcDatabase.queryWithPsSetter2(SYNC_SQL){ps=>
        ps.setString(1, dbId.get)
        ps.setLong(2, from)
        ps.setLong(3, from + FETCH_BATCH_SIZE)
      }{rs=>
        while (rs.next()){
          val cardid = rs.getString("sid")
          val seq = rs.getLong("seq")
          if(cardIdBuffer.length >= size){
            val lastSeq = cardIdBuffer.last._2
            if(lastSeq < seq){
              return
            }
          }
          cardIdBuffer += (cardid -> seq)
        }
        if(cardIdBuffer.length < size){
          doFetcher(cardIdBuffer, from + FETCH_BATCH_SIZE, size, dbId)
        }
      }

    }
  }

  /**
   * 获取最大的seq值
   * @return
   */
  def getMaxSeq(dbId: Option[String])(implicit dataSource: DataSource): Long

  /**
   * 获取最小的seq值, 大于from
   * @param from
   * @return
   */
  def getMinSeq(from: Long, dbId: Option[String])(implicit dataSource: DataSource): Long

  protected def getSeqBySql(sql: String)(implicit dataSource: DataSource):Long ={
    JdbcDatabase.queryFirst(sql){ps=>}{_.getLong(1)}.getOrElse(0)
  }
}
