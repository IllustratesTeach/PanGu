package nirvana.hall.webservice

import nirvana.hall.webservice.services.TenPrinterExportService
import org.junit.Test

/**
  * Created by songpeng on 2017/5/11.
  */
class TenPrinterExportServiceImplTest extends BaseTestCase{

  @Test
  def test_exportTenPrinterFPT(): Unit ={
    val service = getService[TenPrinterExportService]
    service.exportTenPrinterFPT()
  }
}
