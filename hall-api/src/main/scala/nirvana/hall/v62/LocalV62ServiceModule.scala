package nirvana.hall.v62

import nirvana.hall.api.internal.remote.TPCardRemoteServiceImpl
import nirvana.hall.api.internal.sync.Sync6to7ServiceImpl
import nirvana.hall.api.services._
import nirvana.hall.api.services.remote.TPCardRemoteService
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.internal._
import nirvana.hall.v62.services.Sync6to7Service
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
    binder.bind(classOf[TPCardRemoteService], classOf[TPCardRemoteServiceImpl])

    //同步数据到7.0定时任务
    binder.bind(classOf[Sync6to7Service], classOf[Sync6to7ServiceImpl]).eagerLoad()
  }

}
