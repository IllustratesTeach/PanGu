package nirvana.hall.image.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.image.config.HallImageConfig
import nirvana.hall.image.jni.BaseJniTest
import nirvana.hall.image.services.RawImageDataType
import org.apache.commons.io.IOUtils
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
}
