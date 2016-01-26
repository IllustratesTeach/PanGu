package nirvana.hall.v70.internal.sync

import nirvana.hall.protocol.v62.FPTProto.Case
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter
import nirvana.hall.v70.jpa.{SyncQueue, GafisCaseFinger, GafisCase}

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync7to6CaseService {
  /**
   * 同步案件信息到62
   * @param syncQueue
   * @return
   */
  def syncCase(facade: V62Facade, v62Config: HallV62Config, syncQueue: SyncQueue): Unit = {
    val caseId = syncQueue.uploadKeyid
    syncQueue.opration match {
      case "insert" =>
        addCase(facade, v62Config, caseId)
      case "update" =>
        updateCase(facade, v62Config, caseId)
      case "delete" =>
        deleteCase(facade, v62Config, caseId)
    }
  }

  private def addCase(facade: V62Facade, v62Config: HallV62Config, caseId: String): Unit ={
    val caseInfo = getCase(caseId)
    val gCase = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseInfo)
    facade.NET_GAFIS_CASE_Add(v62Config.caseTable.dbId.toShort, v62Config.caseTable.tableId.toShort, gCase)
  }
  private def updateCase(facade: V62Facade, v62Config: HallV62Config, caseId: String): Unit ={
    val caseInfo = getCase(caseId)
    val gCase = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseInfo)
    facade.NET_GAFIS_CASE_Update(v62Config.caseTable.dbId.toShort, v62Config.caseTable.tableId.toShort, gCase)
  }
  private def deleteCase(facade: V62Facade, v62Config: HallV62Config, caseId: String): Unit ={
    facade.NET_GAFIS_CASE_Del(v62Config.caseTable.dbId.toShort, v62Config.caseTable.tableId.toShort,
      if(caseId.indexOf("A") == 0) caseId.substring(1) else caseId)
  }

  private def getCase(caseId: String): Case = {
    val caseBuilder = Case.newBuilder()
    val caseInfo = GafisCase.find(caseId)
    caseBuilder.setStrCaseID(caseId)

    val textBuilder = caseBuilder.getTextBuilder
    magicSet(caseInfo.caseClassCode, textBuilder.setStrCaseType1)
    magicSet(caseInfo.caseOccurDate, textBuilder.setStrCaseOccurDate)
    magicSet(caseInfo.caseOccurPlaceCode, textBuilder.setStrCaseOccurPlaceCode)
    magicSet(caseInfo.caseOccurPlaceDetail, textBuilder.setStrCaseOccurPlace)

    magicSet(caseInfo.remark, textBuilder.setStrComment)
    if("1".equals(caseInfo.isMurder))
      textBuilder.setBPersonKilled(true)
    magicSet(caseInfo.amount, textBuilder.setStrMoneyLost)
    magicSet(caseInfo.extractUnitCode, textBuilder.setStrExtractUnitCode)
    magicSet(caseInfo.extractUnitName, textBuilder.setStrExtractUnitName)
    magicSet(caseInfo.extractDate, textBuilder.setStrExtractDate)
    magicSet(caseInfo.extractor, textBuilder.setStrExtractor)
    magicSet(caseInfo.suspiciousAreaCode, textBuilder.setStrSuspArea1Code)
    textBuilder.setNSuperviseLevel(caseInfo.assistLevel)
    magicSet(caseInfo.assistDeptCode, textBuilder.setStrXieChaRequestUnitCode)
    magicSet(caseInfo.assistDeptName, textBuilder.setStrXieChaRequestUnitName)
    magicSet(caseInfo.assistDate, textBuilder.setStrXieChaDate)
    textBuilder.setNCaseState(caseInfo.assistSign)
    textBuilder.setNCancelFlag(caseInfo.assistRevokeSign)
    textBuilder.setNCaseState(caseInfo.caseState)

    GafisCaseFinger.find_by_caseId(caseId).foreach(f => caseBuilder.addStrFingerID(f.fingerId))
    caseBuilder.setNCaseFingerCount(caseBuilder.getStrFingerIDCount)

    caseBuilder.build()
  }

}
