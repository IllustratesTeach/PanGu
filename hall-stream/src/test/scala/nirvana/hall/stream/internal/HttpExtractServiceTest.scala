package nirvana.hall.stream.internal

import com.google.protobuf.{ByteString, ExtensionRegistry}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.support.internal.RpcHttpClientImpl
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/12/15.
 */
class HttpExtractServiceTest{

  @Test
  def test_extract(): Unit = {
    val registry = ExtensionRegistry.newInstance()
    ExtractProto.registerAllExtensions(registry)
    val httpClient = new RpcHttpClientImpl(registry)
    val service = new HttpExtractService("http://127.0.0.1:9002/extractor", httpClient)
    val is = getClass.getResourceAsStream("/wsq.data.uncompressed")
    val img = ByteString.readFrom(is).toByteArray
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nImgSize = img.length
    gafisImg.stHead.bIsCompressed = 1
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
    gafisImg.bnData = img

    val mnt = service.extract(gafisImg, FingerPosition.FINGER_L_THUMB, FeatureType.FingerLatent)
    Assert.assertEquals(704, mnt.get.size())
  }
}
