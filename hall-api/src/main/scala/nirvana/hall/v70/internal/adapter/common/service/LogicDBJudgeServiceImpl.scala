package nirvana.hall.v70.internal.adapter.common.service

import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.services.sync.LogicDBJudgeService
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.internal.adapter.common.jpa.{GafisLogicDb, GafisLogicDbRule}
import org.apache.tapestry5.json.JSONObject

/**
  * Created by zhangjinqi on 2017/3/31.
  */
class LogicDBJudgeServiceImpl extends LogicDBJudgeService {

  def logicJudge(cardId: String,dbid :Option[String],cardType: String): Option[String] = {
    var cType = "0"
    if (cardType.equals(HallApiConstants.SYNC_TYPE_LPCARD)) {
      cType = "1"
    }
    //逻辑分库处理
    var logicDbPkid = ""
    //获取所有比对规则list
    val logicDbRules = GafisLogicDbRule.select(GafisLogicDbRule.logicRule).where(GafisLogicDbRule.logicRule.notNull and GafisLogicDbRule.logicCategory === cType)
    //先获取规则为空的库，赋初值,默认库的禁起用标识必须是1，否则会出错
    val logicDbRule = GafisLogicDbRule.where(GafisLogicDbRule.logicRule.isNull and GafisLogicDbRule.logicCategory === cType).headOption
    logicDbRule match {
      case Some(logicDbRule) =>
        logicDbPkid = logicDbRule.pkId
      case _ =>
    }
    var head = "tmp"
    var exclusive = "tmp"
    val list = logicDbRules.toList
    for (i <- 0 until list.length) {
      val json  = new JSONObject(list(i).toString)
      if(json.has("head")){
        head = json.getString("head")
      }
      if(json.has("exclusive")){
        exclusive = json.getString("exclusive")
      }
      if (cardId.startsWith(head) && !cardId.startsWith(exclusive)) {
        val logicDbRule = GafisLogicDbRule.where(GafisLogicDbRule.logicRule === list(i) and GafisLogicDbRule.logicCategory === cType).headOption
        logicDbPkid = logicDbRule.get.pkId
      }
      head = "tmp"
      exclusive = "tmp"
    }
    Option(logicDbPkid)
  }

  def logicRemoteJudge(remoteIP: Option[String], logicCategory: Short): Option[String] = {
    //逻辑分库处理
    var logicDbPkid = ""
    var logicDb:Option[GafisLogicDb] = None
    if (logicCategory == QueryConstants.QUERY_TYPE_TT || logicCategory == QueryConstants.QUERY_TYPE_LT) {
      logicDb = GafisLogicDb.where(GafisLogicDb.logicName === remoteIP.get and GafisLogicDb.logicCategory === "0" and GafisLogicDb.logicDeltag === "1").headOption
    } else {
      logicDb = GafisLogicDb.where(GafisLogicDb.logicName === remoteIP.get and GafisLogicDb.logicCategory === "1" and GafisLogicDb.logicDeltag === "1").headOption
    }
    logicDb match {
      case Some(logicDb) =>
        logicDbPkid = logicDb.pkId
      case _ =>
    }
    Option(logicDbPkid)
  }
}
