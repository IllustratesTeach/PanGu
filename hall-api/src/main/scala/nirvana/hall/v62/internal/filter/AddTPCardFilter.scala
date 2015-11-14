package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.AddTPCardProto.{AddTPCardRequest, AddTPCardResponse}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoctpConverter

/**
 * Add TPCard Filter
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class AddTPCardFilter(facade:V62Facade,config:HallV62Config) extends ProtobufRequestFilter{
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(AddTPCardRequest.cmd)){
      val request = protobufRequest.getExtension(AddTPCardRequest.cmd)

      val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(request.getCard)
      facade.NET_GAFIS_FLIB_Add(config.templateTable.dbId.toShort,
        config.templateTable.tableId.toShort,
        request.getCard.getStrCardID,tpCard)
      responseBuilder.setExtension(AddTPCardResponse.cmd,AddTPCardResponse.newBuilder().build())
      true
    }else{
      handler.handle(protobufRequest,responseBuilder)
    }
  }
}
