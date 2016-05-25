package nirvana.hall.c.services

import org.junit.{Assert, Test}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-24
  */
class PacakgeTest {
  @Test
  def test_sidToLong: Unit ={
    def testSID(num:Long) {
      Assert.assertEquals(num, SIDArrayToLong(longToSidArray(num)))
    }
    testSID(1L << 32|1)
  }
}
