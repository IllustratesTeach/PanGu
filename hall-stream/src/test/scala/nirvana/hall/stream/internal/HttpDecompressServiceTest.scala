package nirvana.hall.stream.internal

import com.google.protobuf.{ByteString, ExtensionRegistry}
import nirvana.hall.protocol.image.FirmImageDecompressProto
import nirvana.hall.protocol.image.FirmImageDecompressProto.FirmImageDecompressRequest
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
    request.setCode("1400")
    request.setCprData(ByteString.readFrom(is))
    request.setDpi(500)
    request.setHeight(400)
    request.setWidth(400)
    val result = service.decompress(request.build())
    Assert.assertTrue(result.isDefined)
    Assert.assertEquals(409600,result.get.size())
  }
}
