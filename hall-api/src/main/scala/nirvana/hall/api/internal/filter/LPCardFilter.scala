package nirvana.hall.api.internal.filter

import monad.rpc.protocol.CommandProto.BaseCommand
import nirvana.hall.api.services.{LPCardService, ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.api.LPCardProto._

/**
 * Created by songpeng on 15/11/15.
 */
class LPCardFilter(lPCardService: LPCardService) extends ProtobufRequestFilter{
  override def handle(protobufRequest: BaseCommand, responseBuilder: BaseCommand.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(LPCardAddRequest.cmd)) {
      val request = protobufRequest.getExtension(LPCardAddRequest.cmd)
      val response = lPCardService.addLPCard(request)

      responseBuilder.setExtension(LPCardAddResponse.cmd, response)
      true
    }//删除现场
    else if(protobufRequest.hasExtension(LPCardDelRequest.cmd)) {
      val request = protobufRequest.getExtension(LPCardDelRequest.cmd)
      val response = lPCardService.delLPCard(request)

      responseBuilder.setExtension(LPCardDelResponse.cmd, response)
      true
    }//更新现场
    else if(protobufRequest.hasExtension(LPCardUpdateRequest.cmd)) {
      val request = protobufRequest.getExtension(LPCardUpdateRequest.cmd)
      val response = lPCardService.updateLPCard(request)

      responseBuilder.setExtension(LPCardUpdateResponse.cmd, response)
      true
    }//查询现场
    else if(protobufRequest.hasExtension(LPCardGetRequest.cmd)){
      val request = protobufRequest.getExtension(LPCardGetRequest.cmd)
      val response = lPCardService.getLPCard(request)

      responseBuilder.setExtension(LPCardGetResponse.cmd, response)
      true
    }else{
      handler.handle(protobufRequest, responseBuilder);
    }
  }
}
