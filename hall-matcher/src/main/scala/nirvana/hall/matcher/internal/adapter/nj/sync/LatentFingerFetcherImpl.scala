package nirvana.hall.matcher.internal.adapter.nj.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.adapter.common.sync.SyncDataFetcher
import nirvana.hall.matcher.service.LatentFingerFetcher
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.OperationType

class LatentFingerFetcherImpl(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource)  extends SyncDataFetcher(hallMatcherConfig, dataSource) with LatentFingerFetcher{
  override val MAX_SEQ_SQL: String = "select max(t.seq) from gafis_case_finger t "
  override val MIN_SEQ_SQL: String = "select min(t.seq) from gafis_case_finger t where t.seq >"
  override val SYNC_SQL: String = "select t.sid, t.fgp, mnt.finger_mnt, mnt.finger_ridge, t.seq, t.deletag " +
    " from gafis_case_finger t " +
    " left join gafis_case c on c.case_id = t.case_id" +
    " left join gafis_case_finger_mnt mnt on t.finger_id = mnt.finger_id and mnt.is_main_mnt=1 " +
    " where mnt.finger_mnt is not null and (c.data_status is null or c.data_status !=0) and t.seq >= ? and t.seq <= ? order by t.seq"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val syncDataBuilder = SyncData.newBuilder()
      val objectId = rs.getInt("sid")
      syncDataBuilder.setObjectId(objectId)
      syncDataBuilder.setMinutiaType(SyncData.MinutiaType.FINGER)
      val lastSeq = rs.getLong("seq")
      val deletag = rs.getString("deletag")
      var operationType = OperationType.PUT
      if ("0" == deletag) {
        operationType = OperationType.DEL
      }
      syncDataBuilder.setOperationType(operationType)
      //现场指位同一设置为1
      syncDataBuilder.setPos(1)
      val mnt: ByteString = ByteString.copyFrom(rs.getBytes("finger_mnt"))
      syncDataBuilder.setData(mnt)
      syncDataBuilder.setTimestamp(lastSeq)
      val finger_ridge = rs.getBytes("finger_ridge")
      //如果有纹线数据，同步纹线数据
      if (hallMatcherConfig.mnt.hasRidge && finger_ridge != null) {
        val ridgeBuilder = SyncData.newBuilder()
        ridgeBuilder.setObjectId(objectId)
        ridgeBuilder.setMinutiaType(SyncData.MinutiaType.RIDGE)
        ridgeBuilder.setOperationType(operationType)
        ridgeBuilder.setPos(1)
        ridgeBuilder.setTimestamp(lastSeq)
        ridgeBuilder.setData(ByteString.copyFrom(finger_ridge))
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
