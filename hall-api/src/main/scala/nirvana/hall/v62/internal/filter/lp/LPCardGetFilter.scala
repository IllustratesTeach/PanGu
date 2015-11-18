package nirvana.hall.v62.internal.filter.lp

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.lp.LPCardGetProto.{LPCardGetRequest, LPCardGetResponse}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclp.GLPCARDINFOSTRUCT
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
 * Created by songpeng on 15/11/18.
 */
class LPCardGetFilter(facade: V62Facade, config: HallV62Config) extends ProtobufRequestFilter{
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(LPCardGetRequest.cmd)){
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
