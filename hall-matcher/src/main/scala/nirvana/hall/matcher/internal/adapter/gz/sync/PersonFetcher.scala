package nirvana.hall.matcher.internal.adapter.gz.sync

import java.io.UnsupportedEncodingException
import java.sql.ResultSet
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.{DataConverter, TextQueryUtil}
import nirvana.hall.matcher.internal.adapter.SyncDataFetcher
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.TextQueryProto.TextData
import nirvana.protocol.TextQueryProto.TextData.ColType

/**
  * Created by songpeng on 16/3/29.
  */
class PersonFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  override val MAX_SEQ_SQL: String = "select max(t.seq) from gafis_person t"
  override val MIN_SEQ_SQL: String = "select min(t.seq) from gafis_person t where t.seq > "
  /** 同步人员基本信息 */
  override val SYNC_SQL: String = "select t.sid, t.seq, t.personid, t.name, t.sex_code sexCode, t.birthdayst birthday, t.door, t.address, t.gather_category gatherCategory, t.gather_type_id gatherType, t.gather_date gatherDate, t.data_sources dataSources, t.case_classes caseClass, t.deletag  from gafis_person t  where t.seq > ? and t.seq <= ? order by t.seq"
  private val personCols: Array[String] = Array[String]("gatherCategory", "gatherType", "door", "address", "sexCode", "name", "dataSources", "caseClass")
  /**
    * 读取人员信息
    * @param syncDataResponse
    * @param rs
    * @param size
    */
  override def readResultSet(syncDataResponse: SyncDataResponse.Builder, rs: ResultSet, size: Int): Unit = {
    if (syncDataResponse.getSyncDataCount() < size) {
      val syncDataBuilder = SyncData.newBuilder()
      syncDataBuilder.setMinutiaType(SyncData.MinutiaType.TEXT)
      syncDataBuilder.setObjectId(rs.getInt("sid"))
      syncDataBuilder.setTimestamp(rs.getLong("seq"))
      val deletag = rs.getString("deletag")
      if ("0".equals(deletag)) {
        //判断是添加还是删除,0：删除
        syncDataBuilder.setOperationType(SyncData.OperationType.DEL)
      } else {
        syncDataBuilder.setOperationType(SyncData.OperationType.PUT)
      }

      val textData = TextData.newBuilder()
      for (col <- personCols) {
        val value = rs.getString(col)
        if (value != null) {
          try {
            textData.addColBuilder.setColName(col).setColType(ColType.KEYWORD).setColValue(ByteString.copyFrom(value.getBytes("UTF-8")))
          } catch {
            case e: UnsupportedEncodingException => {
              error("UnsupportedEncodingException:{}", col)
            }
          }
        }
      }
      //人员编号
      val personId: String = rs.getString("personId")
      TextQueryUtil.getColDataByPersonid(personId).foreach(textData.addCol(_))

      val birthdayst: Long = if (rs.getDate("birthday") != null) rs.getDate("birthday").getTime else 0
      if (birthdayst > 0) {
        textData.addColBuilder.setColName("birthday").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(birthdayst)))
      }
      val gatherDate: Long = if (rs.getDate("gatherDate") != null) rs.getDate("gatherDate").getTime else 0
      if (gatherDate > 0) {
        textData.addColBuilder.setColName("gatherDate").setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(gatherDate)))
      }

      syncDataBuilder.setData(ByteString.copyFrom(textData.build.toByteArray))
      if (validSyncData(syncDataBuilder.build, false)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }
}
