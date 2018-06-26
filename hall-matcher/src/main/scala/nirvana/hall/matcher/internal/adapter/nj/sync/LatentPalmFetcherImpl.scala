package nirvana.hall.matcher.internal.adapter.nj.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.adapter.common.sync.SyncDataFetcher
import nirvana.hall.matcher.service.LatentPalmFetcher
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.OperationType

class LatentPalmFetcherImpl(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource) with LatentPalmFetcher{
  override val MAX_SEQ_SQL: String = "select max(t.seq) as seq from gafis_case_palm t "
  override val MIN_SEQ_SQL: String = "select min(t.seq) as seq from gafis_case_palm t where t.seq >"
  override val SYNC_SQL: String = "select t.sid, t.fgp, mnt.palm_mnt, mnt.palm_ridge, t.seq, t.deletag " +
    " from gafis_case_palm t " +
    " left join gafis_case c on c.case_id = t.case_id" +
    " left join gafis_case_palm_mnt mnt on mnt.palm_id=t.palm_id and mnt.is_main_mnt ='1' " +
    " where mnt.palm_mnt is not null and (c.data_status is null or c.data_status !=0) and t.seq >= ? and t.seq <= ? order by t.seq"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val syncDataBuilder = SyncData.newBuilder
      syncDataBuilder.setMinutiaType(SyncData.MinutiaType.PALM)
      val sid = rs.getInt("sid")
      syncDataBuilder.setObjectId(sid)
      val deletag = rs.getString("deletag")
      val lastSeq = rs.getLong("seq")
      if ("0" == deletag) {
        syncDataBuilder.setOperationType(OperationType.DEL)
      }
      else {
        syncDataBuilder.setOperationType(OperationType.PUT)
      }
      syncDataBuilder.setPos(1)
      syncDataBuilder.setData(ByteString.copyFrom(rs.getBytes("palm_mnt")))
      syncDataBuilder.setTimestamp(lastSeq)
      val ridge = rs.getBytes("palm_ridge")
      //如果有纹线数据，同步纹线数据
      if (ridge != null) {
        val ridgeBuilder = SyncData.newBuilder()
        ridgeBuilder.setObjectId(sid)
        ridgeBuilder.setMinutiaType(SyncData.MinutiaType.RIDGE)
        ridgeBuilder.setOperationType(OperationType.PUT)
        if ("0" == deletag) {
          ridgeBuilder.setOperationType(OperationType.DEL)
        } else {
          ridgeBuilder.setOperationType(OperationType.PUT)
        }
        ridgeBuilder.setPos(1)
        ridgeBuilder.setTimestamp(lastSeq)
        ridgeBuilder.setData(ByteString.copyFrom(ridge))
        if (validSyncData(ridgeBuilder.build, true)) {
          syncDataResponse.addSyncData(ridgeBuilder.build)
        }
      }
      if (validSyncData(syncDataBuilder.build, true)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }

}
