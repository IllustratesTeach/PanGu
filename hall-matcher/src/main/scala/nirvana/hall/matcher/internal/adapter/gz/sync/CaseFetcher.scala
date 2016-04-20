package nirvana.hall.matcher.internal.adapter.gz.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.MinutiaType
import nirvana.protocol.TextQueryProto.TextData
import nirvana.protocol.TextQueryProto.TextData.ColType

/**
 * Created by songpeng on 16/4/8.
 */
class CaseFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig ,dataSource){
  override val MAX_SEQ_SQL: String = "select max(seq) from gafis_case_finger"
  override val MIN_SEQ_SQL: String = "select min(seq) from gafis_case_finger where seq > "
  override val SYNC_SQL: String = "select f.sid, f.seq, f.finger_id, f.data_in, f.data_matcher from gafis_case_finger f where f.seq >=? and f.seq <=?"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setMinutiaType(MinutiaType.TEXT)
      syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      syncDataBuilder.setTimestamp(rs.getLong("seq"))
      val fingerId = rs.getString("finger_id")

      val textData = TextData.newBuilder()
      textData.addColBuilder.setColName("fingerId").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(fingerId.getBytes("UTF-8")))

      syncDataBuilder.setData(ByteString.copyFrom(textData.build().toByteArray()))
      if(validSyncData(syncDataBuilder.build(), true)){
        syncDataResponse.addSyncData(syncDataBuilder.build())
      }
    }
  }

}
