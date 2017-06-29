package nirvana.hall.spark.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.SparkFunctions.StreamEvent

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-30
  */
object PartitionRecordsSaverService {
  private lazy val saver:PartitionRecordsSaver = createSaver
  private var saverClassName:String = _
  def createSaver: PartitionRecordsSaver ={
    Thread.currentThread().getContextClassLoader
      .loadClass(saverClassName).newInstance()
      .asInstanceOf[PartitionRecordsSaver]
  }
  def savePartitionRecords(parameter: NirvanaSparkConfig)(records:Iterator[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]):Unit={
    saverClassName = parameter.dataSaverClass
    saver.savePartitionRecords(parameter)(records)
  }
}
