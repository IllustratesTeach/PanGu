package nirvana.hall.stream.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.{CommandStatus, BaseCommand}
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
    val baseRequest = BaseCommand.newBuilder()
    baseRequest.setTaskId(1L)//use constant
    baseRequest.setExtension(FirmImageDecompressRequest.cmd,request)
    val baseResponseBuilder = BaseCommand.newBuilder()
    WebHttpClientUtils.call(url, request, baseResponseBuilder)
    val baseResponse = baseResponseBuilder.build

    baseResponse.getStatus  match{
      case CommandStatus.OK =>
        val response = baseResponse.getExtension(FirmImageDecompressResponse.cmd)
        Some(response.getData)
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to decompress,server message:%s".format(baseResponse.getMsg))
    }
  }
}
