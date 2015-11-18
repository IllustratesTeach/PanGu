package nirvana.hall.v62.internal.filter.lp

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.lp.LPCardUpdateProto.{LPCardUpdateRequest, LPCardUpdateResponse}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
 * Created by songpeng on 15/11/18.
 */
class LPCardUpdateFilter(facade: V62Facade, config: HallV62Config) extends ProtobufRequestFilter{
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(LPCardUpdateRequest.cmd)){
      val request = protobufRequest.getExtension(LPCardUpdateRequest.cmd)
      val lpCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(request.getCard)
      facade.NET_GAFIS_FLIB_Update(config.latentTable.dbId.toShort,
        config.latentTable.tableId.toShort,
        request.getCard.getStrCardID, lpCard)

      responseBuilder.setExtension(LPCardUpdateResponse.cmd, LPCardUpdateResponse.newBuilder().build())
      true
    }else{
      handler.handle(protobufRequest, responseBuilder);
    }
  }
}
