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
import nirvana.hall.matcher.internal.TextQueryConstants._

/**
  * Created by songpeng on 16/3/29.
  */
class PersonFetcher(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataFetcher(hallMatcherConfig, dataSource){
  override val MAX_SEQ_SQL: String = "select max(t.seq) from gafis_person t"
  override val MIN_SEQ_SQL: String = "select min(t.seq) from gafis_person t where t.seq > "
  /** 同步人员基本信息 */
  override val SYNC_SQL: String = "select t.sid, t.seq" +
    ", t.personid" + COL_NAME_PERSONID
    ", t.name" + COL_NAME_NAME
    ", t.sex_code " + COL_NAME_SEXCODE
    ", t.birthdayst " + COL_NAME_BIRTHDAY
    ", t.door" + COL_NAME_DOOR
    ", t.address" + COL_NAME_ADDRESS
    ", t.gather_category " + COL_NAME_GATHERCATEGORY
    ", t.gather_type_id " + COL_NAME_GATHERTYPEID
    ", t.gather_date " + COL_NAME_GATHERDATE
    ", t.data_sources " + COL_NAME_DATASOURCES
    ", t.case_classes " + COL_NAME_CASECLASS
    ", t.deletag  from gafis_person t  where t.seq > ? and t.seq <= ? order by t.seq"
  private val personCols: Array[String] = Array[String](COL_NAME_NAME, COL_NAME_GATHERCATEGORY, COL_NAME_GATHERTYPE, COL_NAME_DOOR, COL_NAME_ADDRESS, COL_NAME_SEXCODE, COL_NAME_DATASOURCES, COL_NAME_CASECLASS)
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
      val personId: String = rs.getString(COL_NAME_PERSONID)
      TextQueryUtil.getColDataById(personId, COL_NAME_PID_PRE, COL_NAME_PID_DEPT, COL_NAME_PID_DATE).foreach(textData.addCol(_))

      //日期类型
      val dateCols = Array(COL_NAME_BIRTHDAY, COL_NAME_GATHERDATE)
      for (col <- dateCols) {
        val value = rs.getDate(col)
        val time = if (value != null) value.getTime else 0
        if (time > 0) {
          textData.addColBuilder.setColName(col).setColType(ColType.LONG).setColValue(ByteString.copyFrom(DataConverter.long2Bytes(time)))
        }
      }

      syncDataBuilder.setData(ByteString.copyFrom(textData.build.toByteArray))
      if (validSyncData(syncDataBuilder.build, false)) {
        syncDataResponse.addSyncData(syncDataBuilder.build)
      }
    }
  }
}
