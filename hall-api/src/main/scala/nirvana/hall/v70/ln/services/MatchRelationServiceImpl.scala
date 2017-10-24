package nirvana.hall.v70.ln.services

import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.protocol.api.FPTProto.MatchRelationInfo
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse}

/**
 * Created by songpeng on 16/9/21.
 */
class MatchRelationServiceImpl(fptService: FPTService) extends MatchRelationService{
  /**
    * 获取比对关系
    *
    * @param request
    * @return
    */
  override def getMatchRelation(request: MatchRelationGetRequest): MatchRelationGetResponse = ???

  /**
    * 更新比中关系
    *
    * @param matchRelationInfo
    * @param dbId
    */
override def updateMatchRelation(matchRelationInfo: MatchRelationInfo, dbId: Option[String]): Unit = ???
override def isExist(szBreakID: String, dbId: Option[String]): Boolean = ???

  /**
    * 获取比对关系
    *
    * @param breakId
    * @return
    */
override def getMatchRelation(breakId: String): MatchRelationInfo = ???

  /**
    * 增加比中关系
    *
    * @param matchRelationInfo
    * @param dbId
    */
  override def addMatchRelation(matchRelationInfo: MatchRelationInfo, dbId: Option[String]): Unit = ???
}
