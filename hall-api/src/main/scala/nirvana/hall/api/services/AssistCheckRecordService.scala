package nirvana.hall.api.services

import scala.collection.mutable.{HashMap, ListBuffer}


/**
  * Created by yuchen on 2017/4/21.
  * 和每一个厂商对接，例如：公安部、海鑫等，都需要记录来源（即厂商标识）
  */
trait AssistCheckRecordService {

  def recordAssistCheck(queryId:String,oraSid:String,caseId:String,personId:String,source:String):Unit

  def findAssistcheck(size: String): ListBuffer[HashMap[String,Any]]

  def updateAssistcheckStatus(status:Long, id:String): Unit

  def updateAssistcheck(status:Long, id:String, ftpPath:String): Unit

  def findUploadCheckin(uploadTime:String, size: String): ListBuffer[HashMap[String,Any]]

  def saveXcReport(serviceid:String, typ:String, status:Long, fptPath:String): Unit

  def saveErrorReport(serviceid:String, typ:String, status:Long, msg:String): Unit

  def updateAssistcheckLT(queryId:String,oraSid:String,caseId:String, id:String): Unit

  def updateAssistcheckTT(queryId:String,oraSid:String,personId:String, id:String): Unit


}
