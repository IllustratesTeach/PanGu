package nirvana.hall.spark.services

import org.junit.Test
/**
  * Created by wangjue on 2017/1/10.
  */
class FPTViewTest {

  @Test
  def viewTest() : Unit = {
    val fptPath = "D:\\ftp\\cc\\R9999912016100712431151.fpt"
    FPTView.parseFPT(fptPath)
    FPTView.viewFPT()
    FPTView.toolFPT()
    Thread.sleep(10000)
  }

}
