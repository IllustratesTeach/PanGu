package nirvana.hall.v70.internal.filter.stamp

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.sys.stamp.UpdatePersonProto.{UpdatePersonRequest, UpdatePersonResponse}
import nirvana.hall.v70.services.stamp.GatherPersonService

/**
 * Created by wangjue on 2015/11/9.
 */
class UpdatePersonRequestFilter(gatherPersonService : GatherPersonService)
  extends ProtobufRequestFilter
  with LoggerSupport  {

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(UpdatePersonRequest.cmd)) {
      val request = protobufRequest.getExtension(UpdatePersonRequest.cmd)
      val builder = UpdatePersonResponse.newBuilder()
      val person =  gatherPersonService.updateGatherPerson(request.getPersonInfo)
      builder.setNext("1")
      responseBuilder.setExtension(UpdatePersonResponse.cmd,builder.build())
      if (person.personid!=null) {
        builder.setNext("1")
        responseBuilder.setExtension(UpdatePersonResponse.cmd,builder.build())
      } else {
        builder.setNext("0")
        responseBuilder.setExtension(UpdatePersonResponse.cmd,builder.build())
        responseBuilder.setStatus(ResponseStatus.FAIL)
        responseBuilder.setMessage("update failed");
      }
      /*person match {
        case (Some(person.personid)) =>
          builder.setNext("1")
          responseBuilder.setExtension(UpdatePersonResponse.cmd,builder.build())
        case _ =>
          builder.setNext("0")
          responseBuilder.setExtension(UpdatePersonResponse.cmd,builder.build())
          responseBuilder.setStatus(ResponseStatus.FAIL)
          responseBuilder.setMessage("update failed");
      }*/
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }

  }
}
