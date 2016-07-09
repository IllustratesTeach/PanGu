package nirvana.hall.api.internal.filter

import javax.servlet.http.HttpServletRequest

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse, QuerySendRequest, QuerySendResponse}

/**
 * Created by songpeng on 15/12/9.
 */
class QueryFilter(httpServletRequest: HttpServletRequest, queryService: QueryService) extends RpcServerMessageFilter {

  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if (commandRequest.hasExtension(QuerySendRequest.cmd)) {
      val request = commandRequest.getExtension(QuerySendRequest.cmd)
      val dbConfig = getDBConfig(httpServletRequest)
      val response = queryService.sendQuery(request, dbConfig)
      commandResponse.writeMessage(commandRequest, QuerySendResponse.cmd, response)
      true
    } else if (commandRequest.hasExtension(QueryGetRequest.cmd)) {
      val request = commandRequest.getExtension(QueryGetRequest.cmd)
      val matchResult = queryService.getMatchResult(request.getOraSid)
      val response = QueryGetResponse.newBuilder()
      response.setIsComplete(false)
      matchResult.foreach{result =>
        response.setMatchResult(result)
        response.setIsComplete(true)
      }
      commandResponse.writeMessage(commandRequest, QueryGetResponse.cmd, response.build())
      true
    } else {
      handler.handle(commandRequest, commandResponse)
    }
  }

}
