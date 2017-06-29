package nirvana.hall.c.services.gloclib

import nirvana.hall.c.services.gloclib.gaqryque.{GAQUERYCANDSTRUCT, GAQUERYCANDHEADSTRUCT, GAKEYRANGESTRUCT, GAQUERYSTRUCT}
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-30
 */
class gaqryqueTest {
  @Test
  def test_length: Unit ={
    val queryStruct = new GAQUERYSTRUCT
    Assert.assertEquals(192,queryStruct.stSimpQry.getDataSize)
    val range1= new GAKEYRANGESTRUCT
    range1.szStartKey="fdsa"
    queryStruct.stKeyRange = Array[GAKEYRANGESTRUCT](range1)
    Assert.assertEquals(512,queryStruct.getDataSize)
    val buffer = ChannelBuffers.buffer(512)
    queryStruct.writeToStreamWriter(buffer)
    Assert.assertEquals(512,buffer.writerIndex())

    val queryStruct2 = new GAQUERYSTRUCT
    queryStruct2.fromStreamReader(buffer)
    Assert.assertEquals(2,queryStruct2.stKeyRange.size)
    Assert.assertEquals(range1.szStartKey,queryStruct2.stKeyRange(0).szStartKey)


    val candHead = new GAQUERYCANDHEADSTRUCT
    Assert.assertEquals(128,candHead.getDataSize)
    val cand = new GAQUERYCANDSTRUCT
    Assert.assertEquals(96,cand.getDataSize)
  }
}
