package nirvana.hall.image.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.image.services.FirmDecoder
import nirvana.hall.protocol.image.FirmImageDecompressProto.{FirmImageDecompressRequest, FirmImageDecompressResponse}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class FirmImageDecompressRequestFilter(firmDecoder: FirmDecoder) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, response: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(FirmImageDecompressRequest.cmd)){

      val request = commandRequest.getExtension(FirmImageDecompressRequest.cmd)
      val data = firmDecoder.decode(request.getCode,request.getCprData.toByteArray,request.getWidth,request.getHeight,request.getDpi)

      val firmResponseBuilder = FirmImageDecompressResponse.newBuilder()
      firmResponseBuilder.setData(ByteString.copyFrom(data))

      response.writeMessage(commandRequest,FirmImageDecompressResponse.cmd,firmResponseBuilder.build())

      true
    }else{
      handler.handle(commandRequest,response)
    }
  }
}
