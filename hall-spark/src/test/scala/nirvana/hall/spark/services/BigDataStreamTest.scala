package nirvana.hall.spark.services

import org.junit.Test

/**
 * Created by wangjue on 2016/2/16.
 */
class BigDataStreamTest {


  @Test
  def streamTest (){
    val array = Array("src/test/resources/test_spark.xml")
    BigDataStream.main(array)
    //SaveErrorStream.main(array)
  }

}
