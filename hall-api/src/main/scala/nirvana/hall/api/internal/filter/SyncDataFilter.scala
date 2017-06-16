package nirvana.hall.api.internal.filter

import javax.servlet.http.HttpServletRequest

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.{HallApiConstants, HallApiErrorConstants}
import nirvana.hall.api.jpa.HallReadConfig
import nirvana.hall.api.services.{QueryService, _}
import nirvana.hall.api.services.sync._
import nirvana.hall.protocol.api.FPTProto.{Case, LPCard, TPCard}
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.v62.internal.QueryMatchStatusConstants
import org.apache.tapestry5.json.JSONObject
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
                     queryService: QueryService,
                     tPCardService: TPCardService,
                     lPCardService: LPCardService,
                     lPPalmService: LPPalmService,
                     caseInfoService: CaseInfoService,
                     syncInfoLogManageService: SyncInfoLogManageService) extends RpcServerMessageFilter with LoggerSupport{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean ={
    if(commandRequest.hasExtension(SyncTPCardRequest.cmd)) {
      var card_id="" //卡号
      val request = commandRequest.getExtension(SyncTPCardRequest.cmd)
      val uuid = request.getUuid
      var typ_add=""
      var count = 0 //数据数量
      var status=0 //SEQ状态更新结果
      try{
        val responseBuilder = SyncTPCardResponse.newBuilder()
        responseBuilder.setSeq(request.getSeq)
        val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
        val ip = httpServletRequest.getRemoteAddr
        //验证是否有权限
        val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_TPCARD, request.getDbid, "1").headOption
        if(hallReadConfigOpt.nonEmpty) {
          val cardIdList = fetchTPCardService.fetchCardId(request.getSeq, request.getSize, dbId)
          count = cardIdList.length
          cardIdList.foreach { cardId =>
            responseBuilder.setSeq(cardId._2)
            if (tPCardService.isExist(cardId._1, dbId)) {
              val tpCard = tPCardService.getTPCard(cardId._1, dbId)
              card_id = tpCard.getStrCardID
              if (fetchTPCardService.validateByReadStrategy(tpCard, hallReadConfigOpt.get.readStrategy)) {
                val syncTPCard = responseBuilder.addSyncTPCardBuilder()
                syncTPCard.setTpCard(tpCard)
                syncTPCard.setSeq(cardId._2)
                syncTPCard.setOperationType(OperationType.PUT)
                typ_add = HallApiConstants.PUT
              }
            } else {
              val tpCard = TPCard.newBuilder().setStrCardID(cardId._1)
              card_id=cardId._1
              val syncTPCard = responseBuilder.addSyncTPCardBuilder()
              syncTPCard.setTpCard(tpCard.build())
              syncTPCard.setOperationType(OperationType.DEL)
              syncTPCard.setSeq(cardId._2)
              typ_add = HallApiConstants.DELETE
            }
          }
          hallReadConfigOpt.get.seq = request.getSeq
          status=updateSeq(hallReadConfigOpt.get)
          if(count>0 && status >0){
            syncInfoLogManageService.recordSyncDataIdentifyLog(request.getUuid, card_id, HallApiConstants.SYNC_TYPE_TPCARD+typ_add, ip, "1", "1")
          }
          if(status <=0){
            throw new Exception(HallApiErrorConstants.SYNC_UPDATE_TPCard_SEQ_FAIL+"cardId:{} "+card_id)
          }
      }

        commandResponse.writeMessage(commandRequest, SyncTPCardResponse.cmd, responseBuilder.build())
      } catch {
        case e: Exception =>
          val eInfo = ExceptionUtil.getStackTraceInfo(e)
          error("TP-ResponseData fail,uuid:{};cardId:{};错误堆栈信息:{};错误信息:{}",uuid,card_id,eInfo,e.getMessage)
          syncInfoLogManageService.recordSyncDataLog(uuid, card_id, null, eInfo, 2, HallApiErrorConstants.SYNC_RESPONSE_UNKNOWN + HallApiConstants.SYNC_TYPE_TPCARD)
      }
      true
    }else if(commandRequest.hasExtension(SyncLPCardRequest.cmd)){
      val request = commandRequest.getExtension(SyncLPCardRequest.cmd)
      val uuid = request.getUuid
      var card_id="" //卡号
      var typ_add=""
      var count = 0 //数据数量
      var status=0 //SEQ状态更新结果
      try{
        val responseBuilder = SyncLPCardResponse.newBuilder()
        responseBuilder.setSeq(request.getSeq)
        val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
        val ip = httpServletRequest.getRemoteAddr
        //验证是否有权限
        val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_LPCARD, request.getDbid, "1").headOption
        if(hallReadConfigOpt.nonEmpty){
          val cardIdList = fetchLPCardService.fetchCardId(request.getSeq, request.getSize, dbId)
          count=cardIdList.length
          cardIdList.foreach{cardId =>
            responseBuilder.setSeq(cardId._2)
            if(lPCardService.isExist(cardId._1, dbId)){
              val lPCard = lPCardService.getLPCard(cardId._1, dbId)
              card_id=lPCard.getStrCardID
              if(fetchLPCardService.validateByReadStrategy(lPCard, hallReadConfigOpt.get.readStrategy)){
                val syncLPCard = responseBuilder.addSyncLPCardBuilder()
                syncLPCard.setLpCard(lPCard)
                syncLPCard.setOperationType(OperationType.PUT)
                syncLPCard.setSeq(cardId._2)
                typ_add="_PUT"
              }
            }else{
              val syncLPCard = responseBuilder.addSyncLPCardBuilder()
              val lPCard = LPCard.newBuilder().setStrCardID(cardId._1)
              card_id=cardId._1
              syncLPCard.setLpCard(lPCard)
              syncLPCard.setOperationType(OperationType.DEL)
              syncLPCard.setSeq(cardId._2)
              typ_add="_DEL"
            }
          }
          hallReadConfigOpt.get.seq = request.getSeq
          status=updateSeq(hallReadConfigOpt.get)
          if(count>0 && status >0){
            syncInfoLogManageService.recordSyncDataIdentifyLog(uuid,card_id,HallApiConstants.SYNC_TYPE_LPCARD+typ_add,ip,"1","1")
          }
          if(status <=0){
            throw new Exception(HallApiErrorConstants.SYNC_UPDATE_LPCard_SEQ_FAIL+"cardId:{} "+card_id)
          }
        }

        commandResponse.writeMessage(commandRequest, SyncLPCardResponse.cmd, responseBuilder.build())
      } catch {
        case e: Exception =>
          val eInfo = ExceptionUtil.getStackTraceInfo(e)
          error("LP-ResponseData fail,uuid{};cardId:{};错误堆栈信息:{};错误信息:{}",uuid,eInfo,e.getMessage)
          syncInfoLogManageService.recordSyncDataLog(uuid, card_id, null, eInfo, 2, HallApiErrorConstants.SYNC_RESPONSE_UNKNOWN + HallApiConstants.SYNC_TYPE_LPCARD)
      }
      true
    }else if(commandRequest.hasExtension(SyncLPPalmRequest.cmd)) {
      val request = commandRequest.getExtension(SyncLPPalmRequest.cmd)
      val uuid = request.getUuid
      var card_id = "" //卡号
      var typ_add=""
      var count = 0 //数据数量
      var status=0 //SEQ状态更新结果
      try {
        val responseBuilder = SyncLPPalmResponse.newBuilder()
        responseBuilder.setSeq(request.getSeq)
        val dbId = if (request.getDbid.isEmpty) None else Option(request.getDbid)
        val ip = httpServletRequest.getRemoteAddr
        //验证是否有权限
        val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_LPPALM, request.getDbid, "1").headOption
        if (hallReadConfigOpt.nonEmpty) {
          val cardIdList = fetchLPPalmService.fetchCardId(request.getSeq, request.getSize, dbId)
          count=cardIdList.length
          cardIdList.foreach { cardId =>
            responseBuilder.setSeq(cardId._2)
            if (lPPalmService.isExist(cardId._1, dbId)) {
              val lPCard = lPPalmService.getLPCard(cardId._1, dbId)
              card_id = lPCard.getStrCardID
              if (fetchLPPalmService.validateByReadStrategy(lPCard, hallReadConfigOpt.get.readStrategy)) {
                val syncLPCard = responseBuilder.addSyncLPCardBuilder()
                syncLPCard.setLpCard(lPCard)
                syncLPCard.setOperationType(OperationType.PUT)
                syncLPCard.setSeq(cardId._2)
                typ_add="_PUT"
              }
            } else {
              val syncLPCard = responseBuilder.addSyncLPCardBuilder()
              val lPCard = LPCard.newBuilder().setStrCardID(cardId._1)
              card_id=cardId._1
              syncLPCard.setLpCard(lPCard)
              syncLPCard.setOperationType(OperationType.DEL)
              syncLPCard.setSeq(cardId._2)
              typ_add="_DEL"
            }
          }
          hallReadConfigOpt.get.seq = request.getSeq
          status=updateSeq(hallReadConfigOpt.get)
          if(count>0 && status >0){
            syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, card_id, HallApiConstants.SYNC_TYPE_LPPALM+typ_add, ip, "1", "1")
          }
          if(status <=0){
            throw new Exception(HallApiErrorConstants.SYNC_UPDATE_LPalm_SEQ_FAIL+"cardId:{} "+card_id)
          }
        }

        commandResponse.writeMessage(commandRequest, SyncLPPalmResponse.cmd, responseBuilder.build())
      } catch {
        case e:Exception =>
          val eInfo = ExceptionUtil.getStackTraceInfo(e)
          error("LP-Plam-ResponseData fail,uuid{};cardId:{};错误堆栈信息:{};错误信息:{}",uuid,card_id,eInfo,e.getMessage)
          syncInfoLogManageService.recordSyncDataLog(uuid, card_id, null, eInfo, 2, HallApiErrorConstants.SYNC_RESPONSE_UNKNOWN + HallApiConstants.SYNC_TYPE_LPPALM)
      }
      true
    }else if(commandRequest.hasExtension(SyncCaseRequest.cmd)) {
      val request = commandRequest.getExtension(SyncCaseRequest.cmd)
      val responseBuilder = SyncCaseResponse.newBuilder()
      responseBuilder.setSeq(request.getSeq)
      var card_id= "" //卡号
      val uuid = request.getUuid
      var typ_add=""
      var count = 0 //数据数量
      var status=0 //SEQ状态更新结果
      try{
        val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
        val ip = httpServletRequest.getRemoteAddr
        //验证是否有权限
        val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_CASEINFO, request.getDbid, "1").headOption
        if (hallReadConfigOpt.nonEmpty) {
          val caseIdList = fetchCaseInfoService.fetchCaseId(request.getSeq, request.getSize, dbId)
          count=caseIdList.length
          caseIdList.foreach { caseId =>
            if (caseInfoService.isExist(caseId._1, dbId)) {
              val caseInfo = caseInfoService.getCaseInfo(caseId._1, dbId)
              card_id=caseInfo.getStrCaseID
              if (fetchCaseInfoService.validateByReadStrategy(caseInfo, hallReadConfigOpt.get.readStrategy)) {
                val syncCaseInfo = responseBuilder.addSyncCaseBuilder()
                syncCaseInfo.setCaseInfo(caseInfo)
                syncCaseInfo.setOperationType(OperationType.PUT)
                syncCaseInfo.setSeq(caseId._2)
                typ_add="_PUT"
              }
            } else {
              val syncCaseInfo = responseBuilder.addSyncCaseBuilder()
              val caseInfo = Case.newBuilder().setStrCaseID(caseId._1)
              card_id=caseId._1
              syncCaseInfo.setCaseInfo(caseInfo)
              syncCaseInfo.setOperationType(OperationType.DEL)
              syncCaseInfo.setSeq(caseId._2)
              typ_add="_DEL"
            }
          }
          hallReadConfigOpt.get.seq = request.getSeq
          status=updateSeq(hallReadConfigOpt.get)
          if(count>0 && status >0){
            syncInfoLogManageService.recordSyncDataIdentifyLog(request.getUuid,card_id,HallApiConstants.SYNC_TYPE_CASEINFO+typ_add,ip,"1","1")
          }
          if(status <=0){
            throw new Exception(HallApiErrorConstants.SYNC_UPDATE_CASEInfo_SEQ_FAIL +"cardId:{} "+card_id)
          }
        }

        commandResponse.writeMessage(commandRequest, SyncCaseResponse.cmd, responseBuilder.build())
      } catch {
        case e:Exception =>
          val eInfo = ExceptionUtil.getStackTraceInfo(e)
          error("CASE-ResponseData fail,uuid{};cardId:{};错误堆栈信息:{};错误信息:{}",uuid,card_id,eInfo,e.getMessage)
          syncInfoLogManageService.recordSyncDataLog(uuid, card_id, null, eInfo, 2, HallApiErrorConstants.SYNC_RESPONSE_UNKNOWN + HallApiConstants.SYNC_TYPE_CASEINFO)
      }
      true
    }else if(commandRequest.hasExtension(SyncMatchTaskRequest.cmd)){
      val request = commandRequest.getExtension(SyncMatchTaskRequest.cmd)
      val uuid = request.getUuid
      var match_task_orasid=""
      try{
        val responseBuilder = SyncMatchTaskResponse.newBuilder()
        val dbId = if(request.getDbid.isEmpty) None else Option(request.getDbid)
        val ip = httpServletRequest.getRemoteAddr
        //验证是否有权限
        val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_MATCH_TASK, request.getDbid, "1").headOption

        var matchTaskList: Seq[MatchTask] = null
        if(hallReadConfigOpt.nonEmpty){

          val strategy = new JSONObject(hallReadConfigOpt.get.readStrategy)
          var yearThreshold = ""
          if (strategy.has("taskyear")){
            yearThreshold = strategy.getString("taskyear")
          }
          matchTaskList = fetchQueryService.fetchMatchTask(request.getSize,yearThreshold, dbId)
          matchTaskList.foreach{matchTask=>
            match_task_orasid=matchTask.getMatchId
            responseBuilder.addMatchTask(matchTask)
            syncInfoLogManageService.recordSyncDataIdentifyLog(uuid
                                            ,match_task_orasid
              ,HallApiConstants.SYNC_TYPE_MATCH_TASK + HallApiConstants.PUT,ip,HallApiConstants.MESSAGE_RECEIVE
              ,HallApiConstants.MESSAGE_RECEIVE_OR_SEND_SUCCESS)
          }
          matchTaskList.foreach{
            t => fetchQueryService.saveFetchRecord(t.getObjectId.toString)
          }
        }
        commandResponse.writeMessage(commandRequest, SyncMatchTaskResponse.cmd, responseBuilder.build())

      } catch {
        case e:Exception =>
          val eInfo = ExceptionUtil.getStackTraceInfo(e)
          error("MatchTask-ResponseData fail,uuid{};match_task_orasid:{};错误堆栈信息:{};错误信息:{}",uuid,match_task_orasid,eInfo,e.getMessage)
          syncInfoLogManageService.recordSyncDataLog(uuid, match_task_orasid, null, eInfo, HallApiConstants.LOG_ERROR_TYPE, HallApiErrorConstants.SYNC_RESPONSE_UNKNOWN + HallApiConstants.SYNC_TYPE_MATCH_TASK)
      }
      true
    } else if (commandRequest.hasExtension(SyncMatchResultRequest.cmd)) {
      val request = commandRequest.getExtension(SyncMatchResultRequest.cmd)

      val ip = httpServletRequest.getRemoteAddr
      val uuid = request.getUuid
      val match_task_orasid = request.getSid
      val dbId = if (request.getDbid.isEmpty) None else Option(request.getDbid)
      try {
        val responseBuilder = SyncMatchResultResponse.newBuilder()
        //验证是否有权限
        val hallReadConfigOpt = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, HallApiConstants.SYNC_TYPE_MATCH_RESULT, request.getDbid, "1").headOption
        if (hallReadConfigOpt.nonEmpty) {
          val status = queryService.getStatusBySid(request.getSid)
          responseBuilder.setMatchStatus(QueryMatchStatusConstants.matchStatusConvertProtoBuf(status))
          val matchResultOpt = queryService.getMatchResult(request.getSid, dbId)
          if (matchResultOpt.nonEmpty) {
            responseBuilder.setMatchResult(matchResultOpt.get)
          }
        } else {
          responseBuilder.setMatchStatus(MatchStatus.UN_KNOWN)
        }
        commandResponse.writeMessage(commandRequest, SyncMatchResultResponse.cmd, responseBuilder.build())
        syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, match_task_orasid.toString, HallApiConstants.SYNC_TYPE_MATCH_RESULT, ip, "1", "1")
      } catch {
        case e: Exception =>
          val eInfo = ExceptionUtil.getStackTraceInfo(e)
          error("MatchResult-ResponseData fail,uuid{};match_task_orasid:{};错误堆栈信息:{};错误信息:{}", uuid, match_task_orasid, eInfo, e.getMessage)
          syncInfoLogManageService.recordSyncDataLog(uuid, match_task_orasid.toString, null, eInfo, 2, HallApiErrorConstants.SYNC_RESPONSE_UNKNOWN + HallApiConstants.SYNC_TYPE_MATCH_RESULT)
      }
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
  private def updateSeq(readConfig: HallReadConfig): Int ={
    HallReadConfig.update.set(seq = readConfig.seq).where(HallReadConfig.pkId === readConfig.pkId).execute
  }

}
