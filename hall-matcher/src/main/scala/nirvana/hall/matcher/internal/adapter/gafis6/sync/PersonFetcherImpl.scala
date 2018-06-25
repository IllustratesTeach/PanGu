package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.io.UnsupportedEncodingException
import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.{DataConverter, TextQueryConstants, TextQueryUtil}
import nirvana.hall.matcher.service.PersonFetcher
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.MinutiaType
import nirvana.protocol.TextQueryProto.TextData
import nirvana.protocol.TextQueryProto.TextData.ColType

/**
  * Created by songpeng on 2017/11/25.
  */
class PersonFetcherImpl(hallMatcherConfig: HallMatcherConfig, override implicit val dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource) with PersonFetcher{
  override val MAX_SEQ_SQL: String = s"select ${wrapModTimeAsLong(Some("max"))} from normaltp_tpcardinfo_mod t "
  override val MIN_SEQ_SQL: String = s"select ${wrapModTimeAsLong(Some("min"))} from normaltp_tpcardinfo_mod t where ${wrapModTimeAsLong()}  >"
  override val SYNC_SQL =  s"select t.ora_sid as sid, ${wrapModTimeAsLong()} as seq from normaltp_tpcardinfo_mod t where ${wrapModTimeAsLong()} >=? and ${wrapModTimeAsLong()} <=? order by seq"

  val SELECT_TPCARD_TEXT_SQL = "select ora_sid " +
    ", cardid as " + TextQueryConstants.COL_NAME6_CARDID +
    ", name as " + TextQueryConstants.COL_NAME6_NAME +
    ", NamePinYin as " + TextQueryConstants.COL_NAME6_NAMEPINYIN +
    ", Alias as " + TextQueryConstants.COL_NAME6_ALIAS +
    ", sexcode as " + TextQueryConstants.COL_NAME6_SEXCODE +
    ", BirthDate as " + TextQueryConstants.COL_NAME6_BIRTHDATE +
    ", ShenFenID as " + TextQueryConstants.COL_NAME6_SHENFENID +
    ", RaceCode as " + TextQueryConstants.COL_NAME6_RACECODE +
    ", MISPersonID as " + TextQueryConstants.COL_NAME6_MISPERSONID +
    ", CaseClass1Code as " + TextQueryConstants.COL_NAME6_CASECLASS1CODE +
    ", CaseClass2Code as " + TextQueryConstants.COL_NAME6_CASECLASS2CODE +
    ", CaseClass3Code as " + TextQueryConstants.COL_NAME6_CASECLASS3CODE +
    ", AddressCode as " + TextQueryConstants.COL_NAME6_ADDRESSCODE +
    ", AddressTail as " + TextQueryConstants.COL_NAME6_ADDRESSTAIL +
    ", HuKouPlaceCode as " + TextQueryConstants.COL_NAME6_HUKOUPLACECODE +
    ", HuKouPlaceTail as " + TextQueryConstants.COL_NAME6_HUKOUPLACECODE +
    ", PersonState as " + TextQueryConstants.COL_NAME6_PERSONSTATE +
    ", PrintDate as " + TextQueryConstants.COL_NAME6_PRINTDATE +
    ", PrinterUnitNameTail as " + TextQueryConstants.COL_NAME6_PRINTERUNITNAMETAIL +
    ", PrinterUnitCode as " + TextQueryConstants.COL_NAME6_PRINTERUNITCODE +
    ", PrinterName as " + TextQueryConstants.COL_NAME6_PRINTERNAME +
    ", CreateTime as " + TextQueryConstants.COL_NAME6_CREATETIME +
    ", createusername as " + TextQueryConstants.COL_NAME6_CREATEUSERNAME +
    ", updateusername as " + TextQueryConstants.COL_NAME6_UPDATEUSERNAME +
    ", CreatorUnitCode as " + TextQueryConstants.COL_NAME6_CREATORUNITCODE +
    ", UpdatorUnitCode as " + TextQueryConstants.COL_NAME6_UPDATORUNITCODE +
    ", MicbUpdatorUserName as " + TextQueryConstants.COL_NAME6_MICBUPDATORUSERNAME +
    ", MicbUpdatorUnitCode as " + TextQueryConstants.COL_NAME6_MICBUPDATORUNITCODE +
    "  from normaltp_tpcardinfo t where t.ora_sid =? "
  val textColums: Array[String] = Array(
      TextQueryConstants.COL_NAME6_NAME,
      TextQueryConstants.COL_NAME6_NAMEPINYIN,
      TextQueryConstants.COL_NAME6_ALIAS,
      TextQueryConstants.COL_NAME6_SEXCODE,
//      TextQueryConstants.COL_NAME6_BIRTHDATE,+
      TextQueryConstants.COL_NAME6_SHENFENID,
      TextQueryConstants.COL_NAME6_RACECODE,
      TextQueryConstants.COL_NAME6_MISPERSONID,
      TextQueryConstants.COL_NAME6_CASECLASS1CODE,
      TextQueryConstants.COL_NAME6_CASECLASS2CODE,
      TextQueryConstants.COL_NAME6_CASECLASS3CODE,
      TextQueryConstants.COL_NAME6_ADDRESSCODE,
      TextQueryConstants.COL_NAME6_ADDRESSTAIL,
      TextQueryConstants.COL_NAME6_HUKOUPLACECODE,
      TextQueryConstants.COL_NAME6_HUKOUPLACECODE,
      TextQueryConstants.COL_NAME6_PERSONSTATE,
//      TextQueryConstants.COL_NAME6_PRINTDATE,
      TextQueryConstants.COL_NAME6_PRINTERUNITNAMETAIL,
      TextQueryConstants.COL_NAME6_PRINTERUNITCODE,
      TextQueryConstants.COL_NAME6_PRINTERNAME,
//      TextQueryConstants.COL_NAME6_CREATETIME,
      TextQueryConstants.COL_NAME6_CREATEUSERNAME,
      TextQueryConstants.COL_NAME6_UPDATEUSERNAME,
      TextQueryConstants.COL_NAME6_CREATORUNITCODE,
      TextQueryConstants.COL_NAME6_UPDATORUNITCODE,
      TextQueryConstants.COL_NAME6_MICBUPDATORUSERNAME,
      TextQueryConstants.COL_NAME6_MICBUPDATORUNITCODE
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
    JdbcDatabase.queryFirst(SELECT_TPCARD_TEXT_SQL){ps=>
      ps.setInt(1, sid)
    } { rs =>
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setObjectId(sid)
      syncDataBuilder.setTimestamp(seq)
      syncDataBuilder.setMinutiaType(MinutiaType.TEXT)
      syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
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
      val birthdate = rs.getString(TextQueryConstants.COL_NAME6_BIRTHDATE)
      if(birthdate != null && birthdate.length > 0){
        textData.addColBuilder().setColName(TextQueryConstants.COL_NAME6_BIRTHDATE).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(birthdate.toLong)))
      }
      val printdate= rs.getString(TextQueryConstants.COL_NAME6_PRINTDATE)
      if(printdate != null && printdate.length > 0){
        textData.addColBuilder().setColName(TextQueryConstants.COL_NAME6_PRINTDATE).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(printdate.toLong)))
      }
      //人员编号
      val cardId = rs.getString(TextQueryConstants.COL_NAME6_CARDID)
      TextQueryUtil.getColDataByPersonid(cardId).foreach(textData.addCol)

      syncDataBuilder.setData(ByteString.copyFrom(textData.build.toByteArray))
      syncDataResponse.addSyncData(syncDataBuilder)
    }
  }
}
