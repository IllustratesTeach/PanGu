package nirvana.hall.api.internal.filter

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageHandler, RpcServerMessageFilter}
import nirvana.hall.api.services.TPCardService
import nirvana.hall.protocol.api.TPCardProto._

/**
 * Add TPCard Filter
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class TPCardFilter(tpCardService: TPCardService) extends RpcServerMessageFilter{

  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(TPCardAddRequest.cmd)){
      val request = commandRequest.getExtension(TPCardAddRequest.cmd)
      tpCardService.addTPCard(request.getCard)
      val response = TPCardAddResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, TPCardAddResponse.cmd, response)
      true
    }//删除
    else if(commandRequest.hasExtension(TPCardDelRequest.cmd)){
      val request = commandRequest.getExtension(TPCardDelRequest.cmd)
      tpCardService.delTPCard(request.getCardId)
      val response = TPCardDelResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, TPCardDelResponse.cmd, response)
      true
    }//修改
    else if(commandRequest.hasExtension(TPCardUpdateRequest.cmd)){
      val request = commandRequest.getExtension(TPCardUpdateRequest.cmd)
      tpCardService.updateTPCard(request.getCard)
      val response = TPCardUpdateResponse.newBuilder().build()
      commandResponse.writeMessage(commandRequest, TPCardUpdateResponse.cmd, response)
      true
    }//查询
    else if(commandRequest.hasExtension(TPCardGetRequest.cmd)) {
      val request = commandRequest.getExtension(TPCardGetRequest.cmd)
      val tpCard = tpCardService.getTPCard(request.getCardId)
      val response = TPCardGetResponse.newBuilder().setCard(tpCard).build()
      commandResponse.writeMessage(commandRequest, TPCardGetResponse.cmd, response)
      true
    }else{
      handler.handle(commandRequest, commandResponse)
    }
  }
}
