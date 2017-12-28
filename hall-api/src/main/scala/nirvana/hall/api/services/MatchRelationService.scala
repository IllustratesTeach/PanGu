package nirvana.hall.api.services


import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic04Rec, Logic05Rec, Logic06Rec}
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
    * @param cardId 现场指纹编号
    * @param isLatent
    * @return
    */
  def getLtHitResultPackage(cardId: String, isLatent: Boolean): Seq[LtHitResultPackage]

  /**
    * 获取正查或倒查比中关系
    * @param oraSid 任务号
    * @return
    */
  def getLtHitResultPackageByOraSid(oraSid: String): Seq[LtHitResultPackage]

  /**
    * 获取串查比中关系
    * @param cardId
    * @return
    */
  def getLlHitResultPackage(cardId: String): Seq[LlHitResultPackage]

  def getLlHitResultPackageByOraSid(oraSid: String): Seq[LlHitResultPackage]

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

  /**
    * 获取正查或倒查比中关系
    * @param cardId 现场指纹编号
    * @param isLatent 是否现场
    * @return
    */
  def getLogic04Rec(cardId: String, isLatent: Boolean): Seq[Logic04Rec]

  /**
    * 获取重卡比中关系
    * @param cardId
    * @return
    */
  def getLogic05Rec(cardId: String): Seq[Logic05Rec]

  /**
    * 获取串查比中关系
    * @param cardId
    * @return
    */
  def getLogic06Rec(cardId: String): Seq[Logic06Rec]
}
