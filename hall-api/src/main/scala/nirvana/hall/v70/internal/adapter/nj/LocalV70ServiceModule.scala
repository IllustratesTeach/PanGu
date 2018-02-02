package nirvana.hall.v70.internal.adapter.nj

import nirvana.hall.api.services._
import nirvana.hall.api.services.sync.{FetchMatchRelationService, _}
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.internal.adapter.nj.service._
import nirvana.hall.v70.internal.sync.{FetchCaseInfoServiceImpl, FetchLPCardServiceImpl, FetchLPPalmServiceImpl, FetchMatchRelationServiceImpl, FetchQueryServiceImpl, FetchTPCardServiceImpl}
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by songpeng on 2017/5/26.
  */
object LocalV70ServiceModule {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[RpcHttpClient], classOf[RpcHttpClientImpl]).withId("RpcHttpClient")
    //api 接口实现类
    binder.bind(classOf[TPCardService], classOf[TPCardServiceImpl])
    //南京版本的service实现类
    binder.bind(classOf[CaseInfoService], classOf[CaseInfoServiceImpl])
    binder.bind(classOf[LPCardService], classOf[LPCardServiceImpl])
    binder.bind(classOf[LPPalmService], classOf[LPPalmServiceImpl])
    binder.bind(classOf[QueryService],classOf[QueryServiceImpl])
    binder.bind(classOf[MatchRelationService],classOf[MatchRelationServiceImpl])
    binder.bind(classOf[LogicDBJudgeService],classOf[LogicDBJudgeServiceImpl])
    binder.bind(classOf[SyncInfoLogManageService],classOf[SyncInfoLogManageServiceImpl])
    //同步
    binder.bind(classOf[FetchTPCardService], classOf[FetchTPCardServiceImpl])
    binder.bind(classOf[FetchLPCardService], classOf[FetchLPCardServiceImpl])
    binder.bind(classOf[FetchLPPalmService], classOf[FetchLPPalmServiceImpl])
    binder.bind(classOf[FetchCaseInfoService], classOf[FetchCaseInfoServiceImpl])
    binder.bind(classOf[FetchQueryService], classOf[FetchQueryServiceImpl])
    binder.bind(classOf[FetchMatchRelationService],classOf[FetchMatchRelationServiceImpl])
  }
}
