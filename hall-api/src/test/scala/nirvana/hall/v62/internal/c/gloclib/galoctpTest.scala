package nirvana.hall.v62.internal.c.gloclib

import org.apache.commons.io.IOUtils
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.Test

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
    ga.GAFIS_MIC_GetDataFromStream(buffer)
  }
}
