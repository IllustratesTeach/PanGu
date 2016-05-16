package nirvana.hall.v62

import nirvana.hall.api.services._
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.internal.V62Facade.AutoSetupServerContextFilter
import nirvana.hall.v62.internal._
import org.apache.tapestry5.ioc.annotations.Contribute
import org.apache.tapestry5.ioc.{OrderedConfiguration, ServiceBinder}
import org.apache.tapestry5.services.{HttpServletRequestFilter, HttpServletRequestHandler}

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
  }
  @Contribute(classOf[HttpServletRequestHandler])
  def provideAutoSetupServerContextFilter(configuration: OrderedConfiguration[HttpServletRequestFilter]): Unit = {
    configuration.addInstance("AutoSetupServerContext", classOf[AutoSetupServerContextFilter], "before:protobuf")
  }
}
