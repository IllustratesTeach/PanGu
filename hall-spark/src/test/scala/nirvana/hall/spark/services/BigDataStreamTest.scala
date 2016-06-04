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
    val array = Array("hall-spark/src/test/resources/test_spark.xml")
    BigDataStream.main(array)
    //SaveErrorStream.main(array)
  }

}
