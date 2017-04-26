package nirvana.hall.v62

import nirvana.hall.api.internal.remote.HallImageRemoteServiceImpl
import nirvana.hall.api.services._
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.api.services.sync.LogicDBJudgeService
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.internal._
import nirvana.hall.v62.services.GetPKIDServiceImpl
import nirvana.hall.v62.services.service.GetPKIDService
import nirvana.hall.v62.internal.AssistCheckRecordServiceImpl
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
    binder.bind(classOf[HallDatasourceService], classOf[HallDatasourceServiceImpl])
    binder.bind(classOf[LogicDBJudgeService], classOf[LogicDBJudgeServiceImpl])
    binder.bind(classOf[AssistCheckRecordService],classOf[AssistCheckRecordServiceImpl])
    binder.bind(classOf[GetPKIDService], classOf[GetPKIDServiceImpl])
    binder.bind(classOf[HallImageRemoteService], classOf[HallImageRemoteServiceImpl])


  }

}
