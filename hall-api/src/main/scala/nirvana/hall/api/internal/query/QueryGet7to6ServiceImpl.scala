package nirvana.hall.api.internal.query

import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.entities.{GafisNormalqueryQueryque, GafisQuery7to6}
import nirvana.hall.api.services.AutoSpringDataSourceSession
import nirvana.hall.api.services.query.QueryGet7to6Service
import nirvana.hall.c.services.gloclib.gaqryque
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.joda.time.DateTime
import org.springframework.transaction.annotation.Transactional
import scalikejdbc._

/**
 * Created by songpeng on 15/12/30.
 */
class QueryGet7to6ServiceImpl(facade:V62Facade, v62Config:HallV62Config, apiConfig: HallApiConfig)
  extends QueryGet7to6Service{

  @Transactional
  override def getQueryAndSaveMatchResult(queryque: GafisNormalqueryQueryque)(implicit session: DBSession): Unit = {
    val queryId = GafisQuery7to6.find(queryque.oraSid.get).map(_.queryId)
    if(queryId != None){
      val pstQry = gaqryqueConverter.convertQueryId2GAQUERYSTRUCT(queryId.get)
      val gaQueryStruct = facade.NET_GAFIS_QUERY_Get(v62Config.queryTable.dbId.toShort, v62Config.queryTable.tableId.toShort, pstQry)

      if(gaQueryStruct.stSimpQry.nStatus >= 2){//判断是否已经完成比对
        addMatchResult(queryque.pkId, gaQueryStruct)
      }
    }
  }

  private val STATUS_MATCHING = "1"//任务状态，正在比对
  private val STATUS_FINISH = "2"

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    periodicExecutor.addJob(new CronSchedule(apiConfig.sync62Cron), "query-get-70to62", new Runnable {
      override def run(): Unit = {
        doWork
      }
    })
  }
  def doWork: Unit ={
    implicit val session = AutoSpringDataSourceSession.apply()
    //这里手动提交事务
    getGafisNormalqueryQueryqueMatching().foreach{ queryque =>
      session.connection.setAutoCommit(false)
      getQueryAndSaveMatchResult(queryque)
      session.connection.commit()
      doWork
    }
  }

  override def getGafisNormalqueryQueryqueMatching(implicit session: DBSession): Option[GafisNormalqueryQueryque] = {
    val query = GafisNormalqueryQueryque.findAllBy(
      sqls.eq(GafisNormalqueryQueryque.gnq.status, STATUS_MATCHING)
        .and.isNotNull(GafisNormalqueryQueryque.gnq.syncTargetSid)
        .orderBy(GafisNormalqueryQueryque.gnq.priority desc,GafisNormalqueryQueryque.gnq.oraSid)).headOption

    query
  }

  /**
   * 保存比对结果
   * @param gaQueryStruct
   */
  @Transactional
  private def addMatchResult(pkId: String, gaQueryStruct: gaqryque.GAQUERYSTRUCT)(implicit session: DBSession): Unit ={
    withSQL{
      val column = GafisNormalqueryQueryque.column
      update(GafisNormalqueryQueryque).set(
        column.status -> STATUS_FINISH,
        column.curcandnum -> gaQueryStruct.stSimpQry.nCurCandidateNum,
        column.candhead -> gaQueryStruct.pstCandHead_Data.toByteArray,
        column.candlist -> gaQueryStruct.pstCand_Data(0).toByteArray,
        column.hitpossibility -> gaQueryStruct.stSimpQry.nHitPossibility.toInt,
        column.verifyresult -> gaQueryStruct.stSimpQry.nVerifyResult.toInt,
        column.finishtime -> new DateTime()
      ).where.eq(column.pkId, pkId)
    }.update().apply()

  }

}
