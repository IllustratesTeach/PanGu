package nirvana.hall.webservice.internal

import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.{AssistCheckRecordService, CaseInfoService, QueryService, TPCardService}
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.webservice.services.SendQueryService

/**
  * Created by win-20161010 on 2017/5/31.
  */
class SendQueryServiceImpl(queryService: QueryService
                           ,assistCheckRecordService: AssistCheckRecordService
                           ,caseInfoService: CaseInfoService
                           ,tPCardService: TPCardService
                           ,fPTService: FPTService) extends SendQueryService{
  override def sendQuery(fPTFile: FPT4File,id:String): Unit = {
    val queryId = fPTFile.sid
    if (fPTFile.logic03Recs.length > 0) {
      fPTFile.logic03Recs.foreach { sLogic03Rec =>
        if(!caseInfoService.isExist(sLogic03Rec.caseId)){
          fPTService.addLogic03Res(sLogic03Rec)
        }
        var oraSid = 0L
        var fingerId = ""
        var status = 0
        if(sLogic03Rec.fingers.length>0){
          sLogic03Rec.fingers.foreach{ finger =>
            fingerId = finger.fingerId
            for(i<- 0 to 3){
              try{
                status = 7
                oraSid = queryService.sendQueryByCardIdAndMatchType(fingerId, MatchType.FINGER_LT)
                assistCheckRecordService.updateAssistcheckLT(queryId, oraSid.toString, fingerId,id,status,"")
              }catch{
                case e:Exception=> error(ExceptionUtil.getStackTraceInfo(e))
                  status = -2
                  assistCheckRecordService.updateAssistcheckLT(queryId, oraSid.toString, fingerId,id,status,ExceptionUtil.getStackTraceInfo(e))
              }
              if(status == 7){
                return
              }
            }

          }
        }
      }
    } else if(fPTFile.logic02Recs.length > 0) {
      fPTFile.logic02Recs.foreach{ sLogic02Rec =>
        if(!tPCardService.isExist(sLogic02Rec.cardId)){
          fPTService.addLogic02Res(sLogic02Rec)
        }
        var oraSid = 0L
        var status = 0
        for(i<- 0 to 3){
          try {
            status = 7
            oraSid = queryService.sendQueryByCardIdAndMatchType(sLogic02Rec.personId, MatchType.FINGER_TT)
            assistCheckRecordService.updateAssistcheckTT(queryId, oraSid.toString, sLogic02Rec.personId, id, status, "")
          }catch{
            case e:Exception=> error(ExceptionUtil.getStackTraceInfo(e))
              status = -2
              assistCheckRecordService.updateAssistcheckTT(queryId, oraSid.toString, sLogic02Rec.personId,id,status,ExceptionUtil.getStackTraceInfo(e))
          }
        }
      }
    }
  }
}
