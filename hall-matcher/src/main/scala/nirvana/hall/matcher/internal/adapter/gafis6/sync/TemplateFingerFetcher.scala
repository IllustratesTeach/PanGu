package nirvana.hall.matcher.internal.adapter.gafis6.sync

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
 * gafis6.2捺印指纹分库
 */
class TemplateFingerFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  override val MAX_SEQ_SQL: String = s"select ${wrapUpdateTimeAsLong(Some("max"))}  from normaltp_tpcardinfo t "
  override val MIN_SEQ_SQL: String = s"select ${wrapUpdateTimeAsLong(Some("min"))} from normaltp_tpcardinfo t where ${wrapUpdateTimeAsLong()}  >"
//  override val SYNC_SQL: String = "select t.ora_sid, " +
//    " t.fingerrhmmnt, t.fingerrhsmnt, t.fingerrhzmnt, t.fingerrhhmnt, t.fingerrhxmnt, t.fingerlhmmnt, t.fingerlhsmnt, t.fingerlhzmnt, t.fingerlhhmnt, t.fingerlhxmnt," +
//    " t.fingerrhmbin, t.fingerrhsbin, t.fingerrhzbin, t.fingerrhhbin, t.fingerrhxbin, t.fingerlhmbin, t.fingerlhsbin, t.fingerlhzbin, t.fingerlhhbin, t.fingerlhxbin," +
//    " t.tplainrmmnt, t.tplainrsmnt, t.tplainrzmnt, t.tplainrhmnt, t.tplainrxmnt, t.tplainlmmnt, t.tplainlsmnt, t.tplainlzmnt, t.tplainlhmnt, t.tplainlxmnt," +
//    " t.tplainrmbin, t.tplainrsbin, t.tplainrzbin, t.tplainrhbin, t.tplainrxbin, t.tplainlmbin, t.tplainlsbin, t.tplainlzbin, t.tplainlhbin, t.tplainlxbin" +
//    " from normaltp_tpcardinfo t where t.ora_sid=? "
  override val SYNC_SQL = "select * from (select t.ora_sid as sid," +
      " t.fingerrhmmnt, t.fingerrhsmnt, t.fingerrhzmnt, t.fingerrhhmnt, t.fingerrhxmnt, t.fingerlhmmnt, t.fingerlhsmnt, t.fingerlhzmnt, t.fingerlhhmnt, t.fingerlhxmnt," +
      " t.fingerrhmbin, t.fingerrhsbin, t.fingerrhzbin, t.fingerrhhbin, t.fingerrhxbin, t.fingerlhmbin, t.fingerlhsbin, t.fingerlhzbin, t.fingerlhhbin, t.fingerlhxbin," +
      " t.tplainrmmnt, t.tplainrsmnt, t.tplainrzmnt, t.tplainrhmnt, t.tplainrxmnt, t.tplainlmmnt, t.tplainlsmnt, t.tplainlzmnt, t.tplainlhmnt, t.tplainlxmnt," +
      " t.tplainrmbin, t.tplainrsbin, t.tplainrzbin, t.tplainrhbin, t.tplainrxbin, t.tplainlmbin, t.tplainlsbin, t.tplainlzbin, t.tplainlhbin, t.tplainlxbin ," +
//    " t.fingerrhmmnt, t.fingerrhsmnt, t.fingerrhzmnt, t.fingerrhhmnt, t.fingerrhxmnt, t.fingerlhmmnt, t.fingerlhsmnt, t.fingerlhzmnt, t.fingerlhhmnt, t.fingerlhxmnt," +
//    " t.fingerrhmbin, t.fingerrhsbin, t.fingerrhzbin, t.fingerrhhbin, t.fingerrhxbin, t.fingerlhmbin, t.fingerlhsbin, t.fingerlhzbin, t.fingerlhhbin, t.fingerlhxbin," +
    s" ${wrapUpdateTimeAsLong()} as seq from normaltp_tpcardinfo t " +
    s"where ${wrapUpdateTimeAsLong()} >=? order by t.updatetime) tt where rownum <=?";
  //特征字段，根据指位1..20排序
  val mntColums: Array[String] = Array[String](
    "fingerrhmmnt", "fingerrhsmnt", "fingerrhzmnt", "fingerrhhmnt", "fingerrhxmnt", "fingerlhmmnt", "fingerlhsmnt", "fingerlhzmnt", "fingerlhhmnt", "fingerlhxmnt",
    "tplainrmmnt", "tplainrsmnt", "tplainrzmnt", "tplainrhmnt", "tplainrxmnt", "tplainlmmnt", "tplainlsmnt", "tplainlzmnt", "tplainlhmnt", "tplainlxmnt")
  //纹线字段，根据指位1..20排序
  val binColums: Array[String] = Array[String](
    "fingerrhmbin", "fingerrhsbin", "fingerrhzbin", "fingerrhhbin", "fingerrhxbin", "fingerlhmbin", "fingerlhsbin", "fingerlhzbin", "fingerlhhbin", "fingerlhxbin",
    "tplainrmbin", "tplainrsbin", "tplainrzbin", "tplainrhbin", "tplainrxbin", "tplainlmbin", "tplainlsbin", "tplainlzbin", "tplainlhbin", "tplainlxbin")

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    val seq = rs.getLong("seq")
    val sid = rs.getInt("sid")
    var pos = 1 //代表指位
    //循环读取特征
    mntColums.foreach{col =>
      val mnt = rs.getBytes(col)
      if(mnt != null){
        val syncData = SyncData.newBuilder()
        syncData.setObjectId(sid)
        syncData.setData(ByteString.copyFrom(mnt))
        syncData.setObjectId(sid)
        syncData.setPos(DataConverter.fingerPos6to8(pos))
        syncData.setOperationType(SyncData.OperationType.PUT)
        syncData.setMinutiaType(MinutiaType.FINGER)
        syncData.setTimestamp(seq)
        if (validSyncData(syncData.build, false)) {
          syncDataResponse.addSyncData(syncData.build)
        }
      }
      pos += 1
    }
    pos = 1 //获取纹线指位从1开始
    binColums.foreach{col =>
      val bin = rs.getBytes(col)
      if(bin != null){
        val syncData = SyncData.newBuilder()
        syncData.setObjectId(sid)
        syncData.setData(ByteString.copyFrom(bin))
        syncData.setObjectId(sid)
        syncData.setPos(DataConverter.fingerPos6to8(pos))
        syncData.setOperationType(SyncData.OperationType.PUT)
        syncData.setMinutiaType(MinutiaType.RIDGE)
        syncData.setTimestamp(seq)
        if (validSyncData(syncData.build, false)) {
          syncDataResponse.addSyncData(syncData.build)
        }
      }
      pos += 1
    }

  }

}
