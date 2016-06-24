package nirvana.hall.api.services

import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse}

/**
 * Created by songpeng on 16/6/21.
 */
trait MatchRelationService {

  /**
   * 获取比对关系
   * @param request
   * @return
   */
  def getMatchRelation(request: MatchRelationGetRequest): MatchRelationGetResponse
}
