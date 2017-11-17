package nirvana.hall.matcher.internal.adapter.common.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.DataConverter
import nirvana.hall.matcher.internal.adapter.SyncDataFetcher
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData

/**
 * Created by songpeng on 16/4/6.
 */
class TemplateFingerFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  //是否对纹线分库
  val hasRidge = hallMatcherConfig.mnt.hasRidge

  override val MAX_SEQ_SQL: String = "select max(t.seq) from gafis_gather_finger t "
  override val MIN_SEQ_SQL: String = "select min(t.seq) from gafis_gather_finger t where t.seq >"
  override val SYNC_SQL: String = "select p.sid, t.fgp, t.fgp_case, t.group_id, t.gather_data, t.seq, p.deletag " +
    " from gafis_gather_finger t " +
    " left join gafis_person p on t.person_id=p.personid " +
    " where "+(if(hasRidge) " t.group_id in(0,4) " else " t.group_id =0 ")+" and t.seq >= ? and t.seq <= ? order by t.seq"

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if(syncDataResponse.getSyncDataCount < size){
      val group_id = rs.getString("group_id")
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      var fgp = rs.getInt("fgp")
      val fgp_case = rs.getString("fgp_case")
      val lastSeq = rs.getLong("seq")
      val deletag = rs.getString("deletag")
      val mnt = rs.getBytes("gather_data")

      syncDataBuilder.setData(ByteString.copyFrom(mnt))
      //是否是纹线数据
      if ("4" == group_id) {
        syncDataBuilder.setMinutiaType(SyncData.MinutiaType.RIDGE)
        //有些纹线数据存在补位情况（4的倍数）,如果长度跟实际不一样，使用GAFISIMAGESTRUCT截取长度
        val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(mnt)
        if((gafisImage.stHead.nImgSize + hallMatcherConfig.mnt.headerSize) != mnt.length){
          syncDataBuilder.setData(ByteString.copyFrom(gafisImage.toByteArray(AncientConstants.GBK_ENCODING)))
        }
      } else {
        syncDataBuilder.setMinutiaType(SyncData.MinutiaType.FINGER)
      }
      //是否是删除
      if ("0" == deletag) {
        syncDataBuilder.setOperationType(SyncData.OperationType.DEL)
      } else {
        syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
      }
      if ("1" == fgp_case) {
        fgp += 10
      }
      syncDataBuilder.setPos(DataConverter.fingerPos6to8(fgp))

      syncDataBuilder.setTimestamp(lastSeq)
      if (validSyncData(syncDataBuilder.build, false)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }

}
