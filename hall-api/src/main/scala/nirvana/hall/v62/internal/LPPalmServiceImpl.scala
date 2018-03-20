package nirvana.hall.v62.internal

import nirvana.hall.api.services.LPPalmService
import nirvana.hall.c.services.gloclib.galoclp.GLPCARDINFOSTRUCT
import nirvana.hall.protocol.api.FPTProto.LPCard
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter
import nirvana.hall.v62.internal.c.gloclib.gcolnames._

/**
 * 现场掌纹service实现
 */
class LPPalmServiceImpl(facade:V62Facade,config:HallV62Config) extends LPPalmService{

  /**
   * 新增现场卡片
   * @param lpCard
   * @return
   */
  override def addLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    //转换为c的结构
    val gLPCard= galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lpCard)
    //调用实现方法
    facade.NET_GAFIS_FLIB_Add(getDBID(dbId),
      V62Facade.TID_LATPALM,
      lpCard.getStrCardID, gLPCard)
  }

  /**
    * 获取现场关联
    * @param cardId
    */
  private def getGroupName(cardId: String, dbId: Option[String]): String ={
    val data = facade.NET_GAFIS_COL_GetByKey(getDBID(dbId), V62Facade.TID_LATPALM, cardId, g_stCN.stLAdm.pszGroupName)
    if(data != null && data.length > 0){
      new String(data)
    }else{
      ""
    }
  }

  /**
   * 获取现场卡片
   * @param cardId
   * @return
   */
  override def getLPCard(cardId: String, dbId: Option[String]): LPCard = {
    val gCard = new GLPCARDINFOSTRUCT
    facade.NET_GAFIS_FLIB_Get(getDBID(dbId), V62Facade.TID_LATPALM,cardId, gCard, null, 3)
    galoclpConverter.convertGLPCARDINFOSTRUCT2ProtoBuf(gCard,getGroupName(cardId,dbId))
  }

  /**
   * 更新现场卡片
   * @param lpCard
   * @return
   */
  override def updateLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    val gLPCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lpCard)
    facade.NET_GAFIS_FLIB_Update(getDBID(dbId),
      V62Facade.TID_LATPALM,
      lpCard.getStrCardID, gLPCard)
  }

  /**
   * 删除现场卡片
   * @param cardId
   * @return
   */
  override def delLPCard(cardId: String, dbId: Option[String]): Unit = {
    facade.NET_GAFIS_FLIB_Del(getDBID(dbId), V62Facade.TID_LATPALM, cardId)
  }

  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    facade.NET_GAFIS_FLIB_Exist(getDBID(dbId), V62Facade.TID_LATPALM, cardId)
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

}
