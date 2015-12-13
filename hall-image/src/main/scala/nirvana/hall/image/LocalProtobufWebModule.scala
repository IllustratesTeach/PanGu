package nirvana.hall.image

import nirvana.hall.image.internal.ProtobufRpcServerMessageServletFilter
import org.apache.tapestry5.ioc.{Configuration, OrderedConfiguration}
import org.apache.tapestry5.ioc.annotations.Contribute
import org.apache.tapestry5.services.{LibraryMapping, HttpServletRequestFilter, HttpServletRequestHandler}

/**
 * local protobuf web module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-13
 */
object LocalProtobufWebModule {
  @Contribute(classOf[HttpServletRequestHandler])
  def provideProtobufFilter(configuration: OrderedConfiguration[HttpServletRequestFilter]): Unit = {
    configuration.addInstance("protobuf", classOf[ProtobufRpcServerMessageServletFilter], "after:GZIP")
  }
  def contributeComponentClassResolver(configuration: Configuration[LibraryMapping]) {
    configuration.add(new LibraryMapping("api", "nirvana.hall.image"))
  }

}
