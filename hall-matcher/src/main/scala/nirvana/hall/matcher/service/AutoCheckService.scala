package nirvana.hall.matcher.service

import nirvana.hall.matcher.internal.adapter.common.vo.QueryQueVo
import nirvana.protocol.MatchResult.MatchResultRequest

/**
  * Created by songpeng on 2017/8/10.
  */
trait AutoCheckService {

  /**
    * TT自动认定,在比对返回候选的时候自动认定
    * @param matchResultRequest
    * @param queryQue
    */
  def ttAutoCheck(matchResultRequest: MatchResultRequest, queryQue: QueryQueVo)
}
