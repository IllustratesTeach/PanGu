package nirvana.hall.image.app

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import org.apache.commons.io.IOUtils
import org.junit.Test
import org.zeromq.{ZFrame, ZMQ}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-26
 */
class ImageDealerTest {
  @Test
  def test_route: Unit ={
    val context = ZMQ.context(1)
    val broker = context.socket(zmq.ZMQ.ZMQ_ROUTER)
    broker.bind("inproc://image_router")
    val dealer = context.socket(zmq.ZMQ.ZMQ_DEALER)
    dealer.bind("tcp://*:5671")

    val cprData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 1.toByte
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
    gafisImg.bnData = cprData
    gafisImg.stHead.nImgSize = cprData.length
    val thread = new Thread{
      override def run(): Unit = {
        var i = 0
        val req = context.socket(zmq.ZMQ.ZMQ_REQ)
        req.setReceiveTimeOut(2 * 1000)
        req.connect("inproc://image_router")
        while(!Thread.currentThread().isInterrupted){
          try {
            val zframe = new ZFrame(gafisImg.toByteArray())
            zframe.sendAndDestroy(req)
            i += 1
            println(i + "sent")
            ZFrame.recvFrame(req, zmq.ZMQ.ZMQ_DONTWAIT)

            //req.close()
          }catch{
            case e:Throwable =>
              println(e.toString)
          }

          //Thread.sleep(10 * 1000)
        }
      }
    }

    thread.start()



    ZMQ.proxy(broker,dealer,null)


    Thread.currentThread().join()
  }
}
