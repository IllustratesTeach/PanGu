package nirvana.hall.v70.internal

import java.util.Date
import javax.persistence.EntityManager

import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.api.QueryProto.{QuerySendRequest, QuerySendResponse}
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.v70.internal.sync.ProtobufConverter

/**
 * Created by songpeng on 16/1/26.
 */
class QueryServiceImpl(entityManager: EntityManager) extends QueryService{
  /**
   * 发送查询任务
   * @param querySendRequest
   * @return
   */
  override def sendQuery(querySendRequest: QuerySendRequest, dBConfig: DBConfig): QuerySendResponse = {
    val matchTask = querySendRequest.getMatchTask
    val gafisQuery = ProtobufConverter.convertMatchTask2GafisNormalqueryQueryque(matchTask)
    val query = entityManager.createNativeQuery("select SEQ_ORASID.nextval from dual")

    gafisQuery.oraSid = query.getResultList.get(0).asInstanceOf[Long]//TODO 查询序列
    gafisQuery.pkId = CommonUtils.getUUID()
    gafisQuery.createtime = new Date()
    gafisQuery.deletag = Gafis70Constants.DELETAG_USE

    gafisQuery.save()

    QuerySendResponse.newBuilder().setOraSid(gafisQuery.oraSid).build()

  }

  /**
   * 获取查询信息
   * @param oraSid
   * @return
   */
  override def getMatchResult(oraSid: Long, dBConfig: DBConfig): Option[MatchResult]= {
    throw new UnsupportedOperationException
  }

  /**
    * 通过卡号查找第一个的比中结果
    * @param cardId 卡号
    * @return 比对结果
    */
  override def findFirstQueryResultByCardId(cardId: String, dBConfig: DBConfig): Option[MatchResult] = {
    throw new UnsupportedOperationException
  }

  /**
   * 根据卡号查找第一个比对任务的状态, 如果没有获取到返回UN_KNOWN
   * @param cardId
   * @param dBConfig
   * @return
   */
  override def findFirstQueryStatusByCardId(cardId: String, dBConfig: DBConfig): MatchStatus = {
    throw new UnsupportedOperationException
  }
}
