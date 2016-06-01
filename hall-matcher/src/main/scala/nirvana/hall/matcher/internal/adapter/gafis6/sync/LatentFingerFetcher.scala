package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.OperationType

/**
 * gafis6.2现场指纹分库
  */
class LatentFingerFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  override val MAX_SEQ_SQL: String = "select (max(t.updatetime) - to_date('01-JAN-1970','DD-MON-YYYY'))  from normallp_latfinger t "
  override val MIN_SEQ_SQL: String = "select (min(t.updatetime) - to_date('01-JAN-1970','DD-MON-YYYY'))  from normallp_latfinger t where ((t.updatetime) - to_date('01-JAN-1970','DD-MON-YYYY'))  >"
  /** 同步现场指纹 */
  override val SYNC_SQL: String = "select * from (select t.ora_sid as sid, t.fingermnt, t.fingerbin, ((t.updatetime) - to_date('01-JAN-1970','DD-MON-YYYY'))  as seq from normallp_latfinger t  where ((t.updatetime) - to_date('01-JAN-1970','DD-MON-YYYY'))  >=? order by t.updatetime) tt where rownum <=?"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      syncDataBuilder.setMinutiaType(SyncData.MinutiaType.FINGER)
      val lastSeq = rs.getLong("seq")
      syncDataBuilder.setOperationType(OperationType.PUT)
      //现场指位同一设置为1
      syncDataBuilder.setPos(1)
      val bytes = rs.getBytes("fingermnt")
      if(bytes == null) //特征值为空，则不进行同步
        return

      val mnt: ByteString = ByteString.copyFrom(bytes)
      syncDataBuilder.setData(mnt)
      syncDataBuilder.setTimestamp(lastSeq)
      val finger_ridge = rs.getBytes("fingerbin")
      //如果有纹线数据，同步纹线数据
      if (finger_ridge != null) {
        val ridgeBuilder = SyncData.newBuilder()
        ridgeBuilder.setObjectId(rs.getInt("sid"))
        ridgeBuilder.setMinutiaType(SyncData.MinutiaType.RIDGE)
        ridgeBuilder.setOperationType(OperationType.PUT)
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
