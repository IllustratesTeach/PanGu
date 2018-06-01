package nirvana.hall.matcher.internal.adapter.nj.sync

import java.io.UnsupportedEncodingException
import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.TextQueryConstants._
import nirvana.hall.matcher.internal.adapter.SyncDataFetcher
import nirvana.hall.matcher.internal.{DataConverter, TextQueryUtil}
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
  override val SYNC_SQL: String = s"select c.CASE_ID " + COL_NAME_CASEID +
    s", t.CARDID " + COL_NAME_CARDID +
    s", c.CASE_OCCUR_DATE " + COL_NAME_CASEOCCURDATE +
    s", c.CASE_OCCUR_PLACE_CODE " +  COL_NAME_CASEOCCURPLACECODE +
    s", c.CASE_OCCUR_PLACE_DETAIL " + COL_NAME_CASEOCCURPLACEDETAIL +
    s", c.CASE_BRIEF_DETAIL " + COL_NAME_CASEBRIEFDETAIL +
    s", c.IS_MURDER " + COL_NAME_ISMURDER +
    s", c.EXTRACT_UNIT_CODE " + COL_NAME_EXTRACTUNITCODE +
    s", c.EXTRACT_UNIT_NAME " + COL_NAME_EXTRACTUNITNAME +
    s", c.EXTRACT_DATE " + COL_NAME_EXTRACTDATE +
    s", c.SUSPICIOUS_AREA_CODE " + COL_NAME_SUSPICIOUSAREACODE +
    s", c.CASE_STATE " + COL_NAME_CASESTATE +
    s", c.INPUTTIME " + COL_NAME_INPUTTIME +
    s", c.MODIFIEDTIME " + COL_NAME_MODIFIEDTIME +
    s", c.ASSIST_LEVEL " + COL_NAME_ASSISTLEVEL +
    s", c.ASSIST_LEVEL " + COL_NAME_ASSISTLEVEL +
    s", c.CASE_CLASS_CODE " + COL_NAME_CASECLASSCODE +
    s", c.CASE_CLASS_CODE2 " + COL_NAME_CASECLASSCODE2 +
    s", c.CASE_CLASS_CODE3 " + COL_NAME_CASECLASSCODE3 +
    s", c.REMARK " + COL_NAME_REMARK +
    s", t.SID " +
    s", t.SEQ " +
    s", c.DELETAG " +
//    s", c.INPUTPSN " +
//    s", c.MODIFIEDPSN " +
//    s", c.BROKEN_STATUs" +
//    s", c.CASE_SOURCE " +
//    s", c.CREATE_UNIT_CODE " +
//    s", c.AMOUNT " +
//    s", c.EXTRACTOR " +
//    s", c.IS_CHECKED " +
//    s", c.ASSIST_BONUs" +
//    s", c.ASSIST_DEPT_CODE " +
//    s", c.ASSIST_DEPT_NAME " +
//    s", c.ASSIST_DATE " +
//    s", c.ASSIST_SIGN " +
//    s", c.ASSIST_REVOKE_SIGN " +
//    s", c.CS_NO " +
//    s", c.PSIS_NO " +
//    s", c.THAN_STATE_TL " +
//    s", c.THAN_STATE_LT " +
//    s", c.THAN_STATE_LL " +
//    s", c.SUSPICIOUS_AREA_CODE2 " +
//    s", c.SUSPICIOUS_AREA_CODE3 " +
//    s", t.IS_ASSIST " +
//    s", t.DELETAG " +
//    s", t.IS_PALM " +
//    s", t.LT_STATUs" +
//    s", t.CREATOR_UNIT_CODE " +
//    s", t.UPDATOR_UNIT_CODE " +
    //    s", t.INPUTPSN " +
    //    s", t.INPUTTIME " +
    //    s", t.MODIFIEDPSN " +
    //    s", t.MODIFIEDTIME " +
    s", db.LOGIC_DB_PKID " + COL_NAME_LOGICDB +
    " FROM (SELECT f.sid sid, f.case_id case_id, f.finger_id cardid, f.is_assist is_assist, f.seq seq, f.deletag, '0' as is_palm, f.lt_status, f.creator_unit_code, f.updator_unit_code, f.inputpsn, f.inputtime, f.modifiedpsn, f.modifiedtime from gafis_case_finger f   where f.seq >=? and f.seq <=?  " +
    " UNION ALL SELECT p.sid, p.case_id case_id, p.palm_id cardid, p.is_assist is_assist,p.seq seq, p.deletag, '1' as is_palm, p.lt_status, p.creator_unit_code, p.updator_unit_code, p.inputpsn, p.inputtime, p.modifiedpsn, p.modifiedtime from gafis_case_palm p   where p.seq >=? and p.seq <=?) t " +
    " LEFT JOIN gafis_case c ON t.case_id = c.case_id " +
    " LEFT JOIN gafis_logic_db_case db ON db.case_pkid = c.case_id order by t.seq"

  private val caseCols: Array[String] = Array[String](COL_NAME_LOGICDB,COL_NAME_CASECLASSCODE,COL_NAME_CASECLASSCODE2,COL_NAME_CASECLASSCODE3,
    COL_NAME_CARDID,COL_NAME_CASEOCCURPLACECODE,COL_NAME_CASEOCCURPLACEDETAIL,COL_NAME_ISMURDER,COL_NAME_EXTRACTUNITCODE,
    COL_NAME_EXTRACTUNITNAME,COL_NAME_EXTRACTDATE,COL_NAME_SUSPICIOUSAREACODE,COL_NAME_CASESTATE,COL_NAME_ASSISTLEVEL,
    COL_NAME_REMARK,COL_NAME_CASEBRIEFDETAIL)

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
        TextQueryUtil.getColDataByCaseid(caseId).foreach(textData.addCol(_))
      }

      //日期类型
      val dateCols = Array(COL_NAME_CASEOCCURDATE, COL_NAME_EXTRACTDATE,COL_NAME_INPUTTIME,COL_NAME_MODIFIEDTIME)
      for (col <- dateCols) {
        val value = rs.getDate(col)
        val time = if (value != null) value.getTime else 0
        if (time > 0) {
          textData.addColBuilder.setColName(col).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(time)))
        }
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
