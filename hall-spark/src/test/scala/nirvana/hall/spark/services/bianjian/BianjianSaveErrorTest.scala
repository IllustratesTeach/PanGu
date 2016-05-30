package nirvana.hall.spark.services.bianjian

import org.junit.Test

/**
 * Created by wangjue on 2016/2/22.
 */
class BianjianSaveErrorTest {

  @Test
  def streamTest (){
    val array = Array("src/test/resources/test_spark.xml")
    BianjianSaveErrorStream.main(array)
  }

}
