package nirvana.hall.v62

import nirvana.hall.api.services._
import nirvana.hall.api.services.sync.{SyncCaseInfoService, SyncLPCardService, SyncTPCardService}
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.internal._
import nirvana.hall.v62.internal.sync.{SyncCaseInfoServiceImpl, SyncLPCardServiceImpl, SyncTPCardServiceImpl}
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
    binder.bind(classOf[CaseInfoService], classOf[CaseInfoServiceImpl])
    binder.bind(classOf[QueryService], classOf[QueryServiceImpl])
    binder.bind(classOf[MatchRelationService], classOf[MatchRelationServiceImpl])

    //同步数据服务器类
    binder.bind(classOf[SyncTPCardService], classOf[SyncTPCardServiceImpl])
    binder.bind(classOf[SyncLPCardService], classOf[SyncLPCardServiceImpl])
    binder.bind(classOf[SyncCaseInfoService], classOf[SyncCaseInfoServiceImpl])
  }

}
