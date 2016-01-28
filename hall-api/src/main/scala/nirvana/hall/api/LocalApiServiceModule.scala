package nirvana.hall.api

import nirvana.hall.api.internal._
import nirvana.hall.api.internal.filter.{CaseInfoFilter, LPCardFilter, QueryFilter, TPCardFilter}
import nirvana.hall.api.services._
import org.apache.tapestry5.ioc.annotations.Contribute
import org.apache.tapestry5.ioc.{OrderedConfiguration, ServiceBinder}
import org.apache.tapestry5.services.Core

/**
 * local api service module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
object LocalApiServiceModule {
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[ProtobufRequestGlobal], classOf[ProtobufRequestGlobalImpl]).withMarker(classOf[Core])
    binder.bind(classOf[SystemService], classOf[SystemServiceImpl])
    binder.bind(classOf[AuthService], classOf[AuthServiceImpl])
    binder.bind(classOf[RequiresUserAdvisor], classOf[RequiresUserAdvisorImpl])
  }

  @Contribute(classOf[ProtobufRequestHandler])
  def provideProtobufFilter(configuration: OrderedConfiguration[ProtobufRequestFilter]): Unit = {
    configuration.addInstance("TPCardFilter", classOf[TPCardFilter])
    configuration.addInstance("LPCardFilter", classOf[LPCardFilter])
    configuration.addInstance("CaseFilter", classOf[CaseInfoFilter])
    configuration.addInstance("QueryFilter", classOf[QueryFilter])
  }

//  @Match(Array("*"))
//  def adviseAuth(@Local advisor: RequiresUserAdvisor, receiver: MethodAdviceReceiver) {
//    advisor.addAdvice(receiver)
//  }
}
