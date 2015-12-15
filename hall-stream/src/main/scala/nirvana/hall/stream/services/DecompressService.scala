package nirvana.hall.stream.services

import com.google.protobuf.ByteString
import nirvana.hall.protocol.image.FirmImageDecompressProto.FirmImageDecompressRequest

/**
 * decompress service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
trait DecompressService {
  /**
   * decompress image
   * @param request request
   * @return original image data
   */
  def decompress(request:FirmImageDecompressRequest):Option[ByteString]
}
