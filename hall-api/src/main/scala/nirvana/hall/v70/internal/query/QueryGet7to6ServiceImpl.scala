package nirvana.hall.v70.internal.query

import java.util.Date
import javax.persistence.EntityManagerFactory

import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse}
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisNormalqueryQueryque, GafisQuery7to6, SyncTarget}
import nirvana.hall.v70.services.query.QueryGet7to6Service
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

/**
 * Created by songpeng on 15/12/30.
 */
class QueryGet7to6ServiceImpl(v70Config: HallV70Config, rpcHttpClient: RpcHttpClient)
  extends QueryGet7to6Service{

  private val STATUS_MATCHING:Short = 1//任务状态，正在比对
  private val STATUS_FINISH:Short = 2

  @Transactional
  override def getQueryAndSaveMatchResult(queryque: GafisNormalqueryQueryque): Unit = {
    val queryId = GafisQuery7to6.find(queryque.oraSid).queryId

    val syncTagert = SyncTarget.find(queryque.syncTargetSid)

    val request = QueryGetRequest.newBuilder().setOraSid(queryId)

    val baseResponse = rpcHttpClient.call("http://"+ syncTagert.targetIp+":"+ syncTagert.targetPort, QueryGetRequest.cmd, request.build())
    val queryGetResponse = baseResponse.getExtension(QueryGetResponse.cmd)
    if(queryGetResponse.getIsComplete){
      val matchResult = queryGetResponse.getMatchResult
      ProtobufConverter.convertMatchResult2GafisNormalqueryQueryque(matchResult, queryque)

      queryque.finishtime = new Date()
      queryque.status = STATUS_FINISH
      queryque.save()
    }
  }

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, entityManagerFactory: EntityManagerFactory): Unit = {
    periodicExecutor.addJob(new CronSchedule(v70Config.sync62Cron), "query-get-70to62", new Runnable {
      override def run(): Unit = {
        val emHolder= new EntityManagerHolder(entityManagerFactory.createEntityManager())
        TransactionSynchronizationManager.bindResource(entityManagerFactory,emHolder)
        doWork
        TransactionSynchronizationManager.unbindResource(entityManagerFactory)
        EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager)
      }
    })
  }
  def doWork: Unit ={
    getGafisNormalqueryQueryqueMatching().foreach{ queryque =>
      getQueryAndSaveMatchResult(queryque)
      doWork
    }
  }

  override def getGafisNormalqueryQueryqueMatching(): Option[GafisNormalqueryQueryque] = {
    GafisNormalqueryQueryque.where("status=?1 and syncTargetSid is not null", STATUS_MATCHING).desc("priority").asc("oraSid").takeOption
  }
}
