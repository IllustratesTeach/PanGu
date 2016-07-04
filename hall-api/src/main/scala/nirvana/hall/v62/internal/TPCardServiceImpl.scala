package nirvana.hall.v62.internal

import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.galoctpConverter
import nirvana.hall.v62.services.DictCodeConverter

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl(facade:V62Facade,config:HallV62Config) extends TPCardService{
  /**
   * 新增捺印卡片
   * @param tPCard
   * @return
   */
  override def addTPCard(tPCard: TPCard, dbConfig: DBConfig = DBConfig(Left(config.templateTable.dbId.toShort), Option(config.templateTable.tableId.toShort))): Unit = {
    val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(tPCard)
    facade.NET_GAFIS_FLIB_Add(dbConfig.dbId.left.get,
      dbConfig.tableId.get,
      tPCard.getStrCardID,tpCard)
  }

  /**
   * 删除捺印卡片
   * @param cardId
   * @return
   */
  def delTPCard(cardId: String, dBConfig: DBConfig = DBConfig(Left(config.templateTable.dbId.toShort), Option(config.templateTable.tableId.toShort))): Unit ={
    facade.NET_GAFIS_FLIB_Del(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort, cardId)
  }

  /**
   * 更新捺印卡片
   * @param tPCard
   * @return
   */
  override def updateTPCard(tPCard: TPCard, dBConfig: DBConfig = DBConfig(Left(config.templateTable.dbId.toShort), Option(config.templateTable.tableId.toShort))): Unit = {
    val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(tPCard)
    facade.NET_GAFIS_FLIB_Update(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort,
      tPCard.getStrCardID, tpCard)
  }

  /**
   * 验证卡号是否已存在
   * @param cardId
   * @return
   */
  override def isExist(cardId: String, dBConfig: DBConfig  = DBConfig(Left(config.templateTable.dbId.toShort), Option(config.templateTable.tableId.toShort))): Boolean = {
    throw new UnsupportedOperationException
  }

  /**
   * 获取捺印卡信息
   * @param cardId
   * @return
   */
  override def getTPCard(cardId: String, dbConfig: DBConfig = DBConfig(Left(config.templateTable.dbId.toShort), Option(config.templateTable.tableId.toShort))): TPCard = {
    val tp = new GTPCARDINFOSTRUCT
    facade.NET_GAFIS_FLIB_Get(dbConfig.dbId.left.get, dbConfig.tableId.get,
      cardId, tp, null, 3)
    val tpCard = galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(tp)
    //数据校验
    DictCodeConverter.convertTPCard6to7(tpCard)

    tpCard
  }
}
