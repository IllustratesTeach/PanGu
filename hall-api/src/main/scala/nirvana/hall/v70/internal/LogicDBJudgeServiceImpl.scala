package nirvana.hall.v70.internal

import nirvana.hall.api.services.sync.LogicDBJudgeService
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.jpa.{GafisLogicDb, GafisLogicDbRule}
import org.apache.tapestry5.json.JSONObject

/**
  * Created by zhangjinqi on 2017/3/31.
  */
class LogicDBJudgeServiceImpl extends LogicDBJudgeService {

  def logicTJudge(tpCard:TPCard): Option[String] = {
    //逻辑分库处理
    var logicDbPkid = ""
    //获取所有比对规则list
    val logicDbRules = GafisLogicDbRule.select(GafisLogicDbRule.logicRule).where(GafisLogicDbRule.logicRule.notNull and GafisLogicDbRule.logicCategory === "0")
    //先获取规则为空的库，赋初值,默认库的禁起用标识必须是1，否则会出错
    val logicDbRule = GafisLogicDbRule.where(GafisLogicDbRule.logicRule.isNull and GafisLogicDbRule.logicCategory === "0").headOption
    if (logicDbRule != None) {
      logicDbPkid = logicDbRule.get.pkId
    }
    val cardid = tpCard.getStrCardID
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
      if (cardid.startsWith(head) && !cardid.startsWith(exclusive)) {
        val logicDbRule = GafisLogicDbRule.where(GafisLogicDbRule.logicRule === list(i) and GafisLogicDbRule.logicCategory === "0").headOption
        logicDbPkid = logicDbRule.get.pkId
      }
      head = "tmp"
      exclusive = "tmp"
    }
    Option(logicDbPkid)
  }

  def logicLJudge(caseId: String): Option[String] = {
    //逻辑分库处理
    var logicDbPkid = ""
    //获取所有比对规则list
    val logicDbRules = GafisLogicDbRule.select(GafisLogicDbRule.logicRule).where(GafisLogicDbRule.logicRule.notNull and GafisLogicDbRule.logicCategory === "1")
    //先获取规则为空的库，赋初值,默认库的禁起用标识必须是1，否则会出错
    var logicDbRule = GafisLogicDbRule.where(GafisLogicDbRule.logicRule.isNull and GafisLogicDbRule.logicCategory === "1").headOption
    if (logicDbRule != None) {
      logicDbPkid = logicDbRule.get.pkId
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
      if (caseId.startsWith(head) && !caseId.startsWith(exclusive)) {
        logicDbRule = GafisLogicDbRule.where(GafisLogicDbRule.logicRule === list(i) and GafisLogicDbRule.logicCategory === "1").headOption
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
    if (logicDb != None) {
      logicDbPkid = logicDb.get.pkId
    } else {
      return None
    }
    Option(logicDbPkid)
  }
}
