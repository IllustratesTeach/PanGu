package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.service.LatentFingerFetcher
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.OperationType

/**
 * gafis6.2现场指纹分库
  */
class LatentFingerFetcherImpl(hallMatcherConfig: HallMatcherConfig, override implicit val dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource) with LatentFingerFetcher{
//  override val MAX_SEQ_SQL: String = s"select max(seq)  from normallp_latfinger_seq t "
//  override val MIN_SEQ_SQL: String = s"select min(seq)  from normallp_latfinger_seq t where seq >"
//  override val SYNC_SQL = s"select t.ora_sid as sid, seq from normallp_latfinger_seq t where seq >? and seq<=? order by seq"
  override val MAX_SEQ_SQL: String = s"select ${wrapModTimeAsLong(Some("max"))}  from normallp_latfinger_mod t "
  override val MIN_SEQ_SQL: String = s"select ${wrapModTimeAsLong(Some("min"))}  from normallp_latfinger_mod t where ${wrapModTimeAsLong()} >"
  override val SYNC_SQL = s"select t.ora_sid as sid, ${wrapModTimeAsLong()} as seq from normallp_latfinger_mod t where ${wrapModTimeAsLong()} >? and ${wrapModTimeAsLong()} <=? order by seq"
  /** 同步现场指纹 */
  val SELECT_LPCARD_SQL: String = s"select t.ora_sid as sid, t.fingermnt, t.fingerbin from normallp_latfinger t where t.ora_sid =?"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    val sid = rs.getInt("sid")
    val seq = rs.getLong("seq")
    JdbcDatabase.queryFirst(SELECT_LPCARD_SQL){ps=>
      ps.setInt(1, sid)
    }{rs=>
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setObjectId(sid)
      syncDataBuilder.setMinutiaType(SyncData.MinutiaType.FINGER)
      syncDataBuilder.setOperationType(OperationType.PUT)
      //现场指位同一设置为1
      syncDataBuilder.setPos(1)
      val bytes = rs.getBytes("fingermnt")
      if(bytes == null) //特征值为空，则不进行同步
        return

      val mnt: ByteString = ByteString.copyFrom(bytes)
      syncDataBuilder.setData(mnt)
      syncDataBuilder.setTimestamp(seq)
      val finger_ridge = rs.getBytes("fingerbin")
      //如果有纹线数据，同步纹线数据
      if (hallMatcherConfig.mnt.hasRidge && finger_ridge != null) {
        val ridgeBuilder = SyncData.newBuilder()
        ridgeBuilder.setObjectId(rs.getInt("sid"))
        ridgeBuilder.setMinutiaType(SyncData.MinutiaType.RIDGE)
        ridgeBuilder.setOperationType(OperationType.PUT)
        ridgeBuilder.setPos(1)
        ridgeBuilder.setTimestamp(seq)
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
