package nirvana.hall.v62.internal

import java.util.concurrent.atomic.AtomicReferenceArray

import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
class AncientDataTest {

  @Test
  def test_ancient: Unit ={
    val header = new RequestHeader
    header.nIP="10.1.1.1"
    val buffer = ChannelBuffers.buffer(header.getDataSize)
    header.writeToChannelBuffer(buffer)
    val header2 = new RequestHeader
    header2.fromChannelBuffer(buffer)

    Assert.assertEquals(header.nIP,header2.nIP)
  }
  @Test
  def test_array: Unit ={
    val array = new AtomicReferenceArray[Object](20)
    0 to 100 foreach{i=>
      array.lazySet(i&19,new Object)
    }
  }
}
