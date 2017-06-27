package nirvana.hall.api.services


import nirvana.hall.protocol.api.FPTProto.MatchRelationInfo
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse}

/**
 * Created by songpeng on 16/6/21.
 */
trait MatchRelationService {

  /**
   * 获取比对关系
    *
    * @param request
   * @return
   */
  def getMatchRelation(request: MatchRelationGetRequest): MatchRelationGetResponse


  /**
    * 获取比对关系
    *
    * @param breakId
    * @return
    */
  def getMatchRelation(breakId: String): MatchRelationInfo
  /**
    * 增加比中关系
    * @param matchRelationInfo
    * @param dbId
    */
  def addMatchRelation(matchRelationInfo: MatchRelationInfo,dbId: Option[String] = None)


  /**
    * 更新比中关系
    * @param matchRelationInfo
    * @param dbId
    */
  def updateMatchRelation(matchRelationInfo: MatchRelationInfo,dbId: Option[String] = None)

  def isExist(szBreakID:String,dbId: Option[String] = None):Boolean

}
