package nirvana.hall.api.internal

import nirvana.hall.api.jpa.CodeAjlb
import nirvana.hall.protocol.sys.DictProto.CodeData
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-05
 */
class ScalaUtilsTest {

  @Test
  def test_scala: Unit ={
    val codeDataBuilder = CodeData.newBuilder()
    codeDataBuilder.setCode("code")
    codeDataBuilder.setName("name")

    val aj = ScalaUtils.convertProtobufToScala[CodeAjlb](codeDataBuilder.build)

    Assert.assertEquals("code",aj.code)
    Assert.assertEquals(Some("name"),aj.name)

    val builder2 = CodeData.newBuilder()
    ScalaUtils.convertScalaToProtobuf(aj,builder2)
    Assert.assertEquals("code",builder2.getCode)
    Assert.assertEquals("name",builder2.getName)

  }
}
