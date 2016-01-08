package nirvana.hall.api

import com.google.protobuf.ExtensionRegistry
import monad.rpc.services.ProtobufExtensionRegistryConfiger
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{ResponseStatus, BaseResponse, BaseRequest}
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
      override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder): Boolean = {
        responseBuilder.setStatus(ResponseStatus.FAIL)
        responseBuilder.setMessage("request not handle")
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
        val packages = Seq("nirvana.hall.protocol.sys", "nirvana.hall.protocol.v62")
        val contextClassLoader = Thread.currentThread().getContextClassLoader
        packages.foreach { packageName =>
          val it = classNameLocator.locateClassNames(packageName).iterator();
          while (it.hasNext) {
            val protobufClass = contextClassLoader.loadClass(it.next());
            logger.debug("registry class {}", protobufClass)
            protobufClass.getMethod("registerAllExtensions", classOf[ExtensionRegistry]).invoke(null, registry)
          }
        }
      }
    })
  }
}
