package nirvana.hall.v62.internal.filter.tp

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.tp.TPCardProto._
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.v62.internal.c.gloclib.galoctpConverter

/**
 * Add TPCard Filter
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class TPCardFilter(facade:V62Facade,config:HallV62Config) extends ProtobufRequestFilter{
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(TPCardAddRequest.cmd)){
      val request = protobufRequest.getExtension(TPCardAddRequest.cmd)
      val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(request.getCard)
      facade.NET_GAFIS_FLIB_Add(config.templateTable.dbId.toShort,
        config.templateTable.tableId.toShort,
        request.getCard.getStrCardID,tpCard)
      responseBuilder.setExtension(TPCardAddResponse.cmd,TPCardAddResponse.newBuilder().build())
      true
    }//删除
    else if(protobufRequest.hasExtension(TPCardDelRequest.cmd)){
      val request = protobufRequest.getExtension(TPCardDelRequest.cmd)
      facade.NET_GAFIS_FLIB_Del(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort,
        request.getCardId)
      responseBuilder.setExtension(TPCardDelResponse.cmd,TPCardDelResponse.newBuilder().build())
      true
    }//修改
    else if(protobufRequest.hasExtension(TPCardUpdateRequest.cmd)){
      val request = protobufRequest.getExtension(TPCardUpdateRequest.cmd)
      val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(request.getCard)
      facade.NET_GAFIS_FLIB_Update(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort,
        request.getCard.getStrCardID, tpCard)
      responseBuilder.setExtension(TPCardUpdateResponse.cmd,TPCardUpdateResponse.newBuilder().build())
      true
    }//查询
    else if(protobufRequest.hasExtension(TPCardGetRequest.cmd)){
      val request = protobufRequest.getExtension(TPCardGetRequest.cmd)
      val tp = new GTPCARDINFOSTRUCT
      facade.NET_GAFIS_FLIB_Get(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort,
        request.getCardId, tp)
      val tpCard = galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(tp)
      responseBuilder.setExtension(TPCardGetResponse.cmd, TPCardGetResponse.newBuilder().setCard(tpCard).build())
      true
    }else{
      handler.handle(protobufRequest,responseBuilder)
    }
  }
}
