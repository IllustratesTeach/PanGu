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
      val response = tpCardService.addTPCard(request)
      commandResponse.writeMessage(commandRequest, TPCardAddResponse.cmd, response)
      true
    }//删除
    else if(commandRequest.hasExtension(TPCardDelRequest.cmd)){
      val request = commandRequest.getExtension(TPCardDelRequest.cmd)
      val response = tpCardService.delTPCard(request)
      commandResponse.writeMessage(commandRequest, TPCardDelResponse.cmd, response)
      true
    }//修改
    else if(commandRequest.hasExtension(TPCardUpdateRequest.cmd)){
      val request = commandRequest.getExtension(TPCardUpdateRequest.cmd)
      val response = tpCardService.updateTPCard(request)
      commandResponse.writeMessage(commandRequest, TPCardUpdateResponse.cmd, response)
      true
    }//查询
    else if(commandRequest.hasExtension(TPCardGetRequest.cmd)){
      val request = commandRequest.getExtension(TPCardGetRequest.cmd)
      val response = tpCardService.getTPCard(request)
      commandResponse.writeMessage(commandRequest, TPCardGetResponse.cmd, response)
      true
    }else{
      handler.handle(commandRequest, commandResponse)
    }
  }
}
