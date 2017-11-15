package nirvana.hall.api.services


import nirvana.hall.c.services.gfpt5lib.{LlHitResultPackage, LtHitResultPackage, TtHitResultPackage}
import nirvana.hall.protocol.api.FPTProto.MatchRelationInfo
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

  /**
    * 获取重卡比中关系
    * @param cardId
    * @return
    */
  def getTtHitResultPackage(cardId: String): Seq[TtHitResultPackage]

  /**
    * 获取正查或倒查比中关系
    * @param cardId
    * @param isLatent
    * @return
    */
  def getLtHitResultPackage(cardId: String, isLatent: Boolean): Seq[LtHitResultPackage]

  /**
    * 获取串查比中关系
    * @param cardId
    * @return
    */
  def getLlHitResultPackage(cardId: String): Seq[LlHitResultPackage]

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
