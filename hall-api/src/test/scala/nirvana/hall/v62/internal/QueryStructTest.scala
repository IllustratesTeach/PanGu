package nirvana.hall.v62.internal

import org.junit.{Test, Assert}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-30
 */
class QueryStructTest {
  @Test
  def test_length: Unit ={
    val queryStruct = new QueryStruct
    Assert.assertEquals(512,queryStruct.getDataSize)
  }
}
