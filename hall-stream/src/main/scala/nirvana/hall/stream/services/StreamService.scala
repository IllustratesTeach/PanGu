package nirvana.hall.stream.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition

/**
 * stream service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
trait StreamService {
  /**
   * push stream event
   * @param id data unique key
   * @param img image data
   * @param position finger position
   * @param featureType feature type
   */
  def pushEvent(id:Any,img:GAFISIMAGESTRUCT,position:FingerPosition,featureType:FeatureType): Unit
}
