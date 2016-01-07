package nirvana.hall.api.internal.protobuf.sys.stamp

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.MsgBase64
import nirvana.hall.api.services.stamp.GatherFingerPalmService
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.sys.stamp.QueryFingerDataProto.{GatherData, QueryFingerDataRequest, QueryFingerDataResponse}

/**
 * Created by wangjue on 2015/11/18.
 */
class QueryFingerRequestFilter (gatherFingerPalmService : GatherFingerPalmService)
  extends ProtobufRequestFilter
  with LoggerSupport {

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(QueryFingerDataRequest.cmd)) {
      val request = protobufRequest.getExtension(QueryFingerDataRequest.cmd)
      val builder = QueryFingerDataResponse.newBuilder()
      val fingerDatas = gatherFingerPalmService.queryFingerDataByPersonId(request.getPersonId)
      val data = GatherData.newBuilder()
      if (fingerDatas.size > 0) {
        for (finger <- fingerDatas) {
          data.setGatherFgp(finger.fgp)
          data.setGatherFgpCase(finger.fgpCase.toInt)
          data.setLobtype(finger.lobtype)
          data.setGroupId(finger.groupId.toInt)
          val byte = finger.gatherData.getBytes(0,finger.gatherData.length().toInt)
          val gatherData = MsgBase64.toBase64(byte)
          data.setGatherData(gatherData)
          builder.addGatherData(data)
        }
        responseBuilder.setExtension(QueryFingerDataResponse.cmd,builder.build())
      } else {
        responseBuilder.setStatus(ResponseStatus.FAIL)
        responseBuilder.setMessage("fail");
      }
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }
    true
  }

}
