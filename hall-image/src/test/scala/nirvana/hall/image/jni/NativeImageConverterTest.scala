package nirvana.hall.image.jni

import org.apache.commons.io.IOUtils
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class NativeImageConverterTest extends BaseJniTest{
  @Test
  def test_wsq: Unit ={
    val is = getClass.getResourceAsStream("/wsq.data")
    val cpr_data = IOUtils.toByteArray(is)
    val originalData = NativeImageConverter.decodeByWSQ(cpr_data,640,640,500)
    val destIO = getClass.getResourceAsStream("/wsq.data.uncompressed")
    val expect = IOUtils.toByteArray(destIO)
    Assert.assertArrayEquals(expect,originalData)

    //test compress
    val cpr = NativeImageConverter.encodeByWSQ(originalData,640,640,500,10)
    val originalData2 = NativeImageConverter.decodeByWSQ(cpr,640,640,500)
    Assert.assertEquals(expect.length,originalData2.length)
  }
}
