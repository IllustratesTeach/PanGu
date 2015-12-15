package nirvana.hall.stream.internal

import com.google.protobuf.ByteString
import nirvana.hall.protocol.extract.ExtractProto.{ExtractResponse, ExtractRequest, FingerPosition}
import nirvana.hall.stream.services.ExtractService

/**
 * Created by songpeng on 15/12/15.
 */
class HttpExtractService(url: String) extends ExtractService{
  /**
   * extract service
   * @param img image data
   * @param fingerPosition finger position
   * @param featureType feature type
   */
  override def extract(img: ByteString, fingerPosition: FingerPosition, featureType: ExtractRequest.FeatureType): Option[ByteString] = {
    val request = ExtractRequest.newBuilder()
    val response = ExtractResponse.newBuilder()
    request.setImgData(img)
    request.setMntType(featureType)
    request.setPosition(fingerPosition)

    //WebHttpClientUtils.call(url, request.build(), response)
    Option(response.getMntData)
  }
}
