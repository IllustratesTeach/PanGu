package nirvana.hall.image.internal

import nirvana.hall.image.jni.BaseJniTest
import org.apache.commons.io.IOUtils
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class FirmDecoderImplTest extends BaseJniTest{
  @Test
  def test_decode_1700: Unit = {
    val decoder = new FirmDecoderImpl("support")
    val cprData = IOUtils.toByteArray(getClass.getResourceAsStream("/1700.data"))
    decoder.decode("1700", cprData, 640, 640, 500)
  }
  @Test
  def test_decode_1400_vi_wsq{
    val decoder = new FirmDecoderImpl("support")
    val cprData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    Range(0,409600).foreach{
      decoder.decode("1400",cprData,640,640,500)
    }
  }
}
