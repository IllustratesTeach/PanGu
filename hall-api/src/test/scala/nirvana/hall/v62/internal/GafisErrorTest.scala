package nirvana.hall.v62.internal

import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
class GafisErrorTest {

  @Test
  def test_length: Unit ={
    val error = new GafisError
    Assert.assertEquals(640,error.getDataSize)
  }
}
