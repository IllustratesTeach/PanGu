package nirvana.hall.api.services.sync

/**
  * Created by zhangjinqi on 2017/4/18.
  */
trait LogicDBJudgeService {

  /**
    * 获取逻辑库
    * @param caseId
    * @param dbid
    */
  def logicJudge(caseId: String,dbid :Option[String],cardType: String): Option[String]

  /**
    * 获取远程任务逻辑库
    * @param remoteIP
    */
  def logicRemoteJudge(remoteIP: Option[String], logicCategory: Short): Option[String]
}