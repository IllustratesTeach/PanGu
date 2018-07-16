package nirvana.hall.webservice.internal.survey.gafis62

import java.io.{File, FileInputStream, FileOutputStream, InputStream}
import java.util.Date
import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.internal.fpt.FPT5Utils
import nirvana.hall.c.services.gfpt5lib.{FPT5File, LatentPackage}
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.webservice.config.HandprintServiceConfig
import nirvana.hall.webservice.internal.survey.SurveyException.{DataPackageNotAvailableException, ImageException}
import nirvana.hall.webservice.services.xcky.FPT50HandprintService
import org.apache.commons.codec.digest.DigestUtils
import org.apache.tools.zip.ZipFile
import stark.webservice.services.StarkWebServiceClient

import scala.io.Source

/**
  * Created by songpeng on 2017/12/24.
  * 现堪接口FPT50HandprintService接口封装类
  */
class FPT50HandprintServiceClient(handprintServiceConfig: HandprintServiceConfig) extends LoggerSupport{
  private val userID = handprintServiceConfig.user
  private val password = DigestUtils.md5Hex(handprintServiceConfig.password)
  private val fPT50HandprintService = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "FPT50HandprintServiceService", "FPT50HandprintServicePort")

  /**
    * 获取待发送现场指掌纹数量服务
    * @param unitCode 单位代码
    * @param zzhwlx 查询指掌纹类型，’P’代表掌纹，’F’代表指纹，’A’代表全部。必填。
    * @param xckybh 现场勘验编号，选填。
    * @param kssj 开始时间yyyy-mm-dd HH:MM:SS，选填，缺省为当月第一天开始时间。
    * @param jssj 结束时间yyyy-mm-dd HH:MM:SS，选填，缺省为当前时间
    * @return
    */
  def getLatentCount(unitCode: String, zzhwlx: String, xckybh: String, kssj: String, jssj: String): Int={
    info("getFingerPrintCount ,单位代码:{},查询指掌纹类型:{},现场勘验编号:{},开始时间:{},结束时间:{}",
      unitCode, zzhwlx, xckybh, kssj, jssj)
    try{
      fPT50HandprintService.getFingerPrintCount(userID, password, unitCode, zzhwlx, xckybh, kssj, jssj).toInt
    }catch {
      case e:Exception =>
        throw new Exception("调用-getFingerPrintCount-接口异常：" + e.getMessage())
    }

  }

  /**
    * 获取待发送现场指掌/纹列表查询服务接口
    * @param asjfsdd_xzqhdm 案事件发生地点_行政区划代码，必填。
    * @param zzhwlx 查询指掌纹类型，’P’代表掌纹，’F’代表指纹，’A’代表全部。必填。
    * @param xckybh 现场勘验编号，选填。
    * @param kssj 开始时间yyyy-mm-dd HH:MM:SS，选填，缺省为当月第一天开始时间。
    * @param jssj 结束时间yyyy-mm-dd HH:MM:SS，选填，缺省为当前时间
    * @param ks 记录开始位置，选填，缺省值为1。
    * @param js 记录结束位置，选填，缺省值为10。
    * @return FingerPrintListResponse
    */
  def getLatentList(asjfsdd_xzqhdm: String, zzhwlx: String, xckybh: String, kssj: String, jssj: String, ks: Int, js: Int):Option[FingerPrintListResponse]={
    info("getFingerPrintList ：案事件发生地点_行政区划代码:{} 查询指掌纹类型:{} 现场勘验编号:{} 开始时间:{} 结束时间:{} 记录开始位置:{} 记录结束位置:{}", asjfsdd_xzqhdm, zzhwlx,xckybh, kssj, jssj, ks, js)
    try{
      val dataHandler = fPT50HandprintService.getFingerPrintList(userID, password, asjfsdd_xzqhdm, zzhwlx, xckybh, kssj, jssj, ks, js)
      if(dataHandler.getInputStream.available() > 0){
        val fileName = (kssj+"-"+jssj+"-"+ks+"-"+js).replaceAll(" ","-").replaceAll(":","-")
        val inputStream = getInputStreamByDataHandler(dataHandler, handprintServiceConfig.localStoreDir + File.separator
          + "getlist", fileName,asjfsdd_xzqhdm)
        if(handprintServiceConfig.isDeleteListZip){
          //TODO 删除ZIP文件操作
          print(handprintServiceConfig.localStoreDir + File.separator + fileName + ".zip")
        }
        val fingerPrintListStr = Source.fromInputStream(inputStream).mkString
        Option(XmlLoader.parseXML[FingerPrintListResponse](fingerPrintListStr))
      }else{
        warn("getFingerPrintList asjfsdd_xzqhdm:{} xckybh:{} kssj:{} jssj:{} ks:{} js:{} dataHandler is empty", asjfsdd_xzqhdm, xckybh, kssj, jssj, ks, js)
        None
      }
    }catch {
      case e:Exception =>
        throw new Exception("调用-getFingerPrintList-接口异常：" + e.getMessage())
    }


  }

  /**
    * 获取现场服务器时间服务的接口
    * @return 返回时间格式：yyyy-mm-dd HH:MM:SS
    */
  def getSystemDateTime(): String={
    try{
      fPT50HandprintService.getSystemDateTime()
    }catch {
      case e:Exception =>
        throw new Exception("调用-getSystemDateTime-接口异常：" + e.getMessage())
    }

  }

  /**
    * 获取现场指掌纹信息服务的接口
    * @param xcwzbh 现场物证编号，必填
    * @return 现场指掌纹信息FPT5.0数据包。LatentPackage
    */
  def getLatentPackage(xcwzbh: String):Option[LatentPackage]={
    info("getFingerPrint：现场物证编号:{}",xcwzbh)
    val unitcode = xcwzbh.substring(1,7)
    val dataHandler = fPT50HandprintService.getFingerPrint(userID, password, xcwzbh)
    if(dataHandler.getInputStream.available() > 0){
      val inputStream = getInputStreamByDataHandler(dataHandler, handprintServiceConfig.localStoreDir + File.separator
        + "getfingerprint", xcwzbh, unitcode)
      if(handprintServiceConfig.isDeleteFileZip){
        //TODO 删除ZIP文件操作
        print(handprintServiceConfig.localStoreDir + File.pathSeparator + xcwzbh + ".zip")
      }
      val latentPackageStr = Source.fromInputStream(inputStream).mkString
      val fPT5File = Option(XmlLoader.parseXML[FPT5File](latentPackageStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/latent.xsd")), basePath= "/nirvana/hall/fpt5/"))
      if(!Option(fPT5File.get.latentPackage.head.latentFingers).isEmpty){
        if(fPT5File.get.latentPackage.head.latentFingers.head.latentFingerImageMsg.latentFingerImageData.length != 512 *512){
          throw new ImageException
        }
      }
      Option(fPT5File.get.latentPackage(0))
    }else{
      throw new DataPackageNotAvailableException
    }

  }

  /**
    * 发送现场指掌纹状态服务的接口
    * @param xcwzbh 现场物证编号，必填
    * @param resultType 反馈信息，必填 FPT50HandprintServiceConstants.RESULT_TYPE_XXX
    * @return FPT50HandprintServiceConstants.SEND_FBUSE_CONDITION_RESPONSE_XXX
    */
  def sendFBUseCondition(xcwzbh: String, resultType: String): String={
    info("现场物证编号:{},sendFBUseCondition:{}",xcwzbh,resultType)
    try{
      fPT50HandprintService.sendFBUseCondition(userID, password, xcwzbh, resultType)
    }catch {
      case e:Exception =>
        throw new Exception("调用-sendFBUseCondition-接口异常：" + e.getMessage())
    }

  }

  /**
    * 获取接警编号
    * @param xckybh
    */
  def getReceptionNo(xckybh: String): String ={
    info("getReceptionNo 获取接警编号：现场勘验编号:{}",xckybh)
    try{
      fPT50HandprintService.getReceptionNo(userID, password, xckybh)
    }catch {
      case e:Exception =>
        throw new Exception("调用-getReceptionNo-接口异常：" + e.getMessage())
    }

  }

  /**
    * 获取案件编号
    * @param xckybh
    */
  def getCaseNo(xckybh: String): String ={
    info("getCaseNo 获取案件编号：现场勘验编号:{}",xckybh)
    try{
      fPT50HandprintService.getCaseNo(userID, password, xckybh)
    }catch {
      case e:Exception =>
        throw new Exception("调用-getCaseNo-接口异常：" + e.getMessage())
    }

  }

  /**
    * 发送比中信息
    * @param xckybh 现勘编号
    * @param queryType 查询类型
    * @param hitResultDh 比中信息DataHandler
    */
  def sendHitResult(xckybh: String, queryType: Int, hitResultDh:DataHandler): Unit ={
    info("sendHitResult 发送比中信息:现勘编号：{},查询类型：{}",xckybh,queryType)
    try{
      queryType match {
        case QueryConstants.QUERY_TYPE_TL | QueryConstants.QUERY_TYPE_LT =>
          fPT50HandprintService.sendLTHitResult(userID, password, xckybh, hitResultDh)
        case QueryConstants.QUERY_TYPE_LL =>
          fPT50HandprintService.sendLLHitResult(userID, password, xckybh, hitResultDh)
        case other =>
      }
    }catch {
      case e:Exception =>
        throw new Exception("调用-sendHitResult-接口异常：" + e.getMessage())
    }
  }

  /**
    * 解压dataHandler数据，写入到路径unZipFilePath，文件名fileName.zip fileName.xml
    * @param dataHandler
    * @param unZipFilePath 解压目录
    * @param fileName 文件名
    * @return 解压后的fileName.xml文件流
    */
  private def getInputStreamByDataHandler(dataHandler: DataHandler, unZipFilePath: String, fileName: String, unit_code: String): InputStream ={
    val date = DateConverter.convertDate2String(new Date(), "yyyyMMdd")
    val zipFilePath = unZipFilePath + File.separator + date + File.separator + unit_code
    val xmlFilePath = unZipFilePath + File.separator + date + File.separator + unit_code + File.separator  + fileName + ".xml"
    val zipFiles = new File(zipFilePath)
    if(!zipFiles.exists()){
      zipFiles.mkdirs()
    }
    dataHandler.writeTo(new FileOutputStream(new File(zipFilePath + File.separator + fileName + ".zip")))
    FPT5Utils.unzipFile(new ZipFile(zipFilePath + File.separator + fileName + ".zip"), xmlFilePath)

    new FileInputStream(xmlFilePath)
  }
}
