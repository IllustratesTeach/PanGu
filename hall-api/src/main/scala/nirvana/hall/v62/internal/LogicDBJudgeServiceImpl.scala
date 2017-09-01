package nirvana.hall.v62.internal

import nirvana.hall.api.services.sync.LogicDBJudgeService
import nirvana.hall.protocol.api.FPTProto.TPCard

/**
  * Created by zhangjinqi on 2017/4/18.
  */
class LogicDBJudgeServiceImpl extends LogicDBJudgeService {

  /**
    * 获取逻辑库
    * @param caseId
    * @param dbid
    */
  def logicJudge(caseId: String,dbid :Option[String],cardType: String): Option[String] = {
    return dbid
  }

  /**
    * 获取远程任务逻辑库
    *
    * @param remoteIP
    */
  override def logicRemoteJudge(remoteIP: Option[String], logicCategory: Short): Option[String] = ???
}
