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
import nirvana.hall.v70.internal.HttpHeaderUtils
import nirvana.hall.v70.jpa.{GafisNormalqueryQueryque, GafisQuery7to6, RemoteQueryConfig}
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
    * 转换比对任务类型GafisNormalqueryQueryque-->MatchTask
    * @param query
    * @return
    */
  private def convertGafisNormalqueryQueryque2MatchTask(query: GafisNormalqueryQueryque): MatchTask = {
    val matchTask = MatchTask.newBuilder()
    matchTask.setMatchId(query.keyid)
    matchTask.setMatchType(MatchType.valueOf(query.querytype+1))
//    val isPalm = query.flag == 2 || query.flag == 22
//
//    //2016/12/25 比对类型判断
//    if (isPalm) {
//      val queryType = MatchType.valueOf(query.querytype+5)
//      queryType match {
//        case MatchType.PALM_TT =>
//          matchTask.setMatchType(MatchType.PALM_TT)
//        case MatchType.PALM_TL =>
//          matchTask.setMatchType(MatchType.PALM_TL)
//        case MatchType.PALM_LT =>
//          matchTask.setMatchType(MatchType.PALM_LT)
//        case MatchType.PALM_LL =>
//          matchTask.setMatchType(MatchType.PALM_LL)
//      }
//    } else {
//      val queryType = MatchType.valueOf(query.querytype+1)
//      queryType match {
//        case MatchType.FINGER_TT =>
//          matchTask.setMatchType(MatchType.FINGER_TT)
//        case MatchType.FINGER_TL =>
//          matchTask.setMatchType(MatchType.FINGER_TL)
//        case MatchType.FINGER_LT =>
//          matchTask.setMatchType(MatchType.FINGER_LT)
//        case MatchType.FINGER_LL =>
//          matchTask.setMatchType(MatchType.FINGER_LL)
//      }
//    }

    matchTask.setObjectId(query.oraSid)//必填项，现在用于存放oraSid
    matchTask.setPriority(query.priority.toInt)
    matchTask.setScoreThreshold(query.minscore)
    matchTask.setTopN(query.maxcandnum)
    matchTask.setCommitUser(query.username)
    // 2016/12/25 比对类型判断 提交机器Ip 和提交用户单位代码
    matchTask.setComputerIp(query.computerip)
    matchTask.setUserUnitCode(query.userunitcode)
    //解析特征数据
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
      //获取远程查询配置
      val queryConfig = RemoteQueryConfig.find(gafisQuery.syncTargetSid)
      val headerMap = HttpHeaderUtils.getHeaderMapOfQueryConfig(queryConfig.config, matchTask.getMatchType)
      val respnose = rpcHttpClient.call(queryConfig.url, QuerySendRequest.cmd, request.build(), headerMap)
      val querySendResponse = respnose.getExtension(QuerySendResponse.cmd)
      //记录关联62的查询任务号, TODO 删除GafisQuery7to6表 远程查询ID 存到GafisNormalqueryQueryque.queryid字段，获取比对结果一并修改，同时兼顾来自远方的查询
      GafisQuery7to6.where(GafisQuery7to6.oraSid === gafisQuery.oraSid).foreach(_.delete())//删除原有的6.2比对关系，防止重复发6.2远程比对后GafisQuery7to6主键唯一约束报错
      new GafisQuery7to6(gafisQuery.oraSid, querySendResponse.getOraSid).save()
      //更新比对状态为正在比对
      GafisNormalqueryQueryque.update.set(status = QueryConstants.STATUS_MATCHING, begintime = new Date()).where(GafisNormalqueryQueryque.pkId === gafisQuery.pkId).execute
    }
    catch {
      case e: Exception =>
        //发送比对异常，状态更新为失败
        GafisNormalqueryQueryque.update.set(status= QueryConstants.STATUS_FAIL, oracomment = e.getMessage).where(GafisNormalqueryQueryque.pkId === gafisQuery.pkId).execute
        error(e.getMessage, e)
    }
  }

}
