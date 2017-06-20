package nirvana.hall.matcher.internal.adapter.gz.sync

import java.io.UnsupportedEncodingException
import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.{DataConverter, TextQueryUtil}
import nirvana.hall.matcher.internal.adapter.SyncDataFetcher
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.MinutiaType
import nirvana.protocol.TextQueryProto.TextData
import nirvana.protocol.TextQueryProto.TextData.ColType
import nirvana.hall.matcher.internal.TextQueryConstants._

/**
 * Created by songpeng on 16/4/8.
 */
class CaseFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig ,dataSource){
  override val MAX_SEQ_SQL: String = "select max(seq) from (select max(seq) seq from gafis_case_finger union all select max(seq) seq from gafis_case_palm)"
  override val MIN_SEQ_SQL: String ="select min(seq) from (select min(seq) seq from gafis_case_finger f where f.seq >? union all select min(seq) seq from gafis_case_palm p where p.seq >? )"
  override val SYNC_SQL: String = "select c.case_id " + COL_NAME_CASEID
    ", c.case_class_code " + COL_NAME_CASECLASSCODE
    ", c.case_nature " + COL_NAME_CASENATURE
    ", c.case_occur_place_code " + COL_NAME_CASEOCCURPLACECODE
    ", c.suspicious_area_code " + COL_NAME_SUSPICIOUSAREACODE
    ", c.is_murder " + COL_NAME_ISMURDER
    ", c.assist_level " + COL_NAME_ASSISTLEVEL
    ", c.case_state " + COL_NAME_CASESTATE
    ", c.case_occur_date " + COL_NAME_CASEOCCURDATE
    ", t.cardid " + COL_NAME_CARDID
    ", t.is_assist " + COL_NAME_ISASSIST
    ", t.sid sid" +
    ", t.seq seq" +
    ", t.deletag" +
    ", t.is_palm isPalm " +
    " from (select f.sid sid, f.case_id case_id, f.finger_id cardid, f.is_assist is_assist, f.seq seq, f.deletag, '0' as is_palm   from gafis_case_finger f   where f.seq >=? and f.seq <=?  " +
    " union all select p.sid, p.case_id case_id, p.palm_id cardid, p.is_assist is_assist,p.seq seq, p.deletag, '1' as is_palm   from gafis_case_palm p   where p.seq >=? and p.seq <=?) t " +
    " left join gafis_case c on t.case_id = c.case_id order by t.seq"
  private val caseCols: Array[String] = Array[String](COL_NAME_CARDID, COL_NAME_CASECLASSCODE, COL_NAME_CASENATURE, COL_NAME_CASEOCCURPLACECODE, COL_NAME_SUSPICIOUSAREACODE, COL_NAME_ISMURDER, COL_NAME_ISASSIST, COL_NAME_ASSISTLEVEL, COL_NAME_CASESTATE, COL_NAME_DELETAG, COL_NAME_ISPALM)

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
      //案件编号
      val caseId = rs.getString(COL_NAME_CASEID)
      if(caseId != null){
        TextQueryUtil.getColDataById(caseId, COL_NAME_CID_PRE, COL_NAME_CID_DEPT, COL_NAME_CID_DATE).foreach(textData.addCol(_))
      }

      val caseOccurDate = if(rs.getDate(COL_NAME_CASEOCCURDATE) != null) rs.getDate(COL_NAME_CASEOCCURDATE).getTime() else 0
      if(caseOccurDate > 0){
        textData.addColBuilder().setColName(COL_NAME_CASEOCCURDATE).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(caseOccurDate)))
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
