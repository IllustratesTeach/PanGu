package nirvana.hall.webservice.internal.xcky

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gfpt5lib.LatentPackage
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.xcky.FPT50HandprintService
import stark.webservice.services.StarkWebServiceClient

import scala.io.Source

/**
  * Created by songpeng on 2017/12/24.
  * 现堪接口FPT50HandprintService接口封装类
  */
class FPT50HandprintServiceClient(hallWebserviceConfig: HallWebserviceConfig) extends LoggerSupport{
  private val userID = hallWebserviceConfig.handprintService.user
  private val password = hallWebserviceConfig.handprintService.password
  private val unitCode = hallWebserviceConfig.handprintService.unitCode
  private val fPT50HandprintService = StarkWebServiceClient.createClient(classOf[FPT50HandprintService], hallWebserviceConfig.handprintService.url, hallWebserviceConfig.handprintService.targetNamespace)

  /**
    * 获取待发送现场指掌纹数量服务
    * @param zzhwlx 现场勘验编号，选填。
    * @param xckybh 查询指掌纹类型，’P’代表掌纹，’F’代表指纹，’A’代表全部。必填。
    * @param kssj 开始时间yyyy-mm-dd HH:MM:SS，选填，缺省为当月第一天开始时间。
    * @param jssj 结束时间yyyy-mm-dd HH:MM:SS，选填，缺省为当前时间
    * @return
    */
  def getLatentCount(zzhwlx: String, xckybh: String, kssj: String, jssj: String): Int={
    fPT50HandprintService.getFingerPrintCount(userID, password, unitCode, zzhwlx, xckybh, kssj, jssj).toInt
  }

  /**
    * 获取待发送现场指掌/纹列表查询服务接口
    * @param asjfsdd_xzqhdm 案事件发生地点_行政区划代码，必填。
    * @param zzhwlx 现场勘验编号，选填。
    * @param xckybh 查询指掌纹类型，’P’代表掌纹，’F’代表指纹，’A’代表全部。必填。
    * @param kssj 开始时间yyyy-mm-dd HH:MM:SS，选填，缺省为当月第一天开始时间。
    * @param jssj 结束时间yyyy-mm-dd HH:MM:SS，选填，缺省为当前时间
    * @param ks 记录开始位置，选填，缺省值为1。
    * @param js 记录结束位置，选填，缺省值为10。
    * @return FingerPrintListResponse
    */
  def getLatentList(asjfsdd_xzqhdm: String, zzhwlx: String, xckybh: String, kssj: String, jssj: String, ks: Int, js: Int):FingerPrintListResponse={
    val fingerPrintListDataHandler = fPT50HandprintService.getFingerPrintList(userID, password, unitCode, asjfsdd_xzqhdm, zzhwlx, xckybh, kssj, ks, js)
    val fingerPrintListStr = Source.fromInputStream(fingerPrintListDataHandler.getInputStream).toString()

    XmlLoader.parseXML[FingerPrintListResponse](fingerPrintListStr)
  }

  /**
    * 获取现场服务器时间服务的接口
    * @return 返回时间格式：yyyy-mm-dd HH:MM:SS
    */
  def getSystemDateTime(): String={
    fPT50HandprintService.getSystemDateTime()
  }

  /**
    * 获取现场指掌纹信息服务的接口
    * @param xcwzbh 现场物证编号，必填
    * @return 现场指掌纹信息FPT5.0数据包。LatentPackage
    */
  def getLatentPackage(xcwzbh: String):LatentPackage={
    val dataHandler = fPT50HandprintService.getFingerPrint(userID, password, xcwzbh)
    val latentPackageStr = Source.fromInputStream(dataHandler.getInputStream).toString()
    XmlLoader.parseXML[LatentPackage](latentPackageStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/latent.xsd")), basePath= "/nirvana/hall/fpt5/")
  }

  /**
    * 发送现场指掌纹状态服务的接口
    * @param xcwzbh 现场物证编号，必填
    * @param resultType 反馈信息，必填 FPT50HandprintServiceConstants.RESULT_TYPE_XXX
    * @return FPT50HandprintServiceConstants.SEND_FBUSE_CONDITION_RESPONSE_XXX
    */
  def sendFBUseCondition(xcwzbh: String, resultType: String): String={
    fPT50HandprintService.sendFBUseCondition(userID, password, xcwzbh, resultType)
  }

}
