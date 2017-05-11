package nirvana.hall.webservice.internal

import javax.activation.DataHandler
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.services._
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.TaskHandlerService

/**
  * Created by yuchen on 2017/4/25.
  */
class TaskHandlerServiceImpl(hallWebserviceConfig: HallWebserviceConfig,
                           fptService: FPTService,
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
          //TODO 同Union4pfmipCronService.handlerTPcardData
          fpt4.logic02Recs.foreach{ logic02Rec =>
            fptService.addLogic02Res(logic02Rec)
          }
//          val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskProtoBuffer(fpt4)
//          oraSid = queryService.sendQuery(matchTask)
//          assistCheckRecordService.recordAssistCheck(taskId,oraSid.toString,AFISConstant.EMPTY,matchTask.getMatchId,source)
          return
        }else if(fpt4.logic03Recs.length>0){
          //TODO 同Union4pfmipCronService.handlerLPCardData
          fpt4.logic03Recs.foreach(fptService.addLogic03Res(_))
//          val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskCaseProtoBuffer(fpt4)
//          oraSid = queryService.sendQuery(matchTask)
//          assistCheckRecordService.recordAssistCheck(taskId,oraSid.toString,caseInfo.getStrCaseID,AFISConstant.EMPTY,source)
          return
        }else{
          info("success:xingzhuanTaskCronServiceImpl:返回空FPT文件")
          return
        }
    }
  }
}
