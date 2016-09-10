package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.support.services.JdbcDatabase

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/18.
 */
abstract class SyncDataFetcher(implicit dataSource: DataSource) extends LoggerSupport{
  /**
   * 卡号(tablecatlog.keyname),用来查询数据信息
   */
  val KEY_NAME: String
  val FETCH_BATCH_SIZE = 100

  def doFetcher(cardIdBuffer: ArrayBuffer[(String, Long)], seq: Long, size: Int, tableName: String): Unit ={
    val from = getMinSeq(seq, tableName)
    if(from > 0 && getMaxSeq(tableName) >= from){
      JdbcDatabase.queryWithPsSetter2(getSyncSql(tableName, KEY_NAME)){ps=>
        ps.setLong(1, from)
        ps.setLong(2, from + FETCH_BATCH_SIZE)
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
          //存在对应不上卡号的数据,可能数据删除了, 卡号存在空格的情况
          if(cardid != null && cardid.trim.length > 0){
            cardIdBuffer += (cardid -> seq)
          }
        }
        if(cardIdBuffer.length < size){
          doFetcher(cardIdBuffer, from + FETCH_BATCH_SIZE, size, tableName)
        }
      }
    }
  }
  /**
   * 获取最大的seq值
   * @return
   */
  private def getMaxSeq(tableName: String): Long = {
    val sql = s"select max(seq) from hall_${tableName} t "
    getSeqBySql(sql)
  }

  /**
   * 获取最小的seq值, 大于from
   * @return
   */
  private def getMinSeq(from: Long, tableName: String): Long = {
    val sql = s"select min(seq) from hall_${tableName} t where seq > ${from}"
    getSeqBySql(sql)
  }

  private def getSeqBySql(sql: String)(implicit dataSource: DataSource):Long ={
    JdbcDatabase.queryFirst(sql){ps=>}{_.getLong(1)}.getOrElse(0)
  }

  def getSyncSql(tableName: String, cardId: String): String ={
    s"select sid, seq from hall_${tableName} t where seq >=? and seq <=? order by seq"
  }

  /**
   * 获取表名
   * @param dbId
   * @param tableId
   * @return
   */
  def getTableName(dbId: Short, tableId: Short): String={
    val sql = "select t.TABLENAME from TABLECATLOG t where t.DBID =? and t.TABLEID =?"
    JdbcDatabase.queryFirst(sql){ps=>
      ps.setInt(1, dbId)
      ps.setInt(2, tableId)
    }{_.getString(1)}.get
  }

}
