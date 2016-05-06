package nirvana.hall.matcher.internal.adapter.sh.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.OperationType

/**
 * Created by songpeng on 16/4/26.
 */
class LatentPalmFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  override val MAX_SEQ_SQL: String = "select max(t.seq) as seq from gafis_case_palm t "
  override val MIN_SEQ_SQL: String = "select min(t.seq) as seq from gafis_case_palm t where t.seq >"
  override val SYNC_SQL: String = "select t.sid, t.fgp, mnt.palm_mnt, t.seq, t.deletag " +
    " from gafis_case_palm t left join gafis_case_palm_mnt mnt on mnt.palm_id=t.palm_id and mnt.is_main_mnt ='1' " +
    " where mnt.palm_mnt is not null and t.seq >= ? and t.seq <= ? order by t.seq"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val syncDataBuilder = SyncData.newBuilder
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      syncDataBuilder.setMinutiaType(SyncData.MinutiaType.PALM)
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
      if (validSyncData(syncDataBuilder.build, true)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }

}
