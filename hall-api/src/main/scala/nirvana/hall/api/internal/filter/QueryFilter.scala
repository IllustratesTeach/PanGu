package nirvana.hall.api.internal.filter

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageHandler, RpcServerMessageFilter}
import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse, QuerySendRequest, QuerySendResponse}

/**
 * Created by songpeng on 15/12/9.
 */
class QueryFilter(queryService: QueryService) extends RpcServerMessageFilter {

  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if (commandRequest.hasExtension(QuerySendRequest.cmd)) {
      val request = commandRequest.getExtension(QuerySendRequest.cmd)
      val response = queryService.sendQuery(request)
      commandResponse.writeMessage(commandRequest, QuerySendResponse.cmd, response)
      true
    } else if (commandRequest.hasExtension(QueryGetRequest.cmd)) {
      val request = commandRequest.getExtension(QueryGetRequest.cmd)
      val response = queryService.getQuery(request)
      commandResponse.writeMessage(commandRequest, QueryGetResponse.cmd, response)
      true
    } else {
      handler.handle(commandRequest, commandResponse)
    }
  }

}
