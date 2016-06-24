package nirvana.hall.api.internal.filter

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse}

/**
 * Created by songpeng on 16/6/21.
 */
class MatchRelationFilter(matchRelationService: MatchRelationService) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(MatchRelationGetRequest.cmd)){
      val request = commandRequest.getExtension(MatchRelationGetRequest.cmd)
      val response = matchRelationService.getMatchRelation(request)
      commandResponse.writeMessage(commandRequest, MatchRelationGetResponse.cmd, response)
      true
    }else{
      handler.handle(commandRequest, commandResponse)
    }

  }
}
