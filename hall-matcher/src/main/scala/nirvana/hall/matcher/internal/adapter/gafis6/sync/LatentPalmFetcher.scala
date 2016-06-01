package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.OperationType

/**
 * gafis6.2现场掌纹分库
 */
class LatentPalmFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  override val MAX_SEQ_SQL: String = s"select ${wrapUpdateTimeAsLong(Some("max"))} from normallp_latpalm t "
  override val MIN_SEQ_SQL: String = s"select  ${wrapUpdateTimeAsLong(Some("min"))}  from normallp_latpalm t " +
    s"where ${wrapUpdateTimeAsLong()} >"
  override val SYNC_SQL: String = "select * from " +
    s"(select t.ora_sid as sid, t.palmmnt, ${wrapUpdateTimeAsLong()}  as seq from normallp_latpalm t  " +
    s"where ${wrapUpdateTimeAsLong()}  >=? order by t.updatetime) tt where rownum <=?"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val syncDataBuilder = SyncData.newBuilder
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      syncDataBuilder.setMinutiaType(SyncData.MinutiaType.PALM)
      val lastSeq = rs.getLong("seq")
      syncDataBuilder.setOperationType(OperationType.PUT)
      syncDataBuilder.setPos(1)
      val bytes = rs.getBytes("palmmnt")
      if(bytes == null)
        return
      syncDataBuilder.setData(ByteString.copyFrom(bytes))
      syncDataBuilder.setTimestamp(lastSeq)
      if (validSyncData(syncDataBuilder.build, true)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }

}
