package nirvana.hall.extractor

import com.google.protobuf.ExtensionRegistry
import monad.core.MonadCoreConstants
import monad.core.config.ZkClientConfigSupport
import monad.rpc.services.{ProtobufExtensionRegistryConfiger, RpcServerMessageFilter, RpcServerMessageHandler}
import monad.support.services.ZookeeperTemplate
import nirvana.hall.extractor.internal._
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.protocol.extract.{ExtractProto, FeatureDisplayProto, LatentConverterExtractProto, OldConverterNewProto}
import org.apache.tapestry5.ioc.annotations.{Contribute, EagerLoad}
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor
import org.apache.tapestry5.ioc.{Configuration, OrderedConfiguration, ServiceBinder}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
object LocalHallExtractorModule {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[FeatureExtractor],classOf[FeatureExtractorImpl]).withId("FeatureExtractor")
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
    configuration.addInstance("ExtractRequest", classOf[ExtractRequestFilter])
    configuration.addInstance("LatentConverterExtractRequest", classOf[LatentConverterExtractRequestFilter])
    configuration.addInstance("OldConverterNewRequest", classOf[OldConverterNewRequestFilter])
    configuration.addInstance("FeatureDisplayRequest", classOf[FeatureDisplayFilter])
  }
  @Contribute(classOf[ExtensionRegistry])
  def provideProtobufCommand(configuration: Configuration[ProtobufExtensionRegistryConfiger]) {
    configuration.add(new ProtobufExtensionRegistryConfiger {
      override def config(registry: ExtensionRegistry): Unit = {
        ExtractProto.registerAllExtensions(registry)
        LatentConverterExtractProto.registerAllExtensions(registry)
        OldConverterNewProto.registerAllExtensions(registry)
        FeatureDisplayProto.registerAllExtensions(registry)
      }
    })
  }
}
