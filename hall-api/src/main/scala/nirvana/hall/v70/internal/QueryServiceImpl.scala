package nirvana.hall.v70.internal

import java.util.Date
import javax.persistence.EntityManager

import nirvana.hall.api.config.QueryDBConfig
import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.fpt.TypeDefinitionProto.MatchType
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult.MatcherStatus
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{SysUser, GafisNormalqueryQueryque}

/**
 * Created by songpeng on 16/1/26.
 */
class QueryServiceImpl(entityManager: EntityManager) extends QueryService{
  /**
   * 发送查询任务
   * @param matchTask
   * @return
   */
  override def addMatchTask(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = {
    val gafisQuery = ProtobufConverter.convertMatchTask2GafisNormalqueryQueryque(matchTask)
    val query = entityManager.createNativeQuery("select SEQ_ORASID.nextval from dual")
    gafisQuery.oraSid = query.getResultList.get(0).toString.toLong
    gafisQuery.pkId = CommonUtils.getUUID()
    gafisQuery.submittsystem = QueryConstants.SUBMIT_SYS_FINGER
    //用户信息，单位信息
    val sysUser = SysUser.find(Gafis70Constants.INPUTPSN)
    gafisQuery.userid = Gafis70Constants.INPUTPSN
    gafisQuery.username = sysUser.trueName
    gafisQuery.userunitcode = sysUser.departCode
    gafisQuery.createtime = new Date()
    gafisQuery.deletag = Gafis70Constants.DELETAG_USE
    gafisQuery.save()

    gafisQuery.oraSid
  }

  /**
   * 获取查询信息
   * @param oraSid
   * @return
   */
  override def getMatchResult(oraSid: Long, dbId: Option[String]): Option[MatchResult]= {
    val matchResult = MatchResult.newBuilder()
    val queryQue = GafisNormalqueryQueryque.find_by_oraSid(oraSid).head
    if(queryQue.status == QueryConstants.STATUS_SUCCESS){
      val matchResultObj = gaqryqueConverter.convertCandList2GAQUERYCANDSTRUCT(queryQue.candlist)
      matchResultObj.foreach{cand=>
        matchResult.addCandidateResult(cand)
      }
      matchResult.setMatchId(oraSid.toString)
      matchResult.setCandidateNum(queryQue.curcandnum)
      matchResult.setTimeElapsed(queryQue.timeElapsed)
      matchResult.setRecordNumMatched(queryQue.recordNumMatched)
      matchResult.setMaxScore(queryQue.hitpossibility.toInt)
      matchResult.setStatus(MatcherStatus.newBuilder().setCode(0))
    }

    Option(matchResult.build())
  }

  /**
    * 通过卡号查找第一个的比中结果
    * @param cardId 卡号
    * @return 比对结果
    */
  override def findFirstQueryResultByCardId(cardId: String, dbId: Option[String]): Option[MatchResult] = {
    throw new UnsupportedOperationException
  }

  /**
   * 根据卡号查找第一个比对任务的状态, 如果没有获取到返回UN_KNOWN
   * @param cardId
   * @return
   */
  override def findFirstQueryStatusByCardIdAndMatchType(cardId: String, matchType: MatchType, dbId: Option[String]): MatchStatus = {
    throw new UnsupportedOperationException
  }

  /**
    * 根据卡号信息发送查询, 不需要特征信息
    * @param matchTask 只有查询信息不需要特征信息
    * @param queryDBConfig
    * @return 任务号
    */
  override def sendQuery(matchTask: MatchTask, queryDBConfig: QueryDBConfig): Long = {
    throw new UnsupportedOperationException
  }
}
