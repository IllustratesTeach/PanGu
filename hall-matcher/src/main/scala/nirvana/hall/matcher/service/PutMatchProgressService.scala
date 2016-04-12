package nirvana.hall.matcher.service

import nirvana.protocol.MatchProgressProto.{MatchProgressResponse, MatchProgressRequest}

/**
 * Created by songpeng on 16/4/12.
 */
trait PutMatchProgressService {

  /**
   * 更新比对进度
   * @param matchProgressRequest
   * @return
   */
  def putMatchProgress(matchProgressRequest: MatchProgressRequest): MatchProgressResponse
}
