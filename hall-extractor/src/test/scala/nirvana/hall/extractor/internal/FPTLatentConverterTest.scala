package nirvana.hall.extractor.internal

import java.io.{File, FileInputStream}

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.kernel.FPTLDataToMNTDISP
import nirvana.hall.c.services.kernel.mnt_def.FINGERLATMNTSTRUCT
import nirvana.hall.extractor.jni.BaseJniTest
import org.junit.{Assert, Test}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-03-17
  */
class FPTLatentConverterTest extends BaseJniTest{
  @Test
  def test_convert: Unit ={
    val fpt4 = new FPT4File
    fpt4.fromStreamReader(new FileInputStream(new File("/Users/jcai/Downloads/A6500000009992100050002.FPT")),AncientConstants.GBK_ENCODING  )

    val headL = fpt4.logic03Recs.head.fingers.head
    val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(headL)
    val latentFeature = FPTLatentConverter.convert(disp)
    latentFeature.nWidth
    val latent2 = new FINGERLATMNTSTRUCT()
    latent2.fromStreamReader(new FileInputStream(new File("/Users/jcai/Downloads/650000000999210005000201.mnt")),AncientConstants.GBK_ENCODING  )

    Assert.assertEquals(latentFeature.nWidth,latent2.nWidth)
  }
}
