package nirvana.hall.c.services.gbaselib

import nirvana.hall.c.services.gbaselib.gbasedef.AFISDateTime
import org.junit.Test

/**
 * Created by songpeng on 16/6/4.
 */
class gbasedefTest {

  @Test
  def testAFISDateTime: Unit ={
    val time = new AFISDateTime

    print(time.tDate.tYear)

  }
}
