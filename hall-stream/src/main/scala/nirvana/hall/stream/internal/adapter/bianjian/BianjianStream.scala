package nirvana.hall.stream.internal.adapter.bianjian

import javax.annotation.PostConstruct
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.stream.internal.JdbcDatabase
import nirvana.hall.stream.services.StreamService
import org.apache.tapestry5.ioc.annotations.{EagerLoad, InjectService}

import scala.annotation.tailrec

/**
 * bianjian stream
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-16
 */
@EagerLoad
class BianjianStream(@InjectService("ImgDataSource") dataSource:DataSource,streamService:StreamService) {
  private implicit val ds = dataSource
  private val batch_size = 1000
  private val firmCode = "1400"

  @PostConstruct
  def startStream(): Unit ={
    val minSeq = getMinSeq
    val maxSeq = getMaxSeq + 1
    fetch(minSeq,minSeq+batch_size,maxSeq)
  }
  //递归
  @tailrec
  private def fetch(from: Int, until: Int, maxSeq: Int): Unit ={
    if(from < maxSeq){
      fetchSegmentData(from,until)
      fetch(until, until+ batch_size, maxSeq)
    }
  }
  private def fetchSegmentData(from: Int, until: Int): Unit ={
    val sql = "select t.csid, t.zp from T_PC_A_CS t where t.csid >=? and t.csid <? order by t.csid"
    JdbcDatabase.queryWithPsSetter(sql){
      ps=>
        ps.setInt(1, from)
        ps.setInt(2, until)
    }{rs=>
      val csid = rs.getLong("csid")
      val zp = rs.getBytes("zp")
      streamService.pushEvent(csid, ByteString.copyFrom(zp), true,firmCode,FingerPosition.FINGER_L_THUMB, FeatureType.FingerTemplate)
    }
  }

  def getMaxSeq : Int={
    getSeq("select min(csid) from T_PC_A_CS").getOrElse(0)
  }
  def getMinSeq: Int={
    getSeq("select max(csid) from T_PC_A_CS").getOrElse(0)
  }
  def getSeq(sql: String): Option[Int] ={
    JdbcDatabase.queryFirst(sql){ps=>}{rs=>
      rs.getInt(1)
    }
  }
}
