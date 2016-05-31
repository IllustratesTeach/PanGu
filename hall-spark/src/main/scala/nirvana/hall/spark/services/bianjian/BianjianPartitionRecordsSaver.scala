package nirvana.hall.spark.services.bianjian

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.SparkFunctions.{StreamError, StreamEvent}
import nirvana.hall.spark.services.{SparkFunctions, SysProperties}
import nirvana.hall.support.services.JdbcDatabase

import scala.util.control.NonFatal

/**
 * partition records saver
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-09
 */
object BianjianPartitionRecordsSaver {
  private lazy implicit val dataSource = SysProperties.getDataSource("bianjian")
  case class DbError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "S|"+message
  }
  def savePartitionRecords(parameter: NirvanaSparkConfig)(records:Iterator[(StreamEvent, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]):Unit = {
      records.foreach { case (event, mnt,bin) =>
        try {
          //保存人员信息
          saveGafisMnt(event.personId, mnt.toByteArray())
        } catch {
          case NonFatal(e) =>
            e.printStackTrace(System.err)
            SparkFunctions.reportError(parameter, DbError(event, e.toString))
        }
      }
  }

  //查询人员主表信息
  def queryCsid(csid: String) : Option[String] = {
      JdbcDatabase.queryFirst("select t.csid from T_PC_A_CS where csid = ?"){ps =>
        ps.setString(1,csid)
      }{rs=>
        rs.getString("csid")
      }
  }

  //保存人员指纹特征信息
  private def saveGafisMnt(csid: String,mnt : Array[Byte]): Unit = {
    val sql: String = "insert into gafis_mnt (csid, mnt, seq) values(?,?, gafis_mnt_seq.nextval)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1, csid)
      ps.setBytes(2, mnt)
    }
  }
}
