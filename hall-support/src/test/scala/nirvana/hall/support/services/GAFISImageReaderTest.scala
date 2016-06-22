package nirvana.hall.support.services

import java.io.File
import javax.imageio.ImageIO
import javax.imageio.spi.IIORegistry

import org.apache.commons.io.FileUtils
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-14
 */
class GAFISImageReaderTest {
  @Test
  def test_read: Unit ={
    val iioRegistry = IIORegistry.getDefaultInstance
    iioRegistry.registerServiceProvider(new GAFISImageReaderSpi)

    val img = ImageIO.read(getClass.getResourceAsStream("/te.data"))
    ImageIO.write(img, "bmp", new File("te.bmp"));
  }

  @Test
  def test_read_dir: Unit ={
    val iioRegistry = IIORegistry.getDefaultInstance
    iioRegistry.registerServiceProvider(new GAFISImageReaderSpi)
    val files  = FileUtils.listFiles(new File("C:\\Users\\wangjue\\Desktop\\FPT_IMG\\img"),Array[String]("img"),true)
    val itt = files.iterator()
    while (itt.hasNext) {
      val imgFile = itt.next()
      val img = ImageIO.read(imgFile)
      ImageIO.write(img, "bmp", new File("C:\\Users\\wangjue\\Desktop\\FPT_IMG\\img\\bmp\\"+imgFile.getName+".bmp"))
    }

  }
}
