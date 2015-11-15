package nirvana.hall.v62

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.filter._
import org.apache.tapestry5.ioc.{OrderedConfiguration, ServiceBinder}
import org.apache.tapestry5.ioc.annotations.Contribute

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
object LocalV62Module {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[V62Facade])
  }
  @Contribute(classOf[ProtobufRequestHandler])
  def provideProtobufFilter(configuration: OrderedConfiguration[ProtobufRequestFilter]): Unit = {
    configuration.addInstance("AddTPCardFilter", classOf[AddTPCardFilter])
    configuration.addInstance("AddLPCardFilter", classOf[AddLPCardFilter])
    configuration.addInstance("AddCaseFilter", classOf[AddCaseFilter])
    configuration.addInstance("DelCaseFilter", classOf[DelCaseFilter])
  }
}
