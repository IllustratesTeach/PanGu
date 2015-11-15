package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.GetCaseProto.{GetCaseRequest, GetCaseResponse}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
  * Created by songpeng on 15/11/15.
  */
class GetCaseFilter(facade: V62Facade, config: HallV62Config) extends ProtobufRequestFilter{
   override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
     if(protobufRequest.hasExtension(GetCaseRequest.cmd)){
       val request = protobufRequest.getExtension(GetCaseRequest.cmd)
       val caseId = request.getCaseId
       val gCase = facade.NET_GAFIS_CASE_Get(config.caseTable.dbId.toShort,
        config.caseTable.tableId.toShort, caseId)
       val caseInfo = galoclpConverter.convertGCASEINFOSTRUCT2Protobuf(gCase)

       responseBuilder.setExtension(GetCaseResponse.cmd, GetCaseResponse.newBuilder().setCase(caseInfo).build())
       true
     }else{
       handler.handle(protobufRequest, responseBuilder);
     }
   }
 }
