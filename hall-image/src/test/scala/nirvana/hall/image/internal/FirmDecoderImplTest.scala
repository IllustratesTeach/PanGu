package nirvana.hall.image.internal

import java.awt.{RenderingHints, AlphaComposite, Font, Color}
import java.awt.image.{DataBufferByte, BufferedImage}
import java.io.File
import javax.imageio.ImageIO

import com.google.protobuf.ByteString
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.image.jni.BaseJniTest
import nirvana.hall.c.services.AncientData._
import org.apache.commons.io.IOUtils
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class FirmDecoderImplTest extends BaseJniTest{
  @Test
  def test_decode_1700: Unit = {
    val decoder = new FirmDecoderImpl("support")
    val cprData = IOUtils.toByteArray(getClass.getResourceAsStream("/1700.data"))
    decoder.decode("1700", cprData, 640, 640, 500)
  }
  @Test
  def test_decode_gafisimg{
    val decoder = new FirmDecoderImpl("support")
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
  }
  @Test
  def test_decode_1400_vi_wsq{
    val decoder = new FirmDecoderImpl("support")
    val cprData = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    Range(0,409600).foreach{ i=>
      decoder.decode("1400",cprData,640,640,500)
    }
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
