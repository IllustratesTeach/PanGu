package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.service.TemplatePalmFetcher
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData

/**
 * gafis6.2捺印掌纹分库
 */
class TemplatePalmFetcherImpl(hallMatcherConfig: HallMatcherConfig, override implicit val dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource) with TemplatePalmFetcher{
//  override val MAX_SEQ_SQL: String = s"select ${wrapUpdateTimeAsLong(Some("max"))} from normaltp_tpcardinfo t "
//  override val MIN_SEQ_SQL: String = s"select ${wrapUpdateTimeAsLong(Some("min"))} from normaltp_tpcardinfo t " +
//    s"where ${wrapUpdateTimeAsLong()} >"
  override val MAX_SEQ_SQL: String = s"select ${wrapModTimeAsLong(Some("max"))} from normaltp_tpcardinfo_mod t "
  override val MIN_SEQ_SQL: String = s"select ${wrapModTimeAsLong(Some("min"))} from normaltp_tpcardinfo_mod t " +
    s"where ${wrapModTimeAsLong()} >"
  override val SYNC_SQL = s"select t.ora_sid as sid, ${wrapModTimeAsLong()} as seq from normaltp_tpcardinfo_mod t where ${wrapModTimeAsLong()} >=? and ${wrapModTimeAsLong()} <=? order by seq"
  val SELECT_PALM_SQL: String = s"select t.ora_sid as sid, t.palmlmnt, t.palmrmnt from normaltp_tpcardinfo t where t.ora_sid =? "

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    val sid = rs.getInt("sid")
    val seq = rs.getLong("seq")
    JdbcDatabase.queryFirst(SELECT_PALM_SQL){ps=>
      ps.setInt(1, sid)
    } { rs =>
      //左掌特征
      val leftPalmMnt = rs.getBytes("palmlmnt")
      if(leftPalmMnt != null){
        val pos = 1
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
        val pos = 2
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

}
