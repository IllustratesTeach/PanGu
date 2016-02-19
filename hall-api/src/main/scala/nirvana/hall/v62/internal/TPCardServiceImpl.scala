package nirvana.hall.v62.internal

import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.protocol.api.TPCardProto._
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.galoctpConverter

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl(facade:V62Facade,config:HallV62Config) extends TPCardService{
  /**
   * 新增捺印卡片
   * @param tPCardAddRequest
   * @return
   */
  override def addTPCard(tPCardAddRequest: TPCardAddRequest): TPCardAddResponse = {
    val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(tPCardAddRequest.getCard)
    facade.NET_GAFIS_FLIB_Add(config.templateTable.dbId.toShort,
      config.templateTable.tableId.toShort,
      tPCardAddRequest.getCard.getStrCardID,tpCard)

    TPCardAddResponse.newBuilder().build()
  }

  /**
   * 删除捺印卡片
   * @param tPCardDelRequest
   * @return
   */
  override def delTPCard(tPCardDelRequest: TPCardDelRequest): TPCardDelResponse = {
    facade.NET_GAFIS_FLIB_Del(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort,
      tPCardDelRequest.getCardId)

    TPCardDelResponse.newBuilder().build()
  }

  /**
   * 获取捺印卡片
   * @param tPCardGetRequest
   * @return
   */
  override def getTPCard(tPCardGetRequest: TPCardGetRequest): TPCardGetResponse = {
    val tp = new GTPCARDINFOSTRUCT
    facade.NET_GAFIS_FLIB_Get(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort,
      tPCardGetRequest.getCardId, tp)
    val tpCard = galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(tp)

    TPCardGetResponse.newBuilder().setCard(tpCard).build()
  }

  /**
   * 更新捺印卡片
   * @param tPCardUpdateRequest
   * @return
   */
  override def updateTPCard(tPCardUpdateRequest: TPCardUpdateRequest): TPCardUpdateResponse = {
    val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(tPCardUpdateRequest.getCard)
    facade.NET_GAFIS_FLIB_Update(config.templateTable.dbId.toShort, config.templateTable.tableId.toShort,
      tPCardUpdateRequest.getCard.getStrCardID, tpCard)

    TPCardUpdateResponse.newBuilder().build()
  }
}
