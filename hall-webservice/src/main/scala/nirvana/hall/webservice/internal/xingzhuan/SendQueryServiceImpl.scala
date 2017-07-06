package nirvana.hall.webservice.internal.xingzhuan

import java.sql.Timestamp

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.{AssistCheckRecordService, CaseInfoService, QueryService, TPCardService}
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.webservice.HallWebserviceConstants
import nirvana.hall.webservice.services.xingzhuan.SendQueryService

/**
  * Created by ssj on 2017/5/31.
  */
class SendQueryServiceImpl(queryService: QueryService
                           , assistCheckRecordService: AssistCheckRecordService
                           , caseInfoService: CaseInfoService
                           , tPCardService: TPCardService
                           , fPTService: FPTService) extends SendQueryService with LoggerSupport{
  override def sendQuery(fPTFile: FPT4File,id:String,custom1:String): Unit = {
    val queryId = fPTFile.sid
    val ts = new Timestamp(System.currentTimeMillis());
    try {
      if (fPTFile.logic03Recs.length > 0) {
        fPTFile.logic03Recs.foreach { sLogic03Rec =>
          if(!caseInfoService.isExist(sLogic03Rec.caseId)){
            fPTService.addLogic03Res(sLogic03Rec)
          }
          var oraSid = 0L
          var fingerId = ""
          val fingernos = custom1.split(",")
          if(sLogic03Rec.fingers.length>0){
            for(i<- 0 until fingernos.size){
              val fingerno  = "0" + fingernos(i)
              sLogic03Rec.fingers.foreach{ finger =>
                if (finger.fingerNo.equals(fingerno) || finger.fingerNo.equals(fingernos(i))) {
                  //判断指纹序号的格式“01”“011”或“1”“11”
                  fingerId = finger.fingerId
                  assistCheckRecordService.saveXcQuery(id, fingerId, HallWebserviceConstants.TaskXC, HallWebserviceConstants.Status, "", queryId,sLogic03Rec.caseId, "", ts)
                  try {
                    oraSid = queryService.sendQueryByCardIdAndMatchType(fingerId, MatchType.FINGER_LT)
                    assistCheckRecordService.updateXcQuery(id, fingerId, HallWebserviceConstants.TaskXC, HallWebserviceConstants.SucStatus, oraSid.toString, queryId, "", ts)
                  } catch {
                    case e: Exception => error(ExceptionUtil.getStackTraceInfo(e))
                      assistCheckRecordService.updateXcQuery(id, fingerId, 1, HallWebserviceConstants.ErrStatus, oraSid.toString, queryId, ExceptionUtil.getStackTraceInfo(e), ts)
                      throw new Exception(ExceptionUtil.getStackTraceInfo(e))
                  }
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
          assistCheckRecordService.saveXcQuery(id,sLogic02Rec.personId,2,HallWebserviceConstants.Status,"",queryId,sLogic02Rec.personId ,"", ts)
          try {
            oraSid = queryService.sendQueryByCardIdAndMatchType(sLogic02Rec.personId, MatchType.FINGER_TT)
            assistCheckRecordService.updateXcQuery(id,sLogic02Rec.personId,HallWebserviceConstants.TaskZT,HallWebserviceConstants.SucStatus,oraSid.toString,queryId,"", ts)
          }catch{
            case e:Exception=> error(ExceptionUtil.getStackTraceInfo(e))
              assistCheckRecordService.updateXcQuery(id,sLogic02Rec.personId,HallWebserviceConstants.TaskZT,HallWebserviceConstants.ErrStatus,oraSid.toString,queryId,ExceptionUtil.getStackTraceInfo(e), ts)
              throw new Exception(ExceptionUtil.getStackTraceInfo(e))
          }
        }
      }
      if (fPTFile.logic03Recs(0).caseId.toString==null||fPTFile.logic03Recs(0).caseId.toString==""){
        assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.SucStatus,"",fPTFile.logic02Recs(0).personId.toString)
      }else{
        assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.SucStatus,"",fPTFile.logic03Recs(0).caseId.toString)
      }
    }catch{
      case e:Exception=> error(ExceptionUtil.getStackTraceInfo(e))
        if (fPTFile.logic03Recs(0).caseId.toString==null||fPTFile.logic03Recs(0).caseId.toString==""){
          assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.ErrStatus,ExceptionUtil.getStackTraceInfo(e),fPTFile.logic02Recs(0).personId.toString)
        }else{
          assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.ErrStatus,ExceptionUtil.getStackTraceInfo(e),fPTFile.logic03Recs(0).caseId.toString)
        }
    }
  }
}
