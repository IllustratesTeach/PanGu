package nirvana.hall.spark.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.{PartitionRecordsSaver, SparkFunctions, SysProperties}
import nirvana.hall.spark.services.SparkFunctions.{StreamError, StreamEvent}
import nirvana.hall.support.services.JdbcDatabase

import scala.util.control.NonFatal

/**
  * Created by wangjue on 2016/11/23.
  */
class GafisPartitionRecordsWJWUpdate extends PartitionRecordsSaver {
  import GafisPartitionRecordsWJWUpdate._
  def savePartitionRecords(parameter: NirvanaSparkConfig)(records:Iterator[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]):Unit = {
    records.foreach { case (event, fingerImg, fingerMnt, fingerBin) =>
      try {
        updatePersonFingerMntInfo(event.path,fingerMnt.toByteArray(),fingerImg.gatherData)
      }  catch {
        case NonFatal(e) =>
          e.printStackTrace(System.err)
          SparkFunctions.reportError(parameter, DbError(event, e.toString))
      }
    }
  }

}

object GafisPartitionRecordsWJWUpdate{
  lazy implicit val dataSourceName = SysProperties.getPropertyOption("data.source.name").get
  lazy implicit val dataSource = SysProperties.getDataSource(dataSourceName)
  case class DbError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "S|"+message
  }


  private def updatePersonFingerMntInfo(pkId : String,newFeature : Array[Byte],restoreImage : Array[Byte]): Unit = {
    val updateFeatureSql: String = "UPDATE GUIZHOU_IDCARDFINGERINFO t SET t.restore_data=?,t.extract_data= ?,t.update_date=sysdate WHERE t.pk_id = ?"
    JdbcDatabase.update(updateFeatureSql) { ps =>
      ps.setBytes(1, restoreImage)
      ps.setBytes(2, newFeature)
      ps.setString(3, pkId)

    }
  }
}