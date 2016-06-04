package nirvana.hall.c.services.ghpcbase

import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import org.junit.Test

/**
 * Created by songpeng on 16/6/4.
 */
class ghpcdefTest {

  @Test
  def testAFISDateTime: Unit ={
    val time = new AFISDateTime

    print(time.tDate.tYear)

  }
}
