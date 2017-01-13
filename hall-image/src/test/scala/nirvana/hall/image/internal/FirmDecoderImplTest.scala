package nirvana.hall.image.internal

import java.awt.image.{BufferedImage, DataBufferByte}
import java.awt.{AlphaComposite, Color, Font, RenderingHints}
import java.io.{File, FileInputStream}
import javax.imageio.ImageIO

import com.google.protobuf.ByteString
import monad.support.services.XmlLoader
import nirvana.hall.c.services.AncientData._
import nirvana.hall.c.services.gfpt4lib.{FPTFile, fpt4code}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_def.FINGERMNTSTRUCT
import nirvana.hall.image.config.HallImageConfig
import nirvana.hall.image.jni.BaseJniTest
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

import scala.collection.JavaConversions._
import scala.io.Source
/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class FirmDecoderImplTest extends BaseJniTest{
  @Test
  def test_decode_dir: Unit ={
    val decoder = new FirmDecoderImpl("support",new HallImageConfig)
    val files = FileUtils.listFiles(new File("C:\\Users\\wangjue\\Desktop\\大库FPT\\比对丢失分析\\fpt_miss_candidate\\img"),Array[String]("img"),true)
    val itt = files.iterator()
    while (itt.hasNext) {
      val imgFile = itt.next()
      val gafisImg = new GAFISIMAGESTRUCT
      val cprData = FileUtils.readFileToByteArray(imgFile)
      gafisImg.fromByteArray(cprData)
      val expectFeature = new FINGERMNTSTRUCT
      expectFeature.fromByteArray(gafisImg.bnData)
      val originalImg = decoder.decode(gafisImg)
      println("----"+originalImg.toByteArray().length)
    }
  }

  @Test
  def test_decode_shanghai: Unit ={
    val decoder = new FirmDecoderImpl("support",new HallImageConfig)
    val gafisImg = new GAFISIMAGESTRUCT
    //val stream = getClass.getResourceAsStream("/shanghai/31011405020016040001_1_2.img")
    //gafisImg.fromByteArray(IOUtils.toByteArray(stream))
    val cprData = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\大库FPT\\比对丢失分析\\1900_fail\\R9000000000000000013440_20.img"))
    gafisImg.fromByteArray(cprData)
    /*if (gafisImg.stHead.nCompressMethod.toInt < 10) {
      gafisImg.transformForFPT()
      val gafisImg1 = new GAFISIMAGESTRUCT
      gafisImg1.bnData = gafisImg.toByteArray()
      gafisImg1.stHead = gafisImg.stHead
      gafisImg1.stHead.nImgSize = gafisImg1.bnData.length
      gafisImg = gafisImg1
    }*/
    val expectFeature = new FINGERMNTSTRUCT
    expectFeature.fromByteArray(gafisImg.bnData)
    val originalImg = decoder.decode(gafisImg)
    println(originalImg.toByteArray().length)
//    FileUtils.writeByteArrayToFile(new File("31011405020016040001_1_2.img.orginal"),originalImg.toByteArray())
  }
  @Test
  def test_parse_fpt: Unit ={
    val filePath = "C:\\Users\\wangjue\\Desktop\\fail_FPT\\wgetFPT\\fpt\\20161209\\R9999912016100813114072.fpt"

    //val filePath = "C:\\Users\\wangjue\\Desktop\\fail_FPT\\wgetFPT\\fpt\\R1111326224222286523227.fpt"

    val fptEither= FPTFile.parseFromInputStream(new FileInputStream(new File(filePath)))
    fptEither match{
      case Right(fpt4)=>
        fpt4.logic02Recs.foreach { tp =>
          val fingerCount = tp.sendFingerCount.toInt
          assert(fingerCount == tp.fingers.length)
            tp.fingers.foreach { tData =>
              val gafisImg = fpt4code.FPTFingerDataToGafisImage(tData)
              /*if (gafisImg.stHead.nWidth != 640 || gafisImg.stHead.nHeight != 640) {
                println("width or height is not 640,actual width is "+ gafisImg.stHead.nWidth + " height is "+ gafisImg.stHead.nHeight)
                gafisImg.stHead.nWidth = 640
                gafisImg.stHead.nHeight = 640
              }*/
              val decoder = new FirmDecoderImpl("support",new HallImageConfig)
              val dest = decoder.decode(gafisImg)
              FileUtils.writeByteArrayToFile(new File("C:\\Users\\wangjue\\Desktop\\fail_FPT\\wgetFPT\\image\\"+tp.personId+"_"+tData.fgp+".img"),dest.toByteArray())
              println(tData.fgp+"_"+dest.getBnDataLength)
            }
        }
      case Left(fpt3)=>
        fpt3.head
    }

  }
  @Test
  def test_parse_xml: Unit ={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test_config.xml")).mkString
    val config = XmlLoader.parseXML[HallImageConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/image/image.xsd")))
    val dllName = "FPT_DC11.dll"
    val dllPropertyOpt = config.image.dllConcurrent.find(_.name == dllName)
    println(dllPropertyOpt)
    println(XmlLoader.toXml(config))
  }
  @Test
  def test_decode_gafisimg_from_fpt{
    val decoder = new FirmDecoderImpl("support",new HallImageConfig)
    val cprData = IOUtils.toByteArray(getClass.getResourceAsStream("/fpt-error/R2247526324226454323227_2.img"))
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.fromByteArray(cprData)
    /*
    gafisImg.stHead.bIsCompressed = 1.toByte
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_GFS.toByte
    gafisImg.stHead.nWidth = 640
    gafisImg.stHead.nHeight= 640
    gafisImg.bnData = cprData
    gafisImg.stHead.nImgSize = cprData.length
    */


    val dest = decoder.decode(gafisImg)

    Assert.assertNotNull(dest.bnData)

  }
  @Test
  def test_decode_gafisimg_1900{
    val decoder = new FirmDecoderImpl("support",new HallImageConfig)
    val cprData = IOUtils.toByteArray(getClass.getResourceAsStream("/1900-1.data"))
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 1.toByte
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_GFS.toByte
    gafisImg.stHead.nWidth = 640
    gafisImg.stHead.nHeight= 640
    gafisImg.bnData = cprData
    gafisImg.stHead.nImgSize = cprData.length

    val output = ByteString.newOutput(gafisImg.getDataSize)
    gafisImg.writeToStreamWriter(output)
    val byteStringData = output.toByteString
    gafisImg.fromStreamReader(byteStringData.newInput())

    val dest = decoder.decode(gafisImg)

    Assert.assertNotNull(dest.bnData)

  }
  @Test
  def test_decode_gafisimg{
    val decoder = new FirmDecoderImpl("support",new HallImageConfig)
    //val cprData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    val cprData = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\dd\\31010342000007070037_1_0.data"))
    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 1.toByte
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
    gafisImg.bnData = cprData
    gafisImg.stHead.nImgSize = cprData.length

    val output = ByteString.newOutput(gafisImg.getDataSize)
    gafisImg.writeToStreamWriter(output)
    val byteStringData = output.toByteString
    gafisImg.fromStreamReader(byteStringData.newInput())

    val dest = decoder.decode(gafisImg)

    Assert.assertNotNull(dest.bnData)

    val originBytes = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data.uncompressed"))
    Assert.assertArrayEquals(originBytes,dest.bnData)
  }
  @Test
  def test_draw{
    var img = new BufferedImage(640,640,BufferedImage.TYPE_BYTE_GRAY)
    val g = img.createGraphics()
    g.setColor(Color.WHITE);
    g.fillRect(0,0,640,640)
    g.setColor(Color.BLACK);
    g.setFont(new Font("Monaco",Font.BOLD,50))
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.01f))
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.drawString("INAFIS",200,300)
    /*
    g.setFont(new Font("Monaco",Font.BOLD,10))
    g.drawString("PT.ALDINO",10,10)
    */

    ImageIO.write(img,"BMP",new File("s.bmp"))
    img = ImageIO.read(new File("s.bmp"))
    val raster = img.getData
    val e =raster.getDataElements(265,636,null)
    println("e:",e)

    val buffer = img.getData.getDataBuffer.asInstanceOf[DataBufferByte]
    val data = buffer.getData()
    println("width:",img.getWidth(),"height",img.getHeight)
    print("[")
    var m=0;
    for(i <- Range(0,img.getWidth);j <- Range(0,img.getHeight)){
      //if((data(i*j) & 0xff) != 0xff) println(i,j,data(i*j) & 0xff)
      if(buffer.getElem(i*j) != 0xff) {
        print(i*j+",")
        m+=1
        if(m %16 ==0)
          println()
      }
    }
    println("];")
    println("m:",m)

  }

  /**
    * 对比test_decode_gafisimg_1900
    */
  @Test
  def test_decodeByGFS_1900{
    val decoder = new FirmDecoderImpl("support",new HallImageConfig)
    val cprData = IOUtils.toByteArray(getClass.getResourceAsStream("/1900-1.data"))
    val gafisImg = new GAFISIMAGESTRUCT().fromByteArray(cprData)

    val dest = decoder.decodeByGFS(gafisImg)

    Assert.assertNotNull(dest.bnData)

  }
}
