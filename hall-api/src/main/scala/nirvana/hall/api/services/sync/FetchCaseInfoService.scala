package nirvana.hall.api.services.sync

import nirvana.hall.protocol.api.FPTProto.Case

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/8/22.
 */
trait FetchCaseInfoService {

  /**
   * 获取案件列表
   * @param seq
   * @param size
   * @param dbId
   */
  def fetchCaseId(seq: Long, size: Int, dbId: Option[String] = None): ArrayBuffer[(String, Long)]

  /**
   * 验证读取策略
   * @param caseInfo
   * @param readStrategy
   * @return
   */
  def validateByReadStrategy(caseInfo: Case, readStrategy: String): Boolean = true //TODO
}
