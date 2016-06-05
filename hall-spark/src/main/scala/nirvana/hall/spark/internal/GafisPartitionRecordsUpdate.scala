package nirvana.hall.spark.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.{PartitionRecordsSaver, SysProperties, SparkFunctions}
import nirvana.hall.spark.services.SparkFunctions.{StreamError, StreamEvent}
import nirvana.hall.support.services.JdbcDatabase

import scala.util.control.NonFatal

/**
 * Created by wangjue on 2016/6/3.
 */
class GafisPartitionRecordsUpdate extends PartitionRecordsSaver {
  import GafisPartitionRecordsUpdate._
  def savePartitionRecords(parameter: NirvanaSparkConfig)(records:Iterator[(StreamEvent, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]):Unit = {
    records.foreach { case (event, mnt, bin) =>
      try {
        savePersonFingerMntInfo(event.path,mnt.toByteArray())
      }  catch {
        case NonFatal(e) =>
          e.printStackTrace(System.err)
          SparkFunctions.reportError(parameter, DbError(event, e.toString))
      }
    }
  }

}
object GafisPartitionRecordsUpdate{
  lazy implicit val dataSource = SysProperties.getDataSource("gafis")
  case class DbError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "S|"+message
  }


  private def savePersonFingerMntInfo(pkId : String,newFeature : Array[Byte]): Unit = {
    val updateFeatureSql: String = "update gafis_gather_finger t set t.gather_data = ? , t.modifiedtime = sysdate where t.pk_id = ?"
    JdbcDatabase.update(updateFeatureSql) { ps =>
      ps.setBytes(1, newFeature)
      ps.setString(2, pkId)

    }
  }
}