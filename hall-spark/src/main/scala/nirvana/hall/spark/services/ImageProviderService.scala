package nirvana.hall.spark.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.SparkFunctions._

/**
 * request remote fpt file
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-09
 */
object ImageProviderService {
  private val provider:ImageProvider = createProvider
  private var providerClassName:String = _
  def requestRemoteFile(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, GAFISIMAGESTRUCT)] = {
    providerClassName = parameter.imageProviderClass
    provider.requestImage(parameter,message)
  }
  private def createProvider:ImageProvider={
    Class.forName(providerClassName).newInstance().asInstanceOf[ImageProvider]
  }
}
