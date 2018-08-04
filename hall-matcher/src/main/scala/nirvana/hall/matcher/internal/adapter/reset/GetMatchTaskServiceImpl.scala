package nirvana.hall.matcher.internal.adapter.reset

import nirvana.hall.matcher.service.GetMatchTaskService
import nirvana.protocol.MatchTaskQueryProto
import nirvana.protocol.MatchTaskQueryProto.MatchTaskQueryResponse

class GetMatchTaskServiceImpl extends GetMatchTaskService {
  /**
    * 获取比对任务
    *
    * @param matchTaskQueryRequest
    * @return
    */
  override def getMatchTask(matchTaskQueryRequest: MatchTaskQueryProto.MatchTaskQueryRequest): MatchTaskQueryProto.MatchTaskQueryResponse = {
    MatchTaskQueryResponse.newBuilder().build()
  }
}
