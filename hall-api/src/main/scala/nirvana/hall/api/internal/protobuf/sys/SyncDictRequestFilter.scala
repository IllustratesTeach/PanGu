package nirvana.hall.api.internal.protobuf.sys

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler, DictService}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.sys.SyncDictProto.{SyncDictRequest, SyncDictResponse}

/**
 * Created by songpeng on 15/11/4.
 */
class SyncDictRequestFilter(syncDictService: DictService) extends ProtobufRequestFilter with LoggerSupport {
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(SyncDictRequest.cmd)){
      val request = protobufRequest.getExtension(SyncDictRequest.cmd)
      val response = SyncDictResponse.newBuilder().setDictType(request.getDictType)
      val dictList = syncDictService.findAllDict(request.getDictType)
      dictList.foreach(response.addDictData)
      responseBuilder.setExtension(SyncDictResponse.cmd, response.build())
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }
  }
}
