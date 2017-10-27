package nirvana.hall.matcher

import junit.framework.Assert
import nirvana.hall.matcher.service.GetMatchTaskService
import nirvana.protocol.MatchTaskQueryProto.MatchTaskQueryRequest
import org.junit.Test

/**
  * Created by songpeng on 2017/10/26.
  */
class MatchTaskServiceImplTest extends BaseHallMatcherTestCase{

  @Test
  def test_getMatchTask: Unit ={
    val service = getService[GetMatchTaskService]
    val request = MatchTaskQueryRequest.newBuilder()
    request.setSize(1)
    val response = service.getMatchTask(request.build())
    Assert.assertTrue(response.getMatchTaskCount > 0)
  }
}
