package nirvana.hall.spark.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.PartitionRecordsSaver
import nirvana.hall.spark.services.SparkFunctions.StreamEvent

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-06-04
  */
class DumpSaver extends PartitionRecordsSaver{
  override def savePartitionRecords(parameter: NirvanaSparkConfig)(records: Iterator[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]): Unit = {
    records.foreach { case (event, image,mnt, bin) =>
      //do nothing
    }
  }
}
