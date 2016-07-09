package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.api.QueryProto.QuerySendRequest
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.BaseV62TestCase
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/12/9.
 */
class QueryFilterTest extends BaseV62TestCase{

  @Test
  def test_sendQuery: Unit ={
    val requestBuilder = QuerySendRequest.newBuilder()
    val matchTask = requestBuilder.getMatchTaskBuilder()
    matchTask.setMatchId("P3702000000002015129996")
    matchTask.setMatchType(MatchType.FINGER_TT)
    matchTask.setPriority(1)
    matchTask.setScoreThreshold(50)
    matchTask.setObjectId(1)

    val service = getService[QueryService]
    val response = service.sendQuery(requestBuilder.build())
    Assert.assertNotNull(response)
  }

  @Test
  def test_getQuery: Unit ={
    val service = getService[QueryService]
    val matchResult = service.getMatchResult(1)
    Assert.assertNotNull(matchResult)
  }

  @Test
  def test_findFirstQueryStatusByCardId: Unit ={
    val service = getService[QueryService]
    val status = service.findFirstQueryStatusByCardId("0000000000000000000008")
    Assert.assertEquals(status, MatchStatus.RECHECKED)
  }
}
