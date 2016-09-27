package nirvana.hall.api.internal.sync

import monad.rpc.protocol.CommandProto.CommandStatus
import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.jpa.HallFetchConfig
import nirvana.hall.api.services.remote.{LPCardRemoteService, CaseInfoRemoteService, TPCardRemoteService}
import nirvana.hall.api.services.sync.{FetchQueryService, SyncCronService}
import nirvana.hall.api.services._
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.api.SyncDataProto._
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v70.internal.query.QueryConstants
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.apache.tapestry5.json.JSONObject

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/8/18.
 */
class SyncCronServiceImpl(apiConfig: HallApiConfig,
                          rpcHttpClient: RpcHttpClient,
                          tpCardService: TPCardService,
                          lPCardService: LPCardService,
                          lPPalmService: LPPalmService,
                          caseInfoService: CaseInfoService,
                          queryService: QueryService,
                          fetchQueryService: FetchQueryService,
                          tPCardRemoteService: TPCardRemoteService,
                          lPCardRemoteService: LPCardRemoteService,
                          caseInfoRemoteService: CaseInfoRemoteService) extends SyncCronService with LoggerSupport{

  final val SYNC_BATCH_SIZE = 1
  /**
   * 定时器，向6.2同步数据
   * @param periodicExecutor
   * @param syncCronService
   */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncCronService: SyncCronService): Unit = {
    if(apiConfig.sync.syncCron != null){
      periodicExecutor.addJob(new CronSchedule(apiConfig.sync.syncCron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin sync-cron")
          syncCronService.doWork
          info("end sync-cron")
        }
      })
    }
  }

  /**
   * 定时任务调用方法
   */
  override def doWork(): Unit = {
    HallFetchConfig.find_by_deletag("1").toSeq.foreach{ fetchConfig=>
      val strategy = new JSONObject(fetchConfig.writeStrategy)
      val update = if (strategy.has("update")) strategy.getBoolean("update") else true
      fetchConfig.typ match {
        case HallApiConstants.SYNC_TYPE_TPCARD =>
          fetchTPCard(fetchConfig, update)
        case HallApiConstants.SYNC_TYPE_LPCARD =>
          fetchLPCard(fetchConfig, update)
        case HallApiConstants.SYNC_TYPE_CASEINFO =>
        case HallApiConstants.SYNC_TYPE_LPPALM =>
          fetchLPPalm(fetchConfig, update)
        case HallApiConstants.SYNC_TYPE_MATCH_TASK =>
          fetchMatchTask(fetchConfig, update)
        case HallApiConstants.SYNC_TYPE_MATCH_RESULT =>
          fetchMatchResult(fetchConfig, update)
        case other =>
          warn("unsupport fetch type:{}", other)
      }
    }
  }

  /**
   * 执行抓取数据
   * @param fetchConfig
   */
  def fetchTPCard(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("syncTPCard name:{} timestamp:{}", fetchConfig.name, fetchConfig.seq)
    val request = SyncTPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setSeq(fetchConfig.seq)
    request.setDbid(fetchConfig.dbid)
    val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncTPCardRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val response = baseResponse.getExtension(SyncTPCardResponse.cmd)
      var seq = fetchConfig.seq
      val destDBID = Option(fetchConfig.destDbid)
      val iter = response.getSyncTPCardList.iterator()
      try {
        while (iter.hasNext) {
          val syncTPCard = iter.next()
          val tpCard = syncTPCard.getTpCard
          val cardId = tpCard.getStrCardID
          if (syncTPCard.getOperationType == OperationType.PUT &&
            validateTPCardByWriteStrategy(tpCard, fetchConfig.writeStrategy)) {
            //验证本地是否存在
            if (tpCardService.isExist(cardId, destDBID)) {
              if (update) {//更新
                tpCardService.updateTPCard(tpCard, destDBID)
                info("update TPCard:{}", cardId)
              }
            } else {
              tpCardService.addTPCard(tpCard, destDBID)
              info("add TPCard:{}", cardId)
            }
          } else if(syncTPCard.getOperationType == OperationType.DEL){
            tpCardService.delTPCard(cardId, destDBID)
            info("delete TPCard:{}", cardId)
          }
          seq = syncTPCard.getSeq
        }
        seq = response.getSeq
      }
      catch {
        case e: Exception =>
          e.printStackTrace()
          error(e.getMessage)
      }
      //如果获取到数据递归获取
      if(response.getSyncTPCardCount > 0 && fetchConfig.seq != seq){
        //更新配置seq
        fetchConfig.seq = seq
        updateSeq(fetchConfig)
        fetchTPCard(fetchConfig, update)
      }
    }
  }

  /**
   * 抓取现场指纹
   * @param fetchConfig
   * @param update
   */
  def fetchLPCard(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("syncLPCard name:{} timestamp:{}", fetchConfig.name, fetchConfig.seq)
    val request = SyncLPCardRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setSeq(fetchConfig.seq)
    request.setDbid(fetchConfig.dbid)
    val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncLPCardRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val response = baseResponse.getExtension(SyncLPCardResponse.cmd)
      var seq = fetchConfig.seq
      val destDBID = Option(fetchConfig.destDbid)
      val iter = response.getSyncLPCardList.iterator()
      try {
        while (iter.hasNext) {
          val syncLPCard = iter.next()
          var lpCard = syncLPCard.getLpCard
          val cardId = lpCard.getStrCardID
          if (syncLPCard.getOperationType == OperationType.PUT &&
            validateLPCardByWriteStrategy(lpCard, fetchConfig.writeStrategy)) {
            //如果没有案件编号，截掉指纹编号后两位作为案件编号
            var caseId = lpCard.getText.getStrCaseId
            if(caseId.trim.length == 0){
              caseId = cardId.substring(0, cardId.length - 2)
              val lPCardBuilder = lpCard.toBuilder
              lPCardBuilder.getTextBuilder.setStrCaseId(caseId)
              lpCard = lPCardBuilder.build()
            }
            //验证本地是否存在
            if (lPCardService.isExist(cardId, destDBID)) {
              if (update) {//更新
                lPCardService.updateLPCard(lpCard, destDBID)
                info("update LPCard:{}", cardId)
              }
            } else {
              //如果没有案件信息获取案件
              if(!caseInfoService.isExist(caseId, destDBID)){
                fetchCaseInfo(caseId, fetchConfig.url, Option(fetchConfig.dbid), destDBID)
              }
              lPCardService.addLPCard(lpCard, destDBID)
              info("add LPCard:{}", cardId)
            }
          } else if (syncLPCard.getOperationType == OperationType.PUT){
            lPCardService.delLPCard(cardId, destDBID)
            info("delete LPCard:{}", cardId)
          }
          seq = syncLPCard.getSeq
        }
        seq = response.getSeq
      }
      catch {
        case e: Exception =>
          e.printStackTrace()
          error(e.getMessage)
      }
      //如果获取到数据递归获取
      if(response.getSyncLPCardCount > 0 && fetchConfig.seq != seq){
        //更新配置seq
        fetchConfig.seq = seq
        updateSeq(fetchConfig)
        fetchLPCard(fetchConfig, update)
      }
    }
  }

  /**
   * 根据案件编号同步案件信息
   * 由于只有案件编号没有物理配置信息，多物理库同步的dbid使用现场的dbid，tableid=4
   * 如果没有案件信息不同步案件信息，6.2存在只有现场没有案件的情况
   * @param caseId
   */
  def fetchCaseInfo(caseId: String, url: String, dbId: Option[String] = None, destDbId: Option[String] = None): Unit ={
    info("syncCaseInfo caseId:{}", caseId)
    if(caseInfoRemoteService.isExist(caseId, url, dbId)){
      val caseInfoOpt = caseInfoRemoteService.getCaseInfo(caseId, url, dbId)
      caseInfoOpt.foreach(caseInfoService.addCaseInfo(_, destDbId))
    }else{
      //如果远程没有案件信息，系统自动新建一个案件，保证在7.0系统能够查询到数据
      warn("remote caseId:{} is not exist, system auto create", caseId)
      val caseInfo = Case.newBuilder()
      caseInfo.setStrCaseID(caseId)
      caseInfoService.addCaseInfo(caseInfo.build(), destDbId)
    }
  }

  /**
   * 抓取现场掌纹
   * @param fetchConfig
   * @param update
   */
  def fetchLPPalm(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("syncLPPalm name:{} timestamp:{}", fetchConfig.name, fetchConfig.seq)
    val request = SyncLPPalmRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setSeq(fetchConfig.seq)
    request.setDbid(fetchConfig.dbid)
    val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncLPPalmRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val response = baseResponse.getExtension(SyncLPPalmResponse.cmd)
      var seq = fetchConfig.seq
      val destDBID = Option(fetchConfig.destDbid)
      val iter = response.getSyncLPCardList.iterator()
      try {
        while (iter.hasNext) {
          val syncLPCard = iter.next()
          var lpCard = syncLPCard.getLpCard
          val cardId = lpCard.getStrCardID
          if (syncLPCard.getOperationType == OperationType.PUT &&
            validateLPCardByWriteStrategy(lpCard, fetchConfig.writeStrategy)) {
            //如果没有案件编号，截掉指纹编号后两位作为案件编号
            var caseId = lpCard.getText.getStrCaseId
            if(caseId.trim.length == 0){
              caseId = cardId.substring(0, cardId.length - 2)
              val lPCardBuilder = lpCard.toBuilder
              lPCardBuilder.getTextBuilder.setStrCaseId(caseId)
              lpCard = lPCardBuilder.build()
            }
            //验证本地是否存在
            if (lPPalmService.isExist(cardId, destDBID)) {
              if (update) {//更新
                lPPalmService.updateLPCard(lpCard, destDBID)
                info("update LPPalm:{}", cardId)
              }
            } else {
              //如果没有案件信息获取案件
              if(!caseInfoService.isExist(caseId, destDBID)){
                fetchCaseInfo(caseId, fetchConfig.url, Option(fetchConfig.dbid), destDBID)
              }
              lPPalmService.addLPCard(lpCard, destDBID)
              info("add LPPalm:{}", cardId)
            }
          } else if (syncLPCard.getOperationType == OperationType.PUT){
            lPPalmService.delLPCard(cardId, destDBID)
            info("delete LPPalm:{}", cardId)
          }
          seq = syncLPCard.getSeq
        }
        seq = response.getSeq
      }
      catch {
        case e: Exception =>
          e.printStackTrace()
          error(e.getMessage)
      }
      //如果获取到数据递归获取
      if(response.getSyncLPCardCount > 0 && fetchConfig.seq != seq){
        //更新配置seq
        fetchConfig.seq = seq
        updateSeq(fetchConfig)
        fetchLPPalm(fetchConfig, update)
      }
    }
  }

  /**
   * 抓取比对任务
   * @param fetchConfig
   * @param update
   */
  def fetchMatchTask(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("fetchMatchTask name:{} seq:{}", fetchConfig.name, fetchConfig.seq)
    val request = SyncMatchTaskRequest.newBuilder()
    request.setSize(SYNC_BATCH_SIZE)
    request.setDbid(fetchConfig.dbid)
    request.setSeq(fetchConfig.seq)

    val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncMatchTaskRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val response = baseResponse.getExtension(SyncMatchTaskResponse.cmd)
      var seq = fetchConfig.seq
      val iter = response.getMatchTaskList.iterator()
      try {
        while (iter.hasNext) {
          val matchTask = iter.next()
          if(validateMatchTaskByWriteStrategy(matchTask, fetchConfig.writeStrategy)){
            //TODO queryDBConfig 添加是否更新校验
            queryService.addMatchTask(matchTask)
            info("add MatchTask:{} type:{}", matchTask.getMatchId, matchTask.getMatchType)
            seq = matchTask.getObjectId
          }
        }
      } catch {
        case e: Exception =>
          e.printStackTrace()
          error(e.getMessage)
      }
      //如果获取到数据递归获取
      if(response.getMatchTaskCount > 0 && fetchConfig.seq != seq){
        //更新配置seq
        fetchConfig.seq = seq
        updateSeq(fetchConfig)
        fetchMatchTask(fetchConfig, update)
      }
    }
  }

  /**
   * 抓取比对结果候选列表
   * TODO 先查询比对状态是正在比对的任务sid，然后再根据sid获取比对结果
   * @param fetchConfig
   */
  def fetchMatchResult(fetchConfig: HallFetchConfig, update: Boolean): Unit ={
    info("fetchMatchTask name:{} seq:{}", fetchConfig.name, fetchConfig.seq)
    val request = SyncMatchResultRequest.newBuilder()
/*    val sidSeq = fetchQueryService.getSidByStatusMatching(SYNC_BATCH_SIZE, Option(fetchConfig.destDbid))
    if(sidSeq.nonEmpty){
      sidSeq.foreach{sid=>
        request.setSid(sid)
        request.setDbid(fetchConfig.dbid)

      }
    }*/
    request.setSid(fetchConfig.seq)
    request.setDbid(fetchConfig.dbid)
    val baseResponse = rpcHttpClient.call(fetchConfig.url, SyncMatchResultRequest.cmd, request.build())
    if(baseResponse.getStatus == CommandStatus.OK){
      val response = baseResponse.getExtension(SyncMatchResultResponse.cmd)
      val matchStatus = response.getMatchStatus
      if(matchStatus.getNumber > 2 && matchStatus != MatchStatus.UN_KNOWN){//大于2有候选信息
        val matchResult = response.getMatchResult
        if(validateMatchResultByWriteStrategy(matchResult, fetchConfig.writeStrategy)){
          //获取候选信息
          val candDBDIMap = fetchCandListDataByMatchResult(matchResult, fetchConfig)
          fetchQueryService.saveMatchResult(matchResult, fetchConfig: HallFetchConfig, candDBDIMap)
          info("add MatchResult:{} candNum:{}", matchResult.getMatchId, matchResult.getCandidateNum)
        }
        fetchConfig.seq += 1
        updateSeq(fetchConfig)
        //递归获取
        fetchMatchResult(fetchConfig, update)
      }
    }
  }
  /**
   * 循环候选列表，如果本地没有，远程获取候选数据保存到默认库
   * TODO 1,候选应该只存对应指位的信息，不存文本，存到远程库
   * 解决方法需要候选信息增加dbid信息
   * @param matchResult
   */
  private def fetchCandListDataByMatchResult(matchResult: MatchResult,fetchConfig: HallFetchConfig): Map[String, Short]={
    val candDBIDMap = Map[String, Short]()
    val queryQue = fetchQueryService.getQueryQue(matchResult.getMatchId.toInt)
    val dbidList = getDBIDList(fetchConfig, queryQue.queryType)
    val candIter = matchResult.getCandidateResultList.iterator()
    while (candIter.hasNext){
      val cand = candIter.next()
      val cardId = cand.getObjectId
      val candDbId = if(cand.getDbid.nonEmpty) Option(cand.getDbid) else None
      if(queryQue.queryType == QueryConstants.QUERY_TYPE_TT || queryQue.queryType == QueryConstants.QUERY_TYPE_LT){//候选是捺印
        val dbId = getTPDBIDByCardId(cardId, dbidList)
        if(dbId.nonEmpty){
          candDBIDMap.+(cardId -> dbId.get)
        }else{
          val tpCardOpt = tPCardRemoteService.getTPCard(cardId, fetchConfig.url)
          tpCardOpt.foreach(tpCardService.addTPCard(_))
          candDBIDMap.+(cardId -> V62Facade.DBID_TP_DEFAULT)
        }
      }else{//候选是现场
        val dbId = getLPDBIDByCardId(cardId, dbidList)
        if(dbId.nonEmpty){
          candDBIDMap.+(cardId -> dbId.get)
        }else{
          val lPCardOpt = lPCardRemoteService.getLPCard(cardId, fetchConfig.url, candDbId)
          lPCardOpt.foreach{ lpCard =>
            lPCardService.addLPCard(lpCard)
            val caseId = lpCard.getText.getStrCaseId
            if(!caseInfoService.isExist(caseId, candDbId)){//获取案件
              fetchCaseInfo(caseId, fetchConfig.url, Option(fetchConfig.dbid))
            }
            candDBIDMap.+(cardId -> V62Facade.DBID_LP_DEFAULT)
          }
        }
      }

    }

    candDBIDMap
  }

  /**
   * 根据捺印卡号查找所在DBID
   * @param cardId
   * @param dbidList
   * @return
   */
  private def getTPDBIDByCardId(cardId: String, dbidList: Seq[String]): Option[String]={
    dbidList.foreach{dbid =>
      if(tpCardService.isExist(cardId, Option(dbid))){
        return Option(dbid)
      }
    }
    None
  }

  /**
   * 根据现场卡号查找所在DBID
   * @param cardId
   * @param dbidList
   * @return
   */
  private def getLPDBIDByCardId(cardId: String, dbidList: Seq[String]): Option[String]={
    dbidList.foreach{dbid =>
      if(lPCardService.isExist(cardId, Option(dbid))){
        return Option(dbid)
      }
    }
    None
  }

  /**
   * 获取DBID列表
   * @param fetchConfig
   * @param queryType
   * @return
   */
  private def getDBIDList(fetchConfig: HallFetchConfig, queryType: Int): Seq[String]={
    val dbidList = new ArrayBuffer[String]()
    if(fetchConfig.config != null){
      val jsonObj= new JSONObject(fetchConfig.config)
      val key = queryType match {
        case QueryConstants.QUERY_TYPE_TT =>
          QueryConstants.FETCH_CONFIG_TPDB
        case QueryConstants.QUERY_TYPE_TL =>
          QueryConstants.FETCH_CONFIG_LPDB
        case QueryConstants.QUERY_TYPE_LT =>
          QueryConstants.FETCH_CONFIG_TPDB
        case QueryConstants.QUERY_TYPE_LL =>
          QueryConstants.FETCH_CONFIG_LPDB
        case other =>
          throw new RuntimeException("unsupport queryType:"+ queryType)
      }
      if(jsonObj.has(key)){
        val iter = jsonObj.getJSONArray(key).iterator()
        while (iter.hasNext){
          val db = iter.next()
          dbidList += db.toString
        }
      }
    }

    dbidList.toSeq
  }

  /**
   * 更新同步HallFetchConfig.seq 的值, 因为直接调用save方法，session问题,程序调用insert而不是update
   * @param fetchConfig
   */
  private def updateSeq(fetchConfig: HallFetchConfig): Unit ={
    HallFetchConfig.update.set(seq = fetchConfig.seq).where(HallFetchConfig.pkId === fetchConfig.pkId).execute
  }
}
