package nirvana.hall.matcher.internal.adapter.gafis6

import nirvana.hall.matcher.service.PutMatchProgressService
import nirvana.protocol.MatchProgressProto.MatchProgressResponse.MatchProgressStatus
import nirvana.protocol.MatchProgressProto.{MatchProgressRequest, MatchProgressResponse}

/**
 * 比对进度service
 */
class PutMatchProgressServiceImpl extends PutMatchProgressService{
  /**
   * 更新比对进度
   * @param matchProgressRequest
   * @return
   */
  override def putMatchProgress(matchProgressRequest: MatchProgressRequest): MatchProgressResponse = {
    val matchProgressResponse = MatchProgressResponse.newBuilder()
    matchProgressResponse.setStatus(MatchProgressStatus.OK)
    matchProgressResponse.build()
  }
}
