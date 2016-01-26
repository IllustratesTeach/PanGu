package nirvana.hall.v70.internal.sync

import nirvana.hall.protocol.v62.FPTProto.Case
import nirvana.hall.v70.jpa.{GafisCase, GafisCaseFinger, SyncQueue}

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync7to6CaseService {
  /**
   * 同步案件信息到62
   * @param syncQueue
   * @return
   */
  def syncCase(syncQueue: SyncQueue): Unit = {
    val caseId = syncQueue.uploadKeyid
    syncQueue.opration match {
      case "insert" =>
        addCase(caseId)
      case "update" =>
        updateCase(caseId)
      case "delete" =>
        deleteCase(caseId)
    }
  }

  private def addCase(caseId: String): Unit ={
    val caseInfo = getCase(caseId)
    //TODO
    throw new UnsupportedOperationException
  }
  private def updateCase(caseId: String): Unit ={
    //TODO
    val caseInfo = getCase(caseId)
    throw new UnsupportedOperationException
  }
  private def deleteCase(caseId: String): Unit ={
    //TODO
    throw new UnsupportedOperationException

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
