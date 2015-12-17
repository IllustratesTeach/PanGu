package nirvana.hall.stream.internal.adapter.bianjian

import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.stream.internal.JdbcDatabase
import nirvana.hall.stream.services.StreamService
import org.apache.commons.io.IOUtils
import org.apache.tapestry5.ioc.annotations.{EagerLoad, InjectService}

import scala.annotation.tailrec

/**
 * bianjian stream
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-16
 */
@EagerLoad
class BianjianStream(@InjectService("ImgDataSource") dataSource:DataSource,streamService:StreamService)
extends LoggerSupport{
  private implicit val ds = dataSource
  private val batch_size = 1000

  def startStream(): Unit ={
    val minSeq = getMinSeq
    val maxSeq = getMaxSeq + 1
    info("begin to read stream from database min:{} max:{}",minSeq,maxSeq)
    fetch(minSeq,minSeq+batch_size,maxSeq)
    info("congratulation!!! finish to read data")
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
      val zp = rs.getBinaryStream("zp")

      val gafisImg = new GAFISIMAGESTRUCT
      gafisImg.stHead.bIsCompressed = 1
      gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
      gafisImg.bnData = IOUtils.toByteArray(zp)
      gafisImg.stHead.nImgSize = gafisImg.bnData.length
      IOUtils.closeQuietly(zp)


      streamService.pushEvent(csid,gafisImg,FingerPosition.FINGER_L_THUMB, FeatureType.FingerTemplate)
    }
  }

  private def getMaxSeq : Int={
    getSeq("select max(csid) from T_PC_A_CS").getOrElse(0)
  }
  private def getMinSeq: Int={
    getSeq("select min(csid) from T_PC_A_CS").getOrElse(0)
  }
  private def getSeq(sql: String): Option[Int] ={
    JdbcDatabase.queryFirst(sql){ps=>}{rs=>
      rs.getInt(1)
    }
  }
}
