package nirvana.hall.spark.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.SparkFunctions.StreamEvent

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-30
  */
object PartitionRecordsSaverService {
  private val saver:PartitionRecordsSaver = createSaver
  private var saverClassName:String = _
  def createSaver: PartitionRecordsSaver ={
    Class.forName(saverClassName).newInstance().asInstanceOf[PartitionRecordsSaver]
  }
  def savePartitionRecords(parameter: NirvanaSparkConfig)(records:Iterator[(StreamEvent, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]):Unit={
    saverClassName = parameter.dataSaverClass
    saver.savePartitionRecords(parameter)(records)
  }
}
