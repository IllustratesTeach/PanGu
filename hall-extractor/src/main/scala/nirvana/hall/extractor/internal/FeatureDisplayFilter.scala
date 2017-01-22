package nirvana.hall.extractor.internal

import java.io.File

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.protocol.extract.FeatureDisplayProto.{FeatureDisplayRequest, FeatureDisplayResponse}

/**
  * Created by wangjue on 2017/1/22.
  */
class FeatureDisplayFilter extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, response: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if(commandRequest.hasExtension(FeatureDisplayRequest.cmd)){
      val featureDisplayRequest = commandRequest.getExtension(FeatureDisplayRequest.cmd)
      val displayImage = FeatureDisplay.display(featureDisplayRequest.getDecompressImageData.newInput(),featureDisplayRequest.getMntDispData.newInput())
      val featureDisplayResponseBuilder = FeatureDisplayResponse.newBuilder()
      featureDisplayResponseBuilder.setDisplayData(ByteString.copyFrom(displayImage))
      response.writeMessage(commandRequest,FeatureDisplayResponse.cmd,featureDisplayResponseBuilder.build())
      true
    }else{
      handler.handle(commandRequest,response)
    }
  }
}
