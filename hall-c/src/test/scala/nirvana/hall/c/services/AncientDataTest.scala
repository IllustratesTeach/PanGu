package nirvana.hall.c.services

import nirvana.hall.c.annotations.{Length, LengthRef}
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

import scala.language.experimental.macros
import scala.reflect.runtime.universe._
/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-08
 */
class AncientDataTest {
  @Test
  def test_getOffsetAndLength: Unit ={
    val data = new AncientDataModel
    Assert.assertEquals((0,2),data.findFieldOffsetAndLength("f1"))
    Assert.assertEquals((2,1),data.findFieldOffsetAndLength("f2"))
    Assert.assertEquals((3,2),data.findFieldOffsetAndLength("f3"))
    Assert.assertEquals((5,100),data.findFieldOffsetAndLength("f4"))
    Assert.assertEquals((105,20),data.findFieldOffsetAndLength("f5"))
  }

  @Test
  def test_len_ref: Unit ={
    val data = new AncientDataModel3
    data.length = 3
    data.data = Array[Byte](1,2,3)
    Assert.assertEquals(7,data.getDataSize)
    val buffer = ChannelBuffers.buffer(data.getDataSize)
    data.writeToStreamWriter(buffer)

    val data2 = new AncientDataModel3
    data2.fromStreamReader(buffer)
    Assert.assertArrayEquals(data.data,data2.data)
  }
  @Test
  def test_len: Unit ={
    val data = new AncientDataModel
    Assert.assertEquals(125,data.getDataSize)
    data.f1 = 12

    data.f5 = Array(new AncientDataModel2)
    data.f5.head.f1 =123

    val buffer = ChannelBuffers.buffer(data.getDataSize)
    data.writeToStreamWriter(buffer)
    val m = typeOf[java.lang.String]

    val data2 = new AncientDataModel
    data2.fromStreamReader(buffer)

    Assert.assertEquals(data.f1,data2.f1)
    Assert.assertEquals(data.f5.head.f1,data2.f5.head.f1)

    val array =buffer.array()

    buffer.capacity()
    array.length

  }
}
class AncientDataModel3 extends AncientData {
  var length:Int = _
  @LengthRef("length")
  var data:Array[Byte] = _
}
class AncientDataModel2 extends AncientData {
  var f1:Short = _
}
class AncientDataModel extends AncientData {
  var f1:Short = _
  var f2:Byte = _
  var f3:AncientDataModel2 = new AncientDataModel2
  @Length(100)
  var f4:Array[Byte] = _
  @Length(10)
  var f5:Array[AncientDataModel2] = _
  /*
  @Length(2)
  var f6:String = _
  */
}
trait ScalaReflect2 {
  /*
  def getDataSize2: Int = macro AncientDataMacroDefine.getDataSizeImpl[ScalaReflect2,IgnoreTransfer,Length]
  def internalWriteToStreamWriter[T <: StreamWriter,M<: ScalaReflect2](dataSink:T,model:M): Unit = macro AncientDataMacroDefine.writeStream[M,T,ScalaReflect2,IgnoreTransfer,Length]
  def writeToStreamWriter[T](stream:T)(implicit converter:T=> StreamWriter): T= {
    val dataSink = converter(stream)
    this.internalWriteToStreamWriter(dataSink,this)
    stream
  }
  */
}
