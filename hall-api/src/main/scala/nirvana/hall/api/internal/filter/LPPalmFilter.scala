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
      lPPalmService.addLPCard(request.getCard, Option(request.getDbid))
      val response = LPPalmAddResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPPalmAddResponse.cmd, response)
      true
    }//删除现场
    else if(commandRequest.hasExtension(LPPalmDelRequest.cmd)) {
      val request = commandRequest.getExtension(LPPalmDelRequest.cmd)
      lPPalmService.delLPCard(request.getCardId, Option(request.getDbid))
      val response = LPPalmDelResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPPalmDelResponse.cmd, response)
      true
    }//更新现场
    else if(commandRequest.hasExtension(LPPalmUpdateRequest.cmd)) {
      val request = commandRequest.getExtension(LPPalmUpdateRequest.cmd)
      lPPalmService.updateLPCard(request.getCard, Option(request.getDbid))
      val response = LPPalmUpdateResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPPalmUpdateResponse.cmd, response)
      true
    }//查询现场
    else if(commandRequest.hasExtension(LPPalmGetRequest.cmd)){
      val request = commandRequest.getExtension(LPPalmGetRequest.cmd)
      val LPPalm = lPPalmService.getLPCard(request.getCardId, Option(request.getDbid))
      val response = LPPalmGetResponse.newBuilder().setCard(LPPalm).build()
      commandResponse.writeMessage(commandRequest, LPPalmGetResponse.cmd, response)
      true
    }//是否已存在
    else if(commandRequest.hasExtension(LPPalmIsExistRequest.cmd)){
      val request = commandRequest.getExtension(LPPalmIsExistRequest.cmd)
      val cardId = request.getCardId
      val response = LPPalmIsExistResponse.newBuilder()
      response.setIsExist(lPPalmService.isExist(cardId, Option(request.getDbid)))
      commandResponse.writeMessage(commandRequest, LPPalmIsExistResponse.cmd, response.build())
      true
    }else{
      handler.handle(commandRequest, commandResponse);
    }
  }
}
