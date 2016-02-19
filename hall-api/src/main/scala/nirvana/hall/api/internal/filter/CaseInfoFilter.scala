package nirvana.hall.api.internal.filter

import monad.rpc.protocol.CommandProto.BaseCommand
import nirvana.hall.api.services.{CaseInfoService, ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.api.CaseProto._

/**
  * Created by songpeng on 15/11/15.
  */
class CaseInfoFilter(caseInfoService: CaseInfoService) extends ProtobufRequestFilter{
   override def handle(protobufRequest: BaseCommand, responseBuilder: BaseCommand.Builder, handler: ProtobufRequestHandler): Boolean = {
     if(protobufRequest.hasExtension(CaseAddRequest.cmd)){//增加案件
       val request = protobufRequest.getExtension(CaseAddRequest.cmd)
       val response = caseInfoService.addCaseInfo(request)
       responseBuilder.setExtension(CaseAddResponse.cmd, response)
       true
     }//删除案件
     else if(protobufRequest.hasExtension(CaseDelRequest.cmd)) {
       val request = protobufRequest.getExtension(CaseDelRequest.cmd)
       val response = caseInfoService.delCaseInfo(request)
       responseBuilder.setExtension(CaseDelResponse.cmd, response)
       true
     }//修改案件信息
     else if(protobufRequest.hasExtension(CaseUpdateRequest.cmd)) {
       val request = protobufRequest.getExtension(CaseUpdateRequest.cmd)
       val response = caseInfoService.updateCaseInfo(request)

       responseBuilder.setExtension(CaseUpdateResponse.cmd, response)
       true
     }//查询案件
     else if(protobufRequest.hasExtension(CaseGetRequest.cmd)){
       val request = protobufRequest.getExtension(CaseGetRequest.cmd)
       val response = caseInfoService.getCaseInfo(request)

       responseBuilder.setExtension(CaseGetResponse.cmd, response)
       true
     }else{
       handler.handle(protobufRequest, responseBuilder);
     }
   }
 }
