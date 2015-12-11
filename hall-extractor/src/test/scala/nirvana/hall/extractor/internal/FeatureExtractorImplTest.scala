package nirvana.hall.extractor.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_def.FINGERMNTSTRUCT
import nirvana.hall.extractor.jni.BaseJniTest
import nirvana.hall.extractor.services.ExtractorModel.{FeatureType, FingerPosition}
import org.apache.commons.io.IOUtils
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-11
 */
class FeatureExtractorImplTest extends BaseJniTest{
  @Test
  def test_extract: Unit ={
    val img = IOUtils.toByteArray(getClass.getResourceAsStream("/lf.img"))
    val gafisImg = new GAFISIMAGESTRUCT
    val buffer = ChannelBuffers.wrappedBuffer(img)
    gafisImg.fromStreamReader(buffer)
    gafisImg.bnData = new Array[Byte](img.length - buffer.readerIndex())
    buffer.readBytes(gafisImg.bnData)

    val extractor = new FeatureExtractorImpl
    val mnt = extractor.extractByGAFISIMG(gafisImg,FingerPosition.FINGER_L_THUMB,FeatureType.Template)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)

    feature

  }
}
