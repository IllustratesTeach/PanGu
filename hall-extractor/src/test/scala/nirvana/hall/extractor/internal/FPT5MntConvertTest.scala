package nirvana.hall.extractor.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.jni.BaseJniTest
import org.junit.Test

/**
  * Created by songpeng on 2017/12/5.
  */
class FPT5MntConvertTest extends BaseJniTest{

  @Test
  def test_convertGafisMnt2LatentFingerFeatureMsg: Unit ={
    val gafisMnt = new GAFISIMAGESTRUCT().fromStreamReader(getClass.getResourceAsStream("/lf.mnt"))
    val latentFingerFeatureMsg = FPT5MntConverter.convertGafisMnt2LatentFingerFeatureMsg(gafisMnt)
    println(latentFingerFeatureMsg)

  }
}
