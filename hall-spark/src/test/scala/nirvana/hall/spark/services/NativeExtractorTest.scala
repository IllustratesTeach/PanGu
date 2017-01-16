package nirvana.hall.spark.services

import java.io.File

import com.google.protobuf.ByteString
import nirvana.hall.c.services.afiskernel
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.{GAFISIMAGEHEADSTRUCT, GAFISIMAGESTRUCT}
import nirvana.hall.c.services.kernel.mnt_def.FINGERMNTSTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.extractor.jni.NativeExtractor
import nirvana.hall.image.config.HallImageConfig
import nirvana.hall.image.internal.FirmDecoderImpl
import nirvana.hall.protocol.api.FPTProto.FingerFgp
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import org.apache.commons.io.{FileUtils, IOUtils}
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class NativeExtractorTest extends BaseJniTest{
  @Test
  def test_extractor: Unit ={
    val img = IOUtils.toByteArray(getClass.getResourceAsStream("/tp_1.img"))
    val gafisImg = new GAFISIMAGESTRUCT
    val buffer = ChannelBuffers.wrappedBuffer(img)
    gafisImg.fromStreamReader(buffer)

    val mntBuffer = ChannelBuffers.buffer(gafisImg.stHead.getDataSize + 640)
    val mntHead = gafisImg.stHead
    mntHead.bIsCompressed = 0
    mntHead.nImgSize = 640
    mntHead.szName="FingerRHMMnt"
    mntHead.writeToStreamWriter(mntBuffer)
    val mntBytes = mntBuffer.array()
    NativeExtractor.ExtractMNT_All(img,mntBytes,null,FingerFgp.FINGER_R_THUMB.ordinal().toByte,afiskernel.EXTRACTMODE_NEW.toByte,0)
    FileUtils.writeByteArrayToFile(new File("tp_1.mnt"),mntBytes)
    val mntHead2 = new GAFISIMAGEHEADSTRUCT().fromByteArray(mntBytes)


    val expect = IOUtils.toByteArray(getClass.getResourceAsStream("/tp_1.mnt"))
    val expectHead = new GAFISIMAGEHEADSTRUCT().fromByteArray(expect)
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
    //val data = extractor.extractByGAFISIMGBinary(ByteString.copyFrom(gafisImg.toByteArray()).newInput(),FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate)
    //println(data.get._1.length)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt._1.bnData)

    feature

  }

  @Test
  def test_extract_wsq_tj: Unit ={
    val img = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\tjFPT\\crash\\2897_R_01_crash.img"))
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.fromByteArray(img)
    /*gafisImg.stHead.nHeight = 640
    gafisImg.stHead.nWidth= 640
    gafisImg.stHead.nResolution = 500
    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nImgSize = img.length
    gafisImg.bnData = img*/

    val extractor = new FeatureExtractorImpl
    val mnt = extractor.extractByGAFISIMG(gafisImg,FingerPosition.FINGER_R_THUMB,FeatureType.FingerTemplate)
    //val data = extractor.extractByGAFISIMGBinary(ByteString.copyFrom(gafisImg.toByteArray()).newInput(),FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate)
    //println(data.get._1.length)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt._1.bnData)

    feature

  }

}
