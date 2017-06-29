package nirvana.hall.extractor.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{RpcServerMessageHandler, CommandResponse, RpcServerMessageFilter}
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.protocol.extract.OldConverterNewProto.{OldConverterNewResponse, OldConverterNewRequest}

/**
 * Created by wangjue on 2016/5/24.
 */
class OldConverterNewRequestFilter(extractor:FeatureExtractor) extends RpcServerMessageFilter {
  override def handle(commandRequest: BaseCommand, response: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(OldConverterNewRequest.cmd)){
      val mntRequest = commandRequest.getExtension(OldConverterNewRequest.cmd)
      val mntData = extractor.ConvertMntOldToNew(mntRequest.getOldMnt.newInput())

      val mntResponseBuilder = OldConverterNewResponse.newBuilder()
      mntResponseBuilder.setNewMnt(ByteString.copyFrom(mntData.get))
      response.writeMessage(commandRequest,OldConverterNewResponse.cmd,mntResponseBuilder.build())

      true
    }else{
      handler.handle(commandRequest,response)
    }
  }
}
