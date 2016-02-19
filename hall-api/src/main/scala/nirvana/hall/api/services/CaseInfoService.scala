package nirvana.hall.api.services

import nirvana.hall.protocol.api.CaseProto._
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
trait CaseInfoService {

  /**
   * 新增案件信息
   * @param caseAddRequest
   * @return
   */
  @Transactional
  def addCaseInfo(caseAddRequest: CaseAddRequest): CaseAddResponse

  /**
   * 删除案件信息
   * @param caseDelRequest
   * @return
   */
  @Transactional
  def delCaseInfo(caseDelRequest: CaseDelRequest): CaseDelResponse

  /**
   * 更新案件信息
   * @param caseUpdateRequest
   * @return
   */
  @Transactional
  def updateCaseInfo(caseUpdateRequest: CaseUpdateRequest): CaseUpdateResponse

  /**
   * 获取案件信息
   * @param caseGetRequest
   * @return
   */
  def getCaseInfo(caseGetRequest: CaseGetRequest): CaseGetResponse
}
