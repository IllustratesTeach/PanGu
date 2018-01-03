package nirvana.hall.spark.services

import java.io.{ByteArrayInputStream, File}

import monad.support.MonadSupportConstants
import monad.support.services.XmlLoader
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.internal.GafisPartitionRecordsBjsjSave
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

import scala.io.Source

/**
  * Created by wangjue on 2017/12/14.
  */
class PalmProcessTest extends BaseJniTest{

  @Test
  def test_extract_palm: Unit ={
    val extractor = new FeatureExtractorImpl
    val img = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\bmp\\P5200000000002017099991_12.bmp"))
    val fgp = 12
    val mnt = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(img),FingerPosition.FINGER_R_THUMB,FeatureType.PalmTemplate).get._1
    val feature = new GAFISIMAGESTRUCT
    feature.fromByteArray(mnt)
    feature.stHead.szName = "PalmLMnt"
    val DBFeature = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\bmp\\dbmnt.mnt"))
    val feature1 = new GAFISIMAGESTRUCT
    feature1.fromByteArray(DBFeature)
    println(feature1)
    FileUtils.writeByteArrayToFile(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\bmp\\aaa555.mnt"), mnt)

  }

  @Test
  def test_palm: Unit ={
    val extractor = new FeatureExtractorImpl
    val decoder = new FirmDecoderImpl("support",null)
    val encoder = new ImageEncoderImpl(decoder)
    SparkFunctions.loadImageJNI()
    val img = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\bmp\\P5200000000002017099991_12.bmp"))
    val mnt = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(img),FingerPosition.FINGER_R_THUMB,FeatureType.PalmTemplate).get._1
    val gafisImg = extractor.readByteArrayAsGAFISIMAGE(new ByteArrayInputStream(img))
    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
    val wsq = encoder.encodeWSQ(gafisImg)
    FileUtils.writeByteArrayToFile(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\bmp\\P5200000000002017099991_12.mnt"), mnt)
    FileUtils.writeByteArrayToFile(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\bmp\\P5200000000002017099991_12.wsq"), wsq.toByteArray())
    Assert.assertEquals(mnt.length,8256)
  }

  @Test
  def test_fid_palm: Unit ={
    val files = FileUtils.listFiles(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\FID"),Array("fid","FID"),true)
    val it = files.iterator()
    while (it.hasNext) {
      val file = it.next()
      val fileName = file.getName.substring(0,file.getName.lastIndexOf("."))
      val fid = FIDFile.parseFromInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(file)))
      println(fid.name)
      if (fid.palms.length > 0) {
        fid.palms.foreach{t =>
          val gafisImg = fpt4code.FPTFingerDataToGafisImage(t).toByteArray()
          t.fgp match {
            case "1" => processImg(gafisImg,"PalmRMnt",fileName)
            case "2" => processImg(gafisImg,"PalmLMnt",fileName)
            case _ => println(fileName+" ,position : [ "+t.fgp + " ] do not extractor feature!")
          }
        }
      }
    }
  }

  def processImg(img : Array[Byte], szName : String, fileName : String): Unit = {
    val extractor = new FeatureExtractorImpl
    val decoder = new FirmDecoderImpl("support",null)
    val encoder = new ImageEncoderImpl(decoder)
    SparkFunctions.loadImageJNI()

    val mnt = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(img),FingerPosition.FINGER_R_THUMB,FeatureType.PalmTemplate).get._1
    val feature = new GAFISIMAGESTRUCT
    feature.fromByteArray(mnt)
    feature.stHead.szName = szName
    val gafisImg = extractor.readByteArrayAsGAFISIMAGE(new ByteArrayInputStream(img))
    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_PALM.toByte
    val wsq = encoder.encodeWSQ(gafisImg)
    val saveDir = new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\FID\\"+fileName)
    if (!saveDir.exists()) saveDir.mkdirs()
    FileUtils.writeByteArrayToFile(new File(saveDir+"\\"+fileName+"_"+szName+".fea"), feature.toByteArray())
    FileUtils.writeByteArrayToFile(new File(saveDir+"\\"+fileName+"_"+szName+".wsq"), wsq.toByteArray())

  }

}
