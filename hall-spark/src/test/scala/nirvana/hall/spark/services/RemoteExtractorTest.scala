package nirvana.hall.spark.services

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.{ExtractRequest, ExtractResponse, FingerPosition}
import org.apache.commons.io.IOUtils
import org.junit.{Assert, Test}

/**
  * Created by wangjue on 2017/7/26.
  */
class RemoteExtractorTest extends BaseJniTest{

  @Test
  def test_bmpExtractor(): Unit = {
    val img = IOUtils.toByteArray(getClass.getResourceAsStream("/R2100000000002017090799_19.img"))
    //val extractorServer = "http://192.168.56.128:9001/extractor"
    val extractorServer = "http://10.1.7.144:9001/extractor"
    val rpcHttpClient = SparkFunctions.httpClient
    val request = ExtractRequest.newBuilder()
    request.setImgData(ByteString.copyFrom(img))
    request.setFeatureTry(ExtractProto.NewFeatureTry.V2)
    request.setMntType(FeatureType.FingerTemplate)
    request.setPosition(FingerPosition.FINGER_R_THUMB)
    val baseResponse = rpcHttpClient.call(extractorServer, ExtractRequest.cmd, request.build())
    baseResponse.getStatus match {
      case CommandStatus.OK =>
        if (baseResponse.hasExtension(ExtractResponse.cmd)) {
          val response = baseResponse.getExtension(ExtractResponse.cmd)
          val mntData = response.getMntData
          val binData = response.getBinData

          val gafisMnt = new GAFISIMAGESTRUCT
          val is = mntData.newInput()
          gafisMnt.fromStreamReader(is)
          val gafisBin = new GAFISIMAGESTRUCT
          val bin = binData.newInput()
          gafisBin.fromStreamReader(bin)
          Assert.assertEquals(640,gafisMnt.bnData.length)
        } else {
          throw new IllegalAccessException("response haven't ExtractResponse")
        }
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to extractor,server message:%s".format(baseResponse.getMsg))
    }
  }

}
