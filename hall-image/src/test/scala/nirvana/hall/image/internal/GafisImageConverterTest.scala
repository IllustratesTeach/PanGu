package nirvana.hall.image.internal

import java.io.FileOutputStream

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
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
}
