package nirvana.hall.stream.services

import com.google.protobuf.ByteString
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition

/**
 * extract service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
trait ExtractService {
  /**
   * extract service
   * @param img image data
   * @param fingerPosition finger position
   * @param featureType feature type
   */
  def extract(img:ByteString,fingerPosition: FingerPosition,featureType: FeatureType):Option[ByteString]
}
