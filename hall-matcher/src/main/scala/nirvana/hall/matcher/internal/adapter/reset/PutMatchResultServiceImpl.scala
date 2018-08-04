package nirvana.hall.matcher.internal.adapter.reset

import nirvana.hall.matcher.service.PutMatchResultService
import nirvana.protocol.MatchResult
import nirvana.protocol.MatchResult.MatchResultResponse
import nirvana.protocol.MatchResult.MatchResultResponse.MatchResultResponseStatus

class PutMatchResultServiceImpl extends PutMatchResultService{
  /**
    * 推送比对结果
    * @param matchResultRequest
    * @return
    */
  override def putMatchResult(matchResultRequest: MatchResult.MatchResultRequest): MatchResult.MatchResultResponse = {
    val matchResultResponse = MatchResultResponse.newBuilder()
    matchResultResponse.setStatus(MatchResultResponseStatus.OK)

    matchResultResponse.build()
  }
}
