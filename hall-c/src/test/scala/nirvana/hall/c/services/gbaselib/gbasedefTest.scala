package nirvana.hall.c.services.gbaselib

import nirvana.hall.c.services.gbaselib.gbasedef.AFISDateTime
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 16/6/4.
 */
class gbasedefTest {

  @Test
  def testAFISDateTime: Unit ={
    val time = new AFISDateTime
    time.tDate.setJavaYear(2016)
    Assert.assertEquals(2016,time.tDate.convertAsJavaYear())
    time.tTime.setJavaSecs(57)
    Assert.assertEquals(57,time.tTime.convertAsJavaSecs())

  }
}
