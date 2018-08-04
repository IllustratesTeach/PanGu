package nirvana.hall.matcher.internal.adapter.gafis6.sync

import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.HallMatcherSymobls
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.DataConverter
import nirvana.hall.matcher.service.TemplateFingerFetcher
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.MinutiaType

/**
 * gafis6.2捺印指纹分库
 */
class TemplateFingerFetcherImpl(hallMatcherConfig: HallMatcherConfig, override implicit val dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource) with TemplateFingerFetcher{
  private val hasRidge = hallMatcherConfig.mnt.hasRidge
  private val fast = hallMatcherConfig.module.equals(HallMatcherSymobls.MODULE_GAFIS6FAST)
  override val MAX_SEQ_SQL: String = if(fast){
      s"select max(seq) from normaltp_tpcardinfo_seq t "
    }else{
      s"select ${wrapModTimeAsLong(Some("max"))} from normaltp_tpcardinfo_mod t "
    }
  override val MIN_SEQ_SQL: String = if(fast){
      s"select min(seq) from normaltp_tpcardinfo_seq t where seq >"
    }else{
      s"select ${wrapModTimeAsLong(Some("min"))} from normaltp_tpcardinfo_mod t where ${wrapModTimeAsLong()}  >"
    }
  override val SYNC_SQL = if(fast){
      s"select t.ora_sid as sid, seq from normaltp_tpcardinfo_seq t where seq >=? and seq <=? order by seq"
    }else{
      s"select t.ora_sid as sid, ${wrapModTimeAsLong()} as seq from normaltp_tpcardinfo_mod t where ${wrapModTimeAsLong()} >=? and ${wrapModTimeAsLong()} <=? order by seq"
    }
  val SELECT_TPCARD_SQL = "select t.ora_sid as sid," +
      " t.fingerrhmmnt, t.fingerrhsmnt, t.fingerrhzmnt, t.fingerrhhmnt, t.fingerrhxmnt, t.fingerlhmmnt, t.fingerlhsmnt, t.fingerlhzmnt, t.fingerlhhmnt, t.fingerlhxmnt," +
      " t.tplainrmmnt, t.tplainrsmnt, t.tplainrzmnt, t.tplainrhmnt, t.tplainrxmnt, t.tplainlmmnt, t.tplainlsmnt, t.tplainlzmnt, t.tplainlhmnt, t.tplainlxmnt," +
      (if(hasRidge){
        " t.fingerrhmbin, t.fingerrhsbin, t.fingerrhzbin, t.fingerrhhbin, t.fingerrhxbin, t.fingerlhmbin, t.fingerlhsbin, t.fingerlhzbin, t.fingerlhhbin, t.fingerlhxbin," +
        " t.tplainrmbin, t.tplainrsbin, t.tplainrzbin, t.tplainrhbin, t.tplainrxbin, t.tplainlmbin, t.tplainlsbin, t.tplainlzbin, t.tplainlhbin, t.tplainlxbin "
      }else " 1") +
    "  from normaltp_tpcardinfo t where t.ora_sid =?"
  //特征字段，根据指位1..20排序
  val mntColums: Array[String] = Array[String](
    "fingerrhmmnt", "fingerrhsmnt", "fingerrhzmnt", "fingerrhhmnt", "fingerrhxmnt", "fingerlhmmnt", "fingerlhsmnt", "fingerlhzmnt", "fingerlhhmnt", "fingerlhxmnt",
    "tplainrmmnt", "tplainrsmnt", "tplainrzmnt", "tplainrhmnt", "tplainrxmnt", "tplainlmmnt", "tplainlsmnt", "tplainlzmnt", "tplainlhmnt", "tplainlxmnt")
  //纹线字段，根据指位1..20排序
  val binColums: Array[String] = Array[String](
    "fingerrhmbin", "fingerrhsbin", "fingerrhzbin", "fingerrhhbin", "fingerrhxbin", "fingerlhmbin", "fingerlhsbin", "fingerlhzbin", "fingerlhhbin", "fingerlhxbin",
    "tplainrmbin", "tplainrsbin", "tplainrzbin", "tplainrhbin", "tplainrxbin", "tplainlmbin", "tplainlsbin", "tplainlzbin", "tplainlhbin", "tplainlxbin")

  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    val sid = rs.getInt("sid")
    val seq = rs.getLong("seq")
    JdbcDatabase.queryFirst(SELECT_TPCARD_SQL){ps=>
      ps.setInt(1, sid)
    }{rs=>
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
      if(hasRidge){
        pos = 1 //获取纹线指位从1开始
        binColums.foreach{col =>
          val bin = rs.getBytes(col)
          if(bin != null){
            val syncData = SyncData.newBuilder()
            syncData.setObjectId(sid)
            syncData.setPos(DataConverter.fingerPos6to8(pos))
            syncData.setOperationType(SyncData.OperationType.PUT)
            syncData.setMinutiaType(MinutiaType.RIDGE)
            syncData.setTimestamp(seq)
            //纹线数据处理
            val binData = ByteString.copyFrom(bin)
            val dataSizeExpected = DataConverter.readGAFISIMAGESTRUCTDataLength(binData) + hallMatcherConfig.mnt.headerSize
            if(binData.size > dataSizeExpected && binData.size - dataSizeExpected < 4){
              syncData.setData(binData.substring(0, dataSizeExpected))
            }else{
              syncData.setData(binData)
            }
            if (validSyncData(syncData.build, false)) {
              syncDataResponse.addSyncData(syncData.build)
            }
          }
          pos += 1
        }
      }

    }
  }

}
