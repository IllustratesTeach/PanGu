package nirvana.hall.image.jni

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import org.apache.commons.io.IOUtils
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class NativeImageConverterTest extends BaseJniTest{
  @Test
  def test_gfs: Unit ={
    val is = getClass.getResourceAsStream("/lf.img")
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.fromStreamReader(is)

    val destImg = new GAFISIMAGESTRUCT
    destImg.stHead.fromByteArray(gafisImg.stHead.toByteArray)
    destImg.stHead.nImgSize = destImg.stHead.nWidth * destImg.stHead.nHeight
    destImg.bnData = new Array[Byte](destImg.stHead.nImgSize)

    NativeImageConverter.decodeByGFS(gafisImg.toByteArray,destImg.toByteArray)
  }
  @Test
  def test_wsq: Unit ={
    val is = getClass.getResourceAsStream("/wsq.data")
    val cpr_data = IOUtils.toByteArray(is)
    val destIO = getClass.getResourceAsStream("/wsq.data.uncompressed")
    val expect = IOUtils.toByteArray(destIO)
    0 until 100000 foreach { i =>
      val originalImage = NativeImageConverter.decodeByWSQ(cpr_data)
      val originalData = originalImage.getData
      Assert.assertArrayEquals(expect, originalData)

      //test compress
      val cpr = NativeImageConverter.encodeByWSQ(originalData, 640, 640, 500, 10)
      val originalData2 = NativeImageConverter.decodeByWSQ(cpr).getData
      Assert.assertEquals(expect.length, originalData2.length)
    }
  }
}
