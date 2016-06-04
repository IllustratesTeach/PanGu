package nirvana.hall.v70.internal.query

import java.util.Date
import javax.persistence.EntityManager

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.protocol.api.QueryProto.{QuerySendRequest, QuerySendResponse}
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask.LatentMatchData
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.internal.c.gloclib.galoctp
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.jpa.{GafisNormalqueryQueryque, GafisQuery7to6, SyncTarget}
import nirvana.hall.v70.services.query.Query7to6Service
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.jboss.netty.buffer.ChannelBuffers
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 15/12/9.
 */
class Query7to6ServiceImpl(v70Config: HallV70Config, rpcHttpClient: RpcHttpClient, entityManager: EntityManager)
  extends Query7to6Service with LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, query7to6Service: Query7to6Service): Unit = {
    if(v70Config.cron.query7to6Cron != null){
      periodicExecutor.addJob(new CronSchedule(v70Config.cron.query7to6Cron), "query-70to62", new Runnable {
        override def run(): Unit = {
          query7to6Service.doWork
        }
      })
    }
  }

  @Transactional
  override def doWork: Unit ={
    getGafisNormalqueryQueryque.foreach{ gafisQuery =>
      info("sync-70to62 sync_queue info[oraSid:{} keyId:{} type:{}]", gafisQuery.oraSid , gafisQuery.keyid, gafisQuery.querytype)
      sendQuery(gafisQuery)
      doWork
    }
  }

  /**
   * 获取一条查询任务
   * @return
   */
  @Transactional
  override def getGafisNormalqueryQueryque: Option[GafisNormalqueryQueryque] ={
    val gafisQuery = GafisNormalqueryQueryque.where(GafisNormalqueryQueryque.status === QueryConstants.STATUS_WAIT).and(GafisNormalqueryQueryque.deletag === "1").and(GafisNormalqueryQueryque.syncTargetSid[String].notNull).orderBy(GafisNormalqueryQueryque.priority[java.lang.Short].desc).orderBy(GafisNormalqueryQueryque.oraSid).headOption
    // 更新状态为正在比对
    gafisQuery.foreach{ query =>
      GafisNormalqueryQueryque.update.set(status = QueryConstants.STATUS_MATCHING, begintime = new Date()).where(GafisNormalqueryQueryque.pkId === query.pkId).execute
    }

    gafisQuery
  }

  private def convertGafisNormalqueryQueryque2MatchTask(query: GafisNormalqueryQueryque): MatchTask = {
    val matchTask = MatchTask.newBuilder()
    matchTask.setMatchId(query.keyid)
    matchTask.setMatchType(MatchType.valueOf(query.querytype+1))
    matchTask.setObjectId(query.oraSid)//必填项，现在用于存放oraSid
    matchTask.setPriority(query.priority.toInt)
    matchTask.setScoreThreshold(query.minscore)

    val mics = new galoctp{}.GAFIS_MIC_GetDataFromStream(ChannelBuffers.wrappedBuffer(query.mic))
    mics.foreach{mic =>
      if(mic.bIsLatent == 1){
        val ldata = LatentMatchData.newBuilder()
        ldata.setMinutia(ByteString.copyFrom(mic.pstMnt_Data))
        matchTask.setLData(ldata)
      }else{
        matchTask.getTDataBuilder.addMinutiaDataBuilder().setMinutia(ByteString.copyFrom(mic.pstMnt_Data)).setPos(mic.nItemData)
      }
    }
    matchTask.build()
  }

  /**
   * 发送比对任务
   * @param gafisQuery
   * @return
   */
  @Transactional
  override def sendQuery(gafisQuery: GafisNormalqueryQueryque): Unit = {
    try {
      val matchTask = convertGafisNormalqueryQueryque2MatchTask(gafisQuery)
      val request = QuerySendRequest.newBuilder().setMatchTask(matchTask)
      val syncTarget = SyncTarget.find(gafisQuery.syncTargetSid)

      val respnose = rpcHttpClient.call("http://" + syncTarget.targetIp + ":" + syncTarget.targetPort, QuerySendRequest.cmd, request.build())
      val querySendResponse = respnose.getExtension(QuerySendResponse.cmd)
      //记录关联62的查询任务号
      new GafisQuery7to6(gafisQuery.oraSid, querySendResponse.getOraSid).save()
    }
    catch {
      case e: Exception =>
        //发送比对异常，状态更新为失败
        GafisNormalqueryQueryque.update.set(status= QueryConstants.STATUS_FAIL, oracomment = e.getMessage).where(GafisNormalqueryQueryque.pkId === gafisQuery.pkId).execute
    }
  }

}
