package nirvana.hall.v62.internal

import org.junit.{Test, Assert}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
class CaseStructTest {
  @Test
  def test_case: Unit ={
    val struct = new tagGCASEINFOSTRUCT
    Assert.assertEquals(256,struct.getDataSize)
  }
}
