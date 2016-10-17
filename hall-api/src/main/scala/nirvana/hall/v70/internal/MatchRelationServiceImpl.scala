package nirvana.hall.v70.internal

import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetResponse, MatchRelationGetRequest}

/**
 * Created by songpeng on 16/9/21.
 */
class MatchRelationServiceImpl extends MatchRelationService{
  /**
   * 获取比对关系
   * @param request
   * @return
   */
  override def getMatchRelation(request: MatchRelationGetRequest): MatchRelationGetResponse = {
    throw new UnsupportedOperationException
  }
}
