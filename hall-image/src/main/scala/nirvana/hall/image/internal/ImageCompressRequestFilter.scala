package nirvana.hall.image.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.image.services.ImageEncoder
import nirvana.hall.c.services.AncientData._
import nirvana.hall.protocol.image.ImageCompressProto.{ImageCompressRequest, ImageCompressResponse}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class ImageCompressRequestFilter(imageEncoder: ImageEncoder) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, response: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(ImageCompressRequest.cmd)){

      val request = commandRequest.getExtension(ImageCompressRequest.cmd)
      val cprImg = imageEncoder.encode(request)
      val output = ByteString.newOutput(cprImg.getDataSize)
      cprImg.writeToStreamWriter(output)
      val imgCompressResponseBuilder = ImageCompressResponse.newBuilder()
      imgCompressResponseBuilder.setCprData(output.toByteString)

      response.writeMessage(commandRequest,ImageCompressResponse.cmd,imgCompressResponseBuilder.build())

      true
    }else{
      handler.handle(commandRequest,response)
    }
  }

}
