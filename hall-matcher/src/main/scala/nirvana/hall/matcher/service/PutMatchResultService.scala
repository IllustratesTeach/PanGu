package nirvana.hall.matcher.service

import nirvana.protocol.MatchResult.{MatchResultRequest, MatchResultResponse}

/**
 * Created by songpeng on 16/3/20.
 */
trait PutMatchResultService {

  /**
   * 推送比对结果
   * @param matchResultRequest
   * @return
   */
  def putMatchResult(matchResultRequest: MatchResultRequest): MatchResultResponse
}
