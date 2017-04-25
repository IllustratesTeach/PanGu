package nirvana.hall.api.services


/**
  * Created by yuchen on 2017/4/21.
  * 和每一个厂商对接，例如：公安部、海鑫等，都需要记录来源（即厂商标识）
  */
trait AssistCheckRecordService {

  def recordAssistCheck(queryId:String,oraSid:String,caseId:String,personId:String,source:String):Unit
}
