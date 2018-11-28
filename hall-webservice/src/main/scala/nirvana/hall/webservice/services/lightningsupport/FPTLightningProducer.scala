package nirvana.hall.webservice.services.lightningsupport

import com.lmax.disruptor.RingBuffer

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/11/28
  */
class FPTLightningProducer(ringBuffer: RingBuffer[FPTEvent]) {
    def onData(fPTEvent: FPTEvent): Unit ={
      val sequence = ringBuffer.next()
      try{
        val event = ringBuffer.get(sequence)
        event.cardId = fPTEvent.cardId
      }finally {
        ringBuffer.publish(sequence)
      }
    }
}
