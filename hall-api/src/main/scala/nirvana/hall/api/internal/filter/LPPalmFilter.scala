package nirvana.hall.api.internal.filter

import javax.servlet.http.HttpServletRequest

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.services.LPPalmService
import nirvana.hall.protocol.api.LPPalmProto._

/**
 * 现场掌纹Filter
 */
class LPPalmFilter(httpServletRequest: HttpServletRequest, lPPalmService: LPPalmService) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(LPPalmAddRequest.cmd)) {
      val request = commandRequest.getExtension(LPPalmAddRequest.cmd)
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      lPPalmService.addLPCard(request.getCard, dbId)
      val response = LPPalmAddResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPPalmAddResponse.cmd, response)
      true
    }//删除现场
    else if(commandRequest.hasExtension(LPPalmDelRequest.cmd)) {
      val request = commandRequest.getExtension(LPPalmDelRequest.cmd)
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      lPPalmService.delLPCard(request.getCardId, dbId)
      val response = LPPalmDelResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPPalmDelResponse.cmd, response)
      true
    }//更新现场
    else if(commandRequest.hasExtension(LPPalmUpdateRequest.cmd)) {
      val request = commandRequest.getExtension(LPPalmUpdateRequest.cmd)
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      lPPalmService.updateLPCard(request.getCard, dbId)
      val response = LPPalmUpdateResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPPalmUpdateResponse.cmd, response)
      true
    }//查询现场
    else if(commandRequest.hasExtension(LPPalmGetRequest.cmd)){
      val request = commandRequest.getExtension(LPPalmGetRequest.cmd)
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      val LPPalm = lPPalmService.getLPCard(request.getCardId, dbId)
      val response = LPPalmGetResponse.newBuilder().setCard(LPPalm).build()
      commandResponse.writeMessage(commandRequest, LPPalmGetResponse.cmd, response)
      true
    }//是否已存在
    else if(commandRequest.hasExtension(LPPalmIsExistRequest.cmd)){
      val request = commandRequest.getExtension(LPPalmIsExistRequest.cmd)
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      val cardId = request.getCardId
      val response = LPPalmIsExistResponse.newBuilder()
      response.setIsExist(lPPalmService.isExist(cardId, dbId))
      commandResponse.writeMessage(commandRequest, LPPalmIsExistResponse.cmd, response.build())
      true
    }else{
      handler.handle(commandRequest, commandResponse);
    }
  }
}
