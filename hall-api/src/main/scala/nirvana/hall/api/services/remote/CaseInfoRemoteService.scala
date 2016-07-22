package nirvana.hall.api.services.remote

import nirvana.hall.protocol.api.FPTProto.Case

/**
 * Created by songpeng on 16/3/7.
 */
trait CaseInfoRemoteService {

  /**
   * 获取案件信息
   * @param caseId
   * @param url
   * @return
   */
  def getCaseInfo(caseId: String, url: String, headerMap: Map[String, String] = Map()): Case

  /**
   * 添加案件信息
   * @param caseInfo
   * @param url
   * @return
   */
  def addCaseInfo(caseInfo: Case, url: String)

  /**
   * 更新案件信息
   * @param caseInfo
   * @param url
   */
  def updateCaseInfo(caseInfo: Case, url: String)

  /**
   * 删除案件信息
   * @param caseId
   * @param url
   */
  def deleteCaseInfo(caseId: String, url: String)

  /**
   * 案件编号是否存在
   * @param caseId
   * @param url
   * @param headerMap
   */
  def isExist(caseId: String, url: String, headerMap: Map[String, String] = Map()): Boolean
}
