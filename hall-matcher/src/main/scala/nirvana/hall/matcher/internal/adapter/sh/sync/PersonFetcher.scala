package nirvana.hall.matcher.internal.adapter.sh.sync

import java.io.UnsupportedEncodingException
import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.DataConverter
import nirvana.hall.matcher.internal.adapter.SyncDataFetcher
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
   override val SYNC_SQL: String = "select t.sid, t.seq, t.personid, t.name, t.sex_code sexCode, t.birthdayst birthday," +
     " t.door, t.address, t.gather_category gatherCategory, t.gather_type_id gatherTypeId, t.gather_date gatherDate," +
     " t.data_sources dataSources, t.case_classes caseClasses, t.idcardno, t.person_type personType, t.nation_code nationCode," +
     " t.recordmark, t.deletag, db.logic_db_pkid as logicDB, t.gather_org_code gatherOrgCode, t.nativeplace_code nativeplaceCode," +
     " t.foreign_name foreignName, t.assist_level assistLevel, t.assist_ref_person assistRefPerson, t.assist_ref_case assistRefCase," +
     " t.gatherdepartname, t.gatherusername, t.contrcapture_code contrcaptureCode," +
     " t.certificatetype, t.certificateid, t.process_no processNo, t.psis_no psisNo," +
     " t.spellname, t.usedname, t.usedspell, t.aliasname, t.aliasspell, t.birth_code birthCode, t.birth_street birthStreet," +
     " t.birthdetail, t.door_street doorStreet, t.doordetail, t.address_street addressStreet," +
     " t.addressdetail, t.culture_code cultureCode, t.faith_code faithCode, t.haveemployment," +
     " t.job_code jobCode, t.otherspecialty, t.specialidentity_code specialidentityCode," +
     " t.specialgroup_code specialgroupCode, t.gatherer_id gathererId, t.fingerrepeatno, t.inputpsn," +
     " t.inputtime, t.modifiedpsn, t.modifiedtime, t.person_category personCategory, t.gather_finger_mode gatherFingerMode," +
     " t.case_name caseName, t.reason, t.gatherdepartcode, t.gatheruserid," +
     " t.gather_finger_time gatherFingerTime, t.cardid, t.is_xjssmz isXjssmz" +
     " from gafis_person t left join gafis_logic_db_fingerprint db on t.personid=db.fingerprint_pkid" +
     " where t.seq >= ? and t.seq <= ? order by t.seq"
   private val personCols: Array[String] = Array[String]("personId", "gatherCategory", "gatherTypeId", "door", "address",
     "sexCode", "name", "dataSources", "caseClasses", "idcardno", "personType", "nationCode", "recordmark", "logicDB",
     "gatherOrgCode", "nativeplaceCode", "foreignName", "assistLevel", "assistRefPerson", "assistRefCase", "gatherdepartname",
     "gatherusername", "contrcaptureCode", "certificatetype", "certificateid", "processNo", "psisNo", "spellname", "usedname",
     "usedspell", "aliasname", "aliasspell", "birthCode", "birthStreet", "birthdetail", "doorStreet", "doordetail",
     "addressStreet", "addressdetail", "cultureCode", "faithCode", "haveemployment", "jobCode", "otherspecialty",
     "specialidentityCode", "specialgroupCode", "gathererId", "fingerrepeatno", "inputpsn", "modifiedpsn", "personCategory",
     "gatherFingerMode", "caseName", "reason", "gatherdepartcode", "gatheruserid", "cardid", "isXjssmz")
   /**
    * 读取人员信息
    * @param syncDataResponse
    * @param rs
    * @param size
    */
   override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit ={
      if(syncDataResponse.getSyncDataCount() < size){
         val syncDataBuilder = SyncData.newBuilder()
         syncDataBuilder.setMinutiaType(SyncData.MinutiaType.TEXT)
         syncDataBuilder.setObjectId(rs.getInt("sid"))
         syncDataBuilder.setTimestamp(rs.getLong("seq"))
         val deletag = rs.getString("deletag")
         if("0".equals(deletag)){//判断是添加还是删除,0：删除
            syncDataBuilder.setOperationType(SyncData.OperationType.DEL)
         }else{
            syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
         }

         val textData = TextData.newBuilder()
         for(col <- personCols){
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
          * 将人员编号拆分为三部分
          * 第一部分，单位编码12位(1, 13)，由于单位编码可能包含字母，转为36进制的Long值
          * 第二部分，年月日6位(13,19)
          * 第三部分，流水4位(19,23)
          * */
        var personId: String = rs.getString("personId")
        textData.addColBuilder.setColName("personId").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(personId.getBytes))
        try {
          //如果是字母开头截取第一位前缀
          if (personId.matches("^[a-zA-Z]\\w*")) {
            val pId_pre: String = personId.substring(0, 1)
            textData.addColBuilder.setColName("pId_pre").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(pId_pre.getBytes))
            personId = personId.substring(1)
          }
          if(personId.length == 22){
            val pId_deptCode = java.lang.Long.parseLong(personId.substring(0, 12), 36)
            val pId_date = java.lang.Long.parseLong(personId.substring(12, 18), 36)
            val pId_serialNum = Integer.parseInt(personId.substring(18), 36)
            textData.addColBuilder.setColName("pId_deptCode").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(pId_deptCode)))
            textData.addColBuilder.setColName("pId_date").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(pId_date)))
            textData.addColBuilder.setColName("pId_serialNum").setColType(ColType.INT).setColValue(ByteString.copyFrom(DataConverter.int2Bytes(pId_serialNum)))
            info("personId:" + personId + ",pId_deptCode:" + ByteString.copyFrom(DataConverter.long2Bytes(pId_deptCode)) + ",pId_date" + ByteString.copyFrom(DataConverter.long2Bytes(pId_date)) + ",pId_serialNum" + ByteString.copyFrom(DataConverter.int2Bytes(pId_serialNum)))
          }
        }
        catch {
          case e: Exception => {
            error("personId convert error：{}", personId)
          }
        }
        //日期类型
        val dateCols = Array("birthday", "gatherDate", "inputtime", "modifiedtime", "gatherFingerTime")
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
