package nirvana.hall.api.internal.filter

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.api.CaseProto._

/**
  * Created by songpeng on 15/11/15.
  */
class CaseInfoFilter(caseInfoService: CaseInfoService) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
     if(commandRequest.hasExtension(CaseAddRequest.cmd)){//增加案件
       val request = commandRequest.getExtension(CaseAddRequest.cmd)
       val response = caseInfoService.addCaseInfo(request)
       commandResponse.writeMessage(commandRequest, CaseAddResponse.cmd, response)
       true
     }//删除案件
     else if(commandRequest.hasExtension(CaseDelRequest.cmd)) {
       val request = commandRequest.getExtension(CaseDelRequest.cmd)
       val response = caseInfoService.delCaseInfo(request)
       commandResponse.writeMessage(commandRequest, CaseDelResponse.cmd, response)
       true
     }//修改案件信息
     else if(commandRequest.hasExtension(CaseUpdateRequest.cmd)) {
       val request = commandRequest.getExtension(CaseUpdateRequest.cmd)
       val response = caseInfoService.updateCaseInfo(request)
       commandResponse.writeMessage(commandRequest, CaseUpdateResponse.cmd, response)
       true
     }//查询案件
     else if(commandRequest.hasExtension(CaseGetRequest.cmd)){
       val request = commandRequest.getExtension(CaseGetRequest.cmd)
       val response = caseInfoService.getCaseInfo(request)
       commandResponse.writeMessage(commandRequest, CaseGetResponse.cmd, response)
       true
     }else{
       handler.handle(commandRequest, commandResponse);
     }
   }
 }
