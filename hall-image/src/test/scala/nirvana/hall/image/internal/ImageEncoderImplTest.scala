package nirvana.hall.image.internal

import java.io.File

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.image.config.HallImageConfig
import nirvana.hall.image.jni.BaseJniTest
import nirvana.hall.image.services.RawImageDataType
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 2017/1/13.
  */
class ImageEncoderImplTest extends BaseJniTest{

  @Test
  def test_encodeWSQ: Unit ={
    val decoder = new FirmDecoderImpl("support",new HallImageConfig)
    val data = IOUtils.toByteArray(getClass.getResourceAsStream("/1900-1.data"))
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.fromByteArray(data)
    val originalImg = decoder.decode(gafisImg, RawImageDataType)
    val encoder = new ImageEncoderImpl(decoder)
    val wsq = encoder.encodeWSQ(originalImg)
    Assert.assertNotNull(wsq.bnData)

    val wsq2 = encoder.encodeWSQ(gafisImg)
    Assert.assertNotNull(wsq2.bnData)

  }

  @Test
  def test_bmpEncodeWSQ: Unit ={
    val decoder = new FirmDecoderImpl("support",new HallImageConfig)
    val data = IOUtils.toByteArray(getClass.getResourceAsStream("/finger_not_exist.bmp"))
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.nWidth = 640.toShort
    gafisImg.stHead.nHeight = 640.toShort
    gafisImg.stHead.nImageType = 0.toByte
    gafisImg.stHead.bIsCompressed = 0.toByte
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
    gafisImg.bnData = data
    gafisImg.stHead.nImgSize = gafisImg.bnData.length
    gafisImg.stHead.nCaptureMethod = 1.toByte
    gafisImg.stHead.nResolution = 500.toShort
    gafisImg.stHead.nBits = 8.toByte
//    gafisImg.stHead.bIsPlain = 1.toByte
//    gafisImg.stHead.nFingerIndex = 11.toByte
    gafisImg.stHead.szName = "unknown"

    //val originalImg = decoder.decode(gafisImg, RawImageDataType)
    val encoder = new ImageEncoderImpl(decoder)
    val wsq = encoder.encodeWSQ(gafisImg)
    Assert.assertNotNull(wsq.bnData)


    FileUtils.writeByteArrayToFile(new File("/Users/yuchen/finger_not_exist.data"),wsq.bnData)
  }
}
