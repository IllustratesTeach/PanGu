package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.internal.SqlUtils
import nirvana.hall.support.services.JdbcDatabase

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/18.
 */
abstract class SyncDataFetcher(implicit dataSource: DataSource) extends LoggerSupport{
  //卡号(tablecatlog.keyname),用来查询数据信息
  val KEY_NAME: String
  val FETCH_BATCH_SIZE = 100

  def doFetcher(cardIdBuffer: ArrayBuffer[(String, Long)], timestamp: Long, size: Int, tableName: String): Unit ={
    val from = getMinSeq(timestamp, tableName)
    if(from > 0 && getMaxSeq(tableName) >= from){
      JdbcDatabase.queryWithPsSetter2(getSyncSql(tableName, KEY_NAME)){ps=>
        ps.setLong(1, from)
        ps.setLong(2, from + FETCH_BATCH_SIZE)
      }{rs=>
        while (rs.next()){
          val seq = rs.getLong("seq")
          if(cardIdBuffer.length >= size){
            val lastSeq = cardIdBuffer.last._2
            if(lastSeq < seq){
              return
            }
          }
          val cardid = rs.getString("cardid")
          if(cardid != null){//存在对应不上卡号的数据,可能数据删除了
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
    val sql = s"select ${SqlUtils.wrapModTimeAsLong(Some("max"))} from ${tableName}_mod t "
    getSeqBySql(sql)
  }

  /**
   * 获取最小的seq值, 大于from
   * @return
   */
  private def getMinSeq(from: Long, tableName: String): Long = {
    val sql = s"select ${SqlUtils.wrapModTimeAsLong(Some("min"))} from ${tableName}_mod t where ${SqlUtils.wrapModTimeAsLong()} > ${from}"
    getSeqBySql(sql)
  }

  private def getSeqBySql(sql: String)(implicit dataSource: DataSource):Long ={
    JdbcDatabase.queryFirst(sql){ps=>}{_.getLong(1)}.getOrElse(0)
  }

  def getSyncSql(tableName: String, cardId: String): String ={
    s"select tp.${KEY_NAME} as cardid, ${SqlUtils.wrapModTimeAsLong()} as seq from ${tableName}_mod t left join ${tableName} tp on tp.ora_sid= t.ora_sid where ${SqlUtils.wrapModTimeAsLong()} >=? and ${SqlUtils.wrapModTimeAsLong()} <=? order by seq"
  }

  /**
   * 获取表名
   * @param dBConfig
   * @return
   */
  def getTableName(dBConfig: DBConfig): String={
    val sql = "select t.TABLENAME from TABLECATLOG t where t.DBID =? and t.TABLEID =?"
    JdbcDatabase.queryFirst(sql){ps=>
      ps.setInt(1, dBConfig.dbId.left.get)
      ps.setInt(2, dBConfig.tableId.get)
    }{_.getString(1)}.get
  }

}
