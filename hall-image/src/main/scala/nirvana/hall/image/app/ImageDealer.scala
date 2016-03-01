package nirvana.hall.image.app

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.image.config.ImageConfigSupport
import nirvana.hall.image.internal.FirmDecoderImpl
import nirvana.hall.image.jni.JniLoader
import org.zeromq.{ZMQ, ZFrame}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-26
 */
object ImageDealer extends LoggerSupport{

  def main(args:Array[String]): Unit ={
    val Array(routerSocket)=args
    val serverHome=System.getProperty("server.home","support")
    JniLoader.loadJniLibrary(serverHome,null)

    val config = new ImageConfigSupport {}
    val firm = new FirmDecoderImpl(serverHome,config)
    val context = ZMQ.context(1)
    val worker = context.socket(zmq.ZMQ.ZMQ_REP)
    worker.setReceiveBufferSize(400 * 1024) //image size ~400k
    worker.setReceiveTimeOut(10 * 1000) //timeout
    worker.connect(routerSocket)
    var i = 0
    while (!Thread.currentThread.isInterrupted) {
      val frame = ZFrame.recvFrame(worker)
      if(frame != null) {
        i += 1
        val data = frame.getData
        val img = new GAFISIMAGESTRUCT().fromByteArray(data)
        val originImg = firm.decode(img)
        info(i+"received")

        val frameSent = new ZFrame(originImg.toByteArray)
        frameSent.sendAndDestroy(worker)
        info("sent")
      }
    }

    context.term()
  }
}
