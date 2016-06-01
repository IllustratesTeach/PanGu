package nirvana.hall.v70.internal

import java.util.Date

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.api.CaseProto._
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.GafisCase
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class CaseInfoServiceImpl extends CaseInfoService{
  /**
   * 新增案件信息
   * @param caseAddRequest
   * @return
   */
  @Transactional
  override def addCaseInfo(caseAddRequest: CaseAddRequest): CaseAddResponse = {
    val gafisCase = ProtobufConverter.convertCase2GafisCase(caseAddRequest.getCase)
    gafisCase.inputpsn = Gafis70Constants.INPUTPSN
    gafisCase.inputtime = new Date()
    gafisCase.deletag = Gafis70Constants.DELETAG_USE
    gafisCase.save()

    CaseAddResponse.newBuilder().build()
  }

  /**
   * 更新案件信息
   * @param caseUpdateRequest
   * @return
   */
  @Transactional
  override def updateCaseInfo(caseUpdateRequest: CaseUpdateRequest): CaseUpdateResponse = {
    val gafisCase = ProtobufConverter.convertCase2GafisCase(caseUpdateRequest.getCase)
    gafisCase.modifiedpsn = Gafis70Constants.INPUTPSN
    gafisCase.modifiedtime = new Date()
    gafisCase.deletag = Gafis70Constants.DELETAG_USE
    gafisCase.save()

    CaseUpdateResponse.newBuilder().build()
  }

  /**
   * 获取案件信息
   * @param caseGetRequest
   * @return
   */
  override def getCaseInfo(caseGetRequest: CaseGetRequest): CaseGetResponse = {
    val gafisCase = GafisCase.findOption(caseGetRequest.getCaseId)
    if(gafisCase.isEmpty){
      throw new RuntimeException("记录不存在!");
    }
    val caseInfo = ProtobufConverter.convertGafisCase2Case(gafisCase.get)

    CaseGetResponse.newBuilder().setCase(caseInfo).build()
  }

  /**
   * 删除案件信息
   * @param caseDelRequest
   * @return
   */
  @Transactional
  override def delCaseInfo(caseDelRequest: CaseDelRequest): CaseDelResponse = {
    GafisCase.find(caseDelRequest.getCaseId).delete
    CaseDelResponse.newBuilder().build()
  }

  /**
   * 验证案件编号是否已存在
   * @param caseId
   * @return
   */
  override def isExist(caseId: String): Boolean = {
    GafisCase.findOption(caseId).nonEmpty
  }
}
