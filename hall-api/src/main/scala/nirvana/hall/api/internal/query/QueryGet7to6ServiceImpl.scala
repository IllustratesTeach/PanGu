package nirvana.hall.api.internal.query

import java.io.ByteArrayOutputStream
import java.util.Date
import javax.persistence.EntityManagerFactory

import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.jpa.{GafisNormalqueryQueryque, GafisQuery7to6}
import nirvana.hall.api.services.query.QueryGet7to6Service
import nirvana.hall.c.services.gloclib.gaqryque
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

/**
 * Created by songpeng on 15/12/30.
 */
class QueryGet7to6ServiceImpl(facade:V62Facade, v62Config:HallV62Config, apiConfig: HallApiConfig)
  extends QueryGet7to6Service{

  private val STATUS_MATCHING:Short = 1//任务状态，正在比对
  private val STATUS_FINISH:Short = 2

  @Transactional
  override def getQueryAndSaveMatchResult(queryque: GafisNormalqueryQueryque): Unit = {
    val queryId = GafisQuery7to6.find_by_oraSid(queryque.oraSid).takeOption.map(_.queryId)
    if(queryId.isDefined){
      val pstQry = gaqryqueConverter.convertQueryId2GAQUERYSTRUCT(queryId.get)
      val gaQueryStruct = facade.NET_GAFIS_QUERY_Get(v62Config.queryTable.dbId.toShort, v62Config.queryTable.tableId.toShort, pstQry)

      if(gaQueryStruct.stSimpQry.nStatus >= 2){//判断是否已经完成比对
        addMatchResult(queryque.pkId, gaQueryStruct)
        doWork
      }
    }
  }

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, entityManagerFactory: EntityManagerFactory): Unit = {
    periodicExecutor.addJob(new CronSchedule(apiConfig.sync62Cron), "query-get-70to62", new Runnable {
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
    }
  }

  override def getGafisNormalqueryQueryqueMatching(): Option[GafisNormalqueryQueryque] = {
    GafisNormalqueryQueryque.where("status=?1 and syncTargetSid is not null", STATUS_MATCHING).desc("priority").asc("oraSid").takeOption
  }

  /**
   * 保存比对结果
   * @param gaQueryStruct
   */
  @Transactional
  private def addMatchResult(pkId: String, gaQueryStruct: gaqryque.GAQUERYSTRUCT): Unit ={
    val candDataBuffer = new ByteArrayOutputStream()
    gaQueryStruct.pstCand_Data.foreach{candData =>
      candDataBuffer.write(candData.toByteArray)
    }
    GafisNormalqueryQueryque.where("pkId=?1", pkId).update(
      status = STATUS_FINISH,
      curcandnum = gaQueryStruct.stSimpQry.nCurCandidateNum,
      candhead = gaQueryStruct.pstCandHead_Data.toByteArray,
      candlist = candDataBuffer.toByteArray,
      hitpossibility = gaQueryStruct.stSimpQry.nHitPossibility.toShort,
      verifyresult = gaQueryStruct.stSimpQry.nVerifyResult.toShort,
      finishtime = new Date()
    )

  }

}
