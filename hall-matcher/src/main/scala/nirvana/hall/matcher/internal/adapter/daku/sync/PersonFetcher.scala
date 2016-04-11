package nirvana.hall.matcher.internal.adapter.daku.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.TextQueryProto.TextData
import nirvana.protocol.TextQueryProto.TextData.ColType

/**
  * Created by songpeng on 16/3/29.
  */
class PersonFetcher(implicit dataSource: DataSource) extends SyncDataFetcher{
   override val MAX_SEQ_SQL: String = "select max(t.seq) from gafis_person t"
   override val MIN_SEQ_SQL: String = "select min(t.seq) from gafis_person t where t.seq > "
   /** 同步人员基本信息 */
   override val SYNC_SQL: String = "select t.sid, t.seq, t.personid, t.data_type, t.data_in  from gafis_person t  where t.sid is not null and t.seq >= ? and t.seq <= ? order by t.seq"

   /**
    * 读取人员信息
    * @param syncDataResponse
    * @param rs
    * @param size
    */
   override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit ={
      if(syncDataResponse.getSyncDataCount() < size){
         val syncDataBuilder = syncDataResponse.addSyncDataBuilder()
         syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
         syncDataBuilder.setMinutiaType(SyncData.MinutiaType.TEXT)
         syncDataBuilder.setObjectId(rs.getInt("sid"))
         syncDataBuilder.setTimestamp(rs.getLong("seq"))
         val personId = rs.getString("personid")
         val dataType = rs.getString("data_type")
         val dataIn = rs.getString("data_in")

         val textData = TextData.newBuilder()
         textData.addColBuilder.setColName("personId").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(personId.getBytes("UTF-8")))
         if(dataType != null)
            textData.addColBuilder.setColName("dataType").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(dataType.getBytes("UTF-8")))
         if(dataIn != null)
            textData.addColBuilder.setColName("dataIn").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(dataIn.getBytes("UTF-8")))

         syncDataBuilder.setData(ByteString.copyFrom(textData.build.toByteArray))
      }
   }
 }
