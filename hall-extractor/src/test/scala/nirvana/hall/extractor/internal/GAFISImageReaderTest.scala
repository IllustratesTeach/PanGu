package nirvana.hall.extractor.internal

import java.io.File
import javax.imageio.ImageIO
import javax.imageio.spi.IIORegistry

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

    val img = ImageIO.read(getClass.getResourceAsStream("/lf.img"))
    ImageIO.write(img, "bmp", new File("lf.bmp"));
  }
}
