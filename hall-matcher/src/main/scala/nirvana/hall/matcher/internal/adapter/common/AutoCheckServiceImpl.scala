package nirvana.hall.matcher.internal.adapter.common

import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.service.AutoCheckService
import nirvana.protocol.MatchResult.MatchResultRequest

/**
  * Created by songpeng on 2017/8/10.
  */
class AutoCheckServiceImpl(hallMatcherConfig: HallMatcherConfig) extends AutoCheckService{
  /**
    * TT自动认定,在比对返回候选的时候自动认定
    * @param matchResultRequest
    */
  override def ttAutoCheck(matchResultRequest: MatchResultRequest, queryQue: QueryQue): Unit = {
    if(hallMatcherConfig.autoCheck != null){
      //TODO

    }
  }
}
