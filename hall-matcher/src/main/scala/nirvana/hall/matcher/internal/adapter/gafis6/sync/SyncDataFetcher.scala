package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.sql.ResultSet
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.DataConverter
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.{MinutiaType, OperationType}

/**
 * Created by songpeng on 16/3/29.
 */
abstract class SyncDataFetcher(hallMatcherConfig: HallMatcherConfig , implicit val dataSource: DataSource) extends LoggerSupport{
  val MAX_SEQ_SQL: String
  val MIN_SEQ_SQL: String
  val SYNC_SQL: String
  /**
   * 抓取同步数据
   * @param syncDataResponse
   * @param size
   * @param from
   */
  def doFetch(syncDataResponse: SyncDataResponse.Builder, size: Int, from: Long): Unit ={
    val from_ = getMinSeq(from)
    if(from_ > 0 && from_ <= getMaxSeq){
      JdbcDatabase.queryWithPsSetter(SYNC_SQL){ps=>
        ps.setLong(1, from_)
        ps.setLong(2, from_ + HallMatcherConstants.FETCH_BATCH_SIZE)
      }{rs=>
        readResultSet(syncDataResponse, rs, size)
      }
      if(syncDataResponse.getSyncDataCount < size){
        doFetch(syncDataResponse, size, from_ + HallMatcherConstants.FETCH_BATCH_SIZE)
      }
    }
  }

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
    /*数据长度校验*/
    if(syncData.getMinutiaType == MinutiaType.TEXT){
      return syncData.getData.size() > 0
    }else{
      if (syncData.getData.size() <=hallMatcherConfig.mnt.headerSize && syncData.getOperationType == OperationType.PUT) {
        error("data size too small %s for sid:%s minutia_type:%s isLatent:%s".format(
          syncData.getData.size(), syncData.getObjectId, syncData.getMinutiaType.toString, isLatent))
        return false
      }
      var dataSizeExpected:Int = 0
      syncData.getMinutiaType match {
        case MinutiaType.FINGER =>
          if(isLatent){
            dataSizeExpected = hallMatcherConfig.mnt.fingerLatentSize
          }else{
            dataSizeExpected = hallMatcherConfig.mnt.fingerTemplateSize
          }
        case MinutiaType.PALM=>
          if(isLatent){
            dataSizeExpected = hallMatcherConfig.mnt.palmLatentSize
          }else{
            dataSizeExpected = hallMatcherConfig.mnt.palmTemplateSize
          }
        case MinutiaType.RIDGE=>
          dataSizeExpected = DataConverter.readGAFISIMAGESTRUCTDataLength(syncData.getData)
        case MinutiaType.FACE=>
        case MinutiaType.TEXT=>
      }
      dataSizeExpected += hallMatcherConfig.mnt.headerSize
      if (syncData.getData.size != dataSizeExpected || dataSizeExpected <= HallMatcherConstants.HEADER_LENGTH) {
        error("MinutiaType:{} isLatent:{} sid:{}  dataSize:{} expected:{}",
          syncData.getMinutiaType,
          isLatent,
          syncData.getObjectId,
          syncData.getData.size,
          dataSizeExpected)
        return false
      }
      true
    }
  }
}
