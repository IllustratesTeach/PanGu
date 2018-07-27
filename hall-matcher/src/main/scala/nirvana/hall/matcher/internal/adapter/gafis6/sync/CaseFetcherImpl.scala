package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.io.UnsupportedEncodingException
import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.HallMatcherSymobls
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.{DataConverter, TextQueryConstants, TextQueryUtil}
import nirvana.hall.matcher.service.CaseFetcher
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.{MinutiaType, OperationType}
import nirvana.protocol.TextQueryProto.TextData
import nirvana.protocol.TextQueryProto.TextData.ColType

/**
  * Created by songpeng on 2017/12/4.
  */
class CaseFetcherImpl(hallMatcherConfig: HallMatcherConfig, override implicit val dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource) with CaseFetcher{
  private val fast = hallMatcherConfig.module.equals(HallMatcherSymobls.MODULE_GAFIS6FAST)
  override val MAX_SEQ_SQL: String = if(fast){
    s"select max(seq) from normallp_latfinger_seq t "
  }else{
    s"select ${wrapModTimeAsLong(Some("max"))}  from normallp_latfinger_mod t "
  }
  override val MIN_SEQ_SQL: String = if(fast){
    s"select min(seq) from normallp_latfinger_seq t where seq >"
  }else{
    s"select ${wrapModTimeAsLong(Some("min"))}  from normallp_latfinger_mod t where ${wrapModTimeAsLong()}  >"
  }
  override val SYNC_SQL = if (fast) {
    s"select t.ora_sid as sid, seq from normallp_latfinger_seq t where seq >=? and seq<=? order by seq"
  } else {
    s"select t.ora_sid as sid, ${wrapModTimeAsLong()} as seq from normallp_latfinger_mod t where ${wrapModTimeAsLong()} >=? and ${wrapModTimeAsLong()} <=? order by seq"
  }

  val SELECT_LPCARD_TEXT_SQL: String = s"select t.ora_sid as sid " +
    s", t.FingerID " + TextQueryConstants.COL_NAME6_FINGERID +
    s", t.CaseID " + TextQueryConstants.COL_NAME6_CASEID +
    s", t.CreateUserName " + TextQueryConstants.COL_NAME6_CREATEUSERNAME +
    s", t.UpdateUserName " + TextQueryConstants.COL_NAME6_UPDATEUSERNAME +
    s", t.RemainPlace " + TextQueryConstants.COL_NAME6_REMAINPLACE +
    s", t.CaptureMethod " + TextQueryConstants.COL_NAME6_CAPTUREMETHOD +
    s", t.CreatorUnitCode " + TextQueryConstants.COL_NAME6_CREATORUNITCODE +
    s", t.BrokenUser " + TextQueryConstants.COL_NAME6_BROKENUSER +
    s", t.BrokenUnitCode " + TextQueryConstants.COL_NAME6_BROKENUNITCODE +
    s", t.BrokenDate " + TextQueryConstants.COL_NAME6_BROKENDATE +
    s", t.HitPersonState " + TextQueryConstants.COL_NAME6_HITPERSONSTATE +
    s", t.MicbUpdatorUserName " + TextQueryConstants.COL_NAME6_MISCONNECTCASEID +
    s", t.PersonID " + TextQueryConstants.COL_NAME6_PERSONID +
    s", t.GroupName " + TextQueryConstants.COL_NAME6_GROUPNAME +
    s", c.MISConnectCaseID " + TextQueryConstants.COL_NAME6_MISCONNECTCASEID +
    s", c.CaseOccurDate " + TextQueryConstants.COL_NAME6_CASEOCCURDATE +
    s", c.CaseOccurPlaceTail " + TextQueryConstants.COL_NAME6_CASEOCCURPLACETAIL +
    s", c.CaseOccurPlaceCode " + TextQueryConstants.COL_NAME6_CASEOCCURPLACECODE +
    s", c.ExtractUnitNameTail " + TextQueryConstants.COL_NAME6_EXTRACTUNITNAMETAIL +
    s", c.ExtractUnitCode " + TextQueryConstants.COL_NAME6_EXTRACTUNITCODE +
    s", c.Extractor1 " + TextQueryConstants.COL_NAME6_EXTRACTOR1 +
    s", c.SuperviseLevel " + TextQueryConstants.COL_NAME6_SUPERVISELEVEL +
    s" from normallp_latfinger t left join normallp_case c on t.caseid=c.caseid where t.ora_sid =?"
  val textColums: Array[String] = Array(
      TextQueryConstants.COL_NAME6_FINGERID,
      TextQueryConstants.COL_NAME6_CREATEUSERNAME,
      TextQueryConstants.COL_NAME6_UPDATEUSERNAME,
      TextQueryConstants.COL_NAME6_REMAINPLACE,
      TextQueryConstants.COL_NAME6_CAPTUREMETHOD,
      TextQueryConstants.COL_NAME6_CREATORUNITCODE,
      TextQueryConstants.COL_NAME6_BROKENUSER,
      TextQueryConstants.COL_NAME6_BROKENUNITCODE,
      TextQueryConstants.COL_NAME6_HITPERSONSTATE,
      TextQueryConstants.COL_NAME6_MISCONNECTCASEID,
      TextQueryConstants.COL_NAME6_PERSONID,
      TextQueryConstants.COL_NAME6_GROUPNAME,
      TextQueryConstants.COL_NAME6_MISCONNECTCASEID,
      TextQueryConstants.COL_NAME6_CASEOCCURPLACETAIL,
      TextQueryConstants.COL_NAME6_CASEOCCURPLACECODE,
      TextQueryConstants.COL_NAME6_EXTRACTUNITNAMETAIL,
      TextQueryConstants.COL_NAME6_EXTRACTUNITCODE,
      TextQueryConstants.COL_NAME6_EXTRACTOR1,
      TextQueryConstants.COL_NAME6_SUPERVISELEVEL
  )

  /**
    * 读取分库数据
    *
    * @param syncDataResponse
    * @param rs
    * @param size
    */
  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    val sid = rs.getInt("sid")
    val seq = rs.getLong("seq")
    JdbcDatabase.queryFirst(SELECT_LPCARD_TEXT_SQL){ps=>
      ps.setInt(1, sid)
    } { rs =>
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setObjectId(sid)
      syncDataBuilder.setTimestamp(seq)
      syncDataBuilder.setMinutiaType(MinutiaType.TEXT)
      syncDataBuilder.setOperationType(OperationType.PUT)

      val textData = TextData.newBuilder()
      textColums.foreach{col=>
        val value = rs.getString(col)
        if(value != null){
          try {
            textData.addColBuilder().setColName(col).setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(value.getBytes("UTF-8")))
          } catch {
            case e: UnsupportedEncodingException => {
              error("UnsupportedEncodingException:{}", col)
            }
          }
        }
      }
      //其他字段
      val caseOccorDate = rs.getString(TextQueryConstants.COL_NAME6_CASEOCCURDATE)
      if(caseOccorDate != null && caseOccorDate.length > 0){
        textData.addColBuilder().setColName(TextQueryConstants.COL_NAME6_CASEOCCURDATE).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(caseOccorDate.toLong)))
      }
      val brokenDate= rs.getString(TextQueryConstants.COL_NAME6_BROKENDATE)
      if(brokenDate != null && brokenDate.length > 0){
        textData.addColBuilder().setColName(TextQueryConstants.COL_NAME6_BROKENDATE).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(brokenDate.toLong)))
      }
      //案件编号
      val caseId = rs.getString(TextQueryConstants.COL_NAME6_CASEID)
      if(caseId != null){
        TextQueryUtil.getColDataByCaseid(caseId).foreach(textData.addCol)
      }else{
        //如果案件编号不存在，使用指纹卡号去掉后两位
        val fingerId = rs.getString(TextQueryConstants.COL_NAME6_FINGERID)
        TextQueryUtil.getColDataByCaseid(fingerId.substring(0, fingerId.length-2)).foreach(textData.addCol)
      }

      syncDataBuilder.setData(ByteString.copyFrom(textData.build.toByteArray))
      syncDataResponse.addSyncData(syncDataBuilder)
    }
  }
}
