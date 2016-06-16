package nirvana.hall.v62.internal

import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.galoctpConverter

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl(facade:V62Facade,config:HallV62Config) extends TPCardService{
  /**
   * 新增捺印卡片
   * @param tPCard
   * @return
   */
  override def addTPCard(tPCard: TPCard): Unit = {
    val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(tPCard)
    facade.NET_GAFIS_FLIB_Add(config.templateTable.dbId.toShort,
      config.templateTable.tableId.toShort,
      tPCard.getStrCardID,tpCard)
  }

  /**
   * 删除捺印卡片
   * @param cardId
   * @return
   */
  def delTPCard(cardId: String): Unit ={
    facade.NET_GAFIS_FLIB_Del(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort, cardId)
  }

  /**
   * 更新捺印卡片
   * @param tPCard
   * @return
   */
  override def updateTPCard(tPCard: TPCard): Unit = {
    val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(tPCard)
    facade.NET_GAFIS_FLIB_Update(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort,
      tPCard.getStrCardID, tpCard)
  }

  /**
   * 验证卡号是否已存在
   * @param cardId
   * @return
   */
  override def isExist(cardId: String): Boolean = {
    throw new UnsupportedOperationException
  }

  /**
   * 获取捺印卡信息
   * @param cardId
   * @return
   */
  override def getTPCard(cardId: String): TPCard = {
    val tp = new GTPCARDINFOSTRUCT
    facade.NET_GAFIS_FLIB_Get(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort,
      cardId, tp, null, 3)

    galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(tp)
  }
}
