package nirvana.hall.api.internal.filter

import javax.servlet.http.HttpServletRequest

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageHandler, RpcServerMessageFilter}
import nirvana.hall.api.services.TPCardService
import nirvana.hall.protocol.api.TPCardProto._

/**
 * Add TPCard Filter
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class TPCardFilter(httpServletRequest: HttpServletRequest, tpCardService: TPCardService) extends RpcServerMessageFilter{

  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(TPCardAddRequest.cmd)){
      val request = commandRequest.getExtension(TPCardAddRequest.cmd)
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      tpCardService.addTPCard(request.getCard, dbId)
      val response = TPCardAddResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, TPCardAddResponse.cmd, response)
      true
    }//删除
    else if(commandRequest.hasExtension(TPCardDelRequest.cmd)){
      val request = commandRequest.getExtension(TPCardDelRequest.cmd)
      val cardId = request.getCardId
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      if(tpCardService.isExist(cardId, dbId)){
        tpCardService.delTPCard(cardId, dbId)
      }
      val response = TPCardDelResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, TPCardDelResponse.cmd, response)
      true
    }//修改
    else if(commandRequest.hasExtension(TPCardUpdateRequest.cmd)){
      val request = commandRequest.getExtension(TPCardUpdateRequest.cmd)
      val cardId = request.getCard.getStrCardID
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      if(tpCardService.isExist(cardId, dbId)){
        tpCardService.updateTPCard(request.getCard, dbId)
      }
      val response = TPCardUpdateResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, TPCardUpdateResponse.cmd, response)
      true
    }//查询
    else if(commandRequest.hasExtension(TPCardGetRequest.cmd)) {
      val request = commandRequest.getExtension(TPCardGetRequest.cmd)
      val response = TPCardGetResponse.newBuilder()
      val cardId = request.getCardId
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      if(tpCardService.isExist(cardId, dbId)){
        val tpCard = tpCardService.getTPCard(cardId, dbId)
        response.setCard(tpCard)
      }
      commandResponse.writeMessage(commandRequest, TPCardGetResponse.cmd, response.build())
      true
    }//是否已存在
    else if(commandRequest.hasExtension(TPCardIsExistRequest.cmd)){
      val request = commandRequest.getExtension(TPCardIsExistRequest.cmd)
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      val response = TPCardIsExistResponse.newBuilder()
      response.setIsExist(tpCardService.isExist(request.getCardId, dbId))
      commandResponse.writeMessage(commandRequest, TPCardIsExistResponse.cmd, response.build())
      true
    }else{
      handler.handle(commandRequest, commandResponse)
    }
  }
}
