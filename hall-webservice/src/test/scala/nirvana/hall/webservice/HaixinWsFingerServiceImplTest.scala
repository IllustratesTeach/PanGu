package nirvana.hall.webservice

import java.io.{File}
import javax.activation.{DataHandler, FileDataSource}

import nirvana.hall.webservice.services.haixin.WsHaiXinFingerService
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

/**
  * Created by yuchen on 2017/7/25.
  */
class HaixinWsFingerServiceImplTest extends BaseTestCase{

  @Test
  def test_setFinger: Unit ={
    val dataHandler = new DataHandler(new FileDataSource(new File("D://44200482222242323422163.fpt")))
    val service = getService[WsHaiXinFingerService]
    val result = service.setFinger("1701","0101","3701","44200482222242323422163",dataHandler)
    Assert.assertEquals(1,result)
  }

  @Test
  def test_getFingerStatus: Unit ={
    val service = getService[WsHaiXinFingerService]
    val status = service.getFingerStatus("0101","3701","44200482222242323422163")
    Assert.assertEquals(4,status)
  }

  @Test
  def test_setFingerAgain: Unit ={
    val dataHandler = new DataHandler(new FileDataSource(new File("D://44200482222242323422163.fpt")))
    val service = getService[WsHaiXinFingerService]
    val result = service.setFingerAgain("1701","0101","3701","44200482222242323422163",dataHandler)
    Assert.assertEquals(1,result)
  }

  @Test
  def test_getFingerMatchList: Unit ={
    val service = getService[WsHaiXinFingerService]
    val result = service.getFingerMatchList("0101","3701","","",1,10)
    println(new String(IOUtils.toByteArray(result.getInputStream)))
    Assert.assertNotNull(result)
  }

  @Test
  def test_getFingerMatchCount: Unit ={
    val service = getService[WsHaiXinFingerService]
    val result = service.getFingerMatchCount("0101","3701","","")
    Assert.assertTrue(result > 0)
  }

  @Test
  def test_getFingerMatchData: Unit ={
    val service = getService[WsHaiXinFingerService]
    val listDataHandler = service.getFingerMatchData("0101","3701","44200372622242323322549")

    var i = 1
    listDataHandler.foreach{
       t => t.foreach(m => FileUtils.writeByteArrayToFile(new File("D:\\" + "1111" + i + ".FPT"),IOUtils.toByteArray(m.getInputStream)))
        i = i+1
    }

    Assert.assertTrue(listDataHandler.size > 0)
  }

  @Test
  def test_getSysTime: Unit ={
    val service = getService[WsHaiXinFingerService]
    println(service.getSysTime)
  }


}
