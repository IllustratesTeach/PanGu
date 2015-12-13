package nirvana.hall.protobuf

import nirvana.hall.protobuf.internal.ProtobufRpcServerMessageServletFilter
import org.apache.tapestry5.ioc.OrderedConfiguration
import org.apache.tapestry5.ioc.annotations.Contribute
import org.apache.tapestry5.services.{HttpServletRequestFilter, HttpServletRequestHandler}

/**
 * local protobuf web module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-13
 */
object LocalProtobufWebModule {
  @Contribute(classOf[HttpServletRequestHandler])
  def provideProtobufFilter(configuration: OrderedConfiguration[HttpServletRequestFilter]): Unit = {
    configuration.addInstance("ProtobufRpcServerMessageServletFilter", classOf[ProtobufRpcServerMessageServletFilter], "after:GZIP")
  }
}
