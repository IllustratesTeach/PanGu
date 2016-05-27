package nirvana.hall.v70.internal.sync

import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.protocol.api.CaseProto._
import nirvana.hall.support.services.RpcHttpClient
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
  def syncCase(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit = {
    syncQueue.opration match {
      case "insert" =>
        addCase(syncQueue, rpcHttpClient)
      case "update" =>
        updateCase(syncQueue, rpcHttpClient)
      case "delete" =>
        deleteCase(syncQueue, rpcHttpClient)
    }
  }

  private def addCase(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit ={
    val caseInfo = getCase(syncQueue.uploadKeyid)
    val request= CaseAddRequest.newBuilder().setCase(caseInfo).build()

    val baseResponse = rpcHttpClient.call("http://"+syncQueue.targetIp+":"+syncQueue.targetPort, CaseAddRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.FAIL){
      println(baseResponse.getMsg)
    }
  }

  private def updateCase(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit ={
    val caseInfo = getCase(syncQueue.uploadKeyid)
    val request= CaseUpdateRequest.newBuilder().setCase(caseInfo).build()

    val baseResponse = rpcHttpClient.call("http://"+syncQueue.targetIp+":"+syncQueue.targetPort, CaseUpdateRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.FAIL){
      println(baseResponse.getMsg)
    }
  }
  private def deleteCase(syncQueue: SyncQueue, rpcHttpClient: RpcHttpClient): Unit ={
    val request = CaseDelRequest.newBuilder().setCaseId(syncQueue.uploadKeyid).build()

    val baseResponse = rpcHttpClient.call("http://"+syncQueue.targetIp+":"+syncQueue.targetPort, CaseDelRequest.cmd, request)
    if(baseResponse.getStatus == CommandStatus.FAIL){
      println(baseResponse.getMsg)
    }
  }

  private def getCase(caseId: String): Case = {
    val gafisCase = GafisCase.find(caseId)
    val fingerIds = GafisCaseFinger.find_by_caseId(caseId).map(f => f.fingerId).toSeq

    ProtobufConverter.convertGafisCase2Case(gafisCase, fingerIds)
  }

}
