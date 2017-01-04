package nirvana.hall.extractor.internal

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_def.{FINGERLATMNTSTRUCT, FINGERMNTSTRUCT}
import nirvana.hall.extractor.jni.BaseJniTest
import org.junit.{Assert, Test}

/**
  * fpt特征转换测试
  */
class FPTMntConverterTest extends BaseJniTest{

  @Test
  def test_convertFPTMnt2GafisMnt: Unit ={
    val fpt3 = FPTFile.parseFromInputStream(getClass.getResourceAsStream("/fpt3_tp.fpt"), AncientConstants.GBK_ENCODING)
    val fingerTData = fpt3.left.get.logic3Recs.head.fingers.head

    val tpMnt = FPTMntConverter.convertFingerTData2GafisMnt(fingerTData)
    val fingerMnt = new FINGERMNTSTRUCT
    fingerMnt.fromByteArray(tpMnt.bnData)

    val fpt4 = FPTFile.parseFromInputStream(getClass.getResourceAsStream("/fpt4_lp.fpt"), AncientConstants.GBK_ENCODING)
    val fingerLData = fpt4.right.get.logic03Recs.head.fingers.head
    val lpMnt = FPTMntConverter.convertFingerLData2GafisMnt(fingerLData)

    val fingerLatMnt = new FINGERLATMNTSTRUCT
    fingerLatMnt.fromByteArray(lpMnt.bnData)
    Assert.assertTrue(fingerLatMnt.resolution == 500)
  }
  @Test
  def test_convertGafisMnt2FingerTData: Unit ={
    val gafisMnt = new GAFISIMAGESTRUCT
    gafisMnt.fromStreamReader(getClass.getResourceAsStream("/tp_1.mnt"))
    val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisMnt)
    println(fingerTData.featureCount)
    println(fingerTData.feature)
  }

}
