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
    * @param cardId
    * @param dbid
    */
  override def logicTJudge(cardId: String,dbid :Option[String]): Option[String] = {
      return dbid
  }

  /**
    * 获取案件逻辑库
    *
    * @param caseId
    * @param dbid
    */
  override def logicLJudge(caseId: String,dbid :Option[String]): Option[String] = {
    return dbid
  }

  /**
    * 获取远程任务逻辑库
    *
    * @param remoteIP
    */
  override def logicRemoteJudge(remoteIP: Option[String], logicCategory: Short): Option[String] = ???
}
