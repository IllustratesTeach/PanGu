package nirvana.hall.api.internal.filter

import javax.servlet.http.HttpServletRequest

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.LPCardProto._

/**
 * Created by songpeng on 15/11/15.
 */
class LPCardFilter(httpServletRequest: HttpServletRequest, lPCardService: LPCardService) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(LPCardAddRequest.cmd)) {
      val request = commandRequest.getExtension(LPCardAddRequest.cmd)
      val dbId = getDBID(httpServletRequest)
      lPCardService.addLPCard(request.getCard, dbId)
      val response = LPCardAddResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPCardAddResponse.cmd, response)
      true
    }//删除现场
    else if(commandRequest.hasExtension(LPCardDelRequest.cmd)) {
      val request = commandRequest.getExtension(LPCardDelRequest.cmd)
      val dbId = getDBID(httpServletRequest)
      lPCardService.delLPCard(request.getCardId, dbId)
      val response = LPCardDelResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPCardDelResponse.cmd, response)
      true
    }//更新现场
    else if(commandRequest.hasExtension(LPCardUpdateRequest.cmd)) {
      val request = commandRequest.getExtension(LPCardUpdateRequest.cmd)
      val dbId = getDBID(httpServletRequest)
      lPCardService.updateLPCard(request.getCard, dbId)
      val response = LPCardUpdateResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPCardUpdateResponse.cmd, response)
      true
    }//查询现场
    else if(commandRequest.hasExtension(LPCardGetRequest.cmd)){
      val request = commandRequest.getExtension(LPCardGetRequest.cmd)
      val dbId = getDBID(httpServletRequest)
      val lpCard = lPCardService.getLPCard(request.getCardId, dbId)
      val response = LPCardGetResponse.newBuilder().setCard(lpCard).build()
      commandResponse.writeMessage(commandRequest, LPCardGetResponse.cmd, response)
      true
    }//是否已存在
    else if(commandRequest.hasExtension(LPCardIsExistRequest.cmd)){
      val request = commandRequest.getExtension(LPCardIsExistRequest.cmd)
      val dbId = getDBID(httpServletRequest)
      val cardId = request.getCardId
      val response = LPCardIsExistResponse.newBuilder()
      response.setIsExist(lPCardService.isExist(cardId, dbId))
      commandResponse.writeMessage(commandRequest, LPCardIsExistResponse.cmd, response.build())
      true
    }else{
      handler.handle(commandRequest, commandResponse);
    }
  }
}
