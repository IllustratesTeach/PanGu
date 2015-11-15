package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.DelCaseProto.{DelCaseRequest, DelCaseResponse}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

/**
  * Created by songpeng on 15/11/15.
  */
class DelCaseFilter(facade: V62Facade, config: HallV62Config) extends ProtobufRequestFilter{
   override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
     if(protobufRequest.hasExtension(DelCaseRequest.cmd)){
       val request = protobufRequest.getExtension(DelCaseRequest.cmd)
       val caseId = request.getCaseId
       facade.NET_GAFIS_CASE_Del(config.caseTable.dbId.toShort,
        config.caseTable.tableId.toShort, caseId)

       responseBuilder.setExtension(DelCaseResponse.cmd, DelCaseResponse.newBuilder().build())
       true
     }else{
       handler.handle(protobufRequest, responseBuilder);
     }
   }
 }
