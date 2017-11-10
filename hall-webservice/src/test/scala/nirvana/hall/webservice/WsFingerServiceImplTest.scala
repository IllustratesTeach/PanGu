package nirvana.hall.webservice

import java.io.FileOutputStream

import nirvana.hall.api.services.AssistCheckRecordService
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.webservice.services.bjwcsy.WsFingerService
import org.junit.Test

/**
  * Created by songpeng on 2016/12/5.
  */
class WsFingerServiceImplTest extends BaseTestCase{

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
  @Test
  def test_insertXC_report(): Unit ={
    val service = getService[AssistCheckRecordService]
    service.saveErrorReport("123456","123",0,"测试")
  }


  @Test
  def fPtImport_test(): Unit ={
    val service = getService[FPTService]
    try{
      val taskFpt = FPTFile.parseFromInputStream(getClass.getResourceAsStream("/R2100000000002017090205.fpt"), AncientConstants.GBK_ENCODING)
      taskFpt match {
        case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
        case Right(fpt4) =>
          if(fpt4.logic02Recs.length>0){
            fpt4.logic02Recs.foreach { logic02Rec =>
              service.addLogic02Res(logic02Rec)
            }
          }else if(fpt4.logic03Recs.length>0){
            fpt4.logic03Recs.foreach{ logic03Res =>
              service.addLogic03Res(_)
            }
          }
      }
    }catch{
      case e:Exception => println(e.getMessage)
    }
  }
}
