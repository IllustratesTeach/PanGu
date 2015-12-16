package nirvana.hall.stream.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.image.FirmImageDecompressProto.{FirmImageDecompressRequest, FirmImageDecompressResponse}
import nirvana.hall.stream.services.{DecompressService, RpcHttpClient}

/**
 * http decompress service
 * Created by songpeng on 15/12/15.
 */
class HttpDecompressService(url: String,rpcHttpClient: RpcHttpClient) extends DecompressService{
  /**
   * decompress image
   * @param request request
   * @return original image data
   */
  override def decompress(request: FirmImageDecompressRequest): Option[ByteString] = {
    val baseResponse = rpcHttpClient.call(url,FirmImageDecompressRequest.cmd,request)
    baseResponse.getStatus  match{
      case CommandStatus.OK =>
        if(baseResponse.hasExtension(FirmImageDecompressResponse.cmd)) {
          val response = baseResponse.getExtension(FirmImageDecompressResponse.cmd)
          val imgData = response.getData
          val gafisImg = new GAFISIMAGESTRUCT
          //gafisImg.fromByteArray(img1)
          gafisImg.stHead.nWidth= response.getWidth.toShort
          gafisImg.stHead.nHeight = response.getHeight.toShort
          gafisImg.stHead.nResolution = if(response.getDpi > 0 ) response.getDpi.toShort else 500
          //TODO adjust by feature type
          gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
          gafisImg.bnData = imgData.toByteArray
          gafisImg.stHead.nImgSize = gafisImg.bnData.length
          //TODO optimize stream copy
          Some(ByteString.copyFrom(gafisImg.toByteArray))
        }else{
          throw new IllegalAccessException("response haven't FirmImageDecompressResponse")
        }
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to decompress,server message:%s".format(baseResponse.getMsg))
    }
  }
}
