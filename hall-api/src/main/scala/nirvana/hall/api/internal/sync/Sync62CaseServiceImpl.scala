package nirvana.hall.api.internal.sync

import nirvana.hall.api.entities.{GafisCase, SyncQueue}
import nirvana.hall.api.services.sync.Sync62CaseService
import nirvana.hall.protocol.v62.FPTProto.Case
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter
import scalikejdbc.DBSession

/**
 * Created by songpeng on 15/12/7.
 */
class Sync62CaseServiceImpl(facade: V62Facade, v62Config: HallV62Config) extends Sync62CaseService{
  /**
   * 同步案件信息到6.2
   * @param syncQueue
   * @param session
   * @return
   */
  override def syncCase(syncQueue: SyncQueue)(implicit session: DBSession): Unit = {

    val caseId = syncQueue.uploadKeyid.get
    val caseBuilder = Case.newBuilder()
    val caseInfo = GafisCase.find(caseId).get
    caseBuilder.setStrCaseID(caseId)

    val textBuilder = caseBuilder.getTextBuilder
    caseInfo.caseClassCode.foreach(textBuilder.setStrCaseType1)
    caseInfo.caseOccurDate.foreach(f => textBuilder.setStrCaseOccurDate(f))
    caseInfo.caseOccurPlaceCode.foreach(textBuilder.setStrCaseOccurPlaceCode)
    caseInfo.caseOccurPlaceDetail.foreach(textBuilder.setStrCaseOccurPlace)
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

    //      val caseFingerList = findCaseFingerIdsListByCaseId(caseId)
    //      caseFingerList.foreach(caseBuilder.addStrFingerID)

    val gCase = galoclpConverter.convertProtobuf2GCASEINFOSTRUCT(caseBuilder.build())
    facade.NET_GAFIS_CASE_Add(v62Config.caseTable.dbId.toShort, v62Config.caseTable.tableId.toShort, gCase)
  }
//  def findCaseFingerIdsListByCaseId(caseId: String)(implicit session: DBSession): Seq[String] = {
//    val fingerIds = ArrayBuffer[String]()
//    SQL("select finger_id from "+ GafisCaseFinger.tableName + " where case_id=?").bind(caseId).foreach(rs => fingerIds += rs.string(1))
//    fingerIds.toSeq
//  }
}
