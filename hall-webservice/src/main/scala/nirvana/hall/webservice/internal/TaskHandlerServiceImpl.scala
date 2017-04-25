package nirvana.hall.webservice.internal

import javax.activation.DataHandler
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.services._
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.TaskHandlerService
import nirvana.hall.webservice.util.FPTConvertToProtoBuffer

/**
  * Created by yuchen on 2017/4/25.
  */
class TaskHandlerServiceImpl(hallWebserviceConfig: HallWebserviceConfig,
                           tPCardService: TPCardService,
                           lPCardService: LPCardService,
                           caseInfoService: CaseInfoService,
                           queryService: QueryService,
                           assistCheckRecordService: AssistCheckRecordService,
                           implicit val dataSource: DataSource) extends TaskHandlerService with LoggerSupport{
  override def queryTaskHandler(dataHandler: DataHandler,source:String): Unit = {
    val result = FPTFile.parseFromInputStream(dataHandler.getInputStream,AncientConstants.GBK_ENCODING)
    val bytes = result match{
      case Left(fpt3)=>
        throw new Exception("Not support FPT V3.0")
      case Right(fpt4)=>
        val taskId = fpt4.sid
        var oraSid = 0L
        if(fpt4.logic02Recs.length>0){
          fpt4.logic02Recs.foreach{ sLogic02Rec =>
            val tpCard = FPTConvertToProtoBuffer.TPFPT2ProtoBuffer(sLogic02Rec, hallWebserviceConfig.hallImageUrl)
            tPCardService.addTPCard(tpCard)
          }
          val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskProtoBuffer(fpt4)
          oraSid = queryService.sendQuery(matchTask)
          assistCheckRecordService.recordAssistCheck(taskId,oraSid.toString,"",matchTask.getMatchId,source)
          return
        }else if(fpt4.logic03Recs.length>0){
          val lPCard = FPTConvertToProtoBuffer.FPT2LPProtoBuffer(fpt4)
          val caseInfo = FPTConvertToProtoBuffer.FPT2CaseProtoBuffer(fpt4)
          val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskCaseProtoBuffer(fpt4)
          lPCardService.addLPCard(lPCard)
          caseInfoService.addCaseInfo(caseInfo)
          oraSid = queryService.sendQuery(matchTask)
          assistCheckRecordService.recordAssistCheck(taskId,oraSid.toString,caseInfo.getStrCaseID,"",source)
          return
        }else{
          info("success:xingzhuanTaskCronServiceImpl:返回空FPT文件")
          return
        }
    }
  }
}
