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
       caseInfoService.addCaseInfo(request.getCase)
       val response = CaseAddResponse.newBuilder().build()
       commandResponse.writeMessage(commandRequest, CaseAddResponse.cmd, response)
       true
     }//删除案件
     else if(commandRequest.hasExtension(CaseDelRequest.cmd)) {
       val request = commandRequest.getExtension(CaseDelRequest.cmd)
       caseInfoService.delCaseInfo(request.getCaseId)
       val response = CaseDelResponse.newBuilder().build()
       commandResponse.writeMessage(commandRequest, CaseDelResponse.cmd, response)
       true
     }//修改案件信息
     else if(commandRequest.hasExtension(CaseUpdateRequest.cmd)) {
       val request = commandRequest.getExtension(CaseUpdateRequest.cmd)
       caseInfoService.updateCaseInfo(request.getCase)
       val response = CaseUpdateResponse.newBuilder().build()
       commandResponse.writeMessage(commandRequest, CaseUpdateResponse.cmd, response)
       true
     }//查询案件
     else if(commandRequest.hasExtension(CaseGetRequest.cmd)){
       val request = commandRequest.getExtension(CaseGetRequest.cmd)
       caseInfoService.getCaseInfo(request.getCaseId)
       val response = CaseGetResponse.newBuilder().build()
       commandResponse.writeMessage(commandRequest, CaseGetResponse.cmd, response)
       true
     }//是否已存在
     else if(commandRequest.hasExtension(CaseIsExistRequest.cmd)){
       val request = commandRequest.getExtension(CaseIsExistRequest.cmd)
       val cardId = request.getCardId
       val response = CaseIsExistResponse.newBuilder()
       response.setIsExist(caseInfoService.isExist(cardId))
       commandResponse.writeMessage(commandRequest, CaseIsExistResponse.cmd, response.build())
       true
     }else{
       handler.handle(commandRequest, commandResponse);
     }
   }
 }
