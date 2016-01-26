package nirvana.hall.v62.internal

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.v62.lp.CaseProto._
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

/**
 * Created by songpeng on 16/1/26.
 */
class CaseInfoServiceImpl(facade:V62Facade,config:HallV62Config) extends CaseInfoService{
  /**
   * 新增案件信息
   * @param caseAddRequest
   * @return
   */
  override def addCaseInfo(caseAddRequest: CaseAddRequest): CaseAddResponse = {
    val caseInfo = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseAddRequest.getCase)
    facade.NET_GAFIS_CASE_Add(config.caseTable.dbId.toShort,
      config.caseTable.tableId.toShort, caseInfo)

    CaseAddResponse.newBuilder().build()
  }

  /**
   * 更新案件信息
   * @param caseUpdateRequest
   * @return
   */
  override def updateCaseInfo(caseUpdateRequest: CaseUpdateRequest): CaseUpdateResponse = {
    val caseInfo = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseUpdateRequest.getCase)
    facade.NET_GAFIS_CASE_Update(config.caseTable.dbId.toShort, config.caseTable.tableId.toShort, caseInfo)

    CaseUpdateResponse.newBuilder().build()
  }

  /**
   * 获取案件信息
   * @param caseGetRequest
   * @return
   */
  override def getCaseInfo(caseGetRequest: CaseGetRequest): CaseGetResponse = {
    val caseId = caseGetRequest.getCaseId
    val gCase = facade.NET_GAFIS_CASE_Get(config.caseTable.dbId.toShort,
      config.caseTable.tableId.toShort, caseId)
    val caseInfo = galoclpConverter.convertGCASEINFOSTRUCT2Protobuf(gCase)

    CaseGetResponse.newBuilder().setCase(caseInfo).build()
  }

  /**
   * 删除案件信息
   * @param caseDelRequest
   * @return
   */
  override def delCaseInfo(caseDelRequest: CaseDelRequest): CaseDelResponse = {
    val caseId = caseDelRequest.getCaseId
    facade.NET_GAFIS_CASE_Del(config.caseTable.dbId.toShort,
      config.caseTable.tableId.toShort, caseId)

    CaseDelResponse.newBuilder().build()
  }
}
