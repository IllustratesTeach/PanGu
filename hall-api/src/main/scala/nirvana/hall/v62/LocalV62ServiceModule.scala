package nirvana.hall.v62

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.filter.lp.{CaseFilter, LPCardFilter}
import nirvana.hall.v62.internal.filter.tp.TPCardFilter
import org.apache.tapestry5.ioc.{OrderedConfiguration, ServiceBinder}
import org.apache.tapestry5.ioc.annotations.Contribute

/**
 * local v62 service module
 * Created by songpeng on 15/12/7.
 */
object LocalV62ServiceModule {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[V62Facade])
  }

  @Contribute(classOf[ProtobufRequestHandler])
  def provideProtobufFilter(configuration: OrderedConfiguration[ProtobufRequestFilter]): Unit = {
    configuration.addInstance("TPCardAddFilter", classOf[TPCardFilter])
    configuration.addInstance("LPCardFilter", classOf[LPCardFilter])
    configuration.addInstance("CaseFilter", classOf[CaseFilter])
  }
}
