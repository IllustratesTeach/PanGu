package nirvana.hall.v70.internal.query

import java.util.Date
import javax.persistence.EntityManager

import monad.support.services.LoggerSupport
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.internal.{CommonUtils, Gafis70Constants}
import nirvana.hall.v70.jpa._
import nirvana.hall.v70.services.query.QueryGet7to6Service
import nirvana.hall.v70.services.remote.{CaseInfoRemoteService, LPCardRemoteService, QueryRemoteService, TPCardRemoteService}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 15/12/30.
 */
class QueryGet7to6ServiceImpl(v70Config: HallV70Config,
                              entityManager: EntityManager,
                              queryRemoteService: QueryRemoteService,
                              tPCardRemoteService: TPCardRemoteService,
                              lPCardRemoteService: LPCardRemoteService,
                              caseInfoRemoteService: CaseInfoRemoteService)
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
      val syncTagert = SyncTarget.find(queryque.syncTargetSid)
      val matchResult = queryRemoteService.getQuery(gafisQuery7to6.get.queryId, syncTagert.targetIp, syncTagert.targetPort)

      //TODO 单位代码不能写死
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
            val person = GafisPerson.findOption(cand.getObjectId)
            if(person.isEmpty){
              info("queryGet fetch TPCard cardId:{}", cand.getObjectId)
              val tpCard = tPCardRemoteService.getTPCard(cand.getObjectId, syncTagert.targetIp, syncTagert.targetPort)
              val gafisPerson = ProtobufConverter.convertTPCard2GafisPerson(tpCard)
              val sid = java.lang.Long.parseLong(entityManager.createNativeQuery("select gafis_person_sid_seq.nextval from dual").getResultList.get(0).toString)
              gafisPerson.sid = sid
              gafisPerson.deletag = Gafis70Constants.DELETAG_USE
              gafisPerson.inputtime = new Date()
              gafisPerson.inputpsn = Gafis70Constants.INPUTPSN
              gafisPerson.modifiedtime = new Date()
              gafisPerson.modifiedpsn = Gafis70Constants.INPUTPSN
              gafisPerson.fingershowStatus = 1.toShort
              gafisPerson.gatherOrgCode = "370200000000"
              gafisPerson.cityCode = "3702"
              gafisPerson.isfingerrepeat = "0"
              gafisPerson.dataSources = 4.toShort
              gafisPerson.gatherTypeId = "8a20fb2544baa8450144babc6a1e000d"
              gafisPerson.save()
              //保存逻辑库
              val logicDb = GafisLogicDb.where(GafisLogicDb.logicCategory === "0").and(GafisLogicDb.logicName === "默认库").headOption.get
              val logicDbFingerprint = new GafisLogicDbFingerprint()
              logicDbFingerprint.pkId = CommonUtils.getUUID()
              logicDbFingerprint.fingerprintPkid = gafisPerson.personid
              logicDbFingerprint.logicDbPkid = logicDb.pkId
              logicDbFingerprint.save()

              //保存指纹
              val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)
              fingerList.foreach{finger =>
                finger.pkId = CommonUtils.getUUID()
                finger.inputtime = new Date()
                finger.inputpsn = Gafis70Constants.INPUTPSN
                finger.save()
              }
              //保存人像
              val portraitList = ProtobufConverter.convertTPCard2GafisGatherPortrait(tpCard)
              portraitList.foreach{ portrait =>
                portrait.pkId = CommonUtils.getUUID()
                portrait.inputpsn = Gafis70Constants.INPUTPSN
                portrait.inputtime = new Date()
                portrait.save()
              }
            }
          }else{
            //获取现场信息
            val cardId = cand.getObjectId
            val caseFinger = GafisCaseFinger.findOption(cardId)
            if(caseFinger.isEmpty){
              val lpCard = lPCardRemoteService.getLPCard(cardId, syncTagert.targetIp, syncTagert.targetPort)
              val caseId = lpCard.getText.getStrCaseId
              if(caseId != null && caseId.length >0){
                val caseInfo = GafisCase.findOption(caseId)
                if(caseInfo.isEmpty){
                  val caseInfo = caseInfoRemoteService.getCaseInfo(caseId, syncTagert.targetIp, syncTagert.targetPort)
                  val gafisCase = ProtobufConverter.convertCase2GafisCase(caseInfo)
                  gafisCase.deletag = Gafis70Constants.DELETAG_USE
                  gafisCase.inputtime = new Date()
                  gafisCase.inputpsn = Gafis70Constants.INPUTPSN
                  gafisCase.createUnitCode= "370200000000"
                  gafisCase.caseSource = "4"
                  gafisCase.save()
                  //逻辑库
                  val logicDb = GafisLogicDb.where(GafisLogicDb.logicCategory === "1").and(GafisLogicDb.logicName === "默认库").headOption.get
                  val logicDbCase = new GafisLogicDbCase()
                  logicDbCase.pkId = CommonUtils.getUUID()
                  logicDbCase.logicDbPkid = logicDb.pkId
                  logicDbCase.casePkid = gafisCase.caseId
                  logicDbCase.save()
                }
              }

              val caseFinger = ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard)
              val nativeQuery = entityManager.createNativeQuery("select gafis_case_sid_seq.nextval from dual")
              val sid = java.lang.Long.parseLong(nativeQuery.getResultList.get(0).toString)
              caseFinger.sid = sid
              caseFinger.deletag = Gafis70Constants.DELETAG_USE
              caseFinger.inputtime = new Date()
              caseFinger.inputpsn = Gafis70Constants.INPUTPSN
              caseFinger.creatorUnitCode = "370200000000"
              caseFinger.save()
              val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)
              caseFingerMnt.pkId = CommonUtils.getUUID()
              caseFingerMnt.deletag = Gafis70Constants.DELETAG_USE
              caseFingerMnt.inputtime = new Date()
              caseFingerMnt.inputpsn = Gafis70Constants.INPUTPSN
              caseFingerMnt.isMainMnt = "1"
              caseFingerMnt.save()
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
