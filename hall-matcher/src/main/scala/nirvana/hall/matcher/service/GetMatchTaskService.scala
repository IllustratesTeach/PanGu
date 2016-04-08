package nirvana.hall.matcher.service

import nirvana.protocol.MatchTaskQueryProto.{MatchTaskQueryRequest, MatchTaskQueryResponse}

/**
 * Created by songpeng on 16/3/20.
 */
trait GetMatchTaskService {

  /**
   * 获取比对任务
   * @param matchTaskQueryRequest
   * @return
   */
  def getMatchTask(matchTaskQueryRequest: MatchTaskQueryRequest): MatchTaskQueryResponse
}
