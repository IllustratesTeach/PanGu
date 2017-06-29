package nirvana.hall.v70.services

import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

/**
 * Created by songpeng on 16/8/31.
 */
class QueryServiceImplTest extends BaseV70TestCase{

  @Test
  def test_getMatchResult: Unit ={
    val service = getService[QueryService]
    val matchResult = service.getMatchResult(10)
    println(matchResult.get.getCandidateNum)

  }

  @Test
  def test_getStatusBySid: Unit ={
    val service = getService[QueryService]
    val status = service.getStatusBySid(210)
    assert(status == 2)
  }

  @Test
  def test_sendQuery: Unit ={
    val service = getService[QueryService]
    val matchTask = MatchTask.newBuilder()
    matchTask.setMatchId("1234567890")
    matchTask.setMatchType(MatchType.FINGER_TT)
    matchTask.setTopN(10)
    matchTask.setObjectId(0)
    matchTask.setPriority(3)
    matchTask.setScoreThreshold(60)
    service.sendQuery(matchTask.build())
  }

}
