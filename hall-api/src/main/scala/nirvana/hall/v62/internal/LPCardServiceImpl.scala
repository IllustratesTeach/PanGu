package nirvana.hall.v62.internal

import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.services.LPCardService
import nirvana.hall.c.services.gloclib.galoclp.GLPCARDINFOSTRUCT
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
 * Created by songpeng on 16/1/26.
 */
class LPCardServiceImpl(facade:V62Facade,config:HallV62Config) extends LPCardService{

  /**
   * 新增现场卡片
   * @param lpCard
   * @return
   */
  override def addLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    val lpCardHandled = lpCard.toBuilder.setStrCardID(dropLPCardNoHeadLetter(lpCard.getStrCardID)).build
    //转换为c的结构
    val gLPCard= galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lpCardHandled)
    //调用实现方法
    facade.NET_GAFIS_FLIB_Add(getDBID(dbId),
      V62Facade.TID_LATFINGER,
      lpCardHandled.getStrCardID, gLPCard)
  }

  /**
   * 获取现场卡片
   * @param cardId
   * @return
   */
  override def getLPCard(cardId: String, dbId: Option[String]): LPCard = {
    val gCard = new GLPCARDINFOSTRUCT
    facade.NET_GAFIS_FLIB_Get(getDBID(dbId), V62Facade.TID_LATFINGER, dropLPCardNoHeadLetter(cardId), gCard, null, 3)
    val card = galoclpConverter.convertGLPCARDINFOSTRUCT2ProtoBuf(gCard)
    card.toBuilder.setStrCardID(appendLPCardNoHeadLetter(card.getStrCardID)).build
  }

  /**
   * 更新现场卡片
   * @param lpCard
   * @return
   */
  override def updateLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    val lpCardHandled = lpCard.toBuilder.setStrCardID(dropLPCardNoHeadLetter(lpCard.getStrCardID)).build
    val gLPCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lpCardHandled)
    facade.NET_GAFIS_FLIB_Update(getDBID(dbId),
      V62Facade.TID_LATFINGER,
      lpCardHandled.getStrCardID, gLPCard)
  }

  /**
   * 删除现场卡片
   * @param cardId
   * @return
   */
  override def delLPCard(cardId: String, dbId: Option[String]): Unit = {
    facade.NET_GAFIS_FLIB_Del(getDBID(dbId), V62Facade.TID_LATFINGER, dropLPCardNoHeadLetter(cardId))
  }

  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    facade.NET_GAFIS_FLIB_Exist(getDBID(dbId), V62Facade.TID_LATFINGER, dropLPCardNoHeadLetter(cardId))
  }

  /**
   * 获取DBID
   * @param dbId
   */
  private def getDBID(dbId: Option[String]): Short ={
    if(dbId == None){
      config.latentTable.dbId.toShort
    }else{
      dbId.get.toShort
    }
  }

  private def dropLPCardNoHeadLetter(cardId:String):String ={
    if(cardId.toUpperCase.startsWith(HallApiConstants.LPCARDNO_HEAD_LETTER))
      cardId.toUpperCase.drop(1)
    else cardId.toUpperCase
  }

  private def appendLPCardNoHeadLetter(cardId:String):String ={
    if(!cardId.toUpperCase.startsWith(HallApiConstants.LPCARDNO_HEAD_LETTER))
      HallApiConstants.LPCARDNO_HEAD_LETTER.concat(cardId.toUpperCase)
    else cardId.toUpperCase
  }

}
