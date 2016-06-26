package nirvana.hall.api.internal.remote

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.remote.CaseInfoRemoteService
import nirvana.hall.protocol.api.CaseProto._
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.support.services.RpcHttpClient

/**
 * Created by songpeng on 16/3/7.
 */
class CaseInfoRemoteServiceImpl(rpcHttpClient: RpcHttpClient) extends CaseInfoRemoteService with LoggerSupport{
  /**
   * 获取案件信息
   * @param caseId
   * @param url
   * @return
   */
  override def getCaseInfo(caseId: String, url: String): Case = {
    info("remote get caseInfo [caseId:{},url:{}]", caseId, url)
    val request = CaseGetRequest.newBuilder().setCaseId(caseId)
    val response = rpcHttpClient.call(url, CaseGetRequest.cmd, request.build())
    response.getExtension(CaseGetResponse.cmd).getCase
  }

  /**
   * 添加案件信息
   * @param caseInfo
   * @return
   */
  override def addCaseInfo(caseInfo: Case, url: String) = {
    info("remote add caseInfo [caseId:{},url:{}]", caseInfo.getStrCaseID, url)
    val request = CaseAddRequest.newBuilder().setCase(caseInfo)
    rpcHttpClient.call(url, CaseAddRequest.cmd, request.build())
  }

  /**
   * 更新案件信息
   * @param caseInfo
   * @param url
   */
  override def updateCaseInfo(caseInfo: Case, url: String): Unit = {
    info("remote update caseInfo [caseId:{},url:{}]", caseInfo.getStrCaseID, url)
    val request = CaseUpdateRequest.newBuilder().setCase(caseInfo)
    rpcHttpClient.call(url, CaseUpdateRequest.cmd, request.build())
  }

  /**
   * 删除案件信息
   * @param caseId
   * @param url
   */
  override def deleteCaseInfo(caseId: String, url: String): Unit = {
    info("remote delete caseInfo [caseId:{},url:{}]", caseId, url)
    val request = CaseDelRequest.newBuilder().setCaseId(caseId)
    rpcHttpClient.call(url, CaseDelRequest.cmd, request.build())
  }
}
