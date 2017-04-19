package nirvana.hall.v70

import nirvana.hall.api.internal.remote._
import nirvana.hall.api.internal.sync.SyncCronServiceImpl
import nirvana.hall.api.services._
import nirvana.hall.api.services.remote._
import nirvana.hall.api.services.sync._
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.internal._
import nirvana.hall.v70.internal.query.{Query7to6ServiceImpl, QueryGet7to6ServiceImpl}
import nirvana.hall.v70.internal.stamp.{GatherFingerPalmServiceImpl, GatherPersonServiceImpl, GatherPortraitServiceImpl}
import nirvana.hall.v70.internal.sync._
import nirvana.hall.v70.internal.sys.{DictServiceImpl, UserServiceImpl}
import nirvana.hall.v70.services.query.{Query7to6Service, QueryGet7to6Service}
import nirvana.hall.v70.services.stamp.{GatherFingerPalmService, GatherPersonService, GatherPortraitService}
import nirvana.hall.v70.services.sys.{DictService, UserService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * Created by songpeng on 16/1/25.
 */
object LocalV70ServiceModule {
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[RpcHttpClient],classOf[RpcHttpClientImpl]).withId("RpcHttpClient")
    binder.bind(classOf[UserService], classOf[UserServiceImpl])
    binder.bind(classOf[DictService], classOf[DictServiceImpl])
    binder.bind(classOf[GatherPersonService], classOf[GatherPersonServiceImpl])
    binder.bind(classOf[GatherFingerPalmService], classOf[GatherFingerPalmServiceImpl])
    binder.bind(classOf[GatherPortraitService], classOf[GatherPortraitServiceImpl])
    //api 接口实现类
    binder.bind(classOf[CaseInfoService], classOf[CaseInfoServiceImpl])
    binder.bind(classOf[LPCardService], classOf[LPCardServiceImpl])
    binder.bind(classOf[LPPalmService], classOf[LPPalmServiceImpl])
    binder.bind(classOf[TPCardService], classOf[TPCardServiceImpl])
    binder.bind(classOf[QueryService], classOf[QueryServiceImpl])
    binder.bind(classOf[MatchRelationService], classOf[MatchRelationServiceImpl])
    binder.bind(classOf[SyncInfoLogManageService], classOf[SyncInfoLogManageServiceImpl])
    binder.bind(classOf[HallDatasourceService], classOf[HallDatasourceServiceImpl])

    //远程服务类
    binder.bind(classOf[TPCardRemoteService], classOf[TPCardRemoteServiceImpl])
    binder.bind(classOf[QueryRemoteService], classOf[QueryRemoteServiceImpl])
    binder.bind(classOf[LPCardRemoteService], classOf[LPCardRemoteServiceImpl])
    binder.bind(classOf[LPPalmRemoteService], classOf[LPPalmRemoteServiceImpl])
    binder.bind(classOf[CaseInfoRemoteService], classOf[CaseInfoRemoteServiceImpl])

    //同步
    binder.bind(classOf[SyncCronService], classOf[SyncCronServiceImpl]).eagerLoad()
    binder.bind(classOf[FetchTPCardService], classOf[FetchTPCardServiceImpl])
    binder.bind(classOf[FetchLPCardService], classOf[FetchLPCardServiceImpl])
    binder.bind(classOf[FetchLPPalmService], classOf[FetchLPPalmServiceImpl])
    binder.bind(classOf[FetchCaseInfoService], classOf[FetchCaseInfoServiceImpl])
    binder.bind(classOf[FetchQueryService], classOf[FetchQueryServiceImpl])
    binder.bind(classOf[LogicDBJudgeService], classOf[LogicDBJudgeServiceImpl])

    //定时服务
//    binder.bind(classOf[SyncDictService], classOf[SyncDictServiceImpl]).eagerLoad()
    binder.bind(classOf[Query7to6Service], classOf[Query7to6ServiceImpl]).eagerLoad()
    binder.bind(classOf[QueryGet7to6Service], classOf[QueryGet7to6ServiceImpl]).eagerLoad()
  }

//  @Contribute(classOf[ProtobufRequestHandler])
//  def provideProtobufFilter(configuration: OrderedConfiguration[ProtobufRequestFilter]): Unit = {
//    configuration.addInstance("LoginRequestFilter", classOf[LoginRequestFilter])
//    configuration.addInstance("SyncDictRequestFilter", classOf[SyncDictRequestFilter])
//    configuration.addInstance("DictListRequestFilter", classOf[DictListRequestFilter])
//    configuration.addInstance("QueryPersonRequestFilter", classOf[QueryPersonRequestFilter])
//    configuration.addInstance("AddPersonInfoRequestFilter", classOf[AddPersonInfoRequestFilter])
//    configuration.addInstance("UpdatePersonRequestFilter", classOf[UpdatePersonRequestFilter])
//    configuration.addInstance("AddPortraitRequestFilter", classOf[AddPortraitRequestFilter])
//    configuration.addInstance("QueryPortraitRequestFilter", classOf[QueryPortraitRequestFilter])
//    configuration.addInstance("AddFingerRequestFilter", classOf[AddFingerRequestFilter])
//    configuration.addInstance("QueryFingerRequestFilter", classOf[QueryFingerRequestFilter])
//  }
}
