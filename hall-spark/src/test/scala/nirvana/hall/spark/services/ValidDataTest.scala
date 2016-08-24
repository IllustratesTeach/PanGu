package nirvana.hall.spark.services

import org.junit.Test

/**
  * Created by wangjue on 2016/7/21.
  */
class ValidDataTest {

  @Test
  def streamTest (){
    val array = Array("src/test/resources/test_spark_valid.xml")
    ValidDataStream.main(array)
  }
}
