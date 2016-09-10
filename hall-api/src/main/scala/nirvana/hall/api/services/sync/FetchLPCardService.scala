package nirvana.hall.api.services.sync

import nirvana.hall.protocol.api.FPTProto.LPCard

/**
 * Created by songpeng on 16/8/22.
 */
trait FetchLPCardService {
  /**
   * 获取案件列表
   * @param seq
   * @param size
   * @param dbId
   */
  def fetchCardId(seq: Long, size: Int, dbId: Option[String] = None): Seq[(String, Long)]

  /**
   * 验证读取策略
   * @param lPCard
   * @param readStrategy
   * @return
   */
  def validateByReadStrategy(lPCard: LPCard, readStrategy: String): Boolean = true //TODO
}
