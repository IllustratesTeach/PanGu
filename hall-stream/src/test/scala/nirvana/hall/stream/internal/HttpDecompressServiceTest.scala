package nirvana.hall.stream.internal

import com.google.protobuf.{ByteString, ExtensionRegistry}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.image.FirmImageDecompressProto
import nirvana.hall.protocol.image.FirmImageDecompressProto.FirmImageDecompressRequest
import nirvana.hall.support.internal.RpcHttpClientImpl
import org.apache.commons.io.IOUtils
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/12/15.
 */
class HttpDecompressServiceTest {

  @Test
  def test_decompress: Unit ={
    //LocalRpcModule.buildProtobufRegistroy(config)
    val registry = ExtensionRegistry.newInstance()
    FirmImageDecompressProto.registerAllExtensions(registry)
    val httpClient = new RpcHttpClientImpl(registry)

    val service = new HttpDecompressService("http://127.0.0.1:9001/image",httpClient)
    val request = FirmImageDecompressRequest.newBuilder()
    val is = getClass.getResourceAsStream("/wsq.data")
    val bnData = IOUtils.toByteArray(is)

    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 1
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
    gafisImg.bnData = bnData
    gafisImg.stHead.nImgSize = bnData.length

    request.setCprData(ByteString.readFrom(is))
    val result = service.decompress(request.build())
    Assert.assertTrue(result.isDefined)
    Assert.assertEquals(409600,result.get.bnData.length)
  }
}
