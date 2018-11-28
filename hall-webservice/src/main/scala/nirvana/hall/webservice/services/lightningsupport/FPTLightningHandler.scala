package nirvana.hall.webservice.services.lightningsupport

import java.io.File

import com.lmax.disruptor.EventHandler
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib.{FPT5File, FingerprintPackage}
import nirvana.hall.support.services.XmlLoader
import org.apache.commons.io.FileUtils

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/11/28
  */
class FPTLightningHandler(fPT5Service: FPT5Service) extends EventHandler[FPTEvent] with LoggerSupport{
  override def onEvent(t: FPTEvent, l: Long, b: Boolean): Unit = {
    val cardId = t.cardId
    try{
      val fingerPrintPackage = fPT5Service.getFingerprintPackage(cardId)
      val xmlStr = XmlLoader.toXml(buildFPTFileObject(fingerPrintPackage))
      buildFPTFileToDir(xmlStr,cardId)
      info("cardid:{},export success",cardId)
    }catch{
      case ex:Exception =>
        error("cardId:{},error;message:{}",cardId,ExceptionUtil.getStackTraceInfo(ex))
    }
  }


  private def  buildFPTFileObject(fingerprintPackage: FingerprintPackage): FPT5File ={

    val fPT5File = new FPT5File
    val fingerprintPackageBuffer = new scala.collection.mutable.ArrayBuffer[FingerprintPackage]
    fingerprintPackageBuffer += fingerprintPackage
    fPT5File.fingerprintPackage = fingerprintPackageBuffer.toArray
    fPT5File.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    fPT5File.packageHead.sendUnitCode = "520000050000"
    fPT5File.packageHead.sendUnitName = "贵州省公安厅刑事侦查总队"
    fPT5File.packageHead.sendPersonName = ""
    fPT5File.packageHead.sendPersonIdCard = ""
    fPT5File.packageHead.sendPersonTel = ""
    fPT5File.packageHead.sendUnitSystemType = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
    fPT5File
  }

  private def buildFPTFileToDir(xmlStr:String,cardId:String): Unit ={
    XmlLoader.parseXML[FPT5File](xmlStr)
    FileUtils.writeByteArrayToFile(new File("/Users/yuchen/fpt4/guizhou/fptx/tp/"
      + cardId + ".fptx"), xmlStr.getBytes())
  }
}
