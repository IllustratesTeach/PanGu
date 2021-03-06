package nirvana.hall.v70.internal.query

import java.util.{Date, UUID}
import javax.persistence.EntityManager

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.internal.sync.SyncErrorConstants
import nirvana.hall.api.services.SyncInfoLogManageService
import nirvana.hall.protocol.api.QueryProto.{QuerySendRequest, QuerySendResponse}
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.internal.HttpHeaderUtils
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.internal.adapter.common.jpa.{GafisNormalqueryQueryque, GafisQuery7to6, RemoteQueryConfig}
import nirvana.hall.v70.services.query.Query7to6Service
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 15/12/9.
 */
class Query7to6ServiceImpl(v70Config: HallV70Config, rpcHttpClient: RpcHttpClient, entityManager: EntityManager, syncInfoLogManageService: SyncInfoLogManageService)
  extends Query7to6Service with LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, query7to6Service: Query7to6Service): Unit = {
    if(v70Config.cron.query7to6Cron != null){
      periodicExecutor.addJob(new CronScheduleWithStartModel(v70Config.cron.query7to6Cron, StartAtDelay), "query-70to62", new Runnable {
        override def run(): Unit = {
          query7to6Service.doWork
        }
      })
    }
  }

  override def doWork: Unit ={
    getGafisNormalqueryQueryqueWait.foreach{ gafisQuery =>
      info("remote-query info[oraSid:{} keyId:{} type:{}]", gafisQuery.oraSid , gafisQuery.keyid, gafisQuery.querytype)
      sendQuery(gafisQuery)
      //递归调用
      doWork
    }
  }

  /**
   * 获取一条等待比对的查询任务
   * @return
   */
  override def getGafisNormalqueryQueryqueWait: Option[GafisNormalqueryQueryque] ={
    GafisNormalqueryQueryque.where(GafisNormalqueryQueryque.status === QueryConstants.STATUS_WAIT).and(GafisNormalqueryQueryque.deletag === "1").and(GafisNormalqueryQueryque.syncTargetSid[String].notNull).orderBy(GafisNormalqueryQueryque.priority[java.lang.Short].desc).orderBy(GafisNormalqueryQueryque.oraSid).headOption
  }

  /**
   * 发送比对任务
   * @param gafisQuery
   * @return
   */
  @Transactional
  override def sendQuery(gafisQuery: GafisNormalqueryQueryque): Unit = {
    val uuid = UUID.randomUUID().toString
    var taskId = ""
    try {
      val matchTask = ProtobufConverter.convertGafisNormalqueryQueryque2MatchTask(gafisQuery)
      val request = QuerySendRequest.newBuilder().setMatchTask(matchTask)
      request.setUuid(uuid)
      taskId = matchTask.getMatchId

      //获取远程查询配置
      val queryConfig = RemoteQueryConfig.find(gafisQuery.syncTargetSid)
      val headerMap = HttpHeaderUtils.getHeaderMapOfQueryConfig(queryConfig.config, matchTask.getMatchType)
      val respnose = rpcHttpClient.call(queryConfig.url, QuerySendRequest.cmd, request.build(), headerMap)
      val querySendResponse = respnose.getExtension(QuerySendResponse.cmd)
      //记录关联62的查询任务号, TODO 删除GafisQuery7to6表 远程查询ID 存到GafisNormalqueryQueryque.queryid字段，获取比对结果一并修改，同时兼顾来自远方的查询
      GafisQuery7to6.delete.where(GafisQuery7to6.oraSid === gafisQuery.oraSid)//删除原有的6.2比对关系，防止重复发6.2远程比对后GafisQuery7to6主键唯一约束报错
      new GafisQuery7to6(gafisQuery.oraSid, querySendResponse.getOraSid).save()
      //更新比对状态为正在比对
      GafisNormalqueryQueryque.update.set(status = QueryConstants.STATUS_MATCHING, begintime = new Date()).where(GafisNormalqueryQueryque.pkId === gafisQuery.pkId).execute
      syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, taskId, HallApiConstants.REMOTE_TYPE_MATCH_TASK, headerMap.get("X-V62-HOST").get,"0","1")
    }
    catch {
      case e: nirvana.hall.support.internal.CallRpcException =>
        GafisNormalqueryQueryque.update.set(status= QueryConstants.STATUS_FAIL, oracomment = e.getMessage).where(GafisNormalqueryQueryque.pkId === gafisQuery.pkId).execute
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        error("MatchTask-RequestData fail,uuid:{};taskId:{};错误堆栈信息:{};错误信息:{}",uuid,taskId,eInfo,e.getMessage)
        syncInfoLogManageService.recordSyncDataLog(uuid, taskId, null, eInfo, 2, SyncErrorConstants.SEND_REMOTE_RESPONSE_UNKNOWN + HallApiConstants.REMOTE_TYPE_MATCH_TASK)
      case e: Exception =>
        //发送比对异常，状态更新为失败
        GafisNormalqueryQueryque.update.set(status= QueryConstants.STATUS_FAIL, oracomment = e.getMessage).where(GafisNormalqueryQueryque.pkId === gafisQuery.pkId).execute
        error(e.getMessage, e)
        val eInfo = ExceptionUtil.getStackTraceInfo(e)
        syncInfoLogManageService.recordSyncDataLog(uuid, taskId, "", eInfo, 2, SyncErrorConstants.SEND_REMOTE_TASK_FAIL +
          HallApiConstants.REMOTE_TYPE_MATCH_TASK)
    }
  }

}
