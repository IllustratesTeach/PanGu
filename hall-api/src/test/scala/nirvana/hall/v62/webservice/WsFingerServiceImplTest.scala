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
  def test_getTenprintFinger: Unit ={
    val service = getService[WsFingerService]
    val obj = service.getTenprintFinger("", "", "44200322322242323427429")
    val  fileOutPutStream = new FileOutputStream("E:\\44200322322242323427429.fpt")
    obj.writeTo(fileOutPutStream)
    fileOutPutStream.flush()
    fileOutPutStream.close()
    println("文件写入完成")
    assert(null!=obj,"测试出现错误"+obj)
    println(obj)
  }

  @Test
  def test_getLatent: Unit ={
    val service = getService[WsFingerService]
    val dataHandler = service.getLatent("", "", "0000", null, null, null, null, null, null, null)
    println(dataHandler)
    val fileOutPutStream = new FileOutputStream("/Users/songpeng/win7共享/123456.fpt")
    dataHandler.writeTo(fileOutPutStream)
    fileOutPutStream.flush()
    fileOutPutStream.close()
  }

  @Test
  def test_getLatentFinger: Unit ={
    val service = getService[WsFingerService]
    val dataHandler = service.getLatentFinger("","","0000000000000000000001")//00000000000000000000001
    val fileOutPutStream = new FileOutputStream("/Users/songpeng/win7共享/123456.fpt")
    dataHandler.writeTo(fileOutPutStream)
    fileOutPutStream.flush()
    fileOutPutStream.close()
  }
}
