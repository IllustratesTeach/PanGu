package nirvana.hall.v70.internal.query

import java.util.Date

import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.services.remote.{CaseInfoRemoteService, LPCardRemoteService, QueryRemoteService, TPCardRemoteService}
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.internal.HttpHeaderUtils
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa._
import nirvana.hall.v70.services.query.QueryGet7to6Service
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 15/12/30.
 */
class QueryGet7to6ServiceImpl(v70Config: HallV70Config,
                              queryRemoteService: QueryRemoteService,
                              tPCardRemoteService: TPCardRemoteService,
                              lPCardRemoteService: LPCardRemoteService,
                              caseInfoRemoteService: CaseInfoRemoteService,
                              tpCardService:TPCardService,
                              lpCardService: LPCardService,
                              caseInfoService: CaseInfoService)
  extends QueryGet7to6Service with LoggerSupport{

  /**
   * 从6.2获取查询结果，保存候选列表信息
   * @param queryque
   * @return
   */
  @Transactional
  override def getQueryAndSaveMatchResult(queryque: GafisNormalqueryQueryque): Boolean= {
    val gafisQuery7to6 = GafisQuery7to6.findOption(queryque.oraSid)
    if(gafisQuery7to6.isEmpty){
      return false
    }else{
      val syncTagert = RemoteQueryConfig.find(queryque.syncTargetSid)
      val url = syncTagert.url
      val headerMap = HttpHeaderUtils.getV62HeaderMap(syncTagert.config)
      val matchResult = queryRemoteService.getQuery(gafisQuery7to6.get.queryId, url, headerMap)

      if (matchResult != null){
        //写入比对结果
        ProtobufConverter.convertMatchResult2GafisNormalqueryQueryque(matchResult, queryque)

        queryque.finishtime = new Date()
        queryque.status = QueryConstants.STATUS_SUCCESS
        queryque.save()
        //如果候选列表里的编号在本地没有数据，远程获取数据
        val candList = matchResult.getCandidateResultList
        val candIter = candList.iterator()
        while (candIter.hasNext){
          val cand = candIter.next()
          cand.getObjectId
          if(queryque.querytype == 0 || queryque.querytype == 2){
            //获取捺印信息
            if(GafisPerson.findOption(cand.getObjectId).isEmpty){
              val dbIdMap = HttpHeaderUtils.getHeaderMapOfDBID(syncTagert.config, HttpHeaderUtils.DB_KEY_TPLIB)
              val tPCard = tPCardRemoteService.getTPCard(cand.getObjectId, url, headerMap.++(dbIdMap))
              tPCard.foreach{tpCard =>
                //TODO 目前候选获取的数据存到本地库，后期会考虑存到远程库
                tpCardService.addTPCard(tpCard)
              }
            }
          }else{
            //获取现场信息
            val cardId = cand.getObjectId
            if(GafisCaseFinger.findOption(cardId).isEmpty){
              val dbIdMap = HttpHeaderUtils.getHeaderMapOfDBID(syncTagert.config, HttpHeaderUtils.DB_KEY_LPLIB)
              val lPCard = lPCardRemoteService.getLPCard(cardId, url, headerMap.++(dbIdMap))
              lPCard.foreach{lpCard=>
                val caseId = lpCard.getText.getStrCaseId
                if(caseId != null && caseId.length >0){
                  //如果本地没有对应的案件信息，先远程验证是否存在案件信息,远程获取案件到本地
                  if(GafisCase.findOption(caseId).isEmpty){
                    val dbIdMap = HttpHeaderUtils.getHeaderMapOfDBID(syncTagert.config, HttpHeaderUtils.DB_KEY_LPLIB)
                    caseInfoRemoteService.isExist(caseId, url, dbIdMap.get(HallApiConstants.HTTP_HEADER_DBID))
                    val caseInfo = caseInfoRemoteService.getCaseInfo(caseId, url, dbIdMap.get(HallApiConstants.HTTP_HEADER_DBID))
                    caseInfoService.addCaseInfo(caseInfo)
                  }
                }
                lpCardService.addLPCard(lpCard)
              }
            }
          }
        }
        return true
      }
      return false
    }

  }

  /**
   * 定时任务
   * @param periodicExecutor
   */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, queryGet7to6Service: QueryGet7to6Service): Unit = {
    if(v70Config.cron.query7to6Cron != null){
      periodicExecutor.addJob(new CronSchedule(v70Config.cron.query7to6Cron), "query-get-70to62", new Runnable {
        override def run(): Unit = {
          queryGet7to6Service.doWork
        }
      })
    }
  }
  @Transactional
  override def doWork: Unit ={
    getGafisNormalqueryQueryqueMatching().foreach{ gafisQuery =>
      info("query-get-70to62 info[oraSid:{} keyId:{} type:{}]", gafisQuery.oraSid , gafisQuery.keyid, gafisQuery.querytype)
      if(getQueryAndSaveMatchResult(gafisQuery)){
        doWork
      }
    }
  }
  /**
   * 获取正在比对的6.2查询任务
   * @return
   */
  override def getGafisNormalqueryQueryqueMatching(): Option[GafisNormalqueryQueryque] = {
    GafisNormalqueryQueryque.where(GafisNormalqueryQueryque.status === QueryConstants.STATUS_MATCHING).and(GafisNormalqueryQueryque.deletag === "1").and(GafisNormalqueryQueryque.syncTargetSid[String].notNull).orderBy(GafisNormalqueryQueryque.priority[java.lang.Short].desc).headOption
  }
}
