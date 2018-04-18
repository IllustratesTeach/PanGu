package nirvana.hall.webservice.internal.xingzhuan

import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.fpt.ZipUtils
import nirvana.hall.c.services.gfpt5lib.FPT5File
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.webservice.config.XingZhuanConfig
import nirvana.hall.webservice.services.xingzhuan._
import org.apache.axiom.attachments.ByteArrayDataSource
import stark.webservice.services.StarkWebServiceClient

import scala.io.Source

/**
  *刑专接口（指掌纹信息应用服务）调用类
  */
class FingerPalmAppServerClient(xingZhuanConfig: XingZhuanConfig) extends LoggerSupport{
  private val username = xingZhuanConfig.user
  private val password = xingZhuanConfig.password
  private var fingerPalmAppServer = StarkWebServiceClient.createClient(classOf[FingerPalmAppServer], xingZhuanConfig.url, xingZhuanConfig.targetNamespace)

  def sendFingerPrint(fPT5File: FPT5File): Boolean ={
    if(fPT5File.fingerprintPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendFingerPrint, fPT5File)
    }
    false
  }

  def sendLatent(fPT5File: FPT5File): Boolean ={
    if(fPT5File.latentPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendLatent, fPT5File)
    }

    false
  }

  def sendLatentTask(fPT5File: FPT5File): Boolean ={
    if(fPT5File.latentTaskPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendLatentTask, fPT5File)
    }

    false
  }
  def sendPrintTask(fPT5File: FPT5File): Boolean ={
    if(fPT5File.printTaskPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendPrintTask, fPT5File)
    }
    false
  }

  def sendLTResult(fPT5File: FPT5File): Boolean ={
    if(fPT5File.ltResultPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendLTResult, fPT5File)
    }
    false
  }

  def sendTLResult(fPT5File: FPT5File): Boolean ={
    if(fPT5File.tlResultPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendTLResult, fPT5File)
    }
    false
  }

  def sendTTResult(fPT5File: FPT5File): Boolean ={
    if(fPT5File.ttResultPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendTTResult, fPT5File)
    }
    false
  }

  def sendLLResult(fPT5File: FPT5File): Boolean ={
    if(fPT5File.llResultPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendLLResult, fPT5File)
    }
    false
  }

  def sendLTHitResult(fPT5File: FPT5File): Boolean ={
    if(fPT5File.ltHitResultPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendLTHitResult, fPT5File)
    }
    false
  }

  def sendTTHitResult(fPT5File: FPT5File): Boolean ={
    if(fPT5File.ttHitResultPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendTTHitResult, fPT5File)
    }
    false
  }

  def sendLLHitResult(fPT5File: FPT5File): Boolean ={
    if(fPT5File.llHitResultPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendLLHitResult, fPT5File)
    }
    false
  }

  def sendNationalCancelLatent(fPT5File: FPT5File): Boolean ={
    if(fPT5File.cancelLatentPackage.length > 0){
      return sendFPT5File(fingerPalmAppServer.sendNationalCancelLatent, fPT5File)
    }
    false
  }

  def sendStatusChanges(statusChanges: StatusChanges): Unit ={
    if(statusChanges != null){
      val xml = XmlLoader.toXml(statusChanges)
      val dataHandler = new DataHandler(new ByteArrayDataSource(ZipUtils.compress(xml).getBytes))
      sendDataHandler(fingerPalmAppServer.sendStatusChanges, dataHandler)
    }
  }

  private def sendFPT5File(f:(String, String, DataHandler) => DataHandler, fPT5File: FPT5File): Boolean={
    val xml = XmlLoader.toXml(fPT5File)
    val dataHandler = new DataHandler(new ByteArrayDataSource(ZipUtils.compress(xml).getBytes))

    sendDataHandler(f, dataHandler)
  }
  private def sendDataHandler(f:(String, String, DataHandler) => DataHandler, dataHandler: DataHandler): Boolean={
    val result = f(username, password, dataHandler)
    if(result.getInputStream.available() > 0){
      val responseStr =  ZipUtils.unCompress(Source.fromInputStream(result.getInputStream).mkString)
      val response = Option(XmlLoader.parseXML[FingerPalmAppServerResponse](responseStr))
      if(response.nonEmpty){
        //TODO
        response.get.status match {
          case FingerPalmAppServerConstants.RESPONSE_SUCCESS =>
            return true
          case other =>
        }
      }
    }

    false
  }

  def getFingerPrint(IDType: Int, peronsid: String): FPT5File ={
    val dataHandler = fingerPalmAppServer.getFingerPrint(username, password, IDType, new DataHandler(new ByteArrayDataSource(ZipUtils.compress(peronsid).getBytes)))
    val str = ZipUtils.unCompress(Source.fromInputStream(dataHandler.getInputStream).mkString)
    XmlLoader.parseXML[FPT5File](str)
  }

  def getLatent(IDType: Int, peronsid: String): FPT5File ={
    val dataHandler = fingerPalmAppServer.getLatent(username, password, IDType, new DataHandler(new ByteArrayDataSource(ZipUtils.compress(peronsid).getBytes)))
    val str = ZipUtils.unCompress(Source.fromInputStream(dataHandler.getInputStream).mkString)
    XmlLoader.parseXML[FPT5File](str)
  }

  def getNewPrint(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getNewPrint)
  }

  def getNewLatent(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getNewLatent)
  }

  def getNationalLatent(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getNationalLatent)
  }

  def getNationalCancelLatent(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getNationalCancelLatent)
  }


  def getLTTask(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getLTTask)
  }

  def getTLTask(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getTLTask)
  }

  def getTTTask(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getTTTask)
  }

  def getLLTask(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getLLTask)
  }

  def getLTResult(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getLTResult)
  }

  def getTLResult(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getTLResult)
  }

  def getTTResult(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getTTResult)
  }

  def getLLResult(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getLLResult)
  }

  def getLTHitResult(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getLTHitResult)
  }

  def getTTHitResult(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getTTHitResult)
  }

  def getLLHitResult(): FPT5File ={
    getFPT5File(fingerPalmAppServer.getLLHitResult)
  }

  private def getFPT5File(f:(String, String)=> DataHandler): FPT5File ={
    val dataHandler = f(username, password)
    val str = ZipUtils.unCompress(Source.fromInputStream(dataHandler.getInputStream).mkString)
    XmlLoader.parseXML[FPT5File](str)
  }

  def getStatusChanges(statusType: String): StatusChanges ={
    val dataHandler = new DataHandler(new ByteArrayDataSource(ZipUtils.compress("<statusType><type>"+statusType+"</type></statusType>").getBytes))
    val result = fingerPalmAppServer.getStatusChanges(username, password, dataHandler)
    val resultStr = ZipUtils.unCompress(Source.fromInputStream(result.getInputStream).mkString)
    XmlLoader.parseXML[StatusChanges](resultStr)
  }

}
