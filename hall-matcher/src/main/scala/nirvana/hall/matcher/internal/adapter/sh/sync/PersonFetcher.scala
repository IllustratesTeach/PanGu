package nirvana.hall.matcher.internal.adapter.sh.sync

import java.io.UnsupportedEncodingException
import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.{DataConverter, TextQueryUtil}
import nirvana.hall.matcher.internal.adapter.SyncDataFetcher
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.TextQueryProto.TextData
import nirvana.protocol.TextQueryProto.TextData.ColType
import nirvana.hall.matcher.internal.TextQueryConstants._

/**
  * Created by songpeng on 16/3/29.
  */
class PersonFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  override val MAX_SEQ_SQL: String = "select max(t.seq) from gafis_person t"
  override val MIN_SEQ_SQL: String = "select min(t.seq) from gafis_person t where t.seq > "
  /** 同步人员基本信息 */
  override val SYNC_SQL: String = s"SELECT t.sid" +
                                        s", t.seq" +
                                        s", t.personid" +
                                        s", t.name" +
                                        s", t.sex_code " + COL_NAME_SEXCODE +
                                        s", t.birthdayst " + COL_NAME_BIRTHDAY +
                                        s", t.door" +
                                        s", t.address" +
                                        s", t.gather_category " + COL_NAME_GATHERCATEGORY +
                                        s", t.gather_type_id " + COL_NAME_GATHERTYPE +
                                        s", t.gather_date " + COL_NAME_GATHERDATE +
                                        s", t.data_sources " + COL_NAME_DATASOURCES +
                                        s", t.case_classes " + COL_NAME_CASECLASS
                                        s", t.idcardno" +
                                        s", t.person_type " + COL_NAME_PERSONTYPE +
                                        s", t.nation_code " + COL_NAME_NATIONCODE +
                                        s", t.recordmark" +
                                        s", t.deletag" +
                                        s", db.logic_db_pkid as " + COL_NAME_LOGICDB +
                                        s", t.gather_org_code " + COL_NAME_GATHERORGCODE +
                                        s", t.nativeplace_code " + COL_NAME_NATIVEPLACECODE +
                                        s", t.foreign_name " + COL_NAME_FOREIGNNAME +
                                        s", t.assist_level " + COL_NAME_ASSISTLEVEL +
                                        s", t.assist_ref_person " + COL_NAME_ASSISTREFPERSON +
                                        s", t.assist_ref_case " + COL_NAME_ASSISTREFCASE +
                                        s", t.gatherdepartname" +
                                        s", t.gatherusername" +
                                        s", t.contrcapture_code " + COL_NAME_CONTRCAPTURECODE +
                                        s", t.certificatetype" +
                                        s", t.certificateid" +
                                        s", t.process_no " + COL_NAME_PROCESSNO +
                                        s", t.psis_no " + COL_NAME_PSISNO +
                                        s", t.spellname" +
                                        s", t.usedname" +
                                        s", t.usedspell" +
                                        s", t.aliasname" +
                                        s", t.aliasspell" +
                                        s", t.birth_code " + COL_NAME_BIRTHCODE +
                                        s", t.birth_street " + COL_NAME_BIRTHSTREET +
                                        s", t.birthdetail" +
                                        s", t.door_street " + COL_NAME_DOORSTREET +
                                        s", t.doordetail" +
                                        s", t.address_street " + COL_NAME_ADDRESSSTREET +
                                        s", t.addressdetail" +
                                        s", t.culture_code " + COL_NAME_CULTURECODE +
                                        s", t.faith_code " + COL_NAME_FAITHCODE +
                                        s", t.haveemployment" +
                                        s", t.job_code " + COL_NAME_JOBCODE +
                                        s", t.otherspecialty" +
                                        s", t.specialidentity_code " + COL_NAME_SPECIALIDENTITYCODE +
                                        s", t.specialgroup_code " + COL_NAME_SPECIALGROUPCODE +
                                        s", t.gatherer_id " + COL_NAME_GATHERERID +
                                        s", t.fingerrepeatno" +
                                        s", t.inputpsn" +
                                        s", t.inputtime" +
                                        s", t.modifiedpsn" +
                                        s", t.modifiedtime" +
                                        s", t.person_category " + COL_NAME_PERSONCATEGORY +
                                        s", t.gather_finger_mode " + COL_NAME_GATHERFINGERMODE +
                                        s", t.case_name " + COL_NAME_CASENAME +
                                        s", t.reason" +
                                        s", t.gatherdepartcode" +
                                        s", t.gatheruserid" +
                                        s", t.gather_finger_time " + COL_NAME_GATHERFINGERTIME +
                                        s", t.cardid" +
                                        s", t.is_xjssmz " + COL_NAME_ISXJSSMZ +
                                 s" FROM gafis_person t LEFT JOIN gafis_logic_db_fingerprint db ON t.personid=db.fingerprint_pkid" +
                                 s" WHERE t.seq >= ? AND t.seq <= ? ORDER BY t.seq"
  private val personCols: Array[String] = Array[String](COL_NAME_PERSONID, COL_NAME_GATHERCATEGORY, COL_NAME_GATHERTYPE, COL_NAME_DOOR, COL_NAME_ADDRESS,
    COL_NAME_SEXCODE, COL_NAME_NAME, COL_NAME_DATASOURCES, COL_NAME_CASECLASS, IDCARDNO, COL_NAME_PERSONTYPE, COL_NAME_NATIONCODE, COL_NAME_RECORDMARK, COL_NAME_LOGICDB,
    COL_NAME_GATHERORGCODE, COL_NAME_NATIVEPLACECODE, COL_NAME_FOREIGNNAME, COL_NAME_ASSISTLEVEL, COL_NAME_ASSISTREFPERSON, COL_NAME_ASSISTREFCASE, COL_NAME_GATHERDEPARTNAME,
    COL_NAME_GATHERUSERNAME, COL_NAME_CONTRCAPTURECODE, COL_NAME_CERTIFICATETYPE, COL_NAME_CERTIFICATEID, COL_NAME_PROCESSNO, COL_NAME_PSISNO, COL_NAME_SPELLNAME, COL_NAME_USEDNAME,
    COL_NAME_USEDSPELL, COL_NAME_ALIASNAME, COL_NAME_ALIASSPELL, COL_NAME_BIRTHCODE, COL_NAME_BIRTHSTREET, COL_NAME_BIRTHDETAIL, COL_NAME_DOORSTREET, COL_NAME_DOORDETAIL,
    COL_NAME_ADDRESSSTREET, COL_NAME_ADDRESSDETAIL,COL_NAME_CULTURECODE, COL_NAME_FAITHCODE, COL_NAME_HAVEEMPLOYMENT,COL_NAME_JOBCODE, COL_NAME_OTHERSPECIALTY,
    COL_NAME_SPECIALIDENTITYCODE, COL_NAME_SPECIALGROUPCODE, COL_NAME_GATHERERID, COL_NAME_FINGERREPEATNO, COL_NAME_INPUTPSN, COL_NAME_MODIFIEDPSN,COL_NAME_PERSONCATEGORY,
    COL_NAME_GATHERFINGERMODE, COL_NAME_CASENAME, COL_NAME_REASON, COL_NAME_GATHERDEPARTCODE, COL_NAME_GATHERUSERID, COL_NAME_CARDID, COL_NAME_ISXJSSMZ)

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
      TextQueryUtil.getColDataById(personId, COL_NAME_PID_PRE, COL_NAME_PID_DEPT, COL_NAME_PID_DATE).foreach(textData.addCol(_))

      //日期类型
      val dateCols = Array(COL_NAME_BIRTHDAY, COL_NAME_GATHERDATE, COL_NAME_INPUTTIME, COL_NAME_MODIFIEDTIME, COL_NAME_GATHERFINGERTIME)
      for (col <- dateCols) {
        val value = rs.getDate(col)
        val time = if (value != null) value.getTime else 0
        if (time > 0) {
          textData.addColBuilder.setColName(col).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(time)))
        }
      }

      syncDataBuilder.setData(ByteString.copyFrom(textData.build.toByteArray))
      if (validSyncData(syncDataBuilder.build, false)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }
}
