package nirvana.hall.v70.services.remote

import nirvana.hall.protocol.api.FPTProto.Case

/**
 * Created by songpeng on 16/3/7.
 */
trait CaseInfoRemoteService {

  /**
   * 获取案件信息
   * @param caseId
   * @param ip
   * @param port
   * @return
   */
  def getCaseInfo(caseId: String, ip: String, port: String): Case
}
