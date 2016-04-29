package nirvana.hall.extractor.internal

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_def.FINGERMNTSTRUCT
import nirvana.hall.extractor.jni.BaseJniTest
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.{NewFeatureTry, FingerPosition}
import org.apache.commons.io.IOUtils
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-11
 */
class FeatureExtractorImplTest extends BaseJniTest{
  @Test
  def test_extract_bmp: Unit ={
    val imgData = getClass.getResourceAsStream("/bmp.bmp")

    val extractor = new FeatureExtractorImpl
    val mntData = extractor.extractByGAFISIMGBinary(imgData,FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate,NewFeatureTry.V2)
    val mnt = new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)
  }
  @Test
  def test_extract_wsq: Unit ={
    val img = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data.uncompressed"))
    val gafisImg = new GAFISIMAGESTRUCT
    //gafisImg.fromByteArray(img1)
    gafisImg.stHead.nHeight = 640
    gafisImg.stHead.nWidth= 640
    gafisImg.stHead.nResolution = 500
    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nImgSize = img.length
    gafisImg.bnData = img

    val extractor = new FeatureExtractorImpl
    val mnt = extractor.extractByGAFISIMG(gafisImg,FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)

    feature

  }
  @Test
  def test_extract: Unit ={
    val img = IOUtils.toByteArray(getClass.getResourceAsStream("/tp_1.img"))
    val gafisImg = new GAFISIMAGESTRUCT().fromByteArray(img)

    val extractor = new FeatureExtractorImpl
    val mnt = extractor.extractByGAFISIMG(gafisImg,FingerPosition.FINGER_R_THUMB,FeatureType.FingerTemplate)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)

    val bytes = IOUtils.toByteArray(getClass.getResourceAsStream("/tp_1.mnt"))
    val destMntData = new GAFISIMAGESTRUCT().fromByteArray(bytes)
    destMntData.stHead.szName="FingerRHMMnt"
    val expectFeature = new FINGERMNTSTRUCT
    expectFeature.fromByteArray(bytes.slice(64,bytes.length))
    Assert.assertArrayEquals(expectFeature.xx,feature.xx)
    Assert.assertArrayEquals(expectFeature.yy,feature.yy)
    Assert.assertArrayEquals(expectFeature.zz,feature.zz)

  }
}
