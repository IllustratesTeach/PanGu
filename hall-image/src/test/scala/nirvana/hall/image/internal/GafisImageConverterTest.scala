package nirvana.hall.image.internal

import java.io.FileOutputStream
import javax.imageio.ImageIO
import javax.imageio.spi.IIORegistry

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.support.services.GAFISImageReaderSpi
import org.apache.commons.io.IOUtils
import org.junit.Test

/**
  * Created by songpeng on 2017/1/10.
  */
class GafisImageConverterTest {

  @Test
  def test_convertGafisImage2BMP: Unit ={
    val imgData = IOUtils.toByteArray(getClass.getResourceAsStream("/lf.img"))
    val gafisImage = new GAFISIMAGESTRUCT
    gafisImage.fromByteArray(imgData)
    val bmpData = GafisImageConverter.convertGafisImage2BMP(gafisImage)
    val out = new FileOutputStream("/Users/songpeng/win7共享/test.bmp")
    out.write(bmpData)
    out.close()
    val outJpg = new FileOutputStream("/Users/songpeng/win7共享/test.jpg")
    val jpgData = GafisImageConverter.convertGafisImage2jgp(gafisImage)
    outJpg.write(jpgData)
    outJpg.close()

  }
  @Test
  def test_GAFISImageReaderSpi: Unit ={
    val iioRegistry = IIORegistry.getDefaultInstance
    iioRegistry.registerServiceProvider(new GAFISImageReaderSpi)

    val img = ImageIO.read(getClass.getResourceAsStream("/lf.img"))
    val out = new FileOutputStream("/Users/songpeng/win7共享/test.bmp")
    ImageIO.write(img, "bmp", out)
    out.close()
  }
}
