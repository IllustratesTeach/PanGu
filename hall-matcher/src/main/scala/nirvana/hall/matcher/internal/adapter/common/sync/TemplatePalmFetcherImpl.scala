package nirvana.hall.matcher.internal.adapter.common.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.DataConverter
import nirvana.hall.matcher.service.TemplatePalmFetcher
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData

/**
 * Created by songpeng on 16/4/26.
 */
class TemplatePalmFetcherImpl(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource) with TemplatePalmFetcher{
  //是否对纹线分库
  val hasRidge = hallMatcherConfig.mnt.hasRidge
  override val MAX_SEQ_SQL: String = "select max(t.seq) from gafis_gather_palm t "
  override val MIN_SEQ_SQL: String = "select min(t.seq) from gafis_gather_palm t " +
    " where "+(if(hasRidge) " t.group_id in(0,4) " else " t.group_id =0 ")+" and t.seq >"
  override val SYNC_SQL: String = "select p.sid, t.fgp, t.group_id, t.gather_data, t.seq, p.deletag " +
    " from gafis_gather_palm t " +
    " left join gafis_person p on t.person_id=p.personid " +
    " where "+(if(hasRidge) " t.group_id in(0,4) " else " t.group_id =0 ")+" and t.fgp in (11,12) and t.seq >= ? and t.seq <= ? order by t.seq"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val syncDataBuilder = SyncData.newBuilder
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      val group_id = rs.getString("group_id")
      val deletag = rs.getString("deletag")
      val lastSeq = rs.getLong("seq")
      if ("4" == group_id) {
        syncDataBuilder.setMinutiaType(SyncData.MinutiaType.RIDGE)
      } else {
        syncDataBuilder.setMinutiaType(SyncData.MinutiaType.PALM)
      }
      if ("0" == deletag) {
        syncDataBuilder.setOperationType(SyncData.OperationType.DEL)
      } else {
        syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
      }
      syncDataBuilder.setPos(DataConverter.palmPos6to8(rs.getInt("fgp")))
      syncDataBuilder.setData(ByteString.copyFrom(rs.getBytes("gather_data")))
      syncDataBuilder.setTimestamp(lastSeq)
      if (validSyncData(syncDataBuilder.build, false)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }

}
