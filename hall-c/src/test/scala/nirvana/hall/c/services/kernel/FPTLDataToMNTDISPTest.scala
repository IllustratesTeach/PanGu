package nirvana.hall.c.services.kernel

import java.io.{File, FileInputStream}

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import org.junit.Test

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-03-17
  */
class FPTLDataToMNTDISPTest {
 @Test
 def test_disp: Unit ={
  val fpt4 = new FPT4File
  fpt4.fromStreamReader(new FileInputStream(new File("/Users/jcai/Downloads/A6500000009992100050002.FPT")),AncientConstants.GBK_ENCODING  )

  val headL = fpt4.logic03Recs.head.fingers.head
  val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(headL)

  val palm = disp.bIsPalm
 }
}
