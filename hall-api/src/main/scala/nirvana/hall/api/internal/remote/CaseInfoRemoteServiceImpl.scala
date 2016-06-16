package nirvana.hall.api.internal.remote

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.remote.CaseInfoRemoteService
import nirvana.hall.protocol.api.CaseProto.{CaseAddRequest, CaseGetRequest, CaseGetResponse}
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
    if(response.getStatus == CommandStatus.OK){
      response.getExtension(CaseGetResponse.cmd).getCase
    }else{
      error("remote get caseInfo message:{}", response.getMsg)
      throw new RuntimeException(response.getMsg)
    }
  }

  /**
   * 添加案件信息
   * @param caseInfo
   * @return
   */
  override def addCaseInfo(caseInfo: Case, url: String): Boolean = {
    info("remote add caseInfo [caseId:{},url:{}]", caseInfo.getStrCaseID, url)
    val request = CaseAddRequest.newBuilder().setCase(caseInfo)
    val response = rpcHttpClient.call(url, CaseAddRequest.cmd, request.build())
    if(response.getStatus == CommandStatus.OK){
      true
    }else{
      error("remote add caseInfo message:{}", response.getMsg)
      false
    }
  }
}
