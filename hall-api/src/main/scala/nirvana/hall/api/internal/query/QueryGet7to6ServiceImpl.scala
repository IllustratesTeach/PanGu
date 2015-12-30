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
import scalikejdbc._

/**
 * Created by songpeng on 15/12/30.
 */
class QueryGet7to6ServiceImpl(facade:V62Facade, v62Config:HallV62Config, apiConfig: HallApiConfig)
  extends QueryGet7to6Service{

  private val STATUS_MATCHING = "1"//任务状态，正在比对

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
    getGafisNormalqueryQueryqueMatching().foreach{ queryque =>
      val queryId = GafisQuery7to6.find(queryque.oraSid.get).map(_.queryId)
      if(queryId != null){
        val pstQry = gaqryqueConverter.convertQueryId2GAQUERYSTRUCT(queryId.get)
        val gaQueryStruct = facade.NET_GAFIS_QUERY_Get(v62Config.queryTable.dbId.toShort, v62Config.queryTable.tableId.toShort, pstQry)

        if(gaQueryStruct.stSimpQry.nStatus >= 2){//判断是否已经完成比对
          addMatchResult(gaQueryStruct)
        }
      }
    }

  }

  override def getGafisNormalqueryQueryqueMatching(): Option[GafisNormalqueryQueryque] = {
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
  private def addMatchResult(gaQueryStruct: gaqryque.GAQUERYSTRUCT)(implicit session: DBSession): Unit ={
    val matchId = gaqryqueConverter.convertSixByteArrayToLong(gaQueryStruct.stSimpQry.nQueryID)

    withSQL{
      val column = GafisNormalqueryQueryque.column
      update(GafisNormalqueryQueryque).set(
        column.status -> 2,
        column.curcandnum -> gaQueryStruct.stSimpQry.nCurCandidateNum,
        column.candhead -> gaQueryStruct.pstCandHead_Data,
        column.candlist -> gaQueryStruct.pstCand_Data,
        column.hitpossibility -> gaQueryStruct.stSimpQry.nHitPossibility.toInt,
        column.verifyresult -> gaQueryStruct.stSimpQry.nVerifyResult.toInt,
        column.finishtime -> new DateTime()
      ).where.eq(column.oraSid, matchId)
    }.update().apply()

  }

}
