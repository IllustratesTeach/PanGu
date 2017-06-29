package nirvana.hall.api.services.remote

import nirvana.hall.protocol.api.FPTProto.TPCard

/**
 * 发送远程捺印卡信息
 * Created by songpeng on 16/3/4.
 */
trait TPCardRemoteService {

  /**
   * 获取捺印卡片
   * @return
   */
  def getTPCard(personId: String, url: String, dbId: String = "", headerMap: Map[String, String] = Map()): Option[TPCard]

  /**
   * 添加捺印卡片
   * @param tpCard
   * @param url
   */
  def addTPCard(tpCard: TPCard, url: String, dbId: String = "", headerMap: Map[String, String] = Map())

  /**
   * 更新捺印卡片
   * @param tPCard
   * @param url
   */
  def updateTPCard(tPCard: TPCard, url: String, dbId: String = "", headerMap: Map[String, String] = Map())

  /**
   * 删除捺印卡片
   * @param cardId
   */
  def deleteTPCard(cardId: String, url: String, dbId: String = "", headerMap: Map[String, String] = Map())

  /**
   * 验证编号是否已存在
   * @param personId
   * @param url
   * @return
   */
  def isExist(personId: String, url: String, dbId: String = "", headerMap: Map[String, String] = Map()): Boolean
}
