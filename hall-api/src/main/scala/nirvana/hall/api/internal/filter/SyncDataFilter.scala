package nirvana.hall.api.internal.filter

import javax.servlet.http.HttpServletRequest

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.jpa.HallReadConfig
import nirvana.hall.api.services.{CaseInfoService, LPPalmService, LPCardService, TPCardService}
import nirvana.hall.api.services.sync._
import nirvana.hall.protocol.api.FPTProto.{Case, LPCard, TPCard}
import nirvana.hall.protocol.api.SyncDataProto._

/**
 * Created by songpeng on 16/6/17.
 */
class SyncDataFilter(httpServletRequest: HttpServletRequest,
                     fetchTPCardService: FetchTPCardService,
                     fetchLPCardService: FetchLPCardService,
                     fetchLPPalmService: FetchLPPalmService,
                     fetchCaseInfoService: FetchCaseInfoService,
                     tPCardService: TPCardService,
                     lPCardService: LPCardService,
                     lPPalmService: LPPalmService,
                     caseInfoService: CaseInfoService) extends RpcServerMessageFilter{
  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean ={
    if(commandRequest.hasExtension(SyncTPCardRequest.cmd)) {
      val request = commandRequest.getExtension(SyncTPCardRequest.cmd)
      val responseBuilder = SyncTPCardResponse.newBuilder()
      val dbId = Option(request.getDbid)
      val ip = httpServletRequest.getRemoteAddr
      //验证是否有权限
      val hallReadConfig = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, "TPCard", request.getDbid, "1").headOption
      if(hallReadConfig.nonEmpty){
        val cardIdList = fetchTPCardService.fetchCardId(request.getSeq, request.getSize, dbId)
        cardIdList.foreach{ cardId =>
          responseBuilder.setSeq(cardId._2)
          if(tPCardService.isExist(cardId._1, dbId)){
            val tpCard = tPCardService.getTPCard(cardId._1, dbId)
            if(fetchTPCardService.validateByReadStrategy(tpCard, hallReadConfig.get.readStrategy)){
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
      }
      commandResponse.writeMessage(commandRequest, SyncTPCardResponse.cmd, responseBuilder.build())
      true
    }else if(commandRequest.hasExtension(SyncLPCardRequest.cmd)){
      val request = commandRequest.getExtension(SyncLPCardRequest.cmd)
      val responseBuilder = SyncLPCardResponse.newBuilder()
      val dbId = Option(request.getDbid)
      val ip = httpServletRequest.getRemoteAddr
      //验证是否有权限
      val hallReadConfig = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, "LPCard", request.getDbid, "1").headOption
      if(hallReadConfig.nonEmpty){
        val cardIdList = fetchLPCardService.fetchCardId(request.getSeq, request.getSize, dbId)
        cardIdList.foreach{cardId =>
          responseBuilder.setSeq(cardId._2)
          if(lPCardService.isExist(cardId._1, dbId)){
            val lPCard = lPCardService.getLPCard(cardId._1, dbId)
            if(fetchLPCardService.validateByReadStrategy(lPCard, hallReadConfig.get.readStrategy)){
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
      }
      commandResponse.writeMessage(commandRequest, SyncLPCardResponse.cmd, responseBuilder.build())
      true
    }else if(commandRequest.hasExtension(SyncLPPalmRequest.cmd)){
      val request = commandRequest.getExtension(SyncLPPalmRequest.cmd)
      val responseBuilder = SyncLPPalmResponse.newBuilder()
      val dbId = Option(request.getDbid)
      val ip = httpServletRequest.getRemoteAddr
      //验证是否有权限
      val hallReadConfig = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, "LPPalm", request.getDbid, "1").headOption
      if(hallReadConfig.nonEmpty){
        val cardIdList = fetchLPPalmService.fetchCardId(request.getSeq, request.getSize, dbId)
        cardIdList.foreach{cardId =>
          responseBuilder.setSeq(cardId._2)
          if(lPPalmService.isExist(cardId._1, dbId)){
            val lPCard = lPPalmService.getLPCard(cardId._1, dbId)
            if(fetchLPPalmService.validateByReadStrategy(lPCard, hallReadConfig.get.readStrategy)){
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
      }
      commandResponse.writeMessage(commandRequest, SyncLPPalmResponse.cmd, responseBuilder.build())
      true
    }else if(commandRequest.hasExtension(SyncCaseRequest.cmd)){
      val request = commandRequest.getExtension(SyncCaseRequest.cmd)
      val responseBuilder = SyncCaseResponse.newBuilder()
      val dbId = Option(request.getDbid)
      val ip = httpServletRequest.getRemoteAddr
      //验证是否有权限
      val hallReadConfig = HallReadConfig.find_by_ip_and_typ_and_dbid_and_deletag(ip, "CaseInfo", request.getDbid, "1").headOption
      if(hallReadConfig.nonEmpty){
        val caseIdList = fetchCaseInfoService.fetchCaseId(request.getSeq, request.getSize, dbId)
        caseIdList.foreach{caseId =>
          if(tPCardService.isExist(caseId._1, dbId)){
            val caseInfo = caseInfoService.getCaseInfo(caseId._1, dbId)
            if(fetchCaseInfoService.validateByReadStrategy(caseInfo, hallReadConfig.get.readStrategy)){
              val syncCaseInfo = responseBuilder.addSyncCaseBuilder()
              syncCaseInfo.setCaseInfo(caseInfo)
              syncCaseInfo.setOperationType(OperationType.PUT)
              syncCaseInfo.setSeq(caseId._2)
            }
          }else{
            val syncCaseInfo = responseBuilder.addSyncCaseBuilder()
            val caseInfo = Case.newBuilder().setStrCaseID(caseId._1)
            syncCaseInfo.setCaseInfo(caseInfo)
            syncCaseInfo.setOperationType(OperationType.DEL)
            syncCaseInfo.setSeq(caseId._2)
          }
        }
      }

      commandResponse.writeMessage(commandRequest, SyncCaseResponse.cmd, responseBuilder.build())
      true
    }else{
      handler.handle(commandRequest, commandResponse)
    }
  }

}
