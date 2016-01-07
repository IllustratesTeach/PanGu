package nirvana.hall.api.internal.sync

import nirvana.hall.api.jpa.{GafisCaseFinger, GafisCase, SyncQueue}
import nirvana.hall.protocol.v62.FPTProto.Case
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter
import scalikejdbc.{SQL, DBSession}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 15/12/7.
 */
trait Sync7to6CaseService {
  /**
   * 同步案件信息到62
   * @param syncQueue
   * @param session
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
    textBuilder.setStrCaseType1(caseInfo.caseClassCode)
    textBuilder.setStrCaseOccurDate(caseInfo.caseOccurDate.toString)
    textBuilder.setStrCaseOccurPlaceCode(caseInfo.caseOccurPlaceCode)
    textBuilder.setStrCaseOccurPlace(caseInfo.caseOccurPlaceDetail)

    if(2>1) //TODO 修正注释代码的问题
      throw new UnsupportedOperationException
    /*
    caseInfo.remark.foreach(textBuilder.setStrComment)
    caseInfo.isMurder.foreach(f => textBuilder.setBPersonKilled("1".equals(f)))
    caseInfo.amount.foreach(textBuilder.setStrMoneyLost)
    caseInfo.extractUnitCode.foreach(textBuilder.setStrExtractUnitCode)
    caseInfo.extractUnitName.foreach(textBuilder.setStrExtractUnitName)
    caseInfo.extractDate.foreach(f => textBuilder.setStrExtractDate(f))
    caseInfo.extractor.foreach(textBuilder.setStrExtractor)
    caseInfo.suspiciousAreaCode.foreach(textBuilder.setStrSuspArea1Code)
    caseInfo.assistLevel.foreach(f => textBuilder.setNSuperviseLevel(f))
    caseInfo.assistDeptCode.foreach(textBuilder.setStrXieChaRequestUnitCode)
    caseInfo.assistDeptName.foreach(textBuilder.setStrXieChaRequestUnitName)
    caseInfo.assistDate.foreach(textBuilder.setStrXieChaDate)
    caseInfo.assistSign.foreach(f => textBuilder.setNCaseState(f))
    caseInfo.assistRevokeSign.foreach(f => textBuilder.setNCancelFlag(f))
    caseInfo.caseState.foreach(f => textBuilder.setNCaseState(f))

    findCaseFingerIdsListByCaseId(caseId).foreach(caseBuilder.addStrFingerID)
    */

    caseBuilder.build()
  }

  private def findCaseFingerIdsListByCaseId(caseId: String): Seq[String] = {
      throw new UnsupportedOperationException
    /*
    val fingerIds = ArrayBuffer[String]()
    SQL("select finger_id from " + GafisCaseFinger.tableName + " where case_id=? and deletag='1'").bind(caseId).foreach(rs => fingerIds += rs.string(1))
    fingerIds.toSeq
    */
  }

}
