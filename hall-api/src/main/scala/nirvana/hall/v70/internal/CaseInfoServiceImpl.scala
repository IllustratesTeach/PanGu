package nirvana.hall.v70.internal

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.v62.lp.CaseProto._

/**
 * Created by songpeng on 16/1/26.
 */
class CaseInfoServiceImpl extends CaseInfoService{
  /**
   * 新增案件信息
   * @param caseAddRequest
   * @return
   */
  override def addCaseInfo(caseAddRequest: CaseAddRequest): CaseAddResponse = {
    throw new UnsupportedOperationException
  }

  /**
   * 更新案件信息
   * @param caseUpdateRequest
   * @return
   */
  override def updateCaseInfo(caseUpdateRequest: CaseUpdateRequest): CaseUpdateResponse = {
    throw new UnsupportedOperationException
  }

  /**
   * 获取案件信息
   * @param caseGetRequest
   * @return
   */
  override def getCaseInfo(caseGetRequest: CaseGetRequest): CaseGetResponse = {
    throw new UnsupportedOperationException
  }

  /**
   * 删除案件信息
   * @param caseDelRequest
   * @return
   */
  override def delCaseInfo(caseDelRequest: CaseDelRequest): CaseDelResponse = {
    throw new UnsupportedOperationException
  }
}
