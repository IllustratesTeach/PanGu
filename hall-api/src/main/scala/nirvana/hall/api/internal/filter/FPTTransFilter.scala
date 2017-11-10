package nirvana.hall.api.internal.filter


import javax.servlet.http.HttpServletRequest

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.services.FPTTransService
import nirvana.hall.protocol.api.FPTTrans
import nirvana.hall.protocol.api.FPTTrans._
/**
  * Created by yuchen on 2017/11/9.
  * 用于指纹系统7.0导出FPT
  */
class FPTTransFilter (httpServletRequest: HttpServletRequest, fPTTransService: FPTTransService) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(FPTImportRequest.cmd)){
      val request = commandRequest.getExtension(FPTImportRequest.cmd)
      val response = FPTImportResponse.newBuilder
      commandResponse.writeMessage(commandRequest, FPTImportResponse.cmd, response.build)
      true
    }
    else if(commandRequest.hasExtension(FPTExportRequest.cmd)){
      val request = commandRequest.getExtension(FPTExportRequest.cmd)
      val response = FPTTrans.FPTExportResponse.newBuilder()
      response.setDataBytes(ByteString.copyFrom(fPTTransService.fPTExport(request.getCardid,request.getType).getBytes))
      response.setStatus(ResponseStatus.OK)
      commandResponse.writeMessage(commandRequest, FPTExportResponse.cmd, response.build())
      true
    }else{
      handler.handle(commandRequest, commandResponse)
    }
  }
}
