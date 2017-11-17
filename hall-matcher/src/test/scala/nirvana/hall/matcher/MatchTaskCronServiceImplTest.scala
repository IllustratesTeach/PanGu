package nirvana.hall.matcher

import nirvana.hall.matcher.service.MatchTaskCronService
import org.junit.Test

/**
  * Created by songpeng on 2017/8/17.
  */
class MatchTaskCronServiceImplTest extends BaseHallMatcherTestCase{

  @Test
  def test_updateMatchStatusWaitingByMatchTaskTimeout: Unit ={
    val service = getService[MatchTaskCronService]
    service.updateMatchStatusWaitingByMatchTaskTimeout(60)

  }
}
