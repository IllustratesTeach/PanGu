package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import org.apache.commons.io.IOUtils
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-12
 */
class galoctpTest {

  @Test
  def test_read: Unit ={
    val bytes = IOUtils.toByteArray(getClass.getResourceAsStream("/mic.data"))
    val buffer=ChannelBuffers.wrappedBuffer(bytes)
    val ga = new galoctp {}
    val mics = ga.GAFIS_MIC_GetDataFromStream(buffer)
    Assert.assertEquals(10,mics.length)
  }

  @Test
  def test_writeMic: Unit ={
    val bytes = IOUtils.toByteArray(getClass.getResourceAsStream("/mic.data"))
    val buffer=ChannelBuffers.wrappedBuffer(bytes)
    val ga = new galocpkg with gitempkg with galoctp{}
    val mics = ga.GAFIS_MIC_GetDataFromStream(buffer)

    val buffers = ChannelBuffers.buffer(ga.GAFIS_MIC_MicStreamLen(mics.toArray))
    mics.foreach{mic=>
      ga.GAFIS_MIC_Mic2Stream(mic, buffers)
    }
    Assert.assertEquals(bytes.length,buffers.array().length)

  }

}
