package nirvana.hall.spark.internal

import nirvana.hall.spark.services.FptPropertiesConverter.{LatentCaseConvert, TemplateFingerConvert}

object GafisPartitionRecordsMigrationSaver {
  import GafisPartitionRecordsDakuSaver._

  def savePartitionRecords(records : Iterator[Any]): Unit = {
    records.foreach{ t =>
      if (t.isInstanceOf[TemplateFingerConvert]) saveTemplateFinger(t.asInstanceOf[TemplateFingerConvert])
      else saveLatent(t.asInstanceOf[LatentCaseConvert])
    }
  }

  def saveTemplatePartitionRecords(records : Iterator[TemplateFingerConvert]): Unit = {
    records.foreach( t=> saveTemplateFinger(t))
  }

  def saveLatentPartitionRecords(records : Iterator[LatentCaseConvert]): Unit = {
    records.foreach( t=> saveLatent(t))
  }
}
