package nirvana.hall.v62.services

import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicReferenceArray

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.internal.c.gloclib.glocndef.GNETREQUESTHEADOBJECT
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}
import org.xsocket.datagram.UserDatagram

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
class AncientDataTest {

  trait GetData{
    this:UserDatagram =>
    def getMyData = getData
  }
  @Test
  def test_xsocket: Unit = {
    val m = new M
    m.str= "asdf"
    m.n2 = new N
    m.n = Array[N](new N)
    m.n2.aN=Array[Byte](1)
    m.n.head.aN = Array[Byte](8)

    Assert.assertEquals(34,m.getDataSize)
    val byteBuffer = ByteBuffer.allocate(m.getDataSize)
    byteBuffer.mark()
    val connection = new UserDatagram(byteBuffer)
    m.writeToDataSink(connection)
    Assert.assertEquals(0,byteBuffer.remaining())
    byteBuffer.reset()

    val connection2 = new UserDatagram(byteBuffer)
    val m2 = new M().fromDataSource(connection2)

    Assert.assertEquals(m.str,m2.str)
    Assert.assertEquals(m.n.head.aN.head,m2.n.head.aN.head)
  }
  @Test
  def test_scala_length: Unit = {
    val m = new M
    m.str= "asdf"
    m.n2 = new N
    m.n = Array[N](new N)
    m.n2.aN=Array[Byte](1)
    m.n.head.aN = Array[Byte](8)

    Assert.assertEquals(34,m.getDataSize)
    val buffer = ChannelBuffers.buffer(m.getDataSize)
    m.writeToChannelBuffer(buffer)
    Assert.assertFalse(buffer.writable())

    val m2 = new M().fromChannelBuffer(buffer)
    Assert.assertFalse(buffer.readable())

    Assert.assertEquals(m.str,m2.str)
    Assert.assertEquals(m.n.head.aN.head,m2.n.head.aN.head)
  }
  @Test
  def test_ancient: Unit ={
    val header = new GNETREQUESTHEADOBJECT
    header.nIP="10.1.1.1"
    val buffer = ChannelBuffers.buffer(header.getDataSize)
    header.writeToChannelBuffer(buffer)
    Assert.assertFalse(buffer.writable())

    val bytes = buffer.array()

    val header2 = new GNETREQUESTHEADOBJECT
    header2.fromChannelBuffer(buffer)

    Assert.assertEquals(header.szMagicStr,header2.szMagicStr)
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

class M extends ScalaReflect{
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
class N extends ScalaReflect {
  @Length(2)
  var aN:Array[Byte]= _
}

