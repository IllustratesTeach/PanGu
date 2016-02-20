package nirvana.hall.image.internal

import java.awt.image.{BufferedImage, DataBufferByte}
import java.awt.{AlphaComposite, Color, Font, RenderingHints}
import java.io.File
import javax.imageio.ImageIO

import com.google.protobuf.ByteString
import monad.support.services.XmlLoader
import nirvana.hall.c.services.AncientData._
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.image.config.HallImageConfig
import nirvana.hall.image.jni.BaseJniTest
import org.apache.commons.io.IOUtils
import org.junit.{Assert, Test}

import scala.io.Source
import scala.collection.JavaConversions._
/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class FirmDecoderImplTest extends BaseJniTest{
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
  def test_decode_gafisimg{
    val decoder = new FirmDecoderImpl("support",new HallImageConfig)
    val cprData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
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
    g.drawString("PT.ALDIN",200,300)
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
}
