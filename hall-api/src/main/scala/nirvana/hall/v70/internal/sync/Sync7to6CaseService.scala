package nirvana.hall.v70.internal.sync

import nirvana.hall.protocol.v62.FPTProto.Case
import nirvana.hall.protocol.v62.lp.CaseProto._
import nirvana.hall.v70.jpa.{GafisCaseFinger, GafisCase, SyncQueue}

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
    syncQueue.opration match {
      case "insert" =>
        addCase(syncQueue)
      case "update" =>
        updateCase(syncQueue)
      case "delete" =>
        deleteCase(syncQueue)
    }
  }

  private def addCase(syncQueue: SyncQueue): Unit ={
    val caseInfo = getCase(syncQueue.uploadKeyid)
    val request= CaseAddRequest.newBuilder().setCase(caseInfo).build()

    httpCall(syncQueue.targetIp, syncQueue.targetPort, CaseAddRequest.cmd, request, CaseAddResponse.newBuilder())
  }

  private def updateCase(syncQueue: SyncQueue): Unit ={
    val caseInfo = getCase(syncQueue.uploadKeyid)
    val request= CaseUpdateRequest.newBuilder().setCase(caseInfo).build()
    val response = CaseUpdateResponse.newBuilder()

    httpCall(syncQueue.targetIp, syncQueue.targetPort, CaseUpdateRequest.cmd, request, response)
  }
  private def deleteCase(syncQueue: SyncQueue): Unit ={
    val request = CaseDelRequest.newBuilder().setCaseId(syncQueue.uploadKeyid).build()
    val response = CaseDelResponse.newBuilder()

    httpCall(syncQueue.targetIp, syncQueue.targetPort,CaseDelRequest.cmd, request, response)
  }

  private def getCase(caseId: String): Case = {
    val gafisCase = GafisCase.find(caseId)
    val fingerIds = GafisCaseFinger.find_by_caseId(caseId).map(f => f.fingerId)

    ProtobufConverter.convertGafisCase2Case(gafisCase, fingerIds)
  }

}
