package nirvana.hall.spark.services

import java.io.{File, FileInputStream}

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT3File.FPT3File
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.kernel.FPTLDataToMNTDISP
import nirvana.hall.extractor.internal.FPTLatentConverter
import org.junit.{Test}

/**
 * Created by wangjue on 2016/3/22.
 */
class FPTLatentConverterTest extends BaseJniTest {

  @Test
  def test_convert3: Unit ={
    val fpt3 = new FPT3File
    fpt3.fromStreamReader(new FileInputStream(new File("C:\\Users\\wangjue\\Desktop\\fpt_error\\A3262325324222222506255.fpt")),AncientConstants.GBK_ENCODING  )
    val flag = fpt3.head.flag
    val headL = fpt3.logic2Recs.head.fingers.head
    val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(headL)
    val latentFeature = FPTLatentConverter.convert(disp)
    println(latentFeature.nWidth)
  }

  @Test
  def test_convert4: Unit ={
    val fpt4 = new FPT4File
    fpt4.fromStreamReader(new FileInputStream(new File("C:\\Users\\wangjue\\Desktop\\fpt_error\\A2342124324222297342355.fpt")),AncientConstants.GBK_ENCODING  )

    val headL = fpt4.logic03Recs.head.fingers.head
    val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(headL)
    val latentFeature = FPTLatentConverter.convert(disp)
    println(latentFeature.nWidth)
  }

}
