package nirvana.hall.v62.internal

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter
import nirvana.hall.v62.internal.c.gloclib.gcolnames._

/**
 * Created by songpeng on 16/1/26.
 */
class CaseInfoServiceImpl(facade:V62Facade,config:HallV62Config) extends CaseInfoService{
  /**
   * 新增案件信息
   * @param caseInfo
   * @return
   */
  override def addCaseInfo(caseInfo: Case, dbId: Option[String]): Unit = {
    val gCase= galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseInfo)
    facade.NET_GAFIS_CASE_Add(getDBID(dbId),
      V62Facade.TID_CASE, gCase)
  }

  /**
   * 更新案件信息
   * @param caseInfo
   * @return
   */
  override def updateCaseInfo(caseInfo: Case, dbId: Option[String]): Unit = {
    val gCase = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseInfo)
    facade.NET_GAFIS_CASE_Update(getDBID(dbId), V62Facade.TID_CASE, gCase)
  }

  /**
   * 获取案件信息
   * @param caseId
   * @return
   */
  override def getCaseInfo(caseId: String, dbId: Option[String]): Case= {
    val gCase = facade.NET_GAFIS_CASE_Get(getDBID(dbId), V62Facade.TID_CASE, caseId)
    val caseInfo = galoclpConverter.convertGCASEINFOSTRUCT2Protobuf(gCase)

    caseInfo
  }

  /**
   * 删除案件信息
   * @param caseId
   * @return
   */
  override def delCaseInfo(caseId: String, dbId: Option[String]): Unit = {
    facade.NET_GAFIS_CASE_Del(config.caseTable.dbId.toShort,
      V62Facade.TID_CASE, caseId)
  }

  /**
   * 验证案件编号是否已存在
   * @param caseId
   * @return
   */
  override def isExist(caseId: String, dbId: Option[String]): Boolean = {
    facade.NET_GAFIS_CASE_Exist(getDBID(dbId), V62Facade.TID_CASE, caseId, 0)
  }

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
  override def getCaseIdList(ajno: String, ajlb: String, fadddm: String, mabs: String, xcjb: String, xcdwdm: String, startfadate: String, endfadate: String): Seq[String] = {
    val mapper = Map(
      g_stCN.stLCsID.pszName -> "szKey"
    )
    var statement = "(1=1)"
    //TODO 补全所有查询条件，并支持like
    if(isNonBlank(ajno)){
      statement += " AND (caseid='%s')".format(ajno)
    }

    val caseIdList = facade.queryV62Table[GAKEYSTRUCT](V62Facade.DBID_LP_DEFAULT, V62Facade.TID_CASE, mapper, Option(statement), 256)//最多256
    caseIdList.map(_.szKey)
  }
  def isNonBlank(string: String):Boolean = string != null && string.length >0

  /**
   * 获取DBID
   * @param dbId
   */
  private def getDBID(dbId: Option[String]):Short={
    if(dbId == None){
      config.caseTable.dbId.toShort
    }else{
      dbId.get.toShort
    }
  }
}
