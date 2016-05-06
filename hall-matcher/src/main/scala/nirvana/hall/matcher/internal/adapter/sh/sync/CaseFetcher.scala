package nirvana.hall.matcher.internal.adapter.sh.sync

import java.io.UnsupportedEncodingException
import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.DataConverter
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.MinutiaType
import nirvana.protocol.TextQueryProto.TextData
import nirvana.protocol.TextQueryProto.TextData.ColType

/**
 * Created by songpeng on 16/4/8.
 */
class CaseFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig ,dataSource){
  override val MAX_SEQ_SQL: String = "select max(seq) from (select max(seq) seq from gafis_case_finger union all select max(seq) seq from gafis_case_palm)"
  override val MIN_SEQ_SQL: String ="select min(seq) from (select min(seq) seq from gafis_case_finger f where f.seq >? union all select min(seq) seq from gafis_case_palm p where p.seq >? )"
  override val SYNC_SQL: String = "select c.case_id caseId, c.case_class_code caseClassCode, c.case_nature caseNature, c.case_occur_place_code caseOccurPlaceCode, c.suspicious_area_code suspiciousAreaCode, c.is_murder isMurder, c.assist_level assistLevel, c.case_state caseState, c.case_occur_date caseOccurDate, t.sid sid, t.cardid cardId, t.is_assist isAssist, t.seq seq, t.deletag, t.is_palm isPalm " +
    " from (select f.sid sid, f.case_id case_id, f.finger_id cardid, f.is_assist is_assist, f.seq seq, f.deletag, '0' as is_palm   from gafis_case_finger f   where f.seq >=? and f.seq <=?  " +
    " union all select p.sid, p.case_id case_id, p.palm_id cardid, p.is_assist is_assist,p.seq seq, p.deletag, '1' as is_palm   from gafis_case_palm p   where p.seq >=? and p.seq <=?) t " +
    " left join gafis_case c on t.case_id = c.case_id order by t.seq"
  private val caseCols: Array[String] = Array[String]("cardId", "caseClassCode", "caseNature", "caseOccurPlaceCode", "suspiciousAreaCode", "isMurder", "isAssist", "assistLevel", "caseState", "deletag", "isPalm")

  override def doFetch(syncDataResponse: SyncDataResponse.Builder, size: Int, from: Long): Unit ={
    implicit val ds = dataSource
    val from_ = getMinSeq(from)
    if(from_ > 0 && from_ <= getMaxSeq){
      JdbcDatabase.queryWithPsSetter(SYNC_SQL){ps=>
        ps.setLong(1, from_)
        ps.setLong(2, from_ + HallMatcherConstants.FETCH_BATCH_SIZE)
        ps.setLong(3, from_)
        ps.setLong(4, from_ + HallMatcherConstants.FETCH_BATCH_SIZE)
      }{rs=>
        readResultSet(syncDataResponse, rs, size)
      }
      if(syncDataResponse.getSyncDataCount < size){
        doFetch(syncDataResponse, size, from_ + HallMatcherConstants.FETCH_BATCH_SIZE)
      }
    }
  }
  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setMinutiaType(MinutiaType.TEXT)
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      syncDataBuilder.setTimestamp(rs.getLong("seq"))
      val deletag = rs.getString("deletag")
      if ("0" == deletag) {
        syncDataBuilder.setOperationType(SyncData.OperationType.DEL)
      } else {
        syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
      }

      val textData = TextData.newBuilder()
      for(col <- caseCols){
        val value = rs.getString(col)
        if (value != null) {
          try {
            textData.addColBuilder.setColName(col).setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(value.getBytes("UTF-8")))
          } catch {
            case e: UnsupportedEncodingException => {
              error("UnsupportedEncodingException:{}", col)
            }
          }
        }
      }
      /*
       * 将案件编号拆分为三部分, 由于案件编号可能包含字母，转为36进制的Long值
       * 第一部分，单位编码12位(1, 13)，
       * 第二部分，年月日6位(13,19)
       * 第三部分，流水4位(19,23)
       * */
      val caseId = rs.getString("caseId")
      if(caseId != null){
        try {
          textData.addColBuilder().setColName("caseId").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(caseId.getBytes()))
          if(caseId.length == 23){
            val cId_deptCode = caseId.substring(1, 13)
            val cId_date = caseId.substring(13,19)
            val cId_serialNum = caseId.substring(19)
            textData.addColBuilder().setColName("cId_deptCode").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(java.lang.Long.parseLong(cId_deptCode, 36))))
            textData.addColBuilder().setColName("cId_date").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(java.lang.Long.parseLong(cId_date, 36))))
            textData.addColBuilder().setColName("cId_serialNum").setColType(ColType.INT).setColValue(ByteString.copyFrom(DataConverter.int2Bytes(Integer.parseInt(cId_serialNum, 36))))
          }
        } catch {
          case e: Exception =>{
            error("caseId convert error：{}", caseId)
          }
        }
      }

      val caseOccurDate = if(rs.getDate("caseOccurDate") != null) rs.getDate("caseOccurDate").getTime() else 0
      if(caseOccurDate > 0){
        textData.addColBuilder().setColName("caseOccurDate").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(caseOccurDate)))
      }

      syncDataBuilder.setData(ByteString.copyFrom(textData.build().toByteArray()))
      if(validSyncData(syncDataBuilder.build(), true)){
        syncDataResponse.addSyncData(syncDataBuilder.build())
      }
    }
  }

  def getMaxSeq(implicit dataSource: DataSource): Long ={
    JdbcDatabase.queryFirst(MAX_SEQ_SQL){ps=>
    }{rs=>
      rs.getLong(1)
    }.getOrElse(0)
  }

  def getMinSeq(from: Long)(implicit dataSource: DataSource): Long ={
    JdbcDatabase.queryFirst(MIN_SEQ_SQL){ps=>
      ps.setLong(1, from)
      ps.setLong(2, from)
    }{rs=>
      rs.getLong(1)
    }.getOrElse(0)
  }
}
