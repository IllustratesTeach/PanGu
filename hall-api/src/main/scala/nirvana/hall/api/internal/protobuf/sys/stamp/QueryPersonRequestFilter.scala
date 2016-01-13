package nirvana.hall.api.internal.protobuf.sys.stamp

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ScalaUtils
import nirvana.hall.api.services.stamp.GatherPersonService
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.sys.stamp.PersonProto.PersonInfo
import nirvana.hall.protocol.sys.stamp.QueryBasePersonProto.{QueryBasePersonRequest, QueryBasePersonResponse}

/**
 * Created by wangjue on 2015/11/2.
 */
class QueryPersonRequestFilter(gatherPersonService : GatherPersonService)
  extends ProtobufRequestFilter
  with LoggerSupport {

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(QueryBasePersonRequest.cmd)) {
      val request = protobufRequest.getExtension(QueryBasePersonRequest.cmd)
      val builder = QueryBasePersonResponse.newBuilder()
      val person =  gatherPersonService.queryBasePersonInfo(request.getPersonid)
      val b = PersonInfo.newBuilder()
      person match {
        case (Some(person)) =>
          ScalaUtils.convertScalaToProtobuf(person,b)
          builder.addPersonInfo(b)
          responseBuilder.setExtension(QueryBasePersonResponse.cmd,builder.build())
        case _ =>
          responseBuilder.setStatus(ResponseStatus.FAIL)
          responseBuilder.setMessage("no data");
      }
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }

  }
}
