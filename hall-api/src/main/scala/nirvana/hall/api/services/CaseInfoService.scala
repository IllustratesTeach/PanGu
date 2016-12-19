package nirvana.hall.api.services

import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic03Rec
import nirvana.hall.protocol.api.FPTProto.Case
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
trait CaseInfoService {

  /**
   * 新增案件信息
   * @param caseInfo
   * @return
   */
  @Transactional
  def addCaseInfo(caseInfo: Case, dbId: Option[String] = None): Unit

  /**
   * 删除案件信息
   * @param caseId
   * @return
   */
  @Transactional
  def delCaseInfo(caseId: String, dbId: Option[String] = None): Unit

  /**
   * 更新案件信息
   * @param caseInfo
   * @return
   */
  @Transactional
  def updateCaseInfo(caseInfo: Case, dbId: Option[String] = None): Unit

  /**
   * 获取案件信息
   * @param caseId
   * @return
   */
  def getCaseInfo(caseId: String, dbId: Option[String] = None): Case

  /**
   * 验证案件编号是否已存在
   * @param caseId
   * @return
   */
  def isExist(caseId: String, dbId: Option[String] = None): Boolean

  /**
    * 查询案件编号列表
    * @param ajno        案件编号
    * @param ajlb        案件类别
    * @param fadddm      发案地代码
    * @param mabs        命案标识
    * @param xcjb        协查级别
    * @param xcdwdm      查询单位代码
    * @param startfadate 开始时间（检索发案时间，时间格式YYYYMMDD）
    * @param endfadate   结束时间（检索发案时间，时间格式YYYYMMDD）
    * @return
    */
  def getFPT4Logic03RecList(ajno: String, ajlb: String, fadddm: String, mabs: String, xcjb: String, xcdwdm: String, startfadate: String, endfadate: String): Seq[Logic03Rec]
}
