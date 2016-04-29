package nirvana.hall.extractor.internal

import java.io.{File, FileInputStream}

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT3File.FPT3File
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
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
    fpt4.fromStreamReader(new FileInputStream(new File("C:\\Users\\wangjue\\Desktop\\fpt_error\\4.0数据版本.fpt")),AncientConstants.GBK_ENCODING  )

    val headL = fpt4.logic03Recs.head.fingers.head
    val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(headL)
    val latentFeature = FPTLatentConverter.convert(disp)
    val mnt = new GAFISIMAGESTRUCT()
    mnt.bnData = latentFeature.toByteArray()
    mnt.stHead.nImgSize = 640

    latentFeature.nWidth
    print(mnt.getDataSize)
    val latent2 = new FINGERLATMNTSTRUCT()
    val fis = new FileInputStream(new File("/Users/jcai/Downloads/650000000999210005000201.mnt"))
    fis.skip(64)
    latent2.fromStreamReader(fis,AncientConstants.GBK_ENCODING )

    Assert.assertEquals(latentFeature.nWidth,latent2.nWidth)
  }

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
}
