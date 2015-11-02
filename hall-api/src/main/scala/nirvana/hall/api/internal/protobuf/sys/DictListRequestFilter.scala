package nirvana.hall.api.internal.protobuf.sys

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{DictService, ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.sys.DictListProto.{DictListRequest, DictListResponse}

/**
 * Created by songpeng on 15/10/28.
 */
class DictListRequestFilter(dictService: DictService) extends ProtobufRequestFilter with LoggerSupport{
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(DictListRequest.cmd)){
      val request = protobufRequest.getExtension(DictListRequest.cmd)
      val response = DictListResponse.newBuilder()
      val dictList = dictService.findDictList(request.getDictType)
      dictList.foreach { f =>
        response.addDictListBuilder().setCode(f._1).setName(f._2)
      }
      responseBuilder.setExtension(DictListResponse.cmd, response.build())
    }
    false
  }
}
