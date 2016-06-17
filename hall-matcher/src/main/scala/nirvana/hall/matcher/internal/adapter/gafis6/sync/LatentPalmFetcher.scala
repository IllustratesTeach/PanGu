package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.OperationType

/**
 * gafis6.2现场掌纹分库
 */
class LatentPalmFetcher(hallMatcherConfig: HallMatcherConfig,override implicit val dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
//  override val MAX_SEQ_SQL: String = s"select ${wrapUpdateTimeAsLong(Some("max"))} from normallp_latpalm t "
//  override val MIN_SEQ_SQL: String = s"select  ${wrapUpdateTimeAsLong(Some("min"))}  from normallp_latpalm t " +
//    s"where ${wrapUpdateTimeAsLong()} >"
  override val MAX_SEQ_SQL: String = s"select ${wrapModTimeAsLong(Some("max"))} from normallp_latpalm_mod t "
  override val MIN_SEQ_SQL: String = s"select  ${wrapModTimeAsLong(Some("min"))}  from normallp_latpalm_mod t " +
    s"where ${wrapModTimeAsLong()} >"
  override val SYNC_SQL = s"select t.ora_sid as sid, ${wrapModTimeAsLong()} as seq from normallp_latpalm_mod t where ${wrapModTimeAsLong()} >? and ${wrapModTimeAsLong()} <=? order by t.modtime"
  val SELECT_PALM_SQL: String = s"select t.ora_sid as sid, t.palmmnt from normallp_latpalm t where t.ora_sid =? "

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    val sid = rs.getInt("sid")
    val seq = rs.getLong("seq")
    JdbcDatabase.queryFirst(SELECT_PALM_SQL){ps=>
      ps.setInt(1, sid)
    } { rs =>
      val syncDataBuilder = SyncData.newBuilder
      syncDataBuilder.setObjectId(sid)
      syncDataBuilder.setMinutiaType(SyncData.MinutiaType.PALM)
      syncDataBuilder.setOperationType(OperationType.PUT)
      syncDataBuilder.setPos(1)
      val bytes = rs.getBytes("palmmnt")
      if(bytes == null)
        return
      syncDataBuilder.setData(ByteString.copyFrom(bytes))
      syncDataBuilder.setTimestamp(seq)
      if (validSyncData(syncDataBuilder.build, true)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }

}
