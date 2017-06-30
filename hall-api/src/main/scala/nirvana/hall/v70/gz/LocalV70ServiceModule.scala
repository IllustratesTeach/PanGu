package nirvana.hall.v70.gz

import nirvana.hall.api.services._
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.gz.services.{TPCardServiceImpl, CaseInfoServiceImpl, LPCardServiceImpl}
import nirvana.hall.v70.internal._
import nirvana.hall.v70.internal.sys.{DictServiceImpl, UserServiceImpl}
import nirvana.hall.v70.services.sys.{DictService, UserService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by songpeng on 2017/5/26.
  */
object LocalV70ServiceModule {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[RpcHttpClient], classOf[RpcHttpClientImpl]).withId("RpcHttpClient")
    binder.bind(classOf[UserService], classOf[UserServiceImpl])
    binder.bind(classOf[DictService], classOf[DictServiceImpl])
    //api 接口实现类
    binder.bind(classOf[TPCardService], classOf[TPCardServiceImpl])

    //由于依赖关系,这里暂时复用v70，如果有需要单独实现
    binder.bind(classOf[CaseInfoService], classOf[CaseInfoServiceImpl])
    binder.bind(classOf[LPCardService], classOf[LPCardServiceImpl])
    binder.bind(classOf[LPPalmService], classOf[LPPalmServiceImpl])
    binder.bind(classOf[QueryService], classOf[QueryServiceImpl])
    binder.bind(classOf[MatchRelationService], classOf[MatchRelationServiceImpl])
    binder.bind(classOf[ExceptRelationService], classOf[ExceptRelationServiceImpl])
  }
}
