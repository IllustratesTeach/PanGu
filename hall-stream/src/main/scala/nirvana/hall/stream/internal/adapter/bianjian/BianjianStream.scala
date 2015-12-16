package nirvana.hall.stream.internal.adapter.bianjian

import java.sql.{SQLException, ResultSet, PreparedStatement, Connection}
import javax.annotation.PostConstruct
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.stream.services.StreamService
import org.apache.tapestry5.ioc.annotations.EagerLoad

/**
 * bianjian stream
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-16
 */
@EagerLoad
class BianjianStream {
  val batch_size = 10

  @PostConstruct
  def startStream(dataSource:DataSource,streamService: StreamService): Unit ={
    val minSeq = getMinSeq(dataSource)
    val maxSeq = getMaxSeq(dataSource)
    fetch(dataSource, streamService, batch_size, minSeq, maxSeq)
  }
  //递归
  def fetch(dataSource:DataSource,streamService: StreamService, size: Int, from: Long, maxSeq: Long): Unit ={
    if(from < maxSeq){
      fetchData(dataSource, streamService, size, from)
      fetch(dataSource, streamService, size, from + batch_size, maxSeq)
    }
  }
  def fetchData(dataSource:DataSource,streamService: StreamService, size: Int, from: Long): Unit ={
    val conn = dataSource.getConnection
    val ps = conn.prepareStatement("select t.csid, t.zp from T_PC_A_CS t where t.csid >? and t.csid <=? order by t.csid")
    ps.setLong(1, from)
    ps.setLong(2, from + size)
    val rs = ps.executeQuery()

    readAndPush(rs, streamService)

    rs.close()
    ps.close()
    conn.close()
  }

  def readAndPush(rs: ResultSet, streamService: StreamService): Unit ={
    if(rs.next()){
      val csid = rs.getLong("csid")
      val zp = rs.getBytes("zp")
      streamService.pushEvent(csid, ByteString.copyFrom(zp), true, FingerPosition.FINGER_L_THUMB, FeatureType.FingerTemplate)
      readAndPush(rs, streamService)
    }
  }


  def getMaxSeq (dataSource: DataSource): Long ={
    getSeq(dataSource, "select min(csid) from T_PC_A_CS")
  }
  def getMinSeq(dataSource: DataSource): Long={
    getSeq(dataSource, "select max(csid) from T_PC_A_CS")
  }
  def getSeq(dataSource: DataSource, sql: String): Long ={
    val conn: Connection = dataSource.getConnection
    var ps: PreparedStatement = null
    var rs: ResultSet = null
    var seq: Long = 0
    try {
      ps = conn.prepareStatement(sql)
      rs = ps.executeQuery
      if (rs.next) {
        seq = rs.getLong(1)
      }
    }
    catch {
      case e: SQLException => {
        e.printStackTrace
      }
    } finally {
      rs.close()
      ps.close()
      conn.close()
    }
    seq
  }
}
