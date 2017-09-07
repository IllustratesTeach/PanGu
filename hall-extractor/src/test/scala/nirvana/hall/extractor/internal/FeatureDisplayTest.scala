package nirvana.hall.extractor.internal

import java.io.{ByteArrayInputStream, File}
import java.nio.ByteOrder

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_checker_def.MNTDISPSTRUCT
import nirvana.hall.extractor.jni.{BaseJniTest, NativeExtractor}
import org.apache.commons.io.FileUtils
import org.junit.{Assert, Test}

/**
  * Created by wangjue on 2017/1/22.
  */
class FeatureDisplayTest  extends BaseJniTest{
  private lazy val imgData = getClass.getResourceAsStream("/display.img")
  private lazy val mntData  = getClass.getResourceAsStream("/display.mnt")

  @Test
  def test_native_display: Unit ={
    val displayImage = FeatureDisplay.display(imgData,mntData)
    Assert.assertNotNull(displayImage)
    FileUtils.writeByteArrayToFile(new File("display_img_mnt_native.bmp"),displayImage)
  }



  @Test
  def test_native_desk_display: Unit ={
    val img = FileUtils.readFileToByteArray(new File("d:\\img.img"))
    val mnt = FileUtils.readFileToByteArray(new File("d:\\mnt.mnt"))
    val displayImage = FeatureDisplay.display(new ByteArrayInputStream(img),new ByteArrayInputStream(mnt))
    Assert.assertNotNull(displayImage)
    FileUtils.writeByteArrayToFile(new File("display_img_mnt_native_1.bmp"),displayImage)
  }

}
