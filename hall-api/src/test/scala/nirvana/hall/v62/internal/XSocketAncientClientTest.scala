package nirvana.hall.v62.internal


import nirvana.hall.v62.internal.c.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.services.AncientData
import org.junit.{Assert, Test}

import scala.reflect._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-12
 */
class XSocketAncientClientTest {
  @Test
  def test_receive: Unit ={
    Assert.assertNull(receive())
    Assert.assertNotNull(receive[GNETANSWERHEADOBJECT]())
  }
  def receive[R <: AncientData :ClassTag](): R = {
    classTag[R] match{
      case t if t == classTag[Nothing] =>
        null.asInstanceOf[R]
      case _ =>
        classTag[R].runtimeClass.newInstance().asInstanceOf[R]
    }
  }
}
