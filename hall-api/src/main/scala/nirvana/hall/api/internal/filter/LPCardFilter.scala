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
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      lPCardService.addLPCard(request.getCard, dbId)
      val response = LPCardAddResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPCardAddResponse.cmd, response)
      true
    }//删除现场
    else if(commandRequest.hasExtension(LPCardDelRequest.cmd)) {
      val request = commandRequest.getExtension(LPCardDelRequest.cmd)
      val cardId = request.getCardId
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      if(lPCardService.isExist(cardId, dbId)){
        lPCardService.delLPCard(cardId, dbId)
      }
      val response = LPCardDelResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPCardDelResponse.cmd, response)
      true
    }//更新现场
    else if(commandRequest.hasExtension(LPCardUpdateRequest.cmd)) {
      val request = commandRequest.getExtension(LPCardUpdateRequest.cmd)
      val cardId = request.getCard.getStrCardID
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      if(lPCardService.isExist(cardId, dbId)){
        lPCardService.updateLPCard(request.getCard, dbId)
      }
      val response = LPCardUpdateResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, LPCardUpdateResponse.cmd, response)
      true
    }//查询现场
    else if(commandRequest.hasExtension(LPCardGetRequest.cmd)){
      val request = commandRequest.getExtension(LPCardGetRequest.cmd)
      val response = LPCardGetResponse.newBuilder()
      val cardId = request.getCardId
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      if(lPCardService.isExist(cardId, dbId)){
        val lpCard = lPCardService.getLPCard(cardId, dbId)
        response.setCard(lpCard)
      }
      commandResponse.writeMessage(commandRequest, LPCardGetResponse.cmd, response.build())
      true
    }//是否已存在
    else if(commandRequest.hasExtension(LPCardIsExistRequest.cmd)){
      val request = commandRequest.getExtension(LPCardIsExistRequest.cmd)
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
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
