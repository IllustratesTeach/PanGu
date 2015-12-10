package nirvana.hall.image.internal

import nirvana.hall.image.jni.BaseJniTest
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class FirmDecoderTest extends BaseJniTest{
  @Test
  def test_decode: Unit ={
    val decoder = new FirmDecoder("support")
    val cprData:Array[Byte] = "".getBytes()
    decoder.decode("1300",cprData,640,640)
  }
}
