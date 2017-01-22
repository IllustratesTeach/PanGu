package nirvana.hall.extractor.internal

import java.io.File
import org.apache.commons.io.{FileUtils}
import org.junit.{Assert, Test}

/**
  * Created by wangjue on 2017/1/22.
  */
class FeatureDisplayTest {
  private lazy val imgData = getClass.getResourceAsStream("/display.img")
  private lazy val mntData  = getClass.getResourceAsStream("/display.mnt")

  @Test
  def test_native_display: Unit ={
    val displayImage = FeatureDisplay.display(imgData,mntData)
    Assert.assertNotNull(displayImage)
    FileUtils.writeByteArrayToFile(new File("display_img_mnt_native.bmp"),displayImage)
  }


}
