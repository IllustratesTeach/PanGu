package nirvana.hall.api.internal.filter

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.LPCardProto._

/**
 * Created by songpeng on 15/11/15.
 */
class LPCardFilter(lPCardService: LPCardService) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(LPCardAddRequest.cmd)) {
      val request = commandRequest.getExtension(LPCardAddRequest.cmd)
      val response = lPCardService.addLPCard(request)
      commandResponse.writeMessage(commandRequest, LPCardAddResponse.cmd, response)
      true
    }//删除现场
    else if(commandRequest.hasExtension(LPCardDelRequest.cmd)) {
      val request = commandRequest.getExtension(LPCardDelRequest.cmd)
      val response = lPCardService.delLPCard(request)
      commandResponse.writeMessage(commandRequest, LPCardDelResponse.cmd, response)
      true
    }//更新现场
    else if(commandRequest.hasExtension(LPCardUpdateRequest.cmd)) {
      val request = commandRequest.getExtension(LPCardUpdateRequest.cmd)
      val response = lPCardService.updateLPCard(request)
      commandResponse.writeMessage(commandRequest, LPCardUpdateResponse.cmd, response)
      true
    }//查询现场
    else if(commandRequest.hasExtension(LPCardGetRequest.cmd)){
      val request = commandRequest.getExtension(LPCardGetRequest.cmd)
      val response = lPCardService.getLPCard(request)
      commandResponse.writeMessage(commandRequest, LPCardGetResponse.cmd, response)
      true
    }else{
      handler.handle(commandRequest, commandResponse);
    }
  }
}
