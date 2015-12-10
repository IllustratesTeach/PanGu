package nirvana.hall.extractor

import monad.core.MonadCoreConstants
import monad.core.config.ZkClientConfigSupport
import monad.rpc.services.{RpcServerMessageFilter, RpcServerMessageHandler}
import monad.support.services.ZookeeperTemplate
import org.apache.tapestry5.ioc.{ServiceBinder, OrderedConfiguration}
import org.apache.tapestry5.ioc.annotations.{Contribute, EagerLoad}
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
object LocalHallExtractorModule {
  def bind(binder:ServiceBinder): Unit ={
    //binder.bind(classOf[FirmDecoder],classOf[FirmDecoderImpl]).withId("FirmDecoder")
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

  @Contribute(classOf[RpcServerMessageHandler])
  def provideSegMatchRequestMessageHandler(configuration: OrderedConfiguration[RpcServerMessageFilter]) {
    //configuration.addInstance("FirmImageDecompressReques", classOf[FirmImageDecompressRequestFilter])
  }
}
