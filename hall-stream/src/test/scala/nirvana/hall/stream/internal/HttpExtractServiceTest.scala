package nirvana.hall.stream.internal

import com.google.protobuf.{ByteString, ExtensionRegistry}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_def.FINGERMNTSTRUCT_NEWTT
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.stream.config.{NirvanaHallStreamConfig, HallStreamConfig}
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
    val config = new NirvanaHallStreamConfig
    config.stream.isNewFeature= true
    val service = new HttpExtractService("http://10.1.7.144:9002/extractor", httpClient,config)
    val is = getClass.getResourceAsStream("/wsq.data.uncompressed")
    val img = ByteString.readFrom(is).toByteArray
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nImgSize = img.length
    gafisImg.stHead.nWidth = 640
    gafisImg.stHead.nBits= 8
    gafisImg.stHead.nResolution = 500
    gafisImg.stHead.nHeight= 640
    gafisImg.stHead.bIsCompressed = 0
    gafisImg.stHead.nCompressMethod = 0
    gafisImg.bnData = img

    val mnt = service.extract(gafisImg, FingerPosition.FINGER_L_THUMB, FeatureType.FingerLatent)
    val featureStruct = new FINGERMNTSTRUCT_NEWTT
    featureStruct.fromByteArray(mnt.get.toByteArray)
    //debug("minuita number is {} for {}",featureStruct.cm,personId)
    Assert.assertEquals(704, mnt.get.size())
  }
}
