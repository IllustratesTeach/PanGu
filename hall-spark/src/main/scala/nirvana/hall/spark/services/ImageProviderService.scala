package nirvana.hall.spark.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.SparkFunctions._

/**
 * request remote fpt file
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-09
 */
object ImageProviderService {
  private lazy val provider:ImageProvider = createProvider
  private var providerClassName:String = _
  def createProvider:ImageProvider={
    Thread.currentThread().getContextClassLoader
      .loadClass(providerClassName).newInstance().asInstanceOf[ImageProvider]
  }
  def requestRemoteFile(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, GAFISIMAGESTRUCT)] = {
    providerClassName = parameter.imageProviderClass
    provider.requestImage(parameter,message)
  }
  def requestRemoteFileByBMP(parameter: NirvanaSparkConfig, message: String): Option[(StreamEvent,TemplateFingerConvert,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT)]= {
    providerClassName = parameter.imageProviderClass
    provider.requestImageByBMP(parameter,message)
  }
  def requestRemoteFileByFID(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent,TemplateFingerConvert,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT)]= {
    providerClassName = parameter.imageProviderClass
    provider.requestImageByFID(parameter,message)
  }
}
