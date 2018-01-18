package nirvana.hall.spark.services

import java.io.File

import junit.framework.Assert
import nirvana.hall.c.services.gfpt5lib.{fpt5util}
import org.apache.commons.io.FileUtils
import org.junit.Test

/**
  * Created by wangjue on 2018/1/3.
  */
class FPT5Test {

  @Test
  def test_parseFPTX(): Unit ={
    val fptx = FileUtils.readFileToString(new File("C:\\Users\\wangjue\\Desktop\\FPT5\\贵州fptx\\tp\\R5205274100002017120015.fptx"))
    val fptFile = fpt5util.parseFPT5(fptx)
    Assert.assertEquals(fptFile.packageHead.version,"FPT0500")
  }

  @Test
  def test_unzipFPTX: Unit ={
    val zip = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\FPT5\\贵州fptx\\tp\\R5226010890002017120001.fptx.zip"))
    //val unzip = SparkFunctions.unzipByURL("http://10.1.7.204/fpt/fpt5/R5226010890002017120001.fptx.zip")
    val unzip = SparkFunctions.unzipByArray(zip)
    println(unzip.length)
  }

}
