package nirvana.hall.api

import nirvana.hall.api.internal._
import nirvana.hall.api.internal.protobuf.sys._
import nirvana.hall.api.internal.protobuf.sys.stamp.QueryPersonRequestFilter
import nirvana.hall.api.internal.stamp.{GatherPortraitServiceImpl, GatherFingerPalmServiceImpl, GatherPersonServiceImpl}
import nirvana.hall.api.services._
import nirvana.hall.api.services.stamp.{GatherPortraitService, GatherFingerPalmService, GatherPersonService}
import org.apache.tapestry5.ioc.annotations.{Contribute, Local, Match}
import org.apache.tapestry5.ioc.{MethodAdviceReceiver, OrderedConfiguration, ServiceBinder}
import org.apache.tapestry5.services.Core

/**
 * local api service module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
object LocalApiServiceModule {
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[AuthService], classOf[AuthServiceImpl])
    binder.bind(classOf[ProtobufRequestGlobal], classOf[ProtobufRequestGlobalImpl]).withMarker(classOf[Core])
    binder.bind(classOf[RequiresUserAdvisor], classOf[RequiresUserAdvisorImpl])
    binder.bind(classOf[UserService], classOf[UserServiceImpl])
    binder.bind(classOf[SystemService], classOf[SystemServiceImpl])
    binder.bind(classOf[SyncDictService], classOf[SyncDictServiceImpl])
    binder.bind(classOf[GatherPersonService], classOf[GatherPersonServiceImpl])
    binder.bind(classOf[GatherFingerPalmService], classOf[GatherFingerPalmServiceImpl])
    binder.bind(classOf[GatherPortraitService], classOf[GatherPortraitServiceImpl])
  }
  @Contribute(classOf[ProtobufRequestHandler])
  def provideProtobufFilter(configuration: OrderedConfiguration[ProtobufRequestFilter]): Unit = {
    configuration.addInstance("LoginRequestFilter", classOf[LoginRequestFilter])
    configuration.addInstance("SyncDictRequestFilter", classOf[SyncDictRequestFilter])
    configuration.addInstance("QueryPersonRequestFilter", classOf[QueryPersonRequestFilter])
  }
  @Match(Array("*"))
  def adviseAuth(@Local advisor: RequiresUserAdvisor, receiver: MethodAdviceReceiver) {
    advisor.addAdvice(receiver)
  }
}
