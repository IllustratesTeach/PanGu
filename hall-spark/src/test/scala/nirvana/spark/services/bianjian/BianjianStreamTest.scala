package nirvana.spark.services.bianjian

import org.junit.Test

/**
 * Created by wangjue on 2016/2/16.
 */
class BianjianStreamTest {

  @Test
  def streamTest (){
    val array = Array("src/test/resources/test_spark.xml")
    BianjianStream.main(array)
  }

}
