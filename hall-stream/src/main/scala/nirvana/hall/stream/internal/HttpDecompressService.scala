package nirvana.hall.stream.internal

import com.google.protobuf.ByteString
import nirvana.hall.protocol.image.FirmImageDecompressProto.{FirmImageDecompressRequest, FirmImageDecompressResponse}
import nirvana.hall.stream.services.DecompressService

/**
 * Created by songpeng on 15/12/15.
 */
class HttpDecompressService(url: String) extends DecompressService{
  /**
   * decompress image
   * @param request request
   * @return original image data
   */
  override def decompress(request: FirmImageDecompressRequest): Option[ByteString] = {
    val response = FirmImageDecompressResponse.newBuilder()
    WebHttpClientUtils.call(url, request, response)

    Option(response.getData)
  }
}
