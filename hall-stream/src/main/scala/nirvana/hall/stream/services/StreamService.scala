package nirvana.hall.stream.services

import com.google.protobuf.ByteString
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition

/**
 * stream service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
trait StreamService {
  def pushEvent(id:Any,img:ByteString,imgIsCompressed:Boolean,position:FingerPosition,featureType:FeatureType): Unit
}
