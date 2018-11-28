package nirvana.hall.webservice.internal.survey.gafis62

import java.io.{File, FileInputStream, FileOutputStream, InputStream}
import java.util.{Date, UUID}
import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.internal.fpt.FPT5Utils
import nirvana.hall.c.services.gfpt5lib.{FPT5File, LatentPackage}
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.webservice.config.HandprintServiceConfig
import nirvana.hall.webservice.internal.survey.{PlatformOperatorInfoProvider, PlatformOperatorInfoProviderLoader, SurveyConstant}
import nirvana.hall.webservice.internal.survey.SurveyException.{DataPackageNotAvailableException, ImageException}
import nirvana.hall.webservice.jpa.survey._
import nirvana.hall.webservice.services.survey.DictAdministrativeCode
import nirvana.hall.webservice.services.xcky.FPT50HandprintService
import org.apache.commons.codec.digest.DigestUtils
import org.apache.tools.zip.ZipFile
import stark.webservice.services.StarkWebServiceClient

import scala.concurrent.Future
import scala.io.Source
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by songpeng on 2017/12/24.
  * 现堪接口FPT50HandprintService接口封装类
  */
class FPT50HandprintServiceClient(handprintServiceConfig: HandprintServiceConfig) extends LoggerSupport{
  private val userID = handprintServiceConfig.user
  handprintServiceConfig.localStoreDir
  private val password = DigestUtils.md5Hex(handprintServiceConfig.password)
  //private val fPT50HandprintService = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "FPT50HandprintServiceService", "FPT50HandprintServicePort")
  //private val fPT50HandprintService = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "DFJZRECEIVE.asmx", "DFJZRECEIVESoap")
  var provider:Option[PlatformOperatorInfoProvider] = None
  if(handprintServiceConfig.platformOperatorInfoProviderClass != null){
    provider = Option(PlatformOperatorInfoProviderLoader.createProvider(handprintServiceConfig.platformOperatorInfoProviderClass))
  }

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
    val logGetFingerCount = new LogGetfingercount()
    logGetFingerCount.calltime = new Date()
    var latentCount = 0
    try{
      val fPT50HandprintServiceClient = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "FPT50HandprintServiceService", "FPT50HandprintServicePort")
      latentCount = fPT50HandprintServiceClient.getFingerPrintCount(userID, password, unitCode, zzhwlx, xckybh, kssj, jssj).toInt
    }catch {
      case e:Exception =>
        logGetFingerCount.errormsg = e.getMessage
        throw new Exception("调用-getFingerPrintCount-接口异常：" + e.getMessage())
    }
    val f = Future{
      if(provider.nonEmpty) {
        logGetFingerCount.pkId = UUID.randomUUID().toString.replace("-","")
        logGetFingerCount.userid = userID
        logGetFingerCount.asjfsddXzqhdm = unitCode.toLong
        logGetFingerCount.zzhwlx = zzhwlx
        logGetFingerCount.kssj = DateConverter.convertString2Date(kssj,"yyyy-MM-dd HH:mm:ss")
        logGetFingerCount.jssj = DateConverter.convertString2Date(jssj,"yyyy-MM-dd HH:mm:ss")
        logGetFingerCount.returntime = new Date()
        logGetFingerCount.fingercount = latentCount
        provider.get.addLogGetFingerCount(logGetFingerCount)
      }
    }
    f onComplete {
      case Success(t) => info("插入getLatentList记录表成功currentTime:{}", DateConverter.convertDate2String(new Date, SurveyConstant.DATETIME_FORMAT))
      case Failure(t) => info("插入getLatentList记录表失败currentTime:{}", DateConverter.convertDate2String(new Date, SurveyConstant.DATETIME_FORMAT))
    }
    latentCount
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
    val logGetFingerList = new LogGetfingerlist()
    logGetFingerList.calltime = new Date()
    var fingerPrintListStr = ""
    try{
      val fPT50HandprintServiceClient = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "FPT50HandprintServiceService", "FPT50HandprintServicePort")
      val dataHandler = fPT50HandprintServiceClient.getFingerPrintList(userID, password, asjfsdd_xzqhdm, zzhwlx, xckybh, kssj, jssj, ks, js)
      if(dataHandler.getInputStream.available() > 0){
        val fileName = (kssj+"-"+jssj+"-"+ks+"-"+js).replaceAll(" ","-").replaceAll(":","-")
        val inputStream = getInputStreamByDataHandler(dataHandler, handprintServiceConfig.localStoreDir + File.separator
          + "getlist", fileName,asjfsdd_xzqhdm)
        if(handprintServiceConfig.isDeleteListZip){
          //TODO 删除ZIP文件操作
          print(handprintServiceConfig.localStoreDir + File.separator + fileName + ".zip")
        }
        fingerPrintListStr = Source.fromInputStream(inputStream).mkString
        logGetFingerList.returnxml = fingerPrintListStr
        Option(XmlLoader.parseXML[FingerPrintListResponse](fingerPrintListStr))
      }else{
        warn("getFingerPrintList asjfsdd_xzqhdm:{} xckybh:{} kssj:{} jssj:{} ks:{} js:{} dataHandler is empty", asjfsdd_xzqhdm, xckybh, kssj, jssj, ks, js)
        None
      }
    }catch {
      case e:Exception =>
        logGetFingerList.errormsg = e.getMessage
        throw new Exception("调用-getFingerPrintList-接口异常：" + e.getMessage())
    }
    val f = Future{
      if(provider.nonEmpty){
        logGetFingerList.pkId = UUID.randomUUID().toString.replace("-","")
        logGetFingerList.userid = handprintServiceConfig.user
        logGetFingerList.asjfsddXzqhdm = asjfsdd_xzqhdm.toLong
        logGetFingerList.zzhwlx = zzhwlx
        logGetFingerList.kssj = DateConverter.convertString2Date(kssj,"yyyy-MM-dd HH:mm:ss")
        logGetFingerList.jssj = DateConverter.convertString2Date(jssj,"yyyy-MM-dd HH:mm:ss")
        logGetFingerList.ks = ks.toInt
        logGetFingerList.js = js.toInt
        logGetFingerList.returntime = new Date()
        provider.get.addLogGetFingerList(logGetFingerList)
      }
    }
    f onComplete {
      case Success(t) => info("插入getFingerPrintList记录表成功")
      case Failure(t) => warn("插入getFingerPrintList记录表失败")
    }
    Option(XmlLoader.parseXML[FingerPrintListResponse](fingerPrintListStr))
  }

  /**
    * 获取现场服务器时间服务的接口
    * @return 返回时间格式：yyyy-mm-dd HH:MM:SS
    */
  def getSystemDateTime(): String={
    try{
      val fPT50HandprintServiceClient = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "FPT50HandprintServiceService", "FPT50HandprintServicePort")
      fPT50HandprintServiceClient.getSystemDateTime()
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
  def getLatentPackage(xcwzbh: String,getfingerdetail: LogGetfingerdetail):Option[LatentPackage]={
    var latentPackage:Option[LatentPackage] = None
    info("getFingerPrint：现场物证编号:{}",xcwzbh)
    val unitcode = xcwzbh.substring(1,7)
    info("start request DataPackage ============")
    getfingerdetail.calltime = new Date()
    val fPT50HandprintServiceClient = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "FPT50HandprintServiceService", "FPT50HandprintServicePort")
    val dataHandler = fPT50HandprintServiceClient.getFingerPrint(userID, password, xcwzbh)
    //val dataHandler = fPT50HandprintService.getFingerPrint(userID,password,xcwzbh)
    getfingerdetail.returntime = new Date()
    info("end request DataPackage completed ============")
    if(dataHandler.getInputStream.available() > 0){
      val inputStream = getInputStreamByDataHandler(dataHandler, handprintServiceConfig.localStoreDir + File.separator
        + "getfingerprint", xcwzbh, unitcode)
      val latentPackageStr = Source.fromInputStream(inputStream).mkString
      getfingerdetail.checkmessage = provider.get.validLatentPrintPackage(latentPackageStr).toList.mkString
      val fPT5File = Option(XmlLoader.parseXML[FPT5File](latentPackageStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/latent.xsd")), basePath= "/nirvana/hall/fpt5/"))
      info("saved and pass xsd check")
      if(handprintServiceConfig.isCheckAsjbh){
        info("in asjbh check.....")
        if(Option(fPT5File.get.latentPackage.head.caseMsg.caseId).isEmpty
          && Option(fPT5File.get.latentPackage.head.caseMsg.caseId).get.isEmpty){
          throw new DataPackageNotAvailableException("案事件编号为空")
        }
      }


      if(!Option(fPT5File.get.latentPackage.head.latentFingers).isEmpty){
        if(fPT5File.get.latentPackage.head.latentFingers.head.latentFingerImageMsg.latentFingerImageData.length != 512 *512){
          getfingerdetail.status = 0.toString
          throw new ImageException("图像格式不正确,图像大小不是512*512")
        }
      }
      if(!DictAdministrativeCode.loadAdministrativeCode.get.contains(fPT5File.get.packageHead.sendUnitCode)){
        getfingerdetail.checkstatus = 0.toString
        throw new DataPackageNotAvailableException("FPTX发送单位代码不在行政区划6_28规定的范围内,fsdw_gajgjgdm:" + fPT5File.get.packageHead.sendUnitCode)
      }
      if(!DictAdministrativeCode.loadAdministrativeCode.get.contains(fPT5File.get.latentPackage(0).latentCollectInfoMsg.extractUnitCode)){
        getfingerdetail.checkstatus = 0.toString
        throw new DataPackageNotAvailableException("FPTX提取单位公安机关机构代码不在行政区划6_28规定的范围内,tqdw_gajgjgdm:" + fPT5File.get.latentPackage(0).latentCollectInfoMsg.extractUnitCode)
      }
      getfingerdetail.checkstatus = 1.toString
      getfingerdetail.asjbh = Option(fPT5File.get.latentPackage.head.caseMsg.caseId).getOrElse("")
      getfingerdetail.ysxtAsjbh = fPT5File.get.latentPackage.head.caseMsg.originalSystemCaseId
      getfingerdetail.xckybh = fPT5File.get.latentPackage.head.caseMsg.latentSurveyId
      getfingerdetail.asjfsddXzqhdm = fPT5File.get.latentPackage(0).caseMsg.originalSystemCaseId.drop(1).take(6).toLong
      latentPackage = Option(fPT5File.get.latentPackage.head)
    }else{
      getfingerdetail.checkstatus = 0.toString
      getfingerdetail.checkmessage = ""
      getfingerdetail.asjfsddXzqhdm = xcwzbh.drop(1).take(6).toLong
      throw new DataPackageNotAvailableException("FPTX数据包可读长度不符合要求")
    }
      getfingerdetail.xcwzbh = xcwzbh
    latentPackage
  }

  /**
    * 发送现场指掌纹状态服务的接口
    * @param xcwzbh 现场物证编号，必填
    * @param resultType 反馈信息，必填 FPT50HandprintServiceConstants.RESULT_TYPE_XXX
    * @return FPT50HandprintServiceConstants.SEND_FBUSE_CONDITION_RESPONSE_XXX
    *
    *  -1：现场物证编号不存在
        0：反馈失败
        1：反馈成功
    *
    */
  def sendFBUseCondition(xcwzbh: String, resultType: String): String={
    info("现场物证编号:{},sendFBUseCondition:{}",xcwzbh,resultType)
    val logPutFingerStatus = new LogPutfingerstatus()
    var result = ""
    logPutFingerStatus.calltime = new Date()
    try{
      val fPT50HandPrintServiceClient = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "FPT50HandprintServiceService", "FPT50HandprintServicePort")
      result = fPT50HandPrintServiceClient.sendFBUseCondition(userID, password, xcwzbh, resultType)
    }catch {
      case e:Exception =>
        logPutFingerStatus.errormsg = e.getMessage
        throw new Exception("调用-sendFBUseCondition-接口异常：" + e.getMessage())
    }
    val f = Future{
      if(provider.nonEmpty){
        logPutFingerStatus.pkId = UUID.randomUUID().toString.replace("-","")
        logPutFingerStatus.username = userID
        logPutFingerStatus.xcwzbh = xcwzbh
        logPutFingerStatus.resulttype = resultType
        logPutFingerStatus.returnstatus = result
        logPutFingerStatus.returntime = new Date()
        provider.get.addLogPutFingerStatus(logPutFingerStatus)
      }
    }
    f onComplete {
      case Success(t) => info("插入反馈信息状态表成功：现场物证编号:{},sendFBUseCondition:{}",xcwzbh,resultType)
      case Failure(t) => warn("插入反馈信息状态表失败：现场物证编号:{},sendFBUseCondition:{}",xcwzbh,resultType)
    }
    result
  }

  /**
    * 获取接警编号
    * @param xckybh
    */
  def getReceptionNo(xckybh: String): String ={
    info("getReceptionNo 获取接警编号：现场勘验编号:{}",xckybh)
    val logGetReceptionNo = new LogGetReceptionno()
    var result = ""
    logGetReceptionNo.calltime = new Date()
    try{
      val fPT50HandPrintServiceClient = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "FPT50HandprintServiceService", "FPT50HandprintServicePort")
      result = fPT50HandPrintServiceClient.getReceptionNo(userID, password, xckybh)
    }catch {
      case e:Exception =>
        logGetReceptionNo.errormsg = e.getMessage
        throw new Exception("调用-getReceptionNo-接口异常：" + e.getMessage())
    }
    val f = Future{
      if(provider.nonEmpty) {
        logGetReceptionNo.pkId = UUID.randomUUID().toString.replace("-","")
        logGetReceptionNo.username = userID
        logGetReceptionNo.xckybh = xckybh
        logGetReceptionNo.returnJjbh = result
        logGetReceptionNo.returntime = new Date()
        provider.get.addLogGetReceptionNo(logGetReceptionNo)
      }
    }
    f onComplete {
      case Success(t) => info("插入接警编号信息记录表失败：现场勘验编号:{}",xckybh)
      case Failure(t) => warn("插入接警编号信息记录表成功：现场勘验编号:{}",xckybh)
    }
    result
  }

  /**
    * 获取案件编号
    * @param xckybh
    */
  def getCaseNo(xckybh: String): String ={
    info("getCaseNo 获取案件编号：现场勘验编号:{}",xckybh)
    val logGetCaseNo =  new LogGetCaseno()
    logGetCaseNo.calltime = new Date()
    var result = ""
    try{
      val fPT50HandPrintServiceClient = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "FPT50HandprintServiceService", "FPT50HandprintServicePort")
      result = fPT50HandPrintServiceClient.getCaseNo(userID, password, xckybh)
    }catch {
      case e:Exception =>
        logGetCaseNo.errormsg = e.getMessage
        throw new Exception("调用-getCaseNo-接口异常：" + e.getMessage())
    }
    val f = Future{
      if(provider.nonEmpty){
        logGetCaseNo.pkId = UUID.randomUUID().toString.replace("-","")
        logGetCaseNo.username = userID
        logGetCaseNo.xckybh = xckybh
        logGetCaseNo.returnAsjbh = result
        logGetCaseNo.returntime = new Date()
        provider.get.addLogGetCaseNo(logGetCaseNo)
      }
    }
    f onComplete {
      case Success(t) => info("插入获取案件信息记录表成功：现场勘验编号:{}",xckybh)
      case Failure(t) => warn("插入获取案件信息记录表失败：现场勘验编号:{}",xckybh)
    }
    result
  }

  /**
    * 发送比中信息
    * @param xckybh 现勘编号
    * @param queryType 查询类型
    * @param hitResultDh 比中信息DataHandler
    */
  def sendHitResult(xckybh: String, queryType: Int, hitResultDh:DataHandler,logPutHitResult: LogPuthitresult): Unit = {
    info("sendHitResult 发送比中信息:现勘编号：{},查询类型：{}",xckybh,queryType)
    var result = ""
    val fPT50HandPrintServiceClient = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], handprintServiceConfig.url, handprintServiceConfig.targetNamespace, "FPT50HandprintServiceService", "FPT50HandprintServicePort")
    try{
      queryType match {
        case QueryConstants.QUERY_TYPE_TL | QueryConstants.QUERY_TYPE_LT =>
          result = fPT50HandPrintServiceClient.sendLTHitResult(userID, password, xckybh, hitResultDh)
        case QueryConstants.QUERY_TYPE_LL =>
         result =  fPT50HandPrintServiceClient.sendLLHitResult(userID, password, xckybh, hitResultDh)
        case other =>
      }
      logPutHitResult.returnstatus = result
    }catch {
      case e:Exception =>
        logPutHitResult.errormsg = e.getMessage
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
