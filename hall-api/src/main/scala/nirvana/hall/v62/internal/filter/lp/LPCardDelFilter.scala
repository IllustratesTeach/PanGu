package nirvana.hall.v62.internal.filter.lp

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.lp.LPCardDelProto.{LPCardDelRequest, LPCardDelResponse}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

/**
 * Created by songpeng on 15/11/18.
 */
class LPCardDelFilter(facade: V62Facade, config: HallV62Config) extends ProtobufRequestFilter{
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(LPCardDelRequest.cmd)){
      val request = protobufRequest.getExtension(LPCardDelRequest.cmd)
      val cardId = request.getCardId
      facade.NET_GAFIS_FLIB_Del(config.latentTable.dbId.toShort, config.latentTable.tableId.toShort, cardId)

      responseBuilder.setExtension(LPCardDelResponse.cmd, LPCardDelResponse.newBuilder().build())
      true
    }else{
      handler.handle(protobufRequest, responseBuilder);
    }
  }
}
