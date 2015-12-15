package nirvana.hall.stream

import com.google.protobuf.ExtensionRegistry
import monad.rpc.services.ProtobufExtensionRegistryConfiger
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.image.FirmImageDecompressProto
import nirvana.hall.stream.internal.{RpcHttpClientImpl, StreamServiceImpl}
import nirvana.hall.stream.services.{RpcHttpClient, StreamService}
import org.apache.tapestry5.ioc.{Configuration, ServiceBinder}
import org.apache.tapestry5.ioc.annotations.Contribute

/**
 * local hall stream module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
object LocalHallStreamModule {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[StreamService],classOf[StreamServiceImpl]).withId("StreamService")
    binder.bind(classOf[RpcHttpClient],classOf[RpcHttpClientImpl]).withId("RpcHttpClient")
  }
  @Contribute(classOf[ExtensionRegistry])
  def provideProtobufCommand(configuration: Configuration[ProtobufExtensionRegistryConfiger]) {
    configuration.add(new ProtobufExtensionRegistryConfiger {
      override def config(registry: ExtensionRegistry): Unit = {
        FirmImageDecompressProto.registerAllExtensions(registry)
        ExtractProto.registerAllExtensions(registry)
      }
    })
  }
}
