package nirvana.hall.v70.internal

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.v62.lp.CaseProto._
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.GafisCase

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
    val gafisCase = ProtobufConverter.convertCase2GafisCase(caseAddRequest.getCase)
    gafisCase.save()

    CaseAddResponse.newBuilder().build()
  }

  /**
   * 更新案件信息
   * @param caseUpdateRequest
   * @return
   */
  override def updateCaseInfo(caseUpdateRequest: CaseUpdateRequest): CaseUpdateResponse = {
    val gafisCase = ProtobufConverter.convertCase2GafisCase(caseUpdateRequest.getCase)
    gafisCase.save()

    CaseUpdateResponse.newBuilder().build()
  }

  /**
   * 获取案件信息
   * @param caseGetRequest
   * @return
   */
  override def getCaseInfo(caseGetRequest: CaseGetRequest): CaseGetResponse = {
    val gafisCase = GafisCase.find(caseGetRequest.getCaseId)
    val caseInfo = ProtobufConverter.convertGafisCase2Case(gafisCase)

    CaseGetResponse.newBuilder().setCase(caseInfo).build()
  }

  /**
   * 删除案件信息
   * @param caseDelRequest
   * @return
   */
  override def delCaseInfo(caseDelRequest: CaseDelRequest): CaseDelResponse = {
    val gafisCase = GafisCase.find(caseDelRequest.getCaseId)
    gafisCase.delete()

    CaseDelResponse.newBuilder().build()
  }
}
