package nirvana.hall.stream.internal

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{ExecutorService, Executors, ThreadFactory}
import javax.annotation.PostConstruct

import com.google.protobuf.ByteString
import com.lmax.disruptor.{EventTranslator, EventFactory}
import com.lmax.disruptor.dsl.Disruptor
import monad.core.services.LogExceptionHandler
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.stream.config.HallStreamConfigSupport
import nirvana.hall.stream.internal.StreamServiceObject._
import nirvana.hall.stream.services.{StreamService, FeatureSaverService, DecompressService, ExtractService}
import org.apache.tapestry5.ioc.services.RegistryShutdownHub

/**
 * implements stream service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
object StreamServiceObject{
  /**
   * stream event
   */
  class StreamEvent{
    //process id data
    var id:Any = _
    //image data
    var img:GAFISIMAGESTRUCT = _
    //feature type
    var feature:FeatureType = _
    //position
    var position:FingerPosition = _

    //====> if need decompress
    //original image data
    var originalImgData:GAFISIMAGESTRUCT= _
    //feature data
    var featureData:ByteString = _
    def reset(): Unit ={
      id = null
      img = null
      feature = null
      position = null
      originalImgData = null
      featureData = null
    }
  }
  //event factory
  private[internal] val EVENT_FACTORY = new EventFactory[StreamEvent] {
    def newInstance() = new StreamEvent()
  }
}

/**
 * implements StreamService
 * @param config configuration
 */
class StreamServiceImpl(config:HallStreamConfigSupport) extends StreamService{
  private val streamPool: ExecutorService = Executors.newCachedThreadPool(new ThreadFactory {
    private val seq = new AtomicInteger(0)
    def newThread(p1: Runnable) = {
      val t = new Thread(p1)
      t.setName("stream-%s".format(seq.incrementAndGet()))
      t.setDaemon(true)
      t
    }
  })
  //size of to cache awaiting event
  private val buffer = 1 << 10

  private var disruptor:Disruptor[StreamEvent] = _

  @PostConstruct
  def startDisruptor(decompressService: DecompressService,
                     extractService: ExtractService,
                     featureSaverService: FeatureSaverService,
                     registryShutdownHub: RegistryShutdownHub):Unit = {
    disruptor = new Disruptor[StreamEvent](EVENT_FACTORY, buffer, streamPool)
    disruptor.handleExceptionsWith(new LogExceptionHandler)
    //decompress workers
    val decompressWorkers = 0 until config.stream.decompressThread map (x => new DecompressImageWorker(decompressService))
    //extract workers
    val extractWorkers = 0 until config.stream.extractThread map (x => new ExtractFeatureWorker(extractService))
    //saver workers
    val saverWorkers = 0 until config.stream.saveFeatureThread map (x => new FeatureSaverWorker(featureSaverService))

    disruptor.handleEventsWithWorkerPool(decompressWorkers:_*)
      .thenHandleEventsWithWorkerPool(extractWorkers:_*)
      .thenHandleEventsWithWorkerPool(saverWorkers:_*)

    disruptor.start()

    registryShutdownHub.addRegistryShutdownListener(new Runnable {
      override def run(): Unit = disruptor.shutdown()
    })
  }

  /**
   * push event
   * @param id data unique key
   * @param img image data
   * @param position finger position
   * @param featureType feature type
   */
  def pushEvent(id:Any,img:GAFISIMAGESTRUCT,position:FingerPosition,featureType:FeatureType): Unit ={
    disruptor.publishEvent(new EventTranslator[StreamEvent](){
      override def translateTo(t: StreamEvent, l: Long): Unit = {
        t.reset()
        t.id = id
        t.img = img
        t.position = position
        t.feature = featureType
      }
    })
  }
}
