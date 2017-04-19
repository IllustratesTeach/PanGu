package nirvana.hall.v62.internal

import nirvana.hall.api.services.sync.LogicDBJudgeService
import nirvana.hall.protocol.api.FPTProto.TPCard

/**
  * Created by zhangjinqi on 2017/4/18.
  */
class LogicDBJudgeServiceImpl extends LogicDBJudgeService {
  /**
    * 获取捺印逻辑库
    *
    * @param tpCard
    */
  override def logicTJudge(tpCard: TPCard): Option[String] = ???

  /**
    * 获取案件逻辑库
    *
    * @param caseId
    */
override def logicLJudge(caseId: String): Option[String] = ???

  /**
    * 获取远程任务逻辑库
    *
    * @param remoteIP
    */
  override def logicRemoteJudge(remoteIP: Option[String], logicCategory: Short): Option[String] = ???
}
