package nirvana.hall.c.services.gfpt4lib

import java.io.{File, FileInputStream}

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.AncientData._
import nirvana.hall.c.services.gfpt4lib.FPT3File.{FPT3File, Logic1Rec}
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-27
 */
class FPTFileTest {
  @Test
  def test_performance: Unit ={
    val files = FileUtils.listFiles(new File("/Users/jcai/Downloads/fpt-file"),Array[String]("fpt","FPT"),true)
    val it = files.iterator()
    while(it.hasNext){
      val file = it.next()
      println("processing "+file.getName)
      try {
        val is = new FileInputStream(file)
        FPTFile.parseFromInputStream(is)
        IOUtils.closeQuietly(is)
      }catch{
        case e:Throwable=>
          e.printStackTrace()
      }
    }
  }
  @Test
  def test_parse: Unit ={
    val is = getClass.getResourceAsStream("/fpt3.fpt")
    val fpt=FPTFile.parseFromInputStream(is)
    Assert.assertTrue(fpt.isLeft)
    val is2 = getClass.getResourceAsStream("/fpt4.fpt")
    val fpt2=FPTFile.parseFromInputStream(is2)
    Assert.assertTrue(fpt2.isRight)
  }
  @Test
  def test_head: Unit ={
    Assert.assertEquals(690,new Logic1Rec().getDataSize)
  }
  @Test
  def test_read_fpt3: Unit ={
    val bytes = IOUtils.toByteArray(getClass.getResourceAsStream("/fpt3.fpt"))
    val fpt3 = new FPT3File
    fpt3.fromStreamReader(getClass.getResourceAsStream("/fpt3.fpt"),AncientConstants.GBK_ENCODING)
    val fpt3_2= new FPT3File().fromByteArray(fpt3.toByteArray)
    Assert.assertEquals(fpt3.logic1Rec.fileLength.toInt,fpt3.getDataSize)
    Assert.assertEquals(690,new Logic1Rec().getDataSize)
  }
  @Test
  def test_read_fpt4:Unit={
    val fpt4 = new FPT4File
    fpt4.fromStreamReader(getClass.getResourceAsStream("/fpt4.fpt")) //,AncientConstants.GBK_ENCODING)
    Assert.assertEquals(fpt4.logic02Recs.head.fingers.size ,fpt4.logic02Recs.head.sendFingerCount.toInt)
  }
}
