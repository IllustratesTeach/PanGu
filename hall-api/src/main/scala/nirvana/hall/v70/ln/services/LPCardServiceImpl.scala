package nirvana.hall.v70.ln.services


import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.FPTProto.LPCard

/**
  * Created by songpeng on 2017/6/29.
  */
class LPCardServiceImpl extends LPCardService{
  /**
    * 新增现场卡片
    *
    * @param lpCard
    * @return
    */
  override def addLPCard(lpCard: LPCard, dbId: Option[String]): Unit = ???

  /**
    * 删除现场卡片
    *
    * @param cardId
    * @return
    */
override def delLPCard(cardId: String, dbId: Option[String]): Unit = ???

  /**
    * 更新现场卡片
    *
    * @param lpCard
    * @return
    */
  override def updateLPCard(lpCard: LPCard, dbId: Option[String]): Unit = ???

  /**
    * 获取现场卡片
    *
    * @param fingerId
    * @return
    */
  override def getLPCard(fingerId: String, dbId: Option[String]): LPCard = ???

  /**
    * 验证现场卡片是否存在
    *
    * @param cardId
    * @return
    */
  override def isExist(cardId: String, dbId: Option[String]): Boolean = ???
}
