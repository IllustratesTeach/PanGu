package nirvana.hall.api.internal

import com.google.protobuf.{ByteString, ExtensionRegistry}
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.{ExtractRequest, ExtractResponse, FingerPosition}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.support.internal.RpcHttpClientImpl
import org.apache.commons.io.IOUtils
import org.junit.{Assert, Test}

/**
  * Created by Administrator on 2017/9/26.
  */
class bmpExtractor {

  @Test
  def test_bmpExtractor(): Unit = {

    val registry = ExtensionRegistry.newInstance()
    ExtractProto.registerAllExtensions(registry)
    val httpClients = new RpcHttpClientImpl(registry)
    val img = IOUtils.toByteArray(getClass.getResourceAsStream("/R2100000000002017090218_12.bmp"))
    val extractorServer = "http://127.0.0.1:9001/extractor"
    val request = ExtractRequest.newBuilder()
    request.setImgData(ByteString.copyFrom(img))
    request.setFeatureTry(ExtractProto.NewFeatureTry.V1)
    request.setMntType(FeatureType.FingerTemplate)
    request.setPosition(FingerPosition.FINGER_R_INDEX)
    val baseResponse = httpClients.call(extractorServer, ExtractRequest.cmd, request.build())
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
