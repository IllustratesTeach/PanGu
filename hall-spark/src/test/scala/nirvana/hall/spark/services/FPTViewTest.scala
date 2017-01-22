package nirvana.hall.spark.services

import org.junit.Test
/**
  * Created by wangjue on 2017/1/10.
  */
class FPTViewTest {

  @Test
  def viewTest() : Unit = {
    val fptPath = "D:\\ftp\\cc\\马江.FPT"
    FPTView.main(Array(fptPath))
    Thread.sleep(100000)
  }

}
