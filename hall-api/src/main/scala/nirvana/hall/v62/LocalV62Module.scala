package nirvana.hall.v62

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.filter.lp.{CaseAddFilter, CaseDelFilter, CaseGetFilter, LPCardAddFilter}
import nirvana.hall.v62.internal.filter.tp.TPCardAddFilter
import org.apache.tapestry5.ioc.annotations.Contribute
import org.apache.tapestry5.ioc.{OrderedConfiguration, ServiceBinder}

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
    configuration.addInstance("TPCardAddFilter", classOf[TPCardAddFilter])
    configuration.addInstance("LPCardAddFilter", classOf[LPCardAddFilter])
    configuration.addInstance("CaseAddFilter", classOf[CaseAddFilter])
    configuration.addInstance("CaseDelFilter", classOf[CaseDelFilter])
    configuration.addInstance("CaseGetFilter", classOf[CaseGetFilter])
  }
}
