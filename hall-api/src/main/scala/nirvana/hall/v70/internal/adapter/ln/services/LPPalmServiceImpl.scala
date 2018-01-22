package nirvana.hall.v70.internal.adapter.ln.services

import javax.persistence.EntityManager

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.LPPalmService
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v70.services.sys.UserService

/**
  * 现场掌纹service实现
  */
class LPPalmServiceImpl(entityManager: EntityManager, userService: UserService) extends LPPalmService with LoggerSupport{
  /**
    * 新增现场卡片
    *
    * @param lpCard
    * @return
    */
  override def addLPCard(lpCard: LPCard, dbId: Option[String]): Unit = ???

  /**
    * 获取现场卡片
    *
    * @param cardId
    * @return
    */
override def getLPCard(cardId: String, dbId: Option[String]): LPCard = ???

  /**
    * 更新现场卡片
    *
    * @param lpCard
    * @return
   */
  override def updateLPCard(lpCard: LPCard, dbId: Option[String]): Unit = ???

  /**
    * 验证现场卡片是否存在
    *
    * @param cardId
    * @return
    */
  override def isExist(cardId: String, dbId: Option[String]): Boolean = ???

  /**
    * 删除现场卡片
    *
    * @param cardId
    * @return
    */
  override def delLPCard(cardId: String, dbId: Option[String]): Unit = ???
}
