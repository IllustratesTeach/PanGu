package nirvana.hall.spark.services
import java.io.{ByteArrayInputStream, File}

import nirvana.hall.c.AncientConstants
import org.apache.commons.io.FileUtils
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

/**
  * Created by wangjue on 2017/12/22.
  */
class FIDFileTest {

  @Test
  def test_parseFID: Unit ={
    val file = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\FID\\R1100000008882014080781.fid"))
    val fid = FIDFile.parseFromInputStream(new ByteArrayInputStream(file))
    Assert.assertEquals(fid.fileFlag,"FID")
  }

  @Test
  def test_inputStream: Unit ={
    val file = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\测试平台\\bjsj_palm\\FID\\R1100000008882014080781.fid"))
    val buffer = ChannelBuffers.wrappedBuffer(file)
    //2810
    //
    buffer.readerIndex(2298)
    val byte = buffer.readBytes(512).array()
    byte.foreach(t => print(t))
    println()
    println(new String(byte,AncientConstants.GBK_ENCODING))

  }

}
