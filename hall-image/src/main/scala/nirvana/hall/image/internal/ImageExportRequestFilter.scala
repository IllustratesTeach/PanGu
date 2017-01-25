package nirvana.hall.image.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.image.ImageExportProto.{ImageConvertToBMPRequest, ImageConvertToBMPResponse}

/**
  * Created by yuchen on 2017/1/24.
  */
class ImageExportRequestFilter extends RpcServerMessageFilter{

  /**
    * 将blob转换成bmp数据的Array[byte]
    * @param commandRequest
    * @param response
    * @param handler
    * @return
    */
  override def handle(commandRequest: BaseCommand, response: CommandResponse, handler: RpcServerMessageHandler): Boolean = {

    if(commandRequest.hasExtension(ImageConvertToBMPRequest.cmd)){

      val request = commandRequest.getExtension(ImageConvertToBMPRequest.cmd)
      val is = request.getImageData.newInput()
      val gafisImg = new GAFISIMAGESTRUCT
      gafisImg.fromStreamReader(is)
      if(gafisImg.stHead.nImgSize == 0)
        throw new IllegalArgumentException("image data length is zero.")
      val bmpData = GafisImageConverter.convertGafisImage2BMP(gafisImg)

      val imageExportResponseBuilder = ImageConvertToBMPResponse.newBuilder()
      imageExportResponseBuilder.setBmpData(ByteString.copyFrom(bmpData))
      response.writeMessage(commandRequest,ImageConvertToBMPResponse.cmd,imageExportResponseBuilder.build())
    }else{
      handler.handle(commandRequest,response)
    }
    true
  }

}
