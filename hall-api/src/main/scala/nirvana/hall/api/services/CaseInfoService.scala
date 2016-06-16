package nirvana.hall.api.services

import nirvana.hall.protocol.api.FPTProto.Case
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
trait CaseInfoService {

  /**
   * 新增案件信息
   * @param caseInfo
   * @return
   */
  @Transactional
  def addCaseInfo(caseInfo: Case): Unit

  /**
   * 删除案件信息
   * @param caseId
   * @return
   */
  @Transactional
  def delCaseInfo(caseId: String): Unit

  /**
   * 更新案件信息
   * @param caseInfo
   * @return
   */
  @Transactional
  def updateCaseInfo(caseInfo: Case): Unit

  /**
   * 获取案件信息
   * @param caseId
   * @return
   */
  def getCaseInfo(caseId: String): Case

  /**
   * 验证案件编号是否已存在
   * @param caseId
   * @return
   */
  def isExist(caseId: String): Boolean
}
