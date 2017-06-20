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
  override val SYNC_SQL: String = "select c.case_id caseId, c.case_class_code caseClassCode, c.case_nature caseNature, c.case_occur_place_code caseOccurPlaceCode," +
    " c.suspicious_area_code suspiciousAreaCode, c.is_murder isMurder, c.assist_level assistLevel, c.case_state caseState, c.case_occur_date caseOccurDate, c.is_checked isChecked," +
    " c.case_source caseSource, c.case_occur_place_detail caseOccurPlaceDetail, c.extractor, c.extract_unit_code extractUnitCode, c.extract_unit_name extractUnitName, c.extract_date extractDate, c.broken_status brokenStatus," +
    " t.sid sid, t.cardid cardId, t.is_assist isAssist, t.seq seq, t.deletag, t.is_palm isPalm, db.logic_db_pkid logicDB, t.lt_status  ltStatus, t.creator_unit_code creatorUnitCode, t.updator_unit_code updatorUnitCode, t.inputpsn, t.inputtime, t.modifiedpsn, t.modifiedtime " +
    " from (select f.sid sid, f.case_id case_id, f.finger_id cardid, f.is_assist is_assist, f.seq seq, f.deletag, '0' as is_palm, f.lt_status, f.creator_unit_code, f.updator_unit_code, f.inputpsn, f.inputtime, f.modifiedpsn, f.modifiedtime from gafis_case_finger f   where f.seq >=? and f.seq <=?  " +
    " union all select p.sid, p.case_id case_id, p.palm_id cardid, p.is_assist is_assist,p.seq seq, p.deletag, '1' as is_palm, p.lt_status, p.creator_unit_code, p.updator_unit_code, p.inputpsn, p.inputtime, p.modifiedpsn, p.modifiedtime from gafis_case_palm p   where p.seq >=? and p.seq <=?) t " +
    " left join gafis_case c on t.case_id = c.case_id " +
    " left join gafis_logic_db_case db on db.case_pkid = c.case_id order by t.seq"
  private val caseCols: Array[String] = Array[String](COL_NAME_CASEID, COL_NAME_CARDID, COL_NAME_CASECLASSCODE, COL_NAME_CASENATURE, COL_NAME_CASEOCCURPLACECODE,
                                                      COL_NAME_SUSPICIOUSAREACODE, COL_NAME_ISMURDER, COL_NAME_ISASSIST,
                                                      COL_NAME_ASSISTLEVEL, COL_NAME_CASESTATE, COL_NAME_DELETAG,COL_NAME_ISPALM, COL_NAME_LOGICDB,
                                                      COL_NAME_ISCHECKED, COL_NAME_LTSTATUS, COL_NAME_CASESOURCE, COL_NAME_CASEOCCURPLACEDETAIL,
                                                      COL_NAME_EXTRACTOR, COL_NAME_EXTRACTUNITCODE, COL_NAME_EXTRACTUNITNAME, COL_NAME_BROKENSTATUS,
                                                      COL_NAME_CREATORUNITCODE, COL_NAME_UPDATORUNITCODE, COL_NAME_INPUTPSN,COL_NAME_MODIFIEDPSN)

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
      val caseId = rs.getString("caseId")
      if(caseId != null){
        TextQueryUtil.getColDataById(caseId, COL_NAME_CID_PRE, COL_NAME_CID_DEPT, COL_NAME_CID_DATE).foreach(textData.addCol(_))
      }

      //日期类型
      val dateCols = Array(COL_NAME_CASEOCCURDATE, COL_NAME_EXTRACTDATE,COL_NAME_INPUTTIME, COL_NAME_MODIFIEDTIME)
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
