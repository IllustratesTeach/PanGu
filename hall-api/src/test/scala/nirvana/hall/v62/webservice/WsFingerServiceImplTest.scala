package nirvana.hall.v62.webservice

import java.io.FileOutputStream

import nirvana.hall.api.webservice.services.WsFingerService
import nirvana.hall.v62.BaseV62TestCase
import org.junit.Test

/**
  * Created by songpeng on 2016/12/5.
  */
class WsFingerServiceImplTest extends BaseV62TestCase{


  @Test
  def test_getTenprintRecord: Unit ={
    val service = getService[WsFingerService]
    val dataHandler = service.getTenprintRecord("","", "3702", null, null, null, null, null, null, null, null, null, null, null, null, null, null)
    val fileOutPutStream = new FileOutputStream("/Users/songpeng/win7共享/getTenprintRecord.fpt")
    dataHandler.writeTo(fileOutPutStream)
    fileOutPutStream.flush()
    fileOutPutStream.close()
  }
  @Test
  def test_getTenprintFinger: Unit ={
    val service = getService[WsFingerService]
    val obj = service.getTenprintFinger("", "", "1234567890")
    val  fileOutPutStream = new FileOutputStream("/Users/songpeng/win7共享/1234567890.fpt")
    obj.writeTo(fileOutPutStream)
    fileOutPutStream.flush()
    fileOutPutStream.close()
  }

  @Test
  def test_getLatent: Unit ={
    val service = getService[WsFingerService]
    val dataHandler = service.getLatent("", "", "0000", null, null, null, null, null, null, null)
    println(dataHandler)
    val fileOutPutStream = new FileOutputStream("/Users/songpeng/win7共享/getLatent.fpt")
    dataHandler.writeTo(fileOutPutStream)
    fileOutPutStream.flush()
    fileOutPutStream.close()
  }

  @Test
  def test_getLatentFinger: Unit ={
    val service = getService[WsFingerService]
    val dataHandler = service.getLatentFinger("","","123456")
    val fileOutPutStream = new FileOutputStream("/Users/songpeng/win7共享/123456.fpt")
    dataHandler.writeTo(fileOutPutStream)
    fileOutPutStream.flush()
    fileOutPutStream.close()
  }
}
