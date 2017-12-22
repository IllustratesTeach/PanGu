package nirvana.hall.extractor.internal

import java.io.{File, ByteArrayInputStream, InputStream}

import com.google.protobuf.ByteString
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_def.{PALMMNTSTRUCT, FINGERMNTSTRUCT}
import nirvana.hall.extractor.jni.BaseJniTest
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.{FingerPosition, NewFeatureTry}
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-11
 */
class FeatureExtractorImplTest extends BaseJniTest{
  @Test
  def test_plain: Unit ={

    val imgData = getClass.getResourceAsStream("/plain.img")
    val bytes = IOUtils.toByteArray(imgData)
    val extractor = new FeatureExtractorImpl
    //    Range(0,100).foreach{i=>
    val mntData = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(bytes),FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate,NewFeatureTry.V1)
    val mnt = new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)
    Assert.assertEquals(496,feature.nWidth)
    Assert.assertEquals(616,feature.nHeight)
    //    }
  }
  @Test
  def test_crash_shanghai: Unit ={

    val imgData = getClass.getResourceAsStream("/crash.img")
    val bytes = IOUtils.toByteArray(imgData)
    val extractor = new FeatureExtractorImpl
//    Range(0,100).foreach{i=>
    val mntData = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(bytes),FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate,NewFeatureTry.V1)
    val mnt = new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)
//    }
  }
  @Test
  def test_extract_shanghai: Unit ={

    val imgData = getClass.getResourceAsStream("/shanghai/31011405020016040001_1_2.img.orginal")
    val extractor = new FeatureExtractorImpl
    val mntData = extractor.extractByGAFISIMGBinary(imgData,FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate,NewFeatureTry.V1)
    val mnt = new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)
  }
  @Test
  def test_extract_palm: Unit ={
    val imgData = getClass.getResourceAsStream("/palm.bmp")

    val extractor = new FeatureExtractorImpl
    val mntData = extractor.extractByGAFISIMGBinary(imgData,FingerPosition.FINGER_L_THUMB,FeatureType.PalmTemplate,NewFeatureTry.V1)
    var mnt = new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1)
    val feature = new PALMMNTSTRUCT
    feature.fromByteArray(mnt.bnData)
    Assert.assertEquals(1,feature.bePalm)
    mnt = new GAFISIMAGESTRUCT().fromStreamReader(getClass.getResourceAsStream("/palm.mnt"))
    feature.fromByteArray(mnt.bnData)
    Assert.assertEquals(1,feature.bePalm)
  }
  @Test
  def test_extract_bmp: Unit ={
    val imgData = getClass.getResourceAsStream("/52022200000000000000000000009409.bmp")

    val extractor = new FeatureExtractorImpl
    val mntData = extractor.extractByGAFISIMGBinary(imgData,FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate,NewFeatureTry.V1)
    val mnt = new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)
  }
  @Test
  def test_extract_wsq: Unit ={
    val img = IOUtils.toByteArray(getClass.getResourceAsStream("/testt.img"))
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.fromByteArray(img)
    /*gafisImg.stHead.nHeight = 640
    gafisImg.stHead.nWidth= 640
    gafisImg.stHead.nResolution = 500
    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nImgSize = img.length
    gafisImg.bnData = img*/
    val extractor = new FeatureExtractorImpl
    if (gafisImg.stHead.nCompressMethod.toInt>=10) {
      val data = extractor.extractByGAFISIMGBinary(ByteString.copyFrom(gafisImg.toByteArray()).newInput(),FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate)
      println(data.get._1.length)
    } else {
      gafisImg.transformForFPT()
      val gafisImg1 = new GAFISIMAGESTRUCT
      gafisImg1.bnData = gafisImg.toByteArray()
      gafisImg1.stHead = gafisImg.stHead
      gafisImg1.stHead.nImgSize = gafisImg1.bnData.size
      val data = extractor.extractByGAFISIMGBinary(ByteString.copyFrom(gafisImg1.toByteArray()).newInput(),FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate)
      println(data.get._1.length)
    }


    //val mnt = extractor.extractByGAFISIMG(gafisImg,FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate)


    /*val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt._1.bnData)*/


  }
  @Test
  def test_extract: Unit ={
    //val img = IOUtils.toByteArray(getClass.getResourceAsStream("/tp_1.img"))
    val img = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\tt\\t1.data"))
    val gafisImg = new GAFISIMAGESTRUCT().fromByteArray(img)

    val extractor = new FeatureExtractorImpl
    val mnt = extractor.extractByGAFISIMG(gafisImg,FingerPosition.FINGER_L_LITTLE,FeatureType.FingerTemplate)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt._1.bnData)

    val bytes = IOUtils.toByteArray(getClass.getResourceAsStream("/tp_1.mnt"))
    val destMntData = new GAFISIMAGESTRUCT().fromByteArray(bytes)
    destMntData.stHead.szName="FingerRHMMnt"
    val expectFeature = new FINGERMNTSTRUCT
    expectFeature.fromByteArray(bytes.slice(64,bytes.length))
    Assert.assertArrayEquals(expectFeature.xx,feature.xx)
    Assert.assertArrayEquals(expectFeature.yy,feature.yy)
    Assert.assertArrayEquals(expectFeature.zz,feature.zz)

  }
  @Test
  def test_nanjing: Unit ={
    extract(getClass.getResourceAsStream("/test.img"),FingerPosition.FINGER_L_INDEX,FeatureType.FingerTemplate)
  }
  def extract(is:InputStream,pos:FingerPosition,featureType: FeatureType): Unit ={
    val img = IOUtils.toByteArray(is)
    val gafisImg = new GAFISIMAGESTRUCT().fromByteArray(img)
    val extractor = new FeatureExtractorImpl
    extractor.extractByGAFISIMG(gafisImg,pos,featureType)
  }


  @Test
  def test_converterNewFeature: Unit ={
    val bytes = getClass.getResourceAsStream("/tp_1.mnt")
    val extractor = new FeatureExtractorImpl
    val newFeature = extractor.ConvertMntOldToNew(bytes)
    Assert.assertEquals(4024,newFeature.size)
  }

  @Test
  def test_extract_idcard_bmp: Unit ={
    val imgData = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\idcard\\52242500000000000000000000007314\\img.bmp"))
    val in = ByteString.copyFrom(imgData).newInput()
    val extractor = new FeatureExtractorImpl
    val mntData = extractor.extractByGAFISIMGBinary(in,FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate,NewFeatureTry.V1)
    val mnt = new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1)
    FileUtils.writeByteArrayToFile(new File("C:\\Users\\wangjue\\Desktop\\idcard\\52242500000000000000000000007314\\mnt.mnt"),mnt.toByteArray())
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)
  }


  @Test
  def test_extract_wjw_jpg: Unit ={
    val imgData = getClass.getResourceAsStream("/jpg.jpg")

    val extractor = new FeatureExtractorImpl
    val mntData = extractor.extractByGAFISIMGBinary(imgData,FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate,NewFeatureTry.V1)
    val mnt = new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1)
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)
  }

  @Test
  def test_extract_idcard_jpg_mnt_save: Unit ={
    val imgData = FileUtils.readFileToByteArray(new File("d:\\img.img"))
    val in = ByteString.copyFrom(imgData).newInput()
    val extractor = new FeatureExtractorImpl
    val mntData = extractor.extractByGAFISIMGBinary(in,FingerPosition.FINGER_L_THUMB,FeatureType.FingerTemplate,NewFeatureTry.V1)
    val mnt = new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1)
    FileUtils.writeByteArrayToFile(new File("D:\\mnt.mnt"),mnt.toByteArray())
    val feature = new FINGERMNTSTRUCT
    feature.fromByteArray(mnt.bnData)
  }

}
