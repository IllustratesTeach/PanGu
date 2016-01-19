package nirvana.hall.extractor.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{RpcServerMessageHandler, CommandResponse, RpcServerMessageFilter}
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.protocol.extract.ExtractProto.{ExtractResponse, ExtractRequest}

/**
 * support extract request protocol
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-12
 */
class ExtractRequestFilter(extractor:FeatureExtractor) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, response: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(ExtractRequest.cmd)){
      val extractRequest = commandRequest.getExtension(ExtractRequest.cmd)
      val mntData = extractor.extractByGAFISIMGBinary(extractRequest.getImgData.newInput(),
        extractRequest.getPosition,
        extractRequest.getMntType,
        extractRequest.getFeatureTry)

      val extractResponseBuilder = ExtractResponse.newBuilder()
      extractResponseBuilder.setMntData(ByteString.copyFrom(mntData))
      response.writeMessage(commandRequest,ExtractResponse.cmd,extractResponseBuilder.build())

      true
    }else{
      handler.handle(commandRequest,response)
    }
  }
}
