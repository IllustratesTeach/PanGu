package nirvana.hall.v62.internal

import nirvana.hall.api.services.LPCardService
import nirvana.hall.c.services.gloclib.galoclp.GLPCARDINFOSTRUCT
import nirvana.hall.protocol.api.LPCardProto._
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
 * Created by songpeng on 16/1/26.
 */
class LPCardServiceImpl(facade:V62Facade,config:HallV62Config) extends LPCardService{
  /**
   * 新增现场卡片
   * @param lPCardAddRequest
   * @return
   */
  override def addLPCard(lPCardAddRequest: LPCardAddRequest): LPCardAddResponse = {
    //转换为c的结构
    val lpCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lPCardAddRequest.getCard)
    //调用实现方法
    facade.NET_GAFIS_FLIB_Add(config.latentTable.dbId.toShort,
      config.latentTable.tableId.toShort,
      lPCardAddRequest.getCard.getStrCardID, lpCard)

    LPCardAddResponse.newBuilder().build()
  }

  /**
   * 获取现场卡片
   * @param lPCardGetRequest
   * @return
   */
  override def getLPCard(lPCardGetRequest: LPCardGetRequest): LPCardGetResponse = {
    val cardId = lPCardGetRequest.getCardId
    val gCard = new GLPCARDINFOSTRUCT
    facade.NET_GAFIS_FLIB_Get(config.latentTable.dbId.toShort, config.latentTable.tableId.toShort, cardId, gCard)
    val card = galoclpConverter.convertGLPCARDINFOSTRUCT2ProtoBuf(gCard)
    card.toBuilder.setStrCardID(cardId)

    LPCardGetResponse.newBuilder().setCard(card).build()
  }

  /**
   * 更新现场卡片
   * @param lPCardUpdateRequest
   * @return
   */
  override def updateLPCard(lPCardUpdateRequest: LPCardUpdateRequest): LPCardUpdateResponse = {
    val lpCard = galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(lPCardUpdateRequest.getCard)
    facade.NET_GAFIS_FLIB_Update(config.latentTable.dbId.toShort,
      config.latentTable.tableId.toShort,
      lPCardUpdateRequest.getCard.getStrCardID, lpCard)

    LPCardUpdateResponse.newBuilder().build()
  }

  /**
   * 删除现场卡片
   * @param lPCardDelRequest
   * @return
   */
  override def delLPCard(lPCardDelRequest: LPCardDelRequest): LPCardDelResponse = {
    val cardId = lPCardDelRequest.getCardId
    facade.NET_GAFIS_FLIB_Del(config.latentTable.dbId.toShort, config.latentTable.tableId.toShort, cardId)

    LPCardDelResponse.newBuilder().build()
  }
}
