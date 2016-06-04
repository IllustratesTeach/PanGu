package nirvana.hall.spark.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.PartitionRecordsSaver
import nirvana.hall.spark.services.SparkFunctions.StreamEvent

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-06-04
  */
class DumpSaver extends PartitionRecordsSaver{
  override def savePartitionRecords(parameter: NirvanaSparkConfig)(records: Iterator[(StreamEvent, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]): Unit = {
    records.foreach { case (event, mnt, bin) =>
      //do nothing
    }
  }
}
