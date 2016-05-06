package nirvana.hall.matcher.internal.adapter.daku.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.OperationType

/**
  * Created by songpeng on 16/3/29.
  )*/
class LatentFingerFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  override val MAX_SEQ_SQL: String = "select max(t.seq) from gafis_case_finger t "
  override val MIN_SEQ_SQL: String = "select min(t.seq) from gafis_case_finger t where t.seq >"
  /** 同步现场指纹 */
  override val SYNC_SQL: String = "select t.sid, t.fgp, mnt.finger_mnt, mnt.finger_ridge, t.seq, t.deletag " +
    " from gafis_case_finger t " +
    " left join gafis_case_finger_mnt mnt on t.finger_id = mnt.finger_id and mnt.is_main_mnt=1 " +
    " where mnt.finger_mnt is not null and t.seq >= ? and t.seq <= ? order by t.seq"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      syncDataBuilder.setMinutiaType(SyncData.MinutiaType.FINGER)
      val lastSeq = rs.getLong("seq")
      syncDataBuilder.setOperationType(OperationType.PUT)
      //现场指位同一设置为1
      syncDataBuilder.setPos(1)
      val mnt: ByteString = ByteString.copyFrom(rs.getBytes("finger_mnt"))
      syncDataBuilder.setData(mnt)
      syncDataBuilder.setTimestamp(lastSeq)

      if (validSyncData(syncDataBuilder.build, true)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }
}
