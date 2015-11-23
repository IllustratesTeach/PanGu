package nirvana.hall.v62.internal.filter.lp

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.lp.LPCardProto._
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclp.GLPCARDINFOSTRUCT
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
 * Created by songpeng on 15/11/15.
 */
class LPCardFilter(facade: V62Facade, config: HallV62Config) extends ProtobufRequestFilter{
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(LPCardAddRequest.cmd)) {
      val request = protobufRequest.getExtension(LPCardAddRequest.cmd)
      //转换为c的结构
      val lpCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(request.getCard)
      //调用实现方法
      facade.NET_GAFIS_FLIB_Add(config.latentTable.dbId.toShort,
        config.latentTable.tableId.toShort,
        request.getCard.getStrCardID, lpCard)

      responseBuilder.setExtension(LPCardAddResponse.cmd, LPCardAddResponse.newBuilder().build())
      true
    }//删除现场
    else if(protobufRequest.hasExtension(LPCardDelRequest.cmd)) {
      val request = protobufRequest.getExtension(LPCardDelRequest.cmd)
      val cardId = request.getCardId
      facade.NET_GAFIS_FLIB_Del(config.latentTable.dbId.toShort, config.latentTable.tableId.toShort, cardId)

      responseBuilder.setExtension(LPCardDelResponse.cmd, LPCardDelResponse.newBuilder().build())
      true
    }//更新现场
    else if(protobufRequest.hasExtension(LPCardUpdateRequest.cmd)) {
      val request = protobufRequest.getExtension(LPCardUpdateRequest.cmd)
      val lpCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(request.getCard)
      facade.NET_GAFIS_FLIB_Update(config.latentTable.dbId.toShort,
        config.latentTable.tableId.toShort,
        request.getCard.getStrCardID, lpCard)

      responseBuilder.setExtension(LPCardUpdateResponse.cmd, LPCardUpdateResponse.newBuilder().build())
      true
    }//查询现场
    else if(protobufRequest.hasExtension(LPCardGetRequest.cmd)){
      val request = protobufRequest.getExtension(LPCardGetRequest.cmd)
      val cardId = request.getCardId
      val gCard = new GLPCARDINFOSTRUCT
      facade.NET_GAFIS_FLIB_Get(config.latentTable.dbId.toShort, config.latentTable.tableId.toShort, cardId, gCard)
      val card = galoclpConverter.convertGLPCARDINFOSTRUCT2ProtoBuf(gCard)
      card.toBuilder.setStrCardID(cardId)

      responseBuilder.setExtension(LPCardGetResponse.cmd, LPCardGetResponse.newBuilder().setCard(card).build())
      true
    }else{
      handler.handle(protobufRequest, responseBuilder);
    }
  }
}
