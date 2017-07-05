package nirvana.hall.webservice.internal

import java.sql.Timestamp

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
  override def sendQuery(fPTFile: FPT4File,id:String,custom1:String): Unit = {
    val queryId = fPTFile.sid
    val ts = new Timestamp(System.currentTimeMillis());
    if (fPTFile.logic03Recs.length > 0) {
      fPTFile.logic03Recs.foreach { sLogic03Rec =>
        if(!caseInfoService.isExist(sLogic03Rec.caseId)){
          fPTService.addLogic03Res(sLogic03Rec)
        }
        var oraSid = 0L
        var fingerId = ""
        var status = 0
        if(sLogic03Rec.fingers.length>0){
          val aa = custom1.split(",")
          for(i<- 0 to aa.size.toInt-1){
            val a  = "0" + aa(i)
            sLogic03Rec.fingers.foreach{ finger =>
              if (finger.fingerNo.equals(a) || finger.fingerNo.equals(aa(i))) {
                //判断指纹序号的格式“01”“011”或“1”“11”
                fingerId = finger.fingerId
                assistCheckRecordService.saveXcQuery(id, fingerId, 1, status, "", queryId, "", ts)
                try {
                  status = 7
                  oraSid = queryService.sendQueryByCardIdAndMatchType(fingerId, MatchType.FINGER_LT)
                  assistCheckRecordService.updateXcQuery(id, fingerId, 1, status, oraSid.toString, queryId, "", ts)
                } catch {
                  case e: Exception => error(ExceptionUtil.getStackTraceInfo(e))
                    status = -2
                    assistCheckRecordService.updateXcQuery(id, fingerId, 1, status, oraSid.toString, queryId, ExceptionUtil.getStackTraceInfo(e), ts)
                }
              }
            }
          }
          if (status == 7) {
            assistCheckRecordService.updateXcTask(id, 7)
          } else {
            assistCheckRecordService.updateXcTask(id, 6)
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
        assistCheckRecordService.saveXcQuery(id,sLogic02Rec.personId,2,status,"",queryId,"", ts)
        try {
          status = 7
          oraSid = queryService.sendQueryByCardIdAndMatchType(sLogic02Rec.personId, MatchType.FINGER_TT)
          assistCheckRecordService.updateXcQuery(id,sLogic02Rec.personId,2,status,oraSid.toString,queryId,"", ts)
        }catch{
          case e:Exception=> error(ExceptionUtil.getStackTraceInfo(e))
            status = -2
            assistCheckRecordService.updateXcQuery(id,sLogic02Rec.personId,2,status,oraSid.toString,queryId,ExceptionUtil.getStackTraceInfo(e), ts)
        }
        if(status == 7){
          assistCheckRecordService.updateXcTask(id,7)
        }else{
          assistCheckRecordService.updateXcTask(id,6)
        }
      }
    }
  }
}
