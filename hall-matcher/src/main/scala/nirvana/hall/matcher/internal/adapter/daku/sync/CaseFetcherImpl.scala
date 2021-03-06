package nirvana.hall.matcher.internal.adapter.daku.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.adapter.common.sync.SyncDataFetcher
import nirvana.hall.matcher.service.CaseFetcher
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.MinutiaType
import nirvana.protocol.TextQueryProto.TextData
import nirvana.protocol.TextQueryProto.TextData.ColType

/**
 * Created by songpeng on 16/4/8.
 */
class CaseFetcherImpl(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource) with CaseFetcher{
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
      val dataIn = rs.getString("data_in")
      val dataMatcher = rs.getString("data_matcher")

      val textData = TextData.newBuilder()
      if(dataIn != null)
        textData.addColBuilder.setColName("dataIn").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(dataIn.getBytes("UTF-8")))
      if(dataMatcher != null)
        textData.addColBuilder.setColName("dataMatcher").setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(dataMatcher.getBytes("UTF-8")))

      syncDataBuilder.setData(ByteString.copyFrom(textData.build.toByteArray))
      if (validSyncData(syncDataBuilder.build, true)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }

}
