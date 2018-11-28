package nirvana.hall.webservice.services.lightningsupport

import com.lmax.disruptor.EventFactory

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/11/28
  */
class FPTEventFactory extends EventFactory[FPTEvent]{
  override def newInstance(): FPTEvent = {
    new FPTEvent
  }
}
