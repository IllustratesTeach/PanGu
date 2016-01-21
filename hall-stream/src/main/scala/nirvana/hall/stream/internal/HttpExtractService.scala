package nirvana.hall.stream.internal

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.{NewFeatureTry, ExtractRequest, ExtractResponse, FingerPosition}
import nirvana.hall.stream.config.{NirvanaHallStreamConfig, HallStreamConfig}
import nirvana.hall.stream.services.ExtractService
import nirvana.hall.c.services.AncientData._
import nirvana.hall.support.services.RpcHttpClient

/**
 * Created by songpeng on 15/12/15.
 */
class HttpExtractService(url: String, rpcHttpClient: RpcHttpClient,config:NirvanaHallStreamConfig) extends ExtractService{
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

    if(config.stream.isNewFeature)
      request.setFeatureTry(NewFeatureTry.V2)
    else
      request.setFeatureTry(NewFeatureTry.V1)

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
