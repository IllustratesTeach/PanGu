package nirvana.hall.api.services

import nirvana.hall.protocol.v62.lp.CaseProto._

/**
 * Created by songpeng on 16/1/26.
 */
trait CaseInfoService {

  /**
   * 新增案件信息
   * @param caseAddRequest
   * @return
   */
  def addCaseInfo(caseAddRequest: CaseAddRequest): CaseAddResponse

  /**
   * 删除案件信息
   * @param caseDelRequest
   * @return
   */
  def delCaseInfo(caseDelRequest: CaseDelRequest): CaseDelResponse

  /**
   * 更新案件信息
   * @param caseUpdateRequest
   * @return
   */
  def updateCaseInfo(caseUpdateRequest: CaseUpdateRequest): CaseUpdateResponse

  /**
   * 获取案件信息
   * @param caseGetRequest
   * @return
   */
  def getCaseInfo(caseGetRequest: CaseGetRequest): CaseGetResponse
}
