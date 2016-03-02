package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QuerySendRequest}
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
    val requestBuilder = QueryGetRequest.newBuilder()
    requestBuilder.setOraSid(40)

    val service = getService[QueryService]
    val response = service.getQuery(requestBuilder.build())
    println(response.getMatchResult.getCandidateNum)
    println(response.getMatchResult.getCandidateResultCount)
    Assert.assertNotNull(response)
  }
}
