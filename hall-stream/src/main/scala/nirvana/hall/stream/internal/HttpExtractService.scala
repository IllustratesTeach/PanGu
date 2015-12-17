package nirvana.hall.stream.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.{ExtractRequest, ExtractResponse, FingerPosition}
import nirvana.hall.stream.services.{ExtractService, RpcHttpClient}
import nirvana.hall.c.services.AncientData._

/**
 * Created by songpeng on 15/12/15.
 */
class HttpExtractService(url: String, rpcHttpClient: RpcHttpClient) extends ExtractService{
  /**
   * extract service
   * @param img image data
   * @param fingerPosition finger position
   * @param featureType feature type
   */
  override def extract(img: GAFISIMAGESTRUCT, fingerPosition: FingerPosition, featureType: ExtractRequest.FeatureType): Option[ByteString] = {
    val request = ExtractRequest.newBuilder()
    val imgData = ByteString.newOutput()
    img.writeToStreamWriter(imgData)
    request.setImgData(imgData.toByteString)

    request.setMntType(featureType)
    request.setPosition(fingerPosition)
    val baseResponse = rpcHttpClient.call(url, ExtractRequest.cmd, request.build())
    baseResponse.getStatus  match{
      case CommandStatus.OK =>
        if(baseResponse.hasExtension(ExtractResponse.cmd)) {
          val response = baseResponse.getExtension(ExtractResponse.cmd)
          Some(response.getMntData)
        }else{
          throw new IllegalAccessException("response haven't ExtractResponse")
        }
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to extractor,server message:%s".format(baseResponse.getMsg))
    }
  }
}
