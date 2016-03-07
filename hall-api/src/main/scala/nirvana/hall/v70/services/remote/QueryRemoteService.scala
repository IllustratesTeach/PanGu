package nirvana.hall.v70.services.remote

import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult

/**
 * Created by songpeng on 16/3/4.
 */
trait QueryRemoteService {

  /**
   * 获取查询结果
   * @param oraSid
   * @return
   */
  def getQuery(oraSid: Long, ip: String, port: String): MatchResult
}
