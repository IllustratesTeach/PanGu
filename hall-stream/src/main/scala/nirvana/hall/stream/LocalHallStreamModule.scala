package nirvana.hall.stream

import nirvana.hall.stream.internal.StreamServiceImpl
import nirvana.hall.stream.services.StreamService
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * local hall stream module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
object LocalHallStreamModule {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[StreamService],classOf[StreamServiceImpl])
  }
}
