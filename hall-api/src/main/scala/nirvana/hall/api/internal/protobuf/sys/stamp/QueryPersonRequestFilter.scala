package nirvana.hall.api.internal.protobuf.sys.stamp

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{ProtobufRequestHandler, ProtobufRequestFilter}
import nirvana.hall.api.services.stamp.GatherPersonService
import nirvana.hall.protocol.sys.CommonProto.{BaseResponse, BaseRequest}
import nirvana.hall.protocol.sys.stamp.QueryPersonProto.{QueryPersonResponse, QueryPersonRequest}

/**
 * Created by wangjue on 2015/11/2.
 */
class QueryPersonRequestFilter(gatherPersonService : GatherPersonService)
  extends ProtobufRequestFilter
  with LoggerSupport {

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(QueryPersonRequest.cmd)) {
      val request = protobufRequest.getExtension(QueryPersonRequest.cmd)
      val builder = QueryPersonResponse.newBuilder()
      val result =  gatherPersonService.queryGatherPersonBy(request.getName,request.getIdcard,request.getGatherDateST,request.getGatherDateEN,request.getStart,request.getLimit)
      println(result.length)
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }

  }
}
