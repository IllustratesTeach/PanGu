package nirvana.hall.matcher.internal.adapter.daku.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.DataConverter
import nirvana.hall.matcher.internal.adapter.SyncDataFetcher
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.MinutiaType

/**
 * Created by songpeng on 16/4/6.
 */
class TemplateFingerFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  override val MAX_SEQ_SQL: String = "select max(t.seq) from gafis_gather_finger t "
  override val MIN_SEQ_SQL: String = "select min(t.seq) from gafis_gather_finger t where t.seq >"
  override val SYNC_SQL: String = "select p.sid, t.fgp, t.fgp_case, t.gather_data, t.seq,p.deletag" +
    " from gafis_gather_finger t " +
    " left join gafis_person p on t.person_id=p.personid " +
    " where t.group_id=0 and t.seq >= ? and t.seq <= ? order by t.seq"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setMinutiaType(MinutiaType.FINGER)
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      var fgp = rs.getInt("fgp")
      val fgp_case = rs.getString("fgp_case")
      val lastSeq = rs.getLong("seq")
      val mnt = ByteString.copyFrom(rs.getBytes("gather_data"))
      syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
      if ("1" == fgp_case) {
        fgp += 10
      }
      syncDataBuilder.setPos(DataConverter.fingerPos6to8(fgp))

      syncDataBuilder.setData(mnt)
      syncDataBuilder.setTimestamp(lastSeq)
      if (validSyncData(syncDataBuilder.build, false)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }

}
