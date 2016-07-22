package nirvana.hall.api.services.remote

import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult

/**
 * Created by songpeng on 16/3/4.
 */
trait QueryRemoteService {

  /**
   * 获取查询结果, 如果比对没有完成返回null
   * @param oraSid
   * @return
   */
  def getQuery(oraSid: Long, url: String, headerMap: Map[String, String] = null): MatchResult
}
