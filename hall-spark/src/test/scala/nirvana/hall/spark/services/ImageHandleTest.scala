package nirvana.hall.spark.services
import java.awt.image.BufferedImage
import java.io.{ByteArrayOutputStream, FileInputStream, FileOutputStream}
import javax.imageio.ImageIO

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import org.junit.Test

/**
  * Created by wangjue on 2016/11/29.
  */
class ImageHandleTest {

  @Test
  def handle() : Unit = {
    val jpg = new FileInputStream("C:\\Users\\wangjue\\Desktop\\ImageHandle\\idcard.bmp")
    val image = ImageIO.read(jpg)

    val outImage = new FileOutputStream("d:\\img.bmp")
    val tempImage = new BufferedImage(640,640,BufferedImage.TYPE_BYTE_GRAY)

    val g = tempImage.createGraphics()
    g.fillRect(0,0,640,640)
    val width = (640-image.getWidth)/2
    val height = (640-image.getHeight)/2
    g.drawImage(image,width,height,image.getWidth,image.getHeight,null)
    g.dispose()
    val gafisImg = new GAFISIMAGESTRUCT

    val tempByteArray = new ByteArrayOutputStream()
    ImageIO.write(tempImage,"bmp",tempByteArray)

    //gafisImg.fromByteArray(tempByteArray.toByteArray)

    outImage.write(tempByteArray.toByteArray)

    /*ImageIO.write(tempImage,"jpg",outImage)
    outImage.close()*/

  }

}
