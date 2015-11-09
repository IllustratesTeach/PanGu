package nirvana.hall.api.internal.protobuf.sys.stamp

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{ProtobufRequestHandler, ProtobufRequestFilter}
import nirvana.hall.api.services.stamp.GatherPersonService
import nirvana.hall.protocol.sys.CommonProto.{BaseResponse, BaseRequest}
import nirvana.hall.protocol.sys.stamp.SavePersonProto.{SavePersonResponse, SavePersonRequest}

/**
 * Created by wangjue on 2015/11/3.
 */
class AddPersonInfoRequestFilter(gatherPersonService : GatherPersonService)
  extends ProtobufRequestFilter
  with LoggerSupport {

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(SavePersonRequest.cmd)) {
      val request = protobufRequest.getExtension(SavePersonRequest.cmd)
      val builder = SavePersonResponse.newBuilder()
      val result =  gatherPersonService.saveGatherPerson(request.getPersonInfo)
      builder.setNext(result.toString)
      responseBuilder.setExtension(SavePersonResponse.cmd, builder.build())
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }

  }

}
