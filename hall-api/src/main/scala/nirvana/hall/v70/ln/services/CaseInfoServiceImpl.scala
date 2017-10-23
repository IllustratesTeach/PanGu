package nirvana.hall.v70.ln.services


import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic03Rec
import nirvana.hall.protocol.api.FPTProto.Case

/**
  * Created by songpeng on 2017/6/29.
  */
class CaseInfoServiceImpl extends CaseInfoService{
  /**
    * 新增案件信息
    *
    * @param caseInfo
    * @return
    */
  override def addCaseInfo(caseInfo: Case, dbId: Option[String]): Unit = ???

  /**
    * 更新案件信息
    *
    * @param caseInfo
    * @return
    */
override def updateCaseInfo(caseInfo: Case, dbId: Option[String]): Unit = ???

  /**
    * 获取案件信息
    *
    * @param caseId
    * @return
   */
  override def getCaseInfo(caseId: String, dbId: Option[String]): Case = ???

  /**
    * 查询案件编号列表
    *
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
  override def getFPT4Logic03RecList(ajno: String, ajlb: String, fadddm: String, mabs: String, xcjb: String, xcdwdm: String, startfadate: String, endfadate: String): Seq[Logic03Rec] = ???

  /**
    * 验证案件编号是否已存在
    *
    * @param caseId
    * @return
    */
  override def isExist(caseId: String, dbId: Option[String]): Boolean = ???

  /**
    * 删除案件信息
    *
    * @param caseId
    * @return
    */
  override def delCaseInfo(caseId: String, dbId: Option[String]): Unit = ???
}
