package nirvana.hall.api

import monad.core.MonadCoreConstants
import monad.core.config.ZkClientConfigSupport
import monad.rpc.services.{RpcServerMessageFilter, RpcServerMessageHandler}
import monad.support.services.ZookeeperTemplate
import nirvana.hall.api.internal._
import nirvana.hall.api.internal.filter._
import nirvana.hall.api.internal.fpt.FPTServiceImpl
import nirvana.hall.api.internal.sync.SyncCronServiceImpl
import nirvana.hall.api.services._
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.sync.SyncCronService
import org.apache.tapestry5.ioc.annotations.{Contribute, EagerLoad}
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor
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
    binder.bind(classOf[FPTService], classOf[FPTServiceImpl])
    binder.bind(classOf[SyncCronService], classOf[SyncCronServiceImpl]).eagerLoad()
  }

  @Contribute(classOf[RpcServerMessageHandler])
  def provideProtobufFilter(configuration: OrderedConfiguration[RpcServerMessageFilter]): Unit = {
    configuration.addInstance("TPCardFilter", classOf[TPCardFilter])
    configuration.addInstance("LPCardFilter", classOf[LPCardFilter])
    configuration.addInstance("LPPalmFilter", classOf[LPPalmFilter])
    configuration.addInstance("CaseFilter", classOf[CaseInfoFilter])
    configuration.addInstance("QueryFilter", classOf[QueryFilter])
    configuration.addInstance("SyncDataFilter", classOf[SyncDataFilter])
    configuration.addInstance("MatchRelationFilter", classOf[MatchRelationFilter])
  }

  //增加EagerLoad,避免出现deadlock
  @EagerLoad
  def buildZookeeperTemplate(config: ZkClientConfigSupport, periodExecutor: PeriodicExecutor, hub: RegistryShutdownHub): ZookeeperTemplate = {
    val rootZk = new ZookeeperTemplate(config.zk.address)
    rootZk.start(null)
    rootZk.createPersistPath(config.zk.root + MonadCoreConstants.MACHINES)
    rootZk.createPersistPath(config.zk.root + MonadCoreConstants.HEARTBEATS)
    rootZk.createPersistPath(config.zk.root + MonadCoreConstants.ERRORS)
    rootZk.shutdown()

    val zk = new ZookeeperTemplate(config.zk.address, Some(config.zk.root), config.zk.timeoutInMills)
    zk.start(hub)
    zk.startCheckFailed(periodExecutor)

    zk
  }
//  @Match(Array("*"))
//  def adviseAuth(@Local advisor: RequiresUserAdvisor, receiver: MethodAdviceReceiver) {
//    advisor.addAdvice(receiver)
//  }
}
