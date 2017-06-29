package nirvana.hall.webservice

import nirvana.hall.webservice.services.xingzhuan.LocalCheckinService
import org.junit.Test

/**
  * Created by shishijie on 2017/6/20.
  */
class MathResultServiceImplTest extends BaseTestCase{

  @Test
  def test_exportTenPrinterFPT(): Unit ={
    val service = getService[LocalCheckinService]
    service.doWork()
  }
}
