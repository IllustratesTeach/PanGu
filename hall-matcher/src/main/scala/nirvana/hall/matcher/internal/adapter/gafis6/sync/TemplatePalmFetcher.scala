package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData

/**
 * gafis6.2捺印掌纹分库
 */
class TemplatePalmFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
//  override val MAX_SEQ_SQL: String = s"select ${wrapUpdateTimeAsLong(Some("max"))} from normaltp_tpcardinfo t "
//  override val MIN_SEQ_SQL: String = s"select ${wrapUpdateTimeAsLong(Some("min"))} from normaltp_tpcardinfo t " +
//    s"where ${wrapUpdateTimeAsLong()} >"
  override val MAX_SEQ_SQL: String = s"select ${wrapModTimeAsLong(Some("max"))} from normaltp_tpcardinfo_mod t "
  override val MIN_SEQ_SQL: String = s"select ${wrapModTimeAsLong(Some("min"))} from normaltp_tpcardinfo_mod t " +
    s"where ${wrapModTimeAsLong()} >"
  override val SYNC_SQL: String = "select * from " +
    s"(select t.ora_sid as sid, t.palmlmnt, t.palmrmnt, ${wrapUpdateTimeAsLong()} as seq from normaltp_tpcardinfo t  " +
    s"where ${wrapUpdateTimeAsLong()}  >=? order by t.updatetime) tt "

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
      //左掌特征
      val leftPalmMnt = rs.getBytes("palmlmnt")
      if(leftPalmMnt != null){
        val sid = rs.getInt("sid")
        val pos = 1
        val seq = rs.getLong("seq")
        val syncDataBuilder = SyncData.newBuilder
        syncDataBuilder.setObjectId(sid)
        syncDataBuilder.setMinutiaType(SyncData.MinutiaType.PALM)
        syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
        syncDataBuilder.setPos(pos)
        syncDataBuilder.setData(ByteString.copyFrom(leftPalmMnt))
        syncDataBuilder.setTimestamp(seq)
        if (validSyncData(syncDataBuilder.build, false)) {
          syncDataResponse.addSyncData(syncDataBuilder.build)
        }
      }
      //右掌特征
      val rightPalmMnt = rs.getBytes("palmrmnt")
      if(rightPalmMnt != null){
        val sid = rs.getInt("sid")
        val pos = 2
        val seq = rs.getLong("seq")
        val syncDataBuilder = SyncData.newBuilder
        syncDataBuilder.setObjectId(sid)
        syncDataBuilder.setMinutiaType(SyncData.MinutiaType.PALM)
        syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
        syncDataBuilder.setPos(pos)
        syncDataBuilder.setData(ByteString.copyFrom(rightPalmMnt))
        syncDataBuilder.setTimestamp(seq)
        if (validSyncData(syncDataBuilder.build, false)) {
          syncDataResponse.addSyncData(syncDataBuilder.build)
        }
      }
  }

}
