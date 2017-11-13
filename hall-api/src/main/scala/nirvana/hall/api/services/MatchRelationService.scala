package nirvana.hall.api.services


import nirvana.hall.protocol.api.FPTProto.MatchRelationInfo
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse}
import nirvana.hall.protocol.fpt.MatchRelationProto.{MatchRelationLL, MatchRelationTLAndLT, MatchRelationTT}

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
  def getMatchRelationTT(cardId: String): Seq[MatchRelationTT]

  /**
    * 获取正查或倒查比中关系
    * @param cardId
    * @param isLatent
    * @return
    */
  def getMatchRelationTLAndLT(cardId: String, isLatent: Boolean): Seq[MatchRelationTLAndLT]

  /**
    * 获取串查比中关系
    * @param cardId
    * @return
    */
  def getMatchRelationLL(cardId: String): Seq[MatchRelationLL]

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
