package nirvana.hall.api.internal.filter

import monad.rpc.protocol.CommandProto.BaseCommand
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler, QueryService}
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse, QuerySendRequest, QuerySendResponse}

/**
 * Created by songpeng on 15/12/9.
 */
class QueryFilter(queryService: QueryService) extends ProtobufRequestFilter {

  override def handle(protobufRequest: BaseCommand, responseBuilder: BaseCommand.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(QuerySendRequest.cmd)) {
      val request = protobufRequest.getExtension(QuerySendRequest.cmd)
      val response = queryService.sendQuery(request)
      responseBuilder.setExtension(QuerySendResponse.cmd, response)

      true
    } else if (protobufRequest.hasExtension(QueryGetRequest.cmd)) {
      val request = protobufRequest.getExtension(QueryGetRequest.cmd)
      val response = queryService.getQuery(request)

      responseBuilder.setExtension(QueryGetResponse.cmd, response)
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }
  }

}
