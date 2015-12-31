package nirvana.hall.stream.internal

import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.AncientData._
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.image.FirmImageDecompressProto.{FirmImageDecompressRequest, FirmImageDecompressResponse}
import nirvana.hall.stream.services.DecompressService
import nirvana.hall.support.services.RpcHttpClient

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
  override def decompress(request: FirmImageDecompressRequest): Option[GAFISIMAGESTRUCT] = {
    val baseResponse = rpcHttpClient.call(url,FirmImageDecompressRequest.cmd,request)
    baseResponse.getStatus  match{
      case CommandStatus.OK =>
        if(baseResponse.hasExtension(FirmImageDecompressResponse.cmd)) {
          val response = baseResponse.getExtension(FirmImageDecompressResponse.cmd)
          val imgData = response.getOriginalData

          val gafisImg = new GAFISIMAGESTRUCT
          val is = imgData.newInput()
          gafisImg.fromStreamReader(is)

          Some(gafisImg)
        }else{
          throw new IllegalAccessException("response haven't FirmImageDecompressResponse")
        }
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to decompress,server message:%s".format(baseResponse.getMsg))
    }
  }
}
