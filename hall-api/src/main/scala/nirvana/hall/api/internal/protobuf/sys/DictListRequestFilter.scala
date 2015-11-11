package nirvana.hall.api.internal.protobuf.sys

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{DictService, ProtobufRequestHandler, ProtobufRequestFilter}
import nirvana.hall.protocol.sys.CommonProto.{BaseResponse, BaseRequest}
import nirvana.hall.protocol.sys.DictListProto.{DictListResponse, DictListRequest}

/**
 * Created by songpeng on 15/11/10.
 */
class DictListRequestFilter(dictService: DictService) extends ProtobufRequestFilter with LoggerSupport{
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(DictListRequest.cmd)){
      val request = protobufRequest.getExtension(DictListRequest.cmd)
      val response = DictListResponse.newBuilder()
      val dictList = dictService.findDictList(request.getDictType, None, None, 0, 0)
      dictList.foreach(f=>response.addDictBuilder().setCode(f._1).setName(f._2))
      responseBuilder.setExtension(DictListResponse.cmd, response.build())
      true
    }else{
      handler.handle(protobufRequest, responseBuilder)
    }
  }
}
