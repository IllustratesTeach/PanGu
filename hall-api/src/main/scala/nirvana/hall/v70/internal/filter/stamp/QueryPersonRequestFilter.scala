package nirvana.hall.v70.internal.filter.stamp

import monad.rpc.protocol.CommandProto.{CommandStatus, BaseCommand}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ScalaUtils
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.stamp.PersonProto.PersonInfo
import nirvana.hall.protocol.sys.stamp.QueryBasePersonProto.{QueryBasePersonRequest, QueryBasePersonResponse}
import nirvana.hall.v70.services.stamp.GatherPersonService

/**
 * Created by wangjue on 2015/11/2.
 */
class QueryPersonRequestFilter(gatherPersonService : GatherPersonService)
  extends ProtobufRequestFilter
  with LoggerSupport {

  override def handle(protobufRequest: BaseCommand, responseBuilder: BaseCommand.Builder, handler: ProtobufRequestHandler): Boolean = {
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
          responseBuilder.setStatus(CommandStatus.FAIL)
          responseBuilder.setMsg("no data");
      }
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }

  }
}
