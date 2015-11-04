package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.{ProtobufRequestHandler, ProtobufRequestFilter}
import nirvana.hall.protocol.sys.CommonProto.{BaseResponse, BaseRequest}
import nirvana.hall.protocol.v62.AddTPCardProto.{AddTPCardResponse, AddTPCardRequest}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.services.{DatabaseTable, V62ServerAddress}

/**
 * Add TPCard Filter
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class AddTPCardFilter(facade:V62Facade,config:HallV62Config) extends ProtobufRequestFilter{
  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(AddTPCardRequest.cmd)){
      val request = protobufRequest.getExtension(AddTPCardRequest.cmd)
      val address = V62ServerAddress(config.host,
        config.port,config.user,Option(config.password))
      val database = DatabaseTable(config.templateTable.dbId,config.templateTable.tableId)

      facade.addTemplateData(address,database,request.getCard)
      responseBuilder.setExtension(AddTPCardResponse.cmd,AddTPCardResponse.newBuilder().build())

      true
    }else{
      handler.handle(protobufRequest,responseBuilder)
    }
  }
}
