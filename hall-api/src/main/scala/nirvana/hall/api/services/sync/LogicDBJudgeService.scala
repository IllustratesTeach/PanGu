package nirvana.hall.api.services.sync

import nirvana.hall.protocol.api.FPTProto.TPCard

/**
  * Created by zhangjinqi on 2017/4/18.
  */
trait LogicDBJudgeService {
  /**
    * 获取捺印逻辑库
    * @param cardId
    * @param dbid
    */
  def logicTJudge(cardId: String,dbid :Option[String]): Option[String]

  /**
    * 获取案件逻辑库
    * @param caseId
    * @param dbid
    */
  def logicLJudge(caseId: String,dbid :Option[String]): Option[String]

  /**
    * 获取远程任务逻辑库
    * @param remoteIP
    */
  def logicRemoteJudge(remoteIP: Option[String], logicCategory: Short): Option[String]
}