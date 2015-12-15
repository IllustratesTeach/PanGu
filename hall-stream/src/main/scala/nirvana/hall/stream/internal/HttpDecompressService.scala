package nirvana.hall.stream.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.image.FirmImageDecompressProto.{FirmImageDecompressRequest, FirmImageDecompressResponse}
import nirvana.hall.stream.services.{DecompressService, RpcHttpClient}

/**
 * http decompress service
 * Created by songpeng on 15/12/15.
 */
class HttpDecompressService(url: String,rpcHttpClient: RpcHttpClient) extends DecompressService{
  /**
   * decompress image
   * @param request request
   * @return original image data
   */
  override def decompress(request: FirmImageDecompressRequest): Option[ByteString] = {
    val baseResponse = rpcHttpClient.call(url,FirmImageDecompressRequest.cmd,request)

    baseResponse.getStatus  match{
      case CommandStatus.OK =>
        if(baseResponse.hasExtension(FirmImageDecompressResponse.cmd)) {
          val response = baseResponse.getExtension(FirmImageDecompressResponse.cmd)
          Some(response.getData)
        }else{
          throw new IllegalAccessException("response haven't FirmImageDecompressResponse")
        }
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to decompress,server message:%s".format(baseResponse.getMsg))
    }
  }
}
