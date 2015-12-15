package nirvana.hall.stream

import com.google.protobuf.ByteString
import nirvana.hall.protocol.image.FirmImageDecompressProto.FirmImageDecompressRequest
import nirvana.hall.stream.internal.HttpDecompressService
import org.junit.Test

/**
 * Created by songpeng on 15/12/15.
 */
class HttpDecompressServiceTest {

  @Test
  def test_decompress: Unit ={
    val service = new HttpDecompressService("http://127.0.0.1:9001/image")
    val request = FirmImageDecompressRequest.newBuilder()
    val is = getClass.getResourceAsStream("/wsq.data")
    request.setCode("1400")
    request.setCprData(ByteString.readFrom(is))
    request.setDpi(500)
    request.setHeight(400)
    request.setWidth(400)
    service.decompress(request.build())
  }
}
