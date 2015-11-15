package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.{ProtobufRequestHandler, ProtobufRequestFilter}
import nirvana.hall.protocol.sys.CommonProto.{BaseResponse, BaseRequest}
import nirvana.hall.protocol.v62.AddLPCardProto.{AddLPCardResponse, AddLPCardRequest}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
 * Created by songpeng on 15/11/15.
 */
class AddLPCardFilter(facade: V62Facade, config: HallV62Config) extends ProtobufRequestFilter{
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(AddLPCardRequest.cmd)){
      val request = protobufRequest.getExtension(AddLPCardRequest.cmd)
      //转换为c的结构
      val lpCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(request.getCard)
      //调用实现方法
      facade.NET_GAFIS_FLIB_Add(config.latentTable.dbId.toShort,
        config.latentTable.tableId.toShort,
        request.getCard.getStrCardID, lpCard)

      responseBuilder.setExtension(AddLPCardResponse.cmd, AddLPCardResponse.newBuilder().build())
      true
    }else{
      handler.handle(protobufRequest, responseBuilder);
    }
  }
}
