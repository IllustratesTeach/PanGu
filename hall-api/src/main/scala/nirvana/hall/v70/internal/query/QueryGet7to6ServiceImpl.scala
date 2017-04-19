package nirvana.hall.v70.internal.query

import java.util.{Date, UUID}

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.{HallApiConstants, HallApiErrorConstants}
import nirvana.hall.api.services.remote._
import nirvana.hall.api.services._
import nirvana.hall.api.services.sync.LogicDBJudgeService
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.internal.{HttpHeaderUtils}
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa._
import nirvana.hall.v70.services.query.QueryGet7to6Service
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.transaction.annotation.Transactional

/**
 * 7.0获取发送远程6.2的比对结果
 */
class QueryGet7to6ServiceImpl(v70Config: HallV70Config,
                              queryRemoteService: QueryRemoteService,
                              tPCardRemoteService: TPCardRemoteService,
                              lPCardRemoteService: LPCardRemoteService,
                              lPPalmRemoteService: LPPalmRemoteService,
                              caseInfoRemoteService: CaseInfoRemoteService,
                              tpCardService:TPCardService,
                              lpCardService: LPCardService,
                              lpPalmService: LPPalmService,
                              syncInfoLogManageService: SyncInfoLogManageService,
                              logicDBJudgeService: LogicDBJudgeService,
                              caseInfoService: CaseInfoService)
  extends QueryGet7to6Service with LoggerSupport{

  /**
   * 定时任务
    * @param periodicExecutor
   */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, queryGet7to6Service: QueryGet7to6Service): Unit = {
    if(v70Config.cron.query7to6Cron != null){
      periodicExecutor.addJob(new CronSchedule(v70Config.cron.query7to6Cron), "query-get-70to62", new Runnable {
        override def run(): Unit = {
          queryGet7to6Service.doWork
        }
      })
    }
  }
  @Transactional
  override def doWork: Unit ={
    getGafisNormalqueryQueryqueMatching().foreach{ gafisQuery =>
      info("query-get-70to62 info[oraSid:{} keyId:{} type:{}]", gafisQuery.oraSid , gafisQuery.keyid, gafisQuery.querytype)
      if(getQueryAndSaveMatchResult(gafisQuery)){
        doWork
      }
    }
  }
  /**
   * 从6.2获取查询结果，保存候选列表信息
    * @param queryque
   * @return
   */
  @Transactional
  override def getQueryAndSaveMatchResult(queryque: GafisNormalqueryQueryque): Boolean= {
    val gafisQuery7to6 = GafisQuery7to6.findOption(queryque.oraSid)
    val uuid = UUID.randomUUID().toString
    if(gafisQuery7to6.isEmpty){
      error("没有对应的远程查询id")
      return false
    }else{
      val taskId = queryque.keyid
      try {
        val queryConfig = RemoteQueryConfig.find(queryque.syncTargetSid)
        val url = queryConfig.url
        val headerMap = HttpHeaderUtils.getV62HeaderMap(queryConfig.config)
        val matchResult = queryRemoteService.getQuery(gafisQuery7to6.get.queryId, url, headerMap, uuid)
        //逻辑分库处理
        val remoteIP = headerMap.get("X-V62-HOST")
        var dbid:Option[String] = Some("")

        if (matchResult != null) {
          //写入比对结果
          ProtobufConverter.convertMatchResult2GafisNormalqueryQueryque(matchResult, queryque)

          queryque.finishtime = new Date()
          queryque.status = QueryConstants.STATUS_SUCCESS
          queryque.save()
          //如果候选列表里的编号在本地没有数据，远程获取数据
          val candList = matchResult.getCandidateResultList
          val candIter = candList.iterator()
          while (candIter.hasNext) {
            val cand = candIter.next()
            if (queryque.querytype == QueryConstants.QUERY_TYPE_TT || queryque.querytype == QueryConstants.QUERY_TYPE_LT) {
              //获取捺印信息
              dbid = logicDBJudgeService.logicRemoteJudge(remoteIP, queryque.querytype)
              if (GafisPerson.findOption(cand.getObjectId).isEmpty) {
                val dbId = HttpHeaderUtils.getDBIDBySyncTagert(queryConfig.config, HttpHeaderUtils.DB_KEY_TPLIB)
                val tPCard = tPCardRemoteService.getTPCard(cand.getObjectId, url, dbId, headerMap)
                tPCard.foreach { tpCard =>
                  //TODO 目前候选获取的数据存到本地库，后期会考虑存到远程库
                  tpCardService.addTPCard(tpCard, dbid)
                }
              }
              syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, taskId, HallApiConstants.REMOTE_TYPE_MATCH_RESULT_TPCARD, headerMap.get("X-V62-HOST").get,"0","1")
            } else if (queryque.flag == QueryConstants.FLAG_PALM || queryque.flag == QueryConstants.FLAG_PALM_TEXT) {
              //获取现场信息
              dbid = logicDBJudgeService.logicRemoteJudge(remoteIP, queryque.querytype)
              val cardId = cand.getObjectId
              if (GafisCasePalm.findOption(cardId).isEmpty) {
                val dbId = HttpHeaderUtils.getDBIDBySyncTagert(queryConfig.config, HttpHeaderUtils.DB_KEY_LPLIB)
                val lPPalm = lPPalmRemoteService.getLPPalm(cardId, url, dbId, headerMap)
                lPPalm.foreach { lpPalm =>
                  val caseId = lpPalm.getText.getStrCaseId
                  if (caseId != null && caseId.length > 0) {
                    //如果本地没有对应的案件信息，先远程验证是否存在案件信息,远程获取案件到本地
                    if (GafisCase.findOption(caseId).isEmpty) {
                      caseInfoRemoteService.isExist(caseId, url, dbId)
                      val caseInfoOpt = caseInfoRemoteService.getCaseInfo(caseId, url, dbId, headerMap)
                      //如果配置文件中有案件的逻辑分库，则根据配置的逻辑分库查到对应案件，存入本地（7）使用的逻辑分库依旧是根据接收端（6.2通讯服务器IP）IP区分
                      caseInfoOpt.foreach(caseInfoService.addCaseInfo(_, dbid))
                    }
                  }
                  lpPalmService.addLPCard(lpPalm)
                }
              }
              syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, taskId, HallApiConstants.REMOTE_TYPE_MATCH_RESULT_LPPALM, headerMap.get("X-V62-HOST").get,"0","1")
            } else {
              //获取现场信息
              dbid = logicDBJudgeService.logicRemoteJudge(remoteIP, queryque.querytype)
              val cardId = cand.getObjectId
              if (GafisCaseFinger.findOption(cardId).isEmpty) {
                val dbId = HttpHeaderUtils.getDBIDBySyncTagert(queryConfig.config, HttpHeaderUtils.DB_KEY_LPLIB)
                val lPCard = lPCardRemoteService.getLPCard(cardId, url, dbId, headerMap)
                lPCard.foreach { lpCard =>
                  val caseId = lpCard.getText.getStrCaseId
                  if (caseId != null && caseId.length > 0) {
                    //如果本地没有对应的案件信息，先远程验证是否存在案件信息,远程获取案件到本地
                    if (GafisCase.findOption(caseId).isEmpty) {
                      caseInfoRemoteService.isExist(caseId, url, dbId)
                      val caseInfoOpt = caseInfoRemoteService.getCaseInfo(caseId, url, dbId, headerMap)
                      caseInfoOpt.foreach(caseInfoService.addCaseInfo(_, dbid))
                    }
                  }
                  lpCardService.addLPCard(lpCard)
                }
              }
              syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, taskId, HallApiConstants.REMOTE_TYPE_MATCH_RESULT_LPCARD, headerMap.get("X-V62-HOST").get,"0","1")
            }
          }
          return true
        }
      } catch {
        case e: nirvana.hall.support.internal.CallRpcException =>
          val eInfo = ExceptionUtil.getStackTraceInfo(e)
          error("MatchTask-RequestData fail,uuid:{};taskId:{};错误堆栈信息:{};错误信息:{}",uuid,taskId,eInfo,e.getMessage)
          syncInfoLogManageService.recordSyncDataLog(uuid, taskId, null, eInfo, 2, HallApiErrorConstants.SEND_REMOTE_RESPONSE_UNKNOWN + HallApiConstants.REMOTE_TYPE_MATCH_RESULT)
        case e: Exception =>
          val eInfo = ExceptionUtil.getStackTraceInfo(e)
          error("MatchTask-RequestData fail,uuid:{};taskId:{};错误堆栈信息:{};错误信息:{}",uuid,taskId,eInfo,e.getMessage)
          syncInfoLogManageService.recordSyncDataLog(uuid, taskId, null, eInfo, 2, HallApiErrorConstants.GET_REMOTE_RESULT_FAIL + HallApiConstants.REMOTE_TYPE_MATCH_RESULT)
      }
      return false
    }

  }

  /**
   * 获取正在比对的6.2查询任务
    * @return
   */
  override def getGafisNormalqueryQueryqueMatching(): Option[GafisNormalqueryQueryque] = {
    GafisNormalqueryQueryque.where(GafisNormalqueryQueryque.status === QueryConstants.STATUS_MATCHING).and(GafisNormalqueryQueryque.deletag === "1").and(GafisNormalqueryQueryque.syncTargetSid[String].notNull).orderBy(GafisNormalqueryQueryque.priority[java.lang.Short].desc).headOption
  }
}
