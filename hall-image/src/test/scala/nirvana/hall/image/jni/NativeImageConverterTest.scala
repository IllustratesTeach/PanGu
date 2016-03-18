package nirvana.hall.image.jni

import java.io.File
import javax.imageio.spi.IIORegistry

import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.support.services.GAFISImageReaderSpi
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class NativeImageConverterTest extends BaseJniTest{
  val iioRegistry = IIORegistry.getDefaultInstance
  iioRegistry.registerServiceProvider(new GAFISImageReaderSpi)

  @Test
  def test_gfs: Unit ={
    var seq=0;
    val fptOpt=FPTFile.parseFromInputStream(getClass.getResourceAsStream("/gfs.fpt"))
    fptOpt match{
      case Left(fpt3)=>
        fpt3.logic3Recs.foreach{rec=>
          rec.fingers.foreach{f=>
            val method = f.imgCompressMethod
            val img=f.imgData
            val width =f.imgHorizontalLength
            val height = f.imgVerticalLength
            val gafisImg = new GAFISIMAGESTRUCT
            gafisImg.fromByteArray(img)

            FileUtils.writeByteArrayToFile(new File("1900-"+seq+".data"),img)

            /*
            val destImg = new GAFISIMAGESTRUCT
            destImg.stHead.fromByteArray(gafisImg.stHead.toByteArray)
            destImg.stHead.nImgSize = destImg.stHead.nWidth * destImg.stHead.nHeight
            destImg.bnData = new Array[Byte](destImg.stHead.nImgSize)
            */

            val destImgBytes = new Array[Byte](gafisImg.stHead.getDataSize + gafisImg.stHead.nWidth * gafisImg.stHead.nHeight)
            NativeImageConverter.decodeByGFS(gafisImg.toByteArray(),destImgBytes)
            val dest = new GAFISIMAGESTRUCT().fromByteArray(destImgBytes)
            dest.stHead
            seq +=1
            /*
            val destImgBmp= ImageIO.read(new ByteArrayInputStream(destImgBytes))
            ImageIO.write(destImgBmp, "bmp", new File(seq+".bmp"));
            */
          }
        }
      case Right(fpt4)=>
    }

  }
  @Test
  def test_wsq: Unit ={
    val is = getClass.getResourceAsStream("/wsq.data")
    val cpr_data = IOUtils.toByteArray(is)
    val destIO = getClass.getResourceAsStream("/wsq.data.uncompressed")
    val expect = IOUtils.toByteArray(destIO)
    0 until 100000 foreach { i =>
      val originalImage = NativeImageConverter.decodeByWSQ(cpr_data)
      val originalData = originalImage.getData
      Assert.assertArrayEquals(expect, originalData)

      //test compress
      val cpr = NativeImageConverter.encodeByWSQ(originalData, 640, 640, 500, 10)
      val originalData2 = NativeImageConverter.decodeByWSQ(cpr).getData
      Assert.assertEquals(expect.length, originalData2.length)
    }
  }
}
