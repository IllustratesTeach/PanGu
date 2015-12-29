package nirvana.hall.api.internal.query

import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.entities.GafisNormalqueryQueryque
import nirvana.hall.api.services.AutoSpringDataSourceSession
import nirvana.hall.api.services.query.Query7to6Service
import nirvana.hall.c.services.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.joda.time.DateTime
import org.springframework.transaction.annotation.Transactional
import scalikejdbc._

/**
 * Created by songpeng on 15/12/9.
 */
class Query7to6ServiceImpl(facade:V62Facade, v62Config:HallV62Config, apiConfig: HallApiConfig)
  extends Query7to6Service{

  private val STATUS_MATCHING = "1"//任务状态，正在比对

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    periodicExecutor.addJob(new CronSchedule(apiConfig.sync62Cron), "query-70to62", new Runnable {
      override def run(): Unit = {
        doWork
      }
    })
  }

  private def doWork: Unit ={
    implicit val session = AutoSpringDataSourceSession.apply()
    getMatchTask.foreach{ matchTask =>
      session.connection.setAutoCommit(false)
      sendQuery(matchTask)
      session.connection.commit()
      doWork
    }

  }

  /**
   * 获取一条查询任务
   * @param session
   * @return
   */
  @Transactional
  override def getMatchTask(implicit session: DBSession): Option[MatchTask] ={
    val query = GafisNormalqueryQueryque.findAllBy(
      sqls.eq(GafisNormalqueryQueryque.gnq.status, 0)
        .and.isNotNull(GafisNormalqueryQueryque.gnq.syncTargetSid)
        .orderBy(GafisNormalqueryQueryque.gnq.priority desc,GafisNormalqueryQueryque.gnq.oraSid)).headOption

    query.foreach(updateGafisNormalqueryQueryqueStatusMatching)

    query.map(convertGafisNormalqueryQueryque2MatchTask)
  }

  /**
   * 更新状态为正在比对
   * @param queryque
   * @param session
   * @return
   */
  private def updateGafisNormalqueryQueryqueStatusMatching(queryque: GafisNormalqueryQueryque)(implicit session: DBSession): Unit ={
    withSQL{
      val column = GafisNormalqueryQueryque.column
      update(GafisNormalqueryQueryque).set(column.status -> STATUS_MATCHING, column.begintime -> new DateTime()).where.eq(column.pkId, queryque.pkId)
    }.update().apply()
  }

  private def convertGafisNormalqueryQueryque2MatchTask(query: GafisNormalqueryQueryque): MatchTask = {
    val matchTask = MatchTask.newBuilder()
    matchTask.setMatchId(query.keyid.get)
    matchTask.setMatchType(MatchType.valueOf(query.querytype.get+1))
    matchTask.setObjectId(1)//必填项，但是没有用到
    matchTask.setPriority(query.priority.get)
    matchTask.setScoreThreshold(query.minscore.get)

    matchTask.build()
  }

  /**
   * 发送比对任务
   * @param matchTask
   * @param session
   * @return
   */
  @Transactional
  override def sendQuery(matchTask: MatchTask)(implicit session: DBSession): Unit = {
    val key = matchTask.getMatchId.getBytes()
    val pstKey = new GADB_KEYARRAY
    pstKey.nKeyCount = 1
    pstKey.nKeySize = key.size.asInstanceOf[Short]
    pstKey.pKey_Data = key

    val idx= 1 to 10 map(x=>x.asInstanceOf[Byte]) toArray

    val queryStruct = gaqryqueConverter.convertProtoBuf2GAQUERYSTRUCT(matchTask)(v62Config)
    facade.NET_GAFIS_QUERY_Submit(20, 2, pstKey, queryStruct, idx)
  }

}
