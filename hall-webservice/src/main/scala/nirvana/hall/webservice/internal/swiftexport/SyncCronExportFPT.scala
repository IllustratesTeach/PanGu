package nirvana.hall.webservice.internal.swiftexport

import java.io.File
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{Executors, ThreadFactory}

import com.lmax.disruptor.YieldingWaitStrategy
import com.lmax.disruptor.dsl.{Disruptor, ProducerType}
import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.webservice.services.lightningsupport.{FPTEvent, FPTEventFactory, FPTLightningHandler, FPTLightningProducer}
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.api.services.sync.SyncCronService
import nirvana.hall.webservice.config.HallWebserviceConfig
import org.apache.commons.io.FileUtils
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor
import scala.io.Source

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/11/28
  */
class SyncCronExportFPT(hallWebserviceConfig: HallWebserviceConfig
                        ,fPT5Service: FPT5Service) extends LoggerSupport{

  private lazy val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
  private lazy val threadFactory = new ThreadFactory {
    val index = new AtomicInteger(1)
    val securityManager = System.getSecurityManager
    val threadGroup = if(null != securityManager) securityManager.getThreadGroup else Thread.currentThread().getThreadGroup
    override def newThread(r: Runnable) = new Thread(threadGroup,r,"thread-" + index.getAndIncrement())
  }
  private lazy val factory = new FPTEventFactory
  private val ringBufferSize = 1024 * 1024

  private lazy val disruptor = new Disruptor[FPTEvent](factory
    ,ringBufferSize
    ,threadFactory
    ,ProducerType.MULTI
    ,new YieldingWaitStrategy)

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncCronService: SyncCronService): Unit = {
    if(hallWebserviceConfig.swiftExportService.cron != null){
      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.swiftExportService.cron, StartAtDelay), "export-cron", new Runnable {
        override def run(): Unit = {
          info("begin export")
          try{
            doWork
          }catch{
            case e:Exception =>
              error(ExceptionUtil.getStackTraceInfo(e))
          }
          info("end export")
        }
      })
    }
  }

  private def doWork: Unit ={

    disruptor.handleEventsWith(new FPTLightningHandler(fPT5Service))
    disruptor.start()


    val ringBuffer = disruptor.getRingBuffer
    val producer = new FPTLightningProducer(ringBuffer)


    val cardIdFileDir = hallWebserviceConfig.swiftExportService.cardIdFileDir

    val fileList  = FileUtils.listFiles(new File(cardIdFileDir),Array[String]("txt"),true)

    val files = fileList.iterator()
    var file:File = null
    while(files.hasNext){
      executor.execute(new Runnable {
        override def run() = {
          file = files.next()
          file.renameTo(new File(cardIdFileDir + file.getName + ".completed"))
          val source = Source.fromFile(file)
          val cardIdList= source.getLines()
          for(cardId <- cardIdList){
            val dataEvent = new FPTEvent
            dataEvent.cardId = cardId
            dataEvent.exportType = "TP"
            producer.onData(dataEvent)
          }
        }
      })
    }
  }
}
