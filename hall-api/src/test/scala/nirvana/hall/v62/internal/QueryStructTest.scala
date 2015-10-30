package nirvana.hall.v62.internal

import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Test, Assert}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-30
 */
class QueryStructTest {
  @Test
  def test_key: Unit ={
    val key = new Key
    key.id = "asdf"
    val bytesLength = key.id.getBytes.length
    Assert.assertEquals(bytesLength,key.getDataSize)
    val buffer = ChannelBuffers.buffer(bytesLength)
    key.writeToChannelBuffer(buffer)
    Assert.assertEquals(bytesLength,buffer.writerIndex())
  }
  @Test
  def test_length: Unit ={
    val queryStruct = new QueryStruct
    val range1= new KeyRangeStruct
    range1.szStartKey="fdsa"
    queryStruct.stKeyRange = Array[KeyRangeStruct](range1)
    Assert.assertEquals(512,queryStruct.getDataSize)
    val buffer = ChannelBuffers.buffer(512)
    queryStruct.writeToChannelBuffer(buffer)
    Assert.assertEquals(512,buffer.writerIndex())

    val queryStruct2 = new QueryStruct
    queryStruct2.fromChannelBuffer(buffer)
    Assert.assertEquals(2,queryStruct2.stKeyRange.size)
    Assert.assertEquals(range1.szStartKey,queryStruct2.stKeyRange(0).szStartKey)
  }
}
