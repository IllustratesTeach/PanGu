package nirvana.hall.spark.services

import org.junit.Test

/**
 * Created by wangjue on 2016/2/16.
 */
class BigDataStreamTest {


  @Test
  def streamTestConverter (){
    val array = Array("hall-spark/src/test/resources/test_spark_convert.xml")
    BigDataStream.main(array)
    //SaveErrorStream.main(array)
  }
  @Test
  def streamTest (){
    val array = Array("src/test/resources/test_spark.xml")
    BigDataStream.main(array)
    //SaveErrorStream.main(array)
  }
  @Test
  def streamTestFTP (){
    val array = Array("src/test/resources/test_spark_file.xml")
    BigDataStream.main(array)
  }

  @Test
  def streamTestFULL (){
    val array = Array("src/test/resources/test_spark_full.xml")
    BigDataStream.main(array)
  }

  @Test
  def streamTestReExtract (){
    val array = Array("src/test/resources/test_spark_reExtract.xml")
    BigDataStream.main(array)
  }

  @Test
  def streamTestIdCard (){
    val array = Array("src/test/resources/test_spark_idcard.xml")
    BigDataForBMPStream.main(array)
  }

  @Test
  def streamTestWJW (){
    val array = Array("src/test/resources/test_spark_wjw.xml")
    BigDataForBMPStream.main(array)
  }

  @Test
  def streamTestTXT (){
    val array = Array("src/test/resources/test_spark_file.xml","D:\\2010\\http_path\\fail.txt")
    BigDataForFileStream.main(array)
  }


  @Test
  def streamTestBJSJ (){
    val array = Array("src/test/resources/test_spark_bjsj.xml")
    BigDataForBjsjStream.main(array)
  }

}
