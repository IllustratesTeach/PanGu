package nirvana.hall.api.internal.remote

import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.api.services.remote.CaseInfoRemoteService
import nirvana.hall.protocol.api.CaseProto.{CaseGetRequest, CaseGetResponse}
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.support.services.RpcHttpClient

/**
 * Created by songpeng on 16/3/7.
 */
class CaseInfoRemoteServiceImpl(rpcHttpClient: RpcHttpClient) extends CaseInfoRemoteService{
  /**
   * 获取案件信息
   * @param caseId
   * @param ip
   * @param port
   * @return
   */
  override def getCaseInfo(caseId: String, ip: String, port: String): Case = {
    val request = CaseGetRequest.newBuilder().setCaseId(caseId)
    val response = rpcHttpClient.call("http://"+ip+":"+port, CaseGetRequest.cmd, request.build())
    if(response.getStatus == CommandStatus.OK){
      response.getExtension(CaseGetResponse.cmd).getCase
    }else{
      throw new RuntimeException(response.getMsg)
    }
  }
}
