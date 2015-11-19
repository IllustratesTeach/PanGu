package nirvana.hall.v62.internal.filter.lp

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.lp.CaseProto._
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
  * Created by songpeng on 15/11/15.
  */
class CaseFilter(facade: V62Facade, config: HallV62Config) extends ProtobufRequestFilter{
   override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
     if(protobufRequest.hasExtension(CaseAddRequest.cmd)){//增加案件
       val request = protobufRequest.getExtension(CaseAddRequest.cmd)
       val caseInfo = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(request.getCase);
       facade.NET_GAFIS_CASE_Add(config.caseTable.dbId.toShort,
         config.caseTable.tableId.toShort, caseInfo)

       responseBuilder.setExtension(CaseAddResponse.cmd, CaseAddResponse.newBuilder().build())
       true
     }//删除案件
     else if(protobufRequest.hasExtension(CaseDelRequest.cmd)) {
       val request = protobufRequest.getExtension(CaseDelRequest.cmd)
       val caseId = request.getCaseId
       facade.NET_GAFIS_CASE_Del(config.caseTable.dbId.toShort,
         config.caseTable.tableId.toShort, caseId)

       responseBuilder.setExtension(CaseDelResponse.cmd, CaseDelResponse.newBuilder().build())
       true
     }//修改案件信息
     else if(protobufRequest.hasExtension(CaseUpdateRequest.cmd)) {
       val request = protobufRequest.getExtension(CaseUpdateRequest.cmd)
       val caseInfo = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(request.getCase);
       facade.NET_GAFIS_CASE_Update(config.caseTable.dbId.toShort, config.caseTable.tableId.toShort, caseInfo)

       responseBuilder.setExtension(CaseUpdateResponse.cmd, CaseUpdateResponse.newBuilder().build())
       true
     }//查询案件
     else if(protobufRequest.hasExtension(CaseGetRequest.cmd)){
       val request = protobufRequest.getExtension(CaseGetRequest.cmd)
       val caseId = request.getCaseId
       val gCase = facade.NET_GAFIS_CASE_Get(config.caseTable.dbId.toShort,
         config.caseTable.tableId.toShort, caseId)
       val caseInfo = galoclpConverter.convertGCASEINFOSTRUCT2Protobuf(gCase)

       responseBuilder.setExtension(CaseGetResponse.cmd, CaseGetResponse.newBuilder().setCase(caseInfo).build())
       true
     }else{
       handler.handle(protobufRequest, responseBuilder);
     }
   }
 }
