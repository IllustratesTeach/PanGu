package nirvana.hall.v70.internal

import java.util.UUID

import nirvana.hall.api.services.AssistCheckRecordService
import nirvana.hall.api.webservice.util.AFISConstant
import nirvana.hall.v70.jpa.HallAssistCheck

/**
  * Created by yuchen on 2017/4/21.
  */
class AssistCheckRecordServiceImpl extends AssistCheckRecordService{
  override def recordAssistCheck(queryId: String, oraSid: String, caseId: String, personId: String, source: String): Unit = {
    new HallAssistCheck(UUID.randomUUID.toString.replace("-",""),queryId,oraSid,caseId,personId,source,AFISConstant.NO_REPORTED).save
  }
}
