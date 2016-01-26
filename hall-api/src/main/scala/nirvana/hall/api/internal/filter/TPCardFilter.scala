package nirvana.hall.api.internal.filter

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler, TPCardService}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.tp.TPCardProto._

/**
 * Add TPCard Filter
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class TPCardFilter(tpCardService: TPCardService) extends ProtobufRequestFilter{

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if(protobufRequest.hasExtension(TPCardAddRequest.cmd)){
      val request = protobufRequest.getExtension(TPCardAddRequest.cmd)
      val response = tpCardService.addTPCard(request)
      responseBuilder.setExtension(TPCardAddResponse.cmd,response)
      true
    }//删除
    else if(protobufRequest.hasExtension(TPCardDelRequest.cmd)){
      val request = protobufRequest.getExtension(TPCardDelRequest.cmd)
      val response = tpCardService.delTPCard(request)
      responseBuilder.setExtension(TPCardDelResponse.cmd, response)
      true
    }//修改
    else if(protobufRequest.hasExtension(TPCardUpdateRequest.cmd)){
      val request = protobufRequest.getExtension(TPCardUpdateRequest.cmd)
      val response = tpCardService.updateTPCard(request)
      responseBuilder.setExtension(TPCardUpdateResponse.cmd, response)
      true
    }//查询
    else if(protobufRequest.hasExtension(TPCardGetRequest.cmd)){
      val request = protobufRequest.getExtension(TPCardGetRequest.cmd)
      val response = tpCardService.getTPCard(request)
      responseBuilder.setExtension(TPCardGetResponse.cmd, response)
      true
    }else{
      handler.handle(protobufRequest,responseBuilder)
    }
  }
}
