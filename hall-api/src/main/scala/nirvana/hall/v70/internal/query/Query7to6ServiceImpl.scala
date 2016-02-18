package nirvana.hall.v70.internal.query

import java.util.Date
import javax.persistence.EntityManagerFactory

import com.google.protobuf.ByteString
import nirvana.hall.api.internal.WebHttpClientUtils
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask.LatentMatchData
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.protocol.v62.qry.QueryProto.{QuerySendRequest, QuerySendResponse}
import nirvana.hall.v62.internal.c.gloclib.galoctp
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.jpa.{GafisQuery7to6, GafisNormalqueryQueryque, SyncTarget}
import nirvana.hall.v70.services.query.Query7to6Service
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.jboss.netty.buffer.ChannelBuffers
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

/**
 * Created by songpeng on 15/12/9.
 */
class Query7to6ServiceImpl(v70Config: HallV70Config)
  extends Query7to6Service{

  private val STATUS_MATCHING =1.toShort//任务状态，正在比对

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, entityManagerFactory: EntityManagerFactory): Unit = {
    periodicExecutor.addJob(new CronSchedule(v70Config.sync62Cron), "query-70to62", new Runnable {
      override def run(): Unit = {
        try {
          val emHolder = new EntityManagerHolder(entityManagerFactory.createEntityManager())
          TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder)
          doWork
          TransactionSynchronizationManager.unbindResource(entityManagerFactory)
          EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager)
        }
        catch {
          case e: Throwable =>
            e.printStackTrace()
        }
      }
    })
  }

  private def doWork: Unit ={
    getGafisNormalqueryQueryque.foreach{ gafisQuery =>
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
    val gafisQuery = GafisNormalqueryQueryque.where("status=0 and syncTargetSid is not null").desc("priority").asc("oraSid").takeOption
    // 更新状态为正在比对
    gafisQuery.foreach{ query =>
      GafisNormalqueryQueryque.where("pkId=?1", query.pkId).update(status=STATUS_MATCHING, begintime=new Date())
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
    val matchTask = convertGafisNormalqueryQueryque2MatchTask(gafisQuery)

    val request = QuerySendRequest.newBuilder().addMatchTask(matchTask)
    val responseBuilder = QuerySendResponse.newBuilder()

    val syncTarget = SyncTarget.find(gafisQuery.syncTargetSid)

    //TODO orasid的实际值丢失， 可能是ExtensionRegistry的问题
    WebHttpClientUtils.call("http://"+syncTarget.targetIp+":"+syncTarget.targetPort, QuerySendRequest.cmd, request.build(), responseBuilder)

    new GafisQuery7to6(gafisQuery.oraSid, responseBuilder.getOraSid).save()
  }

}
