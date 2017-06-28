package nirvana.hall.v62

import nirvana.hall.api.services._
import nirvana.hall.api.services.sync.{FetchMatchRelationService, LogicDBJudgeService}
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.internal.sync.FetchMatchRelationServiceImpl
import nirvana.hall.v62.internal.{AssistCheckRecordServiceImpl, _}
import nirvana.hall.v62.services.GetPKIDServiceImpl
import nirvana.hall.v62.services.service.GetPKIDService
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * local v62 service module
 * Created by songpeng on 15/12/7.
 */
object LocalV62ServiceModule {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[V62Facade])
    binder.bind(classOf[RpcHttpClient],classOf[RpcHttpClientImpl]).withId("RpcHttpClient")
    binder.bind(classOf[TPCardService], classOf[TPCardServiceImpl])
    binder.bind(classOf[LPCardService], classOf[LPCardServiceImpl])
    binder.bind(classOf[LPPalmService], classOf[LPPalmServiceImpl])
    binder.bind(classOf[CaseInfoService], classOf[CaseInfoServiceImpl])
    binder.bind(classOf[QueryService], classOf[QueryServiceImpl])
    binder.bind(classOf[MatchRelationService], classOf[MatchRelationServiceImpl])
    binder.bind(classOf[SyncInfoLogManageService], classOf[SyncInfoLogManageServiceImpl])
    binder.bind(classOf[LogicDBJudgeService], classOf[LogicDBJudgeServiceImpl])
    binder.bind(classOf[AssistCheckRecordService],classOf[AssistCheckRecordServiceImpl])
    binder.bind(classOf[GetPKIDService], classOf[GetPKIDServiceImpl])
    binder.bind(classOf[ExceptRelationService], classOf[ExceptRelationServiceImpl])
    binder.bind(classOf[FetchMatchRelationService],classOf[FetchMatchRelationServiceImpl])
    binder.bind(classOf[FPTFilterService],classOf[FPTFilterServiceImpl])
  }

}
