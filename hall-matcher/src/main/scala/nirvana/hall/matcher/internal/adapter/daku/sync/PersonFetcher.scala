package nirvana.hall.matcher.internal.adapter.daku.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
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
   override val SYNC_SQL: String = "select t.sid, t.seq, t.data_type, t.data_in, t.person_category  from gafis_person t  where t.seq >= ? and t.seq <= ? order by t.seq"

   /**
    * 读取人员信息
    * @param syncDataResponse
    * @param rs
    * @param size
    */
   override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit ={
      if(syncDataResponse.getSyncDataCount() < size){
         val syncDataBuilder = SyncData.newBuilder()
         syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
         syncDataBuilder.setMinutiaType(SyncData.MinutiaType.TEXT)
         syncDataBuilder.setObjectId(rs.getInt("sid"))
         syncDataBuilder.setTimestamp(rs.getLong("seq"))
         val dataType = rs.getString("data_type")
         val dataIn = rs.getString("data_in")
         //人员类型, 用于特定500w数据的比对
         val personCategory = rs.getString("person_category")

         val textData = TextData.newBuilder()
         if(dataType != null)
            textData.addColBuilder.setColName("dataType").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(dataType.getBytes("UTF-8")))
         if(dataIn != null)
            textData.addColBuilder.setColName("dataIn").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(dataIn.getBytes("UTF-8")))
         if(personCategory != null)
            textData.addColBuilder.setColName("personCategory").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(personCategory.getBytes("UTF-8")))

         syncDataBuilder.setData(ByteString.copyFrom(textData.build.toByteArray))
         if (validSyncData(syncDataBuilder.build, false)) {
            syncDataResponse.addSyncData(syncDataBuilder.build)
         }
      }
   }
 }
