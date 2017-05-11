package nirvana.hall.api

import com.google.protobuf.ExtensionRegistry
import monad.rpc.protocol.CommandProto.{BaseCommand, CommandStatus}
import monad.rpc.services.ProtobufExtensionRegistryConfiger
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import org.apache.tapestry5.ioc.Configuration
import org.apache.tapestry5.ioc.annotations.{Contribute, EagerLoad, ServiceId}
import org.apache.tapestry5.ioc.services.{ClassNameLocator, PipelineBuilder}
import org.slf4j.Logger

/**
 * protobuf module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-05-21
 */
object LocalProtobufModule {

  @ServiceId("ProtobufRequestHandler")
  def buildProtobufRequestHandler(pipelineBuilder: PipelineBuilder, logger: Logger,
                                  configuration: java.util.List[ProtobufRequestFilter]): ProtobufRequestHandler = {
    val terminator = new ProtobufRequestHandler {
      override def handle(protobufRequest: BaseCommand, responseBuilder: BaseCommand.Builder): Boolean = {
        responseBuilder.setStatus(CommandStatus.FAIL)
        responseBuilder.setMsg("request not handle")
        true
      }
    }
    pipelineBuilder.build(logger, classOf[ProtobufRequestHandler], classOf[ProtobufRequestFilter], configuration, terminator)
  }
  @EagerLoad
  def buildProtobufRegistroy(configruation: java.util.Collection[ProtobufExtensionRegistryConfiger]) = {
    val registry = ExtensionRegistry.newInstance()
    val it = configruation.iterator()
    while (it.hasNext)
      it.next().config(registry)

    registry
  }
  @Contribute(classOf[ExtensionRegistry])
  def provideProtobufCommand(configuration: Configuration[ProtobufExtensionRegistryConfiger], classNameLocator: ClassNameLocator, logger: Logger) {
    configuration.add(new ProtobufExtensionRegistryConfiger {
      override def config(registry: ExtensionRegistry): Unit = {
        val packages = Seq("nirvana.hall.protocol.sys",
          "nirvana.hall.protocol.api",
          "nirvana.hall.protocol.image",
          "nirvana.hall.protocol.extractor")
        val contextClassLoader = Thread.currentThread().getContextClassLoader
        packages.foreach { packageName =>
          val it = classNameLocator.locateClassNames(packageName).iterator()
          while (it.hasNext) {
            val protobufClass = contextClassLoader.loadClass(it.next())
            logger.debug("registry class {}", protobufClass)
            protobufClass.getMethod("registerAllExtensions", classOf[ExtensionRegistry]).invoke(null, registry)
          }
        }
      }
    })
  }
}
