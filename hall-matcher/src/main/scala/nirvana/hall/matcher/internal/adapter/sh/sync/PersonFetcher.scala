package nirvana.hall.matcher.internal.adapter.sh.sync

import java.io.UnsupportedEncodingException
import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.DataConverter
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
   override val SYNC_SQL: String = "select t.sid, t.seq, t.personid, t.name, t.sex_code sexCode, t.birthdayst birthday, t.door, t.address, t.gather_category gatherCategory, t.gather_type_id gatherType, t.gather_date gatherDate, t.data_sources dataSources, t.case_classes caseClass, t.deletag  from gafis_person t  where t.sid is not null and t.seq > ? and t.seq <= ? order by t.seq"
   private val personCols: Array[String] = Array[String]("gatherCategory", "gatherType", "door", "address", "sexCode", "name", "dataSources", "caseClass")
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
         val personId: String = rs.getString("personId")
         textData.addColBuilder.setColName("personId").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(personId.getBytes))
         try {
            if(personId.length == 23){
               val pId_pre: String = personId.substring(0, 1)
               val pId_deptCode: String = personId.substring(1, 13)
               val pId_date: String = personId.substring(13, 19)
               val pId_serialNum: String = personId.substring(19)
               textData.addColBuilder.setColName("pId_pre").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(pId_pre.getBytes))
               textData.addColBuilder.setColName("pId_deptCode").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(java.lang.Long.parseLong(pId_deptCode, 36))))
               textData.addColBuilder.setColName("pId_date").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(java.lang.Long.parseLong(pId_date, 36))))
               textData.addColBuilder.setColName("pId_serialNum").setColType(ColType.INT).setColValue(ByteString.copyFrom(DataConverter.int2Bytes(Integer.parseInt(pId_serialNum, 36))))
            }
         }
         catch {
            case e: Exception => {
               error("personId convert error：{}", personId)
            }
         }
         val birthdayst: Long = if (rs.getDate("birthday") != null) rs.getDate("birthday").getTime else 0
         if (birthdayst > 0) {
            textData.addColBuilder.setColName("birthday").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(birthdayst)))
         }
         val gatherDate: Long = if (rs.getDate("gatherDate") != null) rs.getDate("gatherDate").getTime else 0
         if (gatherDate > 0) {
            textData.addColBuilder.setColName("gatherDate").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(gatherDate)))
         }

         syncDataBuilder.setData(ByteString.copyFrom(textData.build.toByteArray))
         if (validSyncData(syncDataBuilder.build, false)) {
            syncDataResponse.addSyncData(syncDataBuilder.build)
         }
      }
   }
 }
