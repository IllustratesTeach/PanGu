package nirvana.hall.v62.services

import java.util.concurrent.atomic.AtomicReferenceArray

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.internal.RequestHeader
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
class AncientDataTest {

  @Test
  def test_scala_length: Unit = {
    val m = new M

    //Assert.assertEquals(34,m.getDataSizeByScala)
  }
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

class M extends AncientData{
  var i:Int = _
  var s:Short = _
  @IgnoreTransfer
  var ignore:Int = _
  @Length(2)
  var a:Array[Byte]= _
  @Length(2)
  var n:Array[N]= _
  @Length(20)
  var str:String= _
  var n2:N= _
}
//2
class N extends AncientData {
  @Length(2)
  var a:Array[Byte]= _
}

