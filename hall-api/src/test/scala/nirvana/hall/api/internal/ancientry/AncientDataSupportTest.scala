package nirvana.hall.api.internal.ancientry

import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
class AncientDataSupportTest {
  @Test
  def test_ancient: Unit ={
    val header = new RequestHeader
    header.nIP="10.1.1.1"
    val buffer = ChannelBuffers.buffer(196)
    header.writeToChannelBuffer(buffer)
    val header2 = new RequestHeader
    header2.fromChannelBuffer(buffer)

    Assert.assertEquals(header.nIP,header2.nIP)
  }
}
