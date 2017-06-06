package nirvana.hall.image

import com.google.protobuf.ExtensionRegistry
import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, ProtobufExtensionRegistryConfiger, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.image.internal._
import nirvana.hall.image.services.{FirmDecoder, ImageEncoder}
import nirvana.hall.protocol.image.{FirmImageDecompressProto, ImageCompressProto}
import org.apache.tapestry5.ioc.annotations.{Contribute, EagerLoad, ServiceId}
import org.apache.tapestry5.ioc.services.PipelineBuilder
import org.apache.tapestry5.ioc.{Configuration, OrderedConfiguration, ServiceBinder}
import org.slf4j.Logger

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
object LocalHallImageModule {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[FirmDecoder],classOf[FirmDecoderImpl]).withId("FirmDecoder")
    binder.bind(classOf[ImageEncoder],classOf[ImageEncoderImpl]).withId("ImageEncoder")
  }
  /*
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
  */
  @EagerLoad
  def buildProtobufRegistroy(configruation: java.util.Collection[ProtobufExtensionRegistryConfiger]) = {
    val registry = ExtensionRegistry.newInstance()
    val it = configruation.iterator()
    while (it.hasNext)
      it.next().config(registry)

    registry
  }
  @ServiceId("RpcServerMessageHandler")
  def buildRpcServerMessageHandler(pipelineBuilder: PipelineBuilder, logger: Logger,
                                   configuration: java.util.List[RpcServerMessageFilter])
  : RpcServerMessageHandler = {
    val terminator = new RpcServerMessageHandler {
      /**
       * @param commandRequest message command
       * @return handled if true .
       */
      override def handle(commandRequest: BaseCommand, response: CommandResponse): Boolean = false
    }
    pipelineBuilder.build(logger, classOf[RpcServerMessageHandler], classOf[RpcServerMessageFilter], configuration, terminator)
  }

  @Contribute(classOf[RpcServerMessageHandler])
  def provideSegMatchRequestMessageHandler(configuration: OrderedConfiguration[RpcServerMessageFilter]) {
    configuration.addInstance("FirmImageDecompressRequest", classOf[FirmImageDecompressRequestFilter])
    configuration.addInstance("ImageCompressRequestFilter", classOf[ImageCompressRequestFilter])
  }
  @Contribute(classOf[ExtensionRegistry])
  def provideProtobufCommand(configuration: Configuration[ProtobufExtensionRegistryConfiger]) {
    configuration.add(new ProtobufExtensionRegistryConfiger {
      override def config(registry: ExtensionRegistry): Unit = {
        FirmImageDecompressProto.registerAllExtensions(registry)
        ImageCompressProto.registerAllExtensions(registry)
      }
    })
  }
}
