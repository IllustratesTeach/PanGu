package nirvana.hall.api.internal

import nirvana.hall.api.entities.CodeAjlb
import nirvana.hall.protocol.sys.SyncDictProto.SyncDictResponse.CodeData
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
  }
}
