package nirvana.hall.v70.internal

import java.util.UUID
import javax.persistence.EntityManager
import nirvana.hall.api.services.AssistCheckRecordService
import nirvana.hall.api.webservice.util.AFISConstant
import nirvana.hall.v70.jpa.HallAssistCheck
import stark.activerecord.services.ActiveRecord
import scala.collection.mutable.{HashMap, ListBuffer}

/**
  * Created by yuchen on 2017/4/21.
  */
class AssistCheckRecordServiceImpl(entityManager: EntityManager) extends AssistCheckRecordService{
  override def recordAssistCheck(queryId: String, oraSid: String, caseId: String, personId: String, source: String): Unit = {
    new HallAssistCheck(UUID.randomUUID.toString.replace("-",""),queryId,oraSid,caseId,personId,source,AFISConstant.NO_REPORTED).save
  }

  override def findAssistcheck(size: String): ListBuffer[HashMap[String,Any]] = {
    throw new UnsupportedOperationException
  }

  override def updateAssistcheckStatus(status:String, id:String): Unit = {
    HallAssistCheck.update.set(status = status).where(HallAssistCheck.id === id).execute
  }

  override def findUploadCheckin(uploadTime:String, size: String): ListBuffer[HashMap[String,Any]] = {
    throw new UnsupportedOperationException
  }

  override def saveXcReport(serviceid:String, msg:String): Unit = {
    throw new UnsupportedOperationException
  }

}
