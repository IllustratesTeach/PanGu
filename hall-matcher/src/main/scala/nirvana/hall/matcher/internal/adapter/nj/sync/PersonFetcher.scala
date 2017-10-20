package nirvana.hall.matcher.internal.adapter.nj.sync

import java.io.UnsupportedEncodingException
import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.TextQueryConstants._
import nirvana.hall.matcher.internal.adapter.SyncDataFetcher
import nirvana.hall.matcher.internal.{DataConverter, PinyinConverter, TextQueryUtil}
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.TextQueryProto.TextData
import nirvana.protocol.TextQueryProto.TextData.ColType

/**
  * Created by songpeng on 16/3/29.
  */
class PersonFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  override val MAX_SEQ_SQL: String = "select max(t.seq) from gafis_person t"
  override val MIN_SEQ_SQL: String = "select min(t.seq) from gafis_person t where t.seq > "
  /** 同步人员基本信息 */
  override val SYNC_SQL: String = s"SELECT t.personid " + COL_NAME_PERSONID +
    s", t.idcardno " + COL_NAME_IDCARDNO +
    s", t.name " + COL_NAME_NAME +
    s", t.spellname " + COL_NAME_SPELLNAME +
    s", t.aliasname " + COL_NAME_ALIASNAME +
    s", t.aliasspell " + COL_NAME_ALIASSPELL +
    s", t.sex_code " + COL_NAME_SEXCODE +
    s", t.nativeplace_code " + COL_NAME_NATIVEPLACECODE +
    s", t.nation_code " + COL_NAME_NATIONCODE +
    s", t.birthdayst " +
    s", t.door " + COL_NAME_DOOR +
    s", t.doordetail " +
    s", t.address " + COL_NAME_ADDRESS +
    s", t.addressdetail " +
    s", t.gather_org_code " +
    s", t.ipaddress" +
    s", t.gather_date " + COL_NAME_GATHERDATE +
    s", t.gather_type_id " +
    s", t.status" +
    s", t.fingerrepeatno " + COL_NAME_FINGERREPEATNO +
    s", t.annex " +
    s", t.inputpsn " +
    s", t.inputtime " +
    s", t.modifiedpsn " +
    s", t.modifiedtime " +
    s", t.deletag " +
    s", t.schedule " +
    s", t.approval " +
    s", t.person_category " +
    s", t.auditor " +
    s", t.auditedtime " +
    s", t.gather_finger_mode " +
    s", t.gather_finger_num " +
    s", t.finger_remark " +
    s", t.gatherdepartcode " +
    s", t.gatheruserid " +
    s", t.gather_finger_time " +
    s", t.case_brief_contents" +
    s", t.data_sources" +
    s", t.city_code " +
    s", t.sid " +
    s", t.seq " +
    s", t.cardid " +
    s", t.recordmark " +
    s", t.recordsituation " +
    s", t.assist_level " + COL_NAME_ASSISTLEVEL +
    s", t.assist_bonus" +
    s", t.assist_purpose " +
    s", t.assist_ref_person " +
    s", t.assist_ref_case " +
    s", t.assist_valid_date " +
    s", t.assist_explain " +
    s", t.assist_dept_code " +
    s", t.assist_dept_name " +
    s", t.assist_date " +
    s", t.assist_contacts" +
    s", t.assist_number " +
    s", t.assist_approval " +
    s", t.assist_sign " +
    s", t.gatherdepartname " +
    s", t.gatherusername " + COL_NAME_GATHERUSERNAME +
    s", t.certificatetype " +
    s", t.certificateid " +
    s", t.person_type " +
    s", t.case_classes " + COL_NAME_CASECLASS +
    s", t.case_classes2 " + COL_NAME_CASECLASS2 +
    s", t.case_classes3 " + COL_NAME_CASECLASS3 +
    s", t.psis_no " +
    s", db.logic_db_pkid " + COL_NAME_LOGICDB +
    s" FROM gafis_person t LEFT JOIN gafis_logic_db_fingerprint db ON t.personid=db.fingerprint_pkid" +
    s" WHERE t.seq >= ? AND t.seq <= ? ORDER BY t.seq"
  private val personCols: Array[String] = Array[String](COL_NAME_LOGICDB,COL_NAME_CASECLASS,COL_NAME_CASECLASS2,COL_NAME_CASECLASS3,
  COL_NAME_IDCARDNO,COL_NAME_NAME,COL_NAME_ALIASNAME,COL_NAME_SEXCODE,COL_NAME_NATIVEPLACECODE,
  COL_NAME_NATIONCODE,COL_NAME_DOOR,COL_NAME_ADDRESS,COL_NAME_FINGERREPEATNO,COL_NAME_ASSISTLEVEL,COL_NAME_GATHERUSERNAME)

  /**
    * 读取人员信息
    * @param syncDataResponse
    * @param rs
    * @param size
    */
  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if (syncDataResponse.getSyncDataCount() < size) {
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setMinutiaType(SyncData.MinutiaType.TEXT)
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      syncDataBuilder.setTimestamp(rs.getLong("seq"))
      val deletag = rs.getString("deletag")
      if ("0".equals(deletag)) {
        //判断是添加还是删除,0：删除
        syncDataBuilder.setOperationType(SyncData.OperationType.DEL)
      } else {
        syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
      }

      val textData = TextData.newBuilder()
      for (col <- personCols) {
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

      //人员编号
      val personId: String = rs.getString(COL_NAME_PERSONID)
      TextQueryUtil.getColDataByPersonid(personId).foreach(textData.addCol(_))

      //日期类型
      val dateCols = Array(COL_NAME_GATHERDATE)
      for (col <- dateCols) {
        val value = rs.getDate(col)
        val time = if (value != null) value.getTime else 0
        if (time > 0) {
          textData.addColBuilder.setColName(col).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(time)))
        }
      }

      //姓名拼音,由于数据库数据不规范，这里不使用数据库spellname,这里通过转换汉字得到拼音
      val name= rs.getString(COL_NAME_NAME)
      if(name != null){
        val spellName = PinyinConverter.convert2Pinyin(name)
        textData.addColBuilder.setColName(COL_NAME_SPELLNAME).setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(spellName.getBytes("UTF-8")))
      }
      //别名拼音,由于数据库数据不规范，这里不使用数据库aliasname,这里通过转换汉字得到拼音
      val aliasname= rs.getString(COL_NAME_ALIASNAME)
      if(aliasname != null){
        val aliasspell = PinyinConverter.convert2Pinyin(aliasname)
        textData.addColBuilder.setColName(COL_NAME_ALIASSPELL).setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(aliasspell.getBytes("UTF-8")))
      }

      syncDataBuilder.setData(ByteString.copyFrom(textData.build.toByteArray))
      if (validSyncData(syncDataBuilder.build, false)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }
}
