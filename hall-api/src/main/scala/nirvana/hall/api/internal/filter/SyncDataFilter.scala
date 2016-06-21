package nirvana.hall.api.internal.filter

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.services.sync.{SyncCaseInfoService, SyncLPCardService, SyncTPCardService}
import nirvana.hall.protocol.api.SyncDataProto._

/**
 * Created by songpeng on 16/6/17.
 */
class SyncDataFilter(syncTPCardService: SyncTPCardService, syncLPCardService: SyncLPCardService, syncCaseInfoService: SyncCaseInfoService) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean ={
    if(commandRequest.hasExtension(SyncTPCardRequest.cmd)) {
      val request = commandRequest.getExtension(SyncTPCardRequest.cmd)
      val responseBuilder = SyncTPCardResponse.newBuilder()
      syncTPCardService.syncTPCard(responseBuilder: SyncTPCardResponse.Builder, request.getTimestamp, request.getSize)
      commandResponse.writeMessage(commandRequest, SyncTPCardResponse.cmd, responseBuilder.build())
      true
    }else if(commandRequest.hasExtension(SyncLPCardRequest.cmd)){
      val request = commandRequest.getExtension(SyncLPCardRequest.cmd)
      val responseBuilder = SyncLPCardResponse.newBuilder()
      syncLPCardService.syncLPCard(responseBuilder, request.getTimestamp, request.getSize)
      commandResponse.writeMessage(commandRequest, SyncLPCardResponse.cmd, responseBuilder.build())
      true
    }else if(commandRequest.hasExtension(SyncCaseRequest.cmd)){
      val request = commandRequest.getExtension(SyncCaseRequest.cmd)
      val responseBuilder = SyncCaseResponse.newBuilder()
      syncCaseInfoService.syncCaseInfo(responseBuilder, request.getTimestamp, request.getSize)
      commandResponse.writeMessage(commandRequest, SyncCaseResponse.cmd, responseBuilder.build())
      true
    }else{
      handler.handle(commandRequest, commandResponse)
    }
  }
}
