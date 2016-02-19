package nirvana.hall.v70.internal.filter.stamp

import monad.rpc.protocol.CommandProto.{CommandStatus, BaseCommand}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ScalaUtils
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.stamp.PersonProto.PersonInfo
import nirvana.hall.protocol.sys.stamp.SavePersonProto.{SavePersonRequest, SavePersonResponse}
import nirvana.hall.v70.services.stamp.GatherPersonService

/**
 * Created by wangjue on 2015/11/3.
 */
class AddPersonInfoRequestFilter(gatherPersonService : GatherPersonService)
  extends ProtobufRequestFilter
  with LoggerSupport {

  override def handle(protobufRequest: BaseCommand, responseBuilder: BaseCommand.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(SavePersonRequest.cmd)) {
      val request = protobufRequest.getExtension(SavePersonRequest.cmd)
      val builder = SavePersonResponse.newBuilder()
      val person =  gatherPersonService.saveGatherPerson(request.getPersonInfo)
      val b = PersonInfo.newBuilder()
      if (person.personid!=null) {
        ScalaUtils.convertScalaToProtobuf(person,b)
        builder.addPersonInfo(b)
        responseBuilder.setExtension(SavePersonResponse.cmd,builder.build())
      } else {
        responseBuilder.setStatus(CommandStatus.FAIL)
        responseBuilder.setMsg("no data");
      }

      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }

  }

}
