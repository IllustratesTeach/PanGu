package nirvana.hall.api.internal.filter

import javax.servlet.http.HttpServletRequest

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.jpa.HallReadConfig
import nirvana.hall.api.services.{CaseInfoService, LPCardService, LPPalmService, TPCardService}
import nirvana.hall.api.services.sync._
import nirvana.hall.protocol.api.FPTProto.{Case, LPCard, TPCard}
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import org.slf4j.LoggerFactory

/**
 * Created by songpeng on 16/6/17.
 */
class SyncDataFilter(httpServletRequest: HttpServletRequest,
                     fetchTPCardService: FetchTPCardService,
                     fetchLPCardService: FetchLPCardService,
                     fetchLPPalmService: FetchLPPalmService,
                     fetchCaseInfoService: FetchCaseInfoService,
                     fetchQueryService: FetchQueryService,
                     tPCardService: TPCardService,
                     lPCardService: LPCardService,
                     lPPalmService: LPPalmService,
                     caseInfoService: CaseInfoService) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean ={
    if(commandRequest.hasExtension(SyncTPCardRequest.cmd)) {
      val request = commandRequest.getExtension(SyncTPCardRequest.cmd)
      val responseBuilder = SyncTPCardResponse.newBuilder()
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      val ip = httpServletRequest.getRemoteAddr
      //验证是否有权限
      val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_TPCARD, request.getDbid, "1").headOption
      if(hallReadConfigOpt.nonEmpty){
        val cardIdList = fetchTPCardService.fetchCardId(request.getSeq, request.getSize, dbId)
        cardIdList.foreach{ cardId =>
          responseBuilder.setSeq(cardId._2)
          if(tPCardService.isExist(cardId._1, dbId)){
            val tpCard = tPCardService.getTPCard(cardId._1, dbId)
            if(fetchTPCardService.validateByReadStrategy(tpCard, hallReadConfigOpt.get.readStrategy)){
              val syncTPCard = responseBuilder.addSyncTPCardBuilder()
              syncTPCard.setTpCard(tpCard)
              syncTPCard.setSeq(cardId._2)
              syncTPCard.setOperationType(OperationType.PUT)
            }
          }else{//如果卡号不存在，删除
            val syncTPCard = responseBuilder.addSyncTPCardBuilder()
            val tpCard = TPCard.newBuilder().setStrCardID(cardId._1)
            syncTPCard.setTpCard(tpCard.build())
            syncTPCard.setOperationType(OperationType.PUT)
            syncTPCard.setSeq(cardId._2)
          }
        }
        hallReadConfigOpt.get.seq = request.getSeq
        updateSeq(hallReadConfigOpt.get)
      }
      commandResponse.writeMessage(commandRequest, SyncTPCardResponse.cmd, responseBuilder.build())
      true
    }else if(commandRequest.hasExtension(SyncLPCardRequest.cmd)){
      val request = commandRequest.getExtension(SyncLPCardRequest.cmd)
      val responseBuilder = SyncLPCardResponse.newBuilder()
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      val ip = httpServletRequest.getRemoteAddr
      //验证是否有权限
      val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_LPCARD, request.getDbid, "1").headOption
      if(hallReadConfigOpt.nonEmpty){
        val cardIdList = fetchLPCardService.fetchCardId(request.getSeq, request.getSize, dbId)
        cardIdList.foreach{cardId =>
          responseBuilder.setSeq(cardId._2)
          if(lPCardService.isExist(cardId._1, dbId)){
            val lPCard = lPCardService.getLPCard(cardId._1, dbId)
            if(fetchLPCardService.validateByReadStrategy(lPCard, hallReadConfigOpt.get.readStrategy)){
              val syncLPCard = responseBuilder.addSyncLPCardBuilder()
              syncLPCard.setLpCard(lPCard)
              syncLPCard.setOperationType(OperationType.PUT)
              syncLPCard.setSeq(cardId._2)
            }
          }else{
            val syncLPCard = responseBuilder.addSyncLPCardBuilder()
            val lPCard = LPCard.newBuilder().setStrCardID(cardId._1)
            syncLPCard.setLpCard(lPCard)
            syncLPCard.setOperationType(OperationType.DEL)
            syncLPCard.setSeq(cardId._2)
          }
        }
        hallReadConfigOpt.get.seq = request.getSeq
        updateSeq(hallReadConfigOpt.get)
      }
      commandResponse.writeMessage(commandRequest, SyncLPCardResponse.cmd, responseBuilder.build())
      true
    }else if(commandRequest.hasExtension(SyncLPPalmRequest.cmd)){
      val request = commandRequest.getExtension(SyncLPPalmRequest.cmd)
      val responseBuilder = SyncLPPalmResponse.newBuilder()
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      val ip = httpServletRequest.getRemoteAddr
      //验证是否有权限
      val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_LPPALM, request.getDbid, "1").headOption
      if(hallReadConfigOpt.nonEmpty){
        val cardIdList = fetchLPPalmService.fetchCardId(request.getSeq, request.getSize, dbId)
        cardIdList.foreach{cardId =>
          responseBuilder.setSeq(cardId._2)
          if(lPPalmService.isExist(cardId._1, dbId)){
            val lPCard = lPPalmService.getLPCard(cardId._1, dbId)
            if(fetchLPPalmService.validateByReadStrategy(lPCard, hallReadConfigOpt.get.readStrategy)){
              val syncLPCard = responseBuilder.addSyncLPCardBuilder()
              syncLPCard.setLpCard(lPCard)
              syncLPCard.setOperationType(OperationType.PUT)
              syncLPCard.setSeq(cardId._2)
            }
          }else{
            val syncLPCard = responseBuilder.addSyncLPCardBuilder()
            val lPCard = LPCard.newBuilder().setStrCardID(cardId._1)
            syncLPCard.setLpCard(lPCard)
            syncLPCard.setOperationType(OperationType.DEL)
            syncLPCard.setSeq(cardId._2)
          }
        }
        hallReadConfigOpt.get.seq = request.getSeq
        updateSeq(hallReadConfigOpt.get)
      }
      commandResponse.writeMessage(commandRequest, SyncLPPalmResponse.cmd, responseBuilder.build())
      true
    }else if(commandRequest.hasExtension(SyncCaseRequest.cmd)) {
      val request = commandRequest.getExtension(SyncCaseRequest.cmd)
      val responseBuilder = SyncCaseResponse.newBuilder()
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      val ip = httpServletRequest.getRemoteAddr
      //验证是否有权限
      val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_CASEINFO, request.getDbid, "1").headOption
      if (hallReadConfigOpt.nonEmpty) {
        val caseIdList = fetchCaseInfoService.fetchCaseId(request.getSeq, request.getSize, dbId)
        caseIdList.foreach { caseId =>
          if (caseInfoService.isExist(caseId._1, dbId)) {
            val caseInfo = caseInfoService.getCaseInfo(caseId._1, dbId)
            if (fetchCaseInfoService.validateByReadStrategy(caseInfo, hallReadConfigOpt.get.readStrategy)) {
              val syncCaseInfo = responseBuilder.addSyncCaseBuilder()
              syncCaseInfo.setCaseInfo(caseInfo)
              syncCaseInfo.setOperationType(OperationType.PUT)
              syncCaseInfo.setSeq(caseId._2)
            }
          } else {
            val syncCaseInfo = responseBuilder.addSyncCaseBuilder()
            val caseInfo = Case.newBuilder().setStrCaseID(caseId._1)
            syncCaseInfo.setCaseInfo(caseInfo)
            syncCaseInfo.setOperationType(OperationType.DEL)
            syncCaseInfo.setSeq(caseId._2)
          }
        }
        hallReadConfigOpt.get.seq = request.getSeq
        updateSeq(hallReadConfigOpt.get)
      }

      commandResponse.writeMessage(commandRequest, SyncCaseResponse.cmd, responseBuilder.build())
      true
    }else if(commandRequest.hasExtension(SyncMatchTaskRequest.cmd)){
      val request = commandRequest.getExtension(SyncMatchTaskRequest.cmd)
      val responseBuilder = SyncMatchTaskResponse.newBuilder()
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      val ip = httpServletRequest.getRemoteAddr
      //验证是否有权限
      val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_MATCH_TASK, request.getDbid, "1").headOption

      var matchTaskList: Seq[MatchTask] = null
      if(hallReadConfigOpt.nonEmpty){
        matchTaskList = fetchQueryService.fetchMatchTask(request.getSize, dbId)
        matchTaskList.foreach{matchTask=>
          responseBuilder.addMatchTask(matchTask)
        }
        hallReadConfigOpt.get.seq = request.getSeq
        updateSeq(hallReadConfigOpt.get)
      }
      commandResponse.writeMessage(commandRequest, SyncMatchTaskResponse.cmd, responseBuilder.build())
      /**
        * sjr 2016/11/29
        * 更新状态
        */
      if(matchTaskList!=null&&matchTaskList.size>0) {
        matchTaskList.foreach { matchTask =>
          fetchQueryService.updateMatchStatus(matchTask.getObjectId, 1) // matchTask.getObjectId 值存为seq
        }
      }

      true
    }else if(commandRequest.hasExtension(SyncMatchResultRequest.cmd)){
      val request = commandRequest.getExtension(SyncMatchResultRequest.cmd)
      val responseBuilder = SyncMatchResultResponse.newBuilder()
      val ip = httpServletRequest.getRemoteAddr
      val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
      //验证是否有权限
      val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_MATCH_RESULT, request.getDbid, "1").headOption
      if(hallReadConfigOpt.nonEmpty){
        val status = fetchQueryService.getMatchStatusByQueryid(request.getSid, request.getPkid, request.getTyp.toShort)
        responseBuilder.setMatchStatus(status)
        val matchResultOpt = fetchQueryService.getMatchResultByQueryid(request.getSid, request.getPkid, request.getTyp.toShort, dbId)
        if(matchResultOpt.nonEmpty) {
          responseBuilder.setMatchResult(matchResultOpt.get)
          hallReadConfigOpt.get.seq = request.getSid
          updateSeq(hallReadConfigOpt.get)
        }
      }else{
        responseBuilder.setMatchStatus(MatchStatus.UN_KNOWN)
      }

      commandResponse.writeMessage(commandRequest, SyncMatchResultResponse.cmd, responseBuilder.build())
      true
    }else{
      handler.handle(commandRequest, commandResponse)
    }
  }

  /**
   * 更新seq
    *
    * @param readConfig
   */
  private def updateSeq(readConfig: HallReadConfig): Unit ={
    HallReadConfig.update.set(seq = readConfig.seq).where(HallReadConfig.pkId === readConfig.pkId).execute
  }

}
