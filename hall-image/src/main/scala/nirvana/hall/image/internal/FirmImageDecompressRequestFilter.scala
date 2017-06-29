package nirvana.hall.image.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.c.services.AncientData._
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
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
      val is = request.getCprData.newInput()
      val gafisImg = new GAFISIMAGESTRUCT
      gafisImg.fromStreamReader(is)
      if(gafisImg.stHead.nImgSize == 0)
        throw new IllegalArgumentException("image data length is zero.")

      val originalImg = firmDecoder.decode(gafisImg)
      val firmResponseBuilder = FirmImageDecompressResponse.newBuilder()
      val output = ByteString.newOutput(originalImg.getDataSize)
      originalImg.writeToStreamWriter(output)
      firmResponseBuilder.setOriginalData(output.toByteString)

      response.writeMessage(commandRequest,FirmImageDecompressResponse.cmd,firmResponseBuilder.build())

      true
    }else{
      handler.handle(commandRequest,response)
    }
  }

}
