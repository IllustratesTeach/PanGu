package nirvana.hall.webservice.internal.xingzhuan

import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.{CaseInfoService, QueryService, TPCardService}
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.webservice.services.xingzhuan.WsSendQueryService

/**
  * Created by yuchen on 2017/7/2.
  */
class WsSendQueryServiceImpl(queryService: QueryService
                             ,caseInfoService: CaseInfoService
                             ,tPCardService: TPCardService
                             ,fPTService: FPTService) extends WsSendQueryService with LoggerSupport{
  override def sendQuery(userId: String, password: String, dataHandler: DataHandler): Long = {
    val fptFile = FPTFile.parseFromInputStream(dataHandler.getInputStream, AncientConstants.GBK_ENCODING).right.get
    var oraSid = 0L
    if (fptFile.logic03Recs.length > 0) {
      fptFile.logic03Recs.foreach { sLogic03Rec =>
        if(!caseInfoService.isExist(sLogic03Rec.caseId)){
          fPTService.addLogic03Res(sLogic03Rec)
        }
        var fingerId = ""
        if(sLogic03Rec.fingers.length>0){
          sLogic03Rec.fingers.foreach{ finger =>
            fingerId = finger.fingerId
            try{
              oraSid = queryService.sendQueryByCardIdAndMatchType(fingerId, null,MatchType.FINGER_LT)
            }catch{
              case e:Exception => throwException(e)
            }
          }
        }
      }
    } else if(fptFile.logic02Recs.length > 0) {
      fptFile.logic02Recs.foreach{ sLogic02Rec =>
        if(!tPCardService.isExist(sLogic02Rec.cardId)){
          fPTService.addLogic02Res(sLogic02Rec)
        }
        try {
          oraSid = queryService.sendQueryByCardIdAndMatchType(sLogic02Rec.personId,null, MatchType.FINGER_TT)
        }catch{
          case e:Exception => throwException(e)
        }
      }
    }
    oraSid
  }

  private def throwException(e:Exception): Unit ={
    val exceptionStr = ExceptionUtil.getStackTraceInfo(e)
    error(ExceptionUtil.getStackTraceInfo(e))
    throw new Exception(exceptionStr)
  }
}
