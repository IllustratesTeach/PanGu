package nirvana.hall.v70.internal.filter.stamp

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.sys.stamp.SaveFingerDataProto.{SaveFingerDataRequest, SaveFingerDataResponse}
import nirvana.hall.v70.services.stamp.GatherFingerPalmService

/**
 * Created by wangjue on 2015/11/18.
 */
class AddFingerRequestFilter (gatherFingerPalmService : GatherFingerPalmService)
  extends ProtobufRequestFilter
  with LoggerSupport {

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(SaveFingerDataRequest.cmd)) {
      val request = protobufRequest.getExtension(SaveFingerDataRequest.cmd)
      val builder = SaveFingerDataResponse.newBuilder()
      val status = gatherFingerPalmService.addFingerPalmData(request.getFingerData, request.getPersonId)
      if ("success".equals(status)) {
        builder.setSaveStatus("success")
        responseBuilder.setExtension(SaveFingerDataResponse.cmd, builder.build())
      } else {
        responseBuilder.setStatus(ResponseStatus.FAIL)
        responseBuilder.setMessage("add fail");
      }
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }
  }
}
