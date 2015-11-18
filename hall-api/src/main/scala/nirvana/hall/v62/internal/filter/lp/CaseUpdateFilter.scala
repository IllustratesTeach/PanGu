package nirvana.hall.v62.internal.filter.lp

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.lp.CaseUpdateProto.{CaseUpdateRequest, CaseUpdateResponse}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
  * Created by songpeng on 15/11/18.
  */
class CaseUpdateFilter(facade: V62Facade, config: HallV62Config) extends ProtobufRequestFilter{
   override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
     if(protobufRequest.hasExtension(CaseUpdateRequest.cmd)){
       val request = protobufRequest.getExtension(CaseUpdateRequest.cmd)
       val caseInfo = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(request.getCase);
       facade.NET_GAFIS_CASE_Update(config.caseTable.dbId.toShort, config.caseTable.tableId.toShort, caseInfo)

       responseBuilder.setExtension(CaseUpdateResponse.cmd, CaseUpdateResponse.newBuilder().build())
       true
     }else{
       handler.handle(protobufRequest, responseBuilder);
     }
   }
 }
