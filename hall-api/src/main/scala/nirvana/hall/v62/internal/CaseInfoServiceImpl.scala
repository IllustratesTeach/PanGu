package nirvana.hall.v62.internal

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic03Rec
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.V62SqlHelper
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter
import nirvana.hall.v62.internal.c.gloclib.gcolnames._
import nirvana.hall.v62.internal.c.vo.LPCase

import scala.collection.mutable.ArrayBuffer

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
    val caseInfoHandled = caseInfo.toBuilder.setStrCaseID(galoclpConverter.dropCaseNoHeadLetter(caseInfo.getStrCaseID)).build
    val gCase= galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseInfoHandled)
    facade.NET_GAFIS_CASE_Add(getDBID(dbId),
      V62Facade.TID_CASE, gCase)
  }

  /**
   * 更新案件信息
   * @param caseInfo
   * @return
   */
  override def updateCaseInfo(caseInfo: Case, dbId: Option[String]): Unit = {
    val caseInfoHandled = caseInfo.toBuilder.setStrCaseID(galoclpConverter.dropCaseNoHeadLetter(caseInfo.getStrCaseID)).build
    val gCase = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseInfoHandled)
    facade.NET_GAFIS_CASE_Update(getDBID(dbId), V62Facade.TID_CASE, gCase)
  }

  /**
   * 获取案件信息
   * @param caseId
   * @return
   */
  override def getCaseInfo(caseId: String, dbId: Option[String]): Case= {
    val gCase = facade.NET_GAFIS_CASE_Get(getDBID(dbId), V62Facade.TID_CASE, galoclpConverter.dropCaseNoHeadLetter(caseId))
    val caseInfo = galoclpConverter.convertGCASEINFOSTRUCT2Protobuf(gCase)
    caseInfo.toBuilder.setStrCaseID(galoclpConverter.appendCaseNoHeadLetter(caseInfo.getStrCaseID)).build
    caseInfo
  }

  /**
   * 删除案件信息
   * @param caseId
   * @return
   */
  override def delCaseInfo(caseId: String, dbId: Option[String]): Unit = {
    facade.NET_GAFIS_CASE_Del(config.caseTable.dbId.toShort,
      V62Facade.TID_CASE, galoclpConverter.dropCaseNoHeadLetter(caseId))
  }

  /**
   * 验证案件编号是否已存在
   * @param caseId
   * @return
   */
  override def isExist(caseId: String, dbId: Option[String]): Boolean = {
    facade.NET_GAFIS_CASE_Exist(getDBID(dbId), V62Facade.TID_CASE,galoclpConverter.dropCaseNoHeadLetter(caseId), 0)
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
  override def getFPT4Logic03RecList(ajno: String, ajlb: String, fadddm: String, mabs: String, xcjb: String, xcdwdm: String, startfadate: String, endfadate: String): Seq[Logic03Rec] = {
    val LCaseText = g_stCN.stLCaseText

    val mapper = Map(
      g_stCN.stLCsID.pszName -> "caseId",
      g_stCN.stLFingerCount.pszName -> "fingerCount",
      LCaseText.pszCaseClass1Code -> "caseClass1Code",
      LCaseText.pszCaseClass2Code -> "caseClass2Code",
      LCaseText.pszCaseClass3Code -> "caseClass3Code",
      LCaseText.pszCaseOccurPlaceCode -> "occurPlaceCode",
      LCaseText.pszSuperviseLevel -> "assistLevel",
      LCaseText.pszCaseOccurDate -> "occurDate",
      LCaseText.pszCaseState -> "caseStatus",
      LCaseText.pszCaseOccurPlaceTail -> "occurPlace",
      LCaseText.pszExtractDate -> "extractDate",
      LCaseText.pszExtractor1 -> "extractor",
      LCaseText.pszExtractUnitCode -> "extractUnitCode",
      LCaseText.pszExtractUnitNameTail -> "extractUnitName",
      LCaseText.pszIllicitMoney -> "amount",
      LCaseText.pszPremium -> "bonus",
      LCaseText.pszSuspiciousArea1Code -> "suspiciousArea1Code",
      LCaseText.pszSuspiciousArea2Code -> "suspiciousArea2Code",
      LCaseText.pszSuspiciousArea3Code -> "suspiciousArea3Code"
    )
    var statement = "(1=1)"
    statement += V62SqlHelper.likeSQL(g_stCN.stLCsID.pszName, ajno)
    statement += V62SqlHelper.andSQL(LCaseText.pszCaseOccurPlaceCode, fadddm)
    statement += V62SqlHelper.andSQL(LCaseText.pszSuperviseLevel,xcjb)
    statement += V62SqlHelper.andSQL(LCaseText.pszExtractUnitCode,xcdwdm)
    if(V62SqlHelper.isNonBlank(ajlb)){
      statement += " AND (%s = '%s' OR %s = '%s' OR %s = '%s')"
        .format(LCaseText.pszCaseClass1Code,ajlb,LCaseText.pszCaseClass2Code,ajlb, LCaseText.pszCaseClass3Code, ajlb)
    }
    if(V62SqlHelper.isNonBlank(startfadate)){
      statement += " AND (%s >= %s)".format(LCaseText.pszCaseOccurDate, startfadate.substring(0,8))
    }
    if(V62SqlHelper.isNonBlank(endfadate)){
      statement += " AND (%s <= %s)".format(LCaseText.pszCaseOccurDate, endfadate.substring(0,8))
    }

    val lpCaseList = facade.queryV62Table[LPCase](V62Facade.DBID_LP_DEFAULT, V62Facade.TID_CASE, mapper, Option(statement), 256)//最多256
    val logic03RecList = new ArrayBuffer[Logic03Rec]()
    lpCaseList.foreach{lpCase =>
      val logic03Rec = new Logic03Rec
      logic03Rec.caseId = lpCase.caseId
      logic03Rec.fingerCount = lpCase.fingerCount.toString
      logic03Rec.caseClass1Code = lpCase.caseClass1Code
      logic03Rec.caseClass2Code = lpCase.caseClass2Code
      logic03Rec.caseClass3Code = lpCase.caseClass3Code
      logic03Rec.occurPlaceCode = lpCase.occurPlaceCode
      logic03Rec.occurDate = lpCase.occurDate
      logic03Rec.assistLevel = lpCase.assistLevel
      logic03Rec.caseStatus = lpCase.caseStatus
      logic03Rec.extractor = lpCase.extractor
      logic03Rec.extractDate = lpCase.extractDate
      logic03Rec.extractUnitCode = lpCase.extractUnitCode
      logic03Rec.extractUnitName = lpCase.extractUnitName
      logic03Rec.amount = lpCase.amount
      logic03Rec.bonus = lpCase.bonus
      logic03Rec.suspiciousArea1Code = lpCase.suspiciousArea1Code
      logic03Rec.suspiciousArea2Code = lpCase.suspiciousArea2Code
      logic03Rec.suspiciousArea3Code = lpCase.suspiciousArea3Code
      logic03RecList += logic03Rec
    }

    logic03RecList
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
