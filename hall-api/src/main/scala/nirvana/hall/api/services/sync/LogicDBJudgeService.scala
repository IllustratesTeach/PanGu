package nirvana.hall.api.services.sync

import nirvana.hall.protocol.api.FPTProto.TPCard

/**
  * Created by zhangjinqi on 2017/4/18.
  */
trait LogicDBJudgeService {
  /**
    * 获取捺印逻辑库
    * @param tpCard
    */
  def logicTJudge(tpCard:TPCard): Option[String]

  /**
    * 获取案件逻辑库
    * @param caseId
    */
  def logicLJudge(caseId: String): Option[String]

  /**
    * 获取远程任务逻辑库
    * @param remoteIP
    */
  def logicRemoteJudge(remoteIP: Option[String], logicCategory: Short): Option[String]
}