package nirvana.hall.v62.internal

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter

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
