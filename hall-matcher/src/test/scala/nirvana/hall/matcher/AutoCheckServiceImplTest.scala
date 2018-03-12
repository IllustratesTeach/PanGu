package nirvana.hall.matcher

import nirvana.hall.matcher.internal.adapter.common.vo.QueryQueVo
import nirvana.hall.matcher.service.AutoCheckService
import nirvana.protocol.MatchResult.MatchResultRequest
import nirvana.protocol.MatchResult.MatchResultRequest.MatchResultObject
import org.junit.Test

/**
  * Created by yuchen on 2018/3/12.
  */
class AutoCheckServiceImplTest extends BaseHallMatcherTestCase{
  @Test
  def test_ttAutoCheck: Unit ={
    val service = getService[AutoCheckService]
    val request = MatchResultRequest.newBuilder
    val cand = MatchResultObject.newBuilder
    cand.setObjectId(252497)
    cand.setScore(70)
    cand.setPos(1)
    cand.setSrcIndex(1)
    cand.setDesc(10000L)
    request.addCandidateResult(cand)
    request.setMatchId("226227")
    request.setCandidateNum(50)
    val matcherStatus = MatchResultRequest.MatcherStatus.newBuilder()
    matcherStatus.setMsg("success")
    matcherStatus.setCode(0)
    request.setStatus(matcherStatus)
    request.setMaxScore(100)
    val queryQue = new QueryQueVo("R1100000000001111927030",226227,1,false,50)
    service.ttAutoCheck(request.build(),queryQue)
  }
}
