package nirvana.hall.v62.internal

import nirvana.hall.api.config.DBConfig
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
  override def addLPCard(lpCard: LPCard, dBConfig: DBConfig = DBConfig(Left(config.latentTable.dbId.toShort), Option(config.latentTable.tableId.toShort))): Unit = {
    //转换为c的结构
    val gLPCard= galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lpCard)
    //调用实现方法
    facade.NET_GAFIS_FLIB_Add(dBConfig.dbId.left.get,
      dBConfig.tableId.get,
      lpCard.getStrCardID, gLPCard)
  }

  /**
   * 获取现场卡片
   * @param cardId
   * @return
   */
  override def getLPCard(cardId: String, dBConfig: DBConfig = DBConfig(Left(config.latentTable.dbId.toShort), Option(config.latentTable.tableId.toShort))): LPCard = {
    val gCard = new GLPCARDINFOSTRUCT
    facade.NET_GAFIS_FLIB_Get(dBConfig.dbId.left.get, dBConfig.tableId.get, cardId, gCard, null, 3)
    val card = galoclpConverter.convertGLPCARDINFOSTRUCT2ProtoBuf(gCard)
    card.toBuilder.setStrCardID(cardId).build()
  }

  /**
   * 更新现场卡片
   * @param lpCard
   * @return
   */
  override def updateLPCard(lpCard: LPCard, dBConfig: DBConfig = DBConfig(Left(config.latentTable.dbId.toShort), Option(config.latentTable.tableId.toShort))): Unit = {
    val gLPCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lpCard)
    facade.NET_GAFIS_FLIB_Update(dBConfig.dbId.left.get,
      dBConfig.tableId.get,
      lpCard.getStrCardID, gLPCard)
  }

  /**
   * 删除现场卡片
   * @param cardId
   * @return
   */
  override def delLPCard(cardId: String): Unit = {
    facade.NET_GAFIS_FLIB_Del(config.latentTable.dbId.toShort, config.latentTable.tableId.toShort, cardId)
  }

  override def isExist(cardId: String, dBConfig: DBConfig = DBConfig(Left(config.latentTable.dbId.toShort), Option(config.latentTable.tableId.toShort))): Boolean = {
    throw new UnsupportedOperationException
  }
}
