package nirvana.hall.v62.internal

import nirvana.hall.api.config.DBConfig
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
  override def addCaseInfo(caseInfo: Case, dBConfig: DBConfig = DBConfig(Left(config.caseTable.dbId.toShort), Option(config.caseTable.tableId.toShort))): Unit = {
    val gCase= galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseInfo)
    facade.NET_GAFIS_CASE_Add(config.caseTable.dbId.toShort,
      config.caseTable.tableId.toShort, gCase)
  }

  /**
   * 更新案件信息
   * @param caseInfo
   * @return
   */
  override def updateCaseInfo(caseInfo: Case, dBConfig: DBConfig = DBConfig(Left(config.caseTable.dbId.toShort), Option(config.caseTable.tableId.toShort))): Unit = {
    val gCase = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseInfo)
    facade.NET_GAFIS_CASE_Update(config.caseTable.dbId.toShort, config.caseTable.tableId.toShort, gCase)
  }

  /**
   * 获取案件信息
   * @param caseId
   * @return
   */
  override def getCaseInfo(caseId: String, dBConfig: DBConfig): Case= {
    val dbConfig = if(dBConfig != null){
      dBConfig
    }else{
      DBConfig(Left(config.caseTable.dbId.toShort), Option(config.caseTable.tableId.toShort))
    }
    val gCase = facade.NET_GAFIS_CASE_Get(dbConfig.dbId.left.get, dbConfig.tableId.get, caseId)
    val caseInfo = galoclpConverter.convertGCASEINFOSTRUCT2Protobuf(gCase)

    caseInfo
  }

  /**
   * 删除案件信息
   * @param caseId
   * @return
   */
  override def delCaseInfo(caseId: String): Unit = {
    facade.NET_GAFIS_CASE_Del(config.caseTable.dbId.toShort,
      config.caseTable.tableId.toShort, caseId)
  }

  /**
   * 验证案件编号是否已存在
   * @param caseId
   * @return
   */
  override def isExist(caseId: String, dBConfig: DBConfig): Boolean = {
    val dbConfig = if(dBConfig != null){
      dBConfig
    }else{
      DBConfig(Left(config.caseTable.dbId.toShort), Option(config.caseTable.tableId.toShort))
    }

    facade.NET_GAFIS_CASE_Exist(dbConfig.dbId.left.get, dbConfig.tableId.get, caseId, 0)
  }
}
