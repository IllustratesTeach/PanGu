package nirvana.hall.webservice

import java.io.{ByteArrayInputStream, File, FileOutputStream}
import javax.activation.{DataHandler, FileDataSource}
import javax.imageio.ImageIO
import javax.imageio.spi.IIORegistry

import com.google.protobuf.ByteString
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.support.services.GAFISImageReaderSpi
import nirvana.hall.webservice.services.haixin.WsHaiXinFingerService
import org.apache.axiom.attachments.ByteArrayDataSource
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by yuchen on 2017/7/25.
  */
class HaixinWsFingerServiceImplTest extends BaseTestCase{

  @Test
  def test_setFinger: Unit ={
    val dataHandler = new DataHandler(new FileDataSource(new File("/Users/yuchen/fpt/R2100000000002017090207.fpt")))
    val service = getService[WsHaiXinFingerService]
    val result = service.setFinger("1701","0101","3701","R2100000000002017090207",dataHandler)
    Assert.assertEquals(1,result)
  }

  @Test
  def test_getFingerStatus: Unit ={
    val service = getService[WsHaiXinFingerService]
    val status = service.getFingerStatus("0101","3701","R2100000000002017090206")
    println("返回状态:" + status)
    Assert.assertEquals(2,status)
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
    val result = service.getFingerMatchList("0101","3701","2017/08/04 00:00:00","2017/12/04 00:00:00",1,10)
    println(new String(IOUtils.toByteArray(result.getInputStream)))
    Assert.assertNotNull(result)
  }

  @Test
  def test_getFingerMatchCount: Unit ={
    val service = getService[WsHaiXinFingerService]
    val result = service.getFingerMatchCount("0101","3701","2017/10/23 16:00:00","2017/12/23 18:00:00")
    Assert.assertTrue(result == 3)
  }

  @Test
  def test_getFingerMatchData: Unit ={
    val service = getService[WsHaiXinFingerService]
    val listDataHandler = service.getFingerMatchData("0101","3701","R2100000000002017090207")

    var i = 1
    /*listDataHandler.forEach { t =>
      if(null != t){
        FileUtils.writeByteArrayToFile(new File("D:\\" + "20170830" + i + ".FPT"), IOUtils.toByteArray(t.getInputStream))
        i = i+1
      }
    }*/
    val a = listDataHandler.iterator()
    while(a.hasNext){
      FileUtils.writeByteArrayToFile(new File("D:\\" + "20171025" + i + ".FPT"), IOUtils.toByteArray(a.next.getInputStream))
      i += 1
    }

    Assert.assertTrue(listDataHandler.size > 0)
  }

  @Test
  def test_getSysTime: Unit ={
    val service = getService[WsHaiXinFingerService]
    println(service.getSysTime)
  }

  @Test
  def test_setPalm: Unit ={
    val data = IOUtils.toByteArray(getClass.getResourceAsStream("/R2100000100002016080603_PM_L.buf"))
    for(i<-105 to 120){
      val service = getService[WsHaiXinFingerService]
      val result = service.setPalm("1701","0101","3701",i.toString,2,i.toString,data)

    }
//
//    Assert.assertEquals(1,result)
  }

  @Test
  def test_getPalmStatus: Unit ={
    val data = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    val service = getService[WsHaiXinFingerService]
    val result = service.getPalmStatus("0101","3701","6720037262224232332255",1)
    Assert.assertEquals(1,result)
  }

  @Test
  def test_setPalmAgain: Unit ={
    val data = IOUtils.toByteArray(getClass.getResourceAsStream("/wsq.data"))
    val service = getService[WsHaiXinFingerService]
    val result = service.setPalmAgain("1701","0101","3701","6720037262224232332255",1,"6720037262224232332255",data)
    Assert.assertEquals(1,result)
  }

  /**
    * 获取 原图
    */
  @Test
  def test_tobmp:Unit = {
    //传入 hallImageRemoteService.decodeGafisImage 返回的结构体 originalImage
    val originalImage = new GAFISIMAGESTRUCT

    val iioRegistry = IIORegistry.getDefaultInstance
    iioRegistry.registerServiceProvider(new GAFISImageReaderSpi)
    val img = ImageIO.read(new ByteArrayInputStream(originalImage.toByteArray()))
    val out = new FileOutputStream("D:\\12313\\" + "1213"  + ".bmp")
    ImageIO.write(img, "bmp", out)
    out.close
  }

  @Test
  def test_fpt_save(): Unit ={

    val dataHandler = new DataHandler(new FileDataSource(new File("D://R2100001400022017020001.fpt")))

    FileUtils.writeByteArrayToFile(new File("D:\\170919\\10101\\" + 10101 + ".FPT"), IOUtils.toByteArray(dataHandler.getInputStream))
  }

}
