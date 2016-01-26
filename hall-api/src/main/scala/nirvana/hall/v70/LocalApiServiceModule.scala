package nirvana.hall.v70

import nirvana.hall.api.internal._
import nirvana.hall.v70.internal.filter.stamp._
import nirvana.hall.v70.internal.filter.sys._
import nirvana.hall.v70.internal.stamp.{GatherFingerPalmServiceImpl, GatherPersonServiceImpl, GatherPortraitServiceImpl}
import nirvana.hall.api.services._
import nirvana.hall.v70.internal.sys.{DictServiceImpl, UserServiceImpl}
import nirvana.hall.v70.services.stamp.{GatherFingerPalmService, GatherPersonService, GatherPortraitService}
import nirvana.hall.v70.services.sys.{UserService, DictService}
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
    binder.bind(classOf[DictService], classOf[DictServiceImpl])
    binder.bind(classOf[GatherPersonService], classOf[GatherPersonServiceImpl])
    binder.bind(classOf[GatherFingerPalmService], classOf[GatherFingerPalmServiceImpl])
    binder.bind(classOf[GatherPortraitService], classOf[GatherPortraitServiceImpl])
  }
  @Contribute(classOf[ProtobufRequestHandler])
  def provideProtobufFilter(configuration: OrderedConfiguration[ProtobufRequestFilter]): Unit = {
    configuration.addInstance("LoginRequestFilter", classOf[LoginRequestFilter])
    configuration.addInstance("SyncDictRequestFilter", classOf[SyncDictRequestFilter])
    configuration.addInstance("DictListRequestFilter", classOf[DictListRequestFilter])
    configuration.addInstance("QueryPersonRequestFilter", classOf[QueryPersonRequestFilter])
    configuration.addInstance("AddPersonInfoRequestFilter", classOf[AddPersonInfoRequestFilter])
    configuration.addInstance("UpdatePersonRequestFilter", classOf[UpdatePersonRequestFilter])
    configuration.addInstance("AddPortraitRequestFilter", classOf[AddPortraitRequestFilter])
    configuration.addInstance("QueryPortraitRequestFilter", classOf[QueryPortraitRequestFilter])
    configuration.addInstance("AddFingerRequestFilter", classOf[AddFingerRequestFilter])
    configuration.addInstance("QueryFingerRequestFilter", classOf[QueryFingerRequestFilter])
  }
  @Match(Array("*"))
  def adviseAuth(@Local advisor: RequiresUserAdvisor, receiver: MethodAdviceReceiver) {
    advisor.addAdvice(receiver)
  }
}
