package nirvana.hall.spark.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.SparkFunctions.StreamEvent

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-30
  */
trait PartitionRecordsSaver {
  def savePartitionRecords(parameter: NirvanaSparkConfig)(records:Iterator[(StreamEvent, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]):Unit
}
