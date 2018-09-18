package nirvana.hall.webservice.internal.survey

import java.io.File
import java.util

import nirvana.hall.c.services.gfpt5lib.FPT5File
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.webservice.FPT5ValidUtil
import nirvana.hall.webservice.jpa.{LogGetfingerdetail, LogInterfacestatus, _}
import org.apache.commons.io.FileUtils
import org.apache.commons.lang.StringUtils
import org.dom4j.{DocumentException, Element}
import org.dom4j.io.SAXReader
import org.springframework.transaction.annotation.Transactional

import scala.collection.JavaConversions
/**
  * Created by yuchen on 2018/8/3.
  */
class PlatformOperatorInfoProvider{

  /**
    * 添加调用现勘系统获取指掌纹数量记录
    * @param logGetfingercount
    */
  @Transactional
  def addLogGetFingerCount(logGetfingercount:LogGetfingercount):Unit = {
    logGetfingercount.save()
  }

  /**
    * 添加调用现勘系统获取指掌纹列表记录
    * @param logGetfingerlist
    */
  @Transactional
  def addLogGetFingerList(logGetfingerlist:LogGetfingerlist):Unit = {
    logGetfingerlist.save()
  }

  /**
    * 添加调用现勘系统发送现场指掌纹状态记录
    * @param logPutfingerstatus
    */
  @Transactional
  def addLogPutFingerStatus(logPutfingerstatus:LogPutfingerstatus):Unit = {
    logPutfingerstatus.save()
  }

  /**
    * 添加获取现场接警号记录
    * @param logGetReceptionNo
    */
  @Transactional
  def addLogGetReceptionNo(logGetReceptionNo:LogGetReceptionno):Unit = {
    logGetReceptionNo.save()
  }

  /**
    * 添加获取现场案事件编号记录
    * @param logGetCaseNo
    */
  @Transactional
  def addLogGetCaseNo(logGetCaseNo:LogGetCaseno):Unit = {
    logGetCaseNo.save()
  }

  /**
    * 添加比中关系上报记录
    * @param logPuthitResult
    */
  @Transactional
  def addHitResultInfo(logPuthitResult:LogPuthitresult):Unit = {
    //LogGetfingerdetail.update.set(hitrecordReportStatus = survey.SURVEY_STATE_SUCCESS.toString).where(LogGetfingerdetail.xcwzbh === logPuthitResult.xczwXcwzbh).execute
    logPuthitResult.save()
  }

  /**
    * 添加获取现场案事件编号记录
    *
    * @param logGetfingerdetail
    */
  def addLogGetFingerDetail(logGetfingerdetail:LogGetfingerdetail):Unit = {
    logGetfingerdetail.save()
  }

  private def stringListTrim(old: util.List[String]): util.List[String] = {
    val newList: util.List[String] = new util.ArrayList[String]
    if (old != null) {
      import scala.collection.JavaConversions._
      for (str <- old) {
        if (StringUtils.isNotEmpty(str)) {
          newList.add(str)
        }
      }
    }
    return newList
  }

  def validLatentPrintPackage(latentPackageStr:String): Seq[String] = {
    val serverHome = System.getProperty("server.home","support")
    System.setProperty("server.home",serverHome)
    FPT5ValidUtil.loadXsd(this.getClass.getResource("/").getPath+"/fpt5")
    println("dangqianlujing"+this.getClass.getResource("/").getPath)
    println("dangqianlujing"+this.getClass.getResource("").getPath)
    val fPT5File = Option(XmlLoader.parseXML[FPT5File](latentPackageStr))
    val result = new util.ArrayList[String]
    val caseMsg = fPT5File.get.latentPackage.head.caseMsg
    result.add(FPT5ValidUtil.valid("原始系统_案事件编号", "type_ysxt_asjbh", caseMsg.originalSystemCaseId, FPT5ValidUtil.VALIDTYPE_REGEX))
    result.add(FPT5ValidUtil.valid("现场勘验编号", "type_xckybh", caseMsg.latentSurveyId, FPT5ValidUtil.VALIDTYPE_REGEX))
    result.add(FPT5ValidUtil.valid("现场指掌纹卡编号", "", caseMsg.latentCardId, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 23))
    if (caseMsg.caseClassSet != null && caseMsg.caseClassSet.caseTypeCode.nonEmpty && caseMsg.caseClassSet.caseTypeCode.length > 0) {
      for (caseClassCode <- caseMsg.caseClassSet.caseTypeCode) {
        result.add(FPT5ValidUtil.valid("案件类别代码集合", "code_ajlb", caseClassCode, FPT5ValidUtil.VALIDTYPE_CODE))
      }
    }
    if (StringUtils.isNotEmpty(caseMsg.money)) {
      result.add(FPT5ValidUtil.valid("损失价值（人民币元）", "float", caseMsg.money, FPT5ValidUtil.VALIDTYPE_REGEX))
      result.add(FPT5ValidUtil.valid("损失价值（人民币元）", "", caseMsg.money, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 11))
    }
    result.add(FPT5ValidUtil.valid("案事件发生地点_行政区划代码", "code_xzqh", caseMsg.caseOccurAdministrativeDivisionCode, FPT5ValidUtil.VALIDTYPE_CODE))
    result.add(FPT5ValidUtil.valid("案事件发生地点_地址名称", "", caseMsg.caseOccurAddress, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 50))
    result.add(FPT5ValidUtil.valid("简要案情", "", caseMsg.briefCase, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 2000))
    result.add(FPT5ValidUtil.valid("是否命案_判断标识", "code_if", caseMsg.whetherKill, FPT5ValidUtil.VALIDTYPE_CODE))
    val latentCollectInfoMsg = fPT5File.get.latentPackage.head.latentCollectInfoMsg
    result.add(FPT5ValidUtil.valid("指纹比对系统描述", "", latentCollectInfoMsg.fingerprintComparisonSysTypeDescript, FPT5ValidUtil.VALIDTYPE_STRING, 4))
    result.add(FPT5ValidUtil.valid("提取单位_公安机关机构代码", "", latentCollectInfoMsg.extractUnitCode, FPT5ValidUtil.VALIDTYPE_STRING, 12))
    result.add(FPT5ValidUtil.valid("提取单位_公安机关名称", "", latentCollectInfoMsg.extractUnitName, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 50))
    result.add(FPT5ValidUtil.valid("提取人员_姓名", "", latentCollectInfoMsg.extractPerson, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 25))
    result.add(FPT5ValidUtil.valid("提取人员_公民身份号码", "", latentCollectInfoMsg.extractPersonIdCard, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 18))
    result.add(FPT5ValidUtil.valid("提取人员_联系电话", "", latentCollectInfoMsg.extractPersonTel, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 18))
    result.add(FPT5ValidUtil.valid("提取时间", "time", latentCollectInfoMsg.extractDateTime, FPT5ValidUtil.VALIDTYPE_REGEX))

    val latentFingers = fPT5File.get.latentPackage.head.latentFingers
    if(latentFingers !=null && latentFingers.length >0 ){
      latentFingers.foreach { latentFinger =>
        val lpFingerResult = new util.ArrayList[String]
        val latentFingerImageMsg = latentFinger.latentFingerImageMsg
        val latentFingerFeatureMsgs = latentFinger.latentFingerFeatureMsg
        if (latentFingerImageMsg != null ) {
          lpFingerResult.add(FPT5ValidUtil.valid("原始系统_现场指掌纹编号", "", latentFingerImageMsg.originalSystemLatentFingerPalmId, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 30))
          lpFingerResult.add(FPT5ValidUtil.valid("现场物证编号", "type_xcwzbh", latentFingerImageMsg.latentPhysicalId, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_现场指掌纹遗留部位", "", latentFingerImageMsg.latentFingerLeftPosition, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 30))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_尸体指掌纹_判断标识", "", latentFingerImageMsg.latentFingerCorpseJudgeIdentify, FPT5ValidUtil.VALIDTYPE_STRING, 1))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_乳突线颜色代码", "code_rtxysdm", latentFingerImageMsg.latentFingerMastoidProcessLineColorCode, FPT5ValidUtil.VALIDTYPE_CODE))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_连指开始_现场物证编号", "", latentFingerImageMsg.latentFingerConnectFingerBeginPhysicalId, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 30))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_连指结束_现场物证编号", "", latentFingerImageMsg.latentFingerConnectFingerEndPhysicalId, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 30))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指掌纹比对状态代码", "code_zzwbdztdm", latentFingerImageMsg.latentFingerComparisonStatusCode, FPT5ValidUtil.VALIDTYPE_CODE))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指位分析_简要情况", "type_xczw_zhiwfx_jyqk", latentFingerImageMsg.latentFingerAnalysisPostionBrief, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_纹型分析_简要情况", "type_xczw_wxfx_jyqk", latentFingerImageMsg.latentFingerPatternAnalysisBrief, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹方向_特征方向", "int",latentFingerImageMsg.latentFingerFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹方向_特征方向", "", latentFingerImageMsg.latentFingerFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹方向_特征方向范围", "int", latentFingerImageMsg.latentFingerFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹方向_特征方向范围", "", latentFingerImageMsg.latentFingerFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 2))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_图像水平方向长度", "int", latentFingerImageMsg.latentFingerImageHorizontalDirectionLength.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_图像水平方向长度", "", latentFingerImageMsg.latentFingerImageHorizontalDirectionLength.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 6))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_图像垂直方向长度", "int", latentFingerImageMsg.latentFingerImageVerticalDirectionLength.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_图像垂直方向长度", "", latentFingerImageMsg.latentFingerImageVerticalDirectionLength.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 6))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_图像分辨率", "int", latentFingerImageMsg.latentFingerImageRatio.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_图像压缩方法描述", "type_xczw_txysffms", latentFingerImageMsg.latentFingerImageCompressMethodDescript, FPT5ValidUtil.VALIDTYPE_REGEX))
        }
        if (latentFingerFeatureMsgs != null ) {
          latentFingerFeatureMsgs.foreach{
            latentFingerFeatureMsg =>
              lpFingerResult.add(FPT5ValidUtil.valid("原始系统_现场指掌纹编号", "", latentFingerFeatureMsg.originalSystemLatentFingerPalmId, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 30))
              lpFingerResult.add(FPT5ValidUtil.valid("现场物证编号", "type_xcwzbh", latentFingerFeatureMsg.latentPhysicalId, FPT5ValidUtil.VALIDTYPE_REGEX))
              lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_特征组合标识符", "", latentFingerFeatureMsg.latentFeatureGroupIdentifier, FPT5ValidUtil.VALIDTYPE_STRING, 15))
              lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_特征组合描述信息", "", latentFingerFeatureMsg.latentFeatureGroupDscriptInfo, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 64))
              lpFingerResult.add(FPT5ValidUtil.valid("特征提取方式代码", "", latentFingerFeatureMsg.latentFeatureExtractMethodCode, FPT5ValidUtil.VALIDTYPE_STRING, 1))
              lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指位分析_简要情况", "type_xczw_zhiwfx_jyqk", latentFingerFeatureMsg.fingerAnalysisPostionBrief, FPT5ValidUtil.VALIDTYPE_REGEX))
              lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_纹型分析_简要情况", "type_xczw_wxfx_jyqk", latentFingerFeatureMsg.fingerPatternAnalysisBrief, FPT5ValidUtil.VALIDTYPE_REGEX))


              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerFeatureDirection.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹方向_特征方向", "", latentFingerFeatureMsg.fingerFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹方向_特征方向", "int", latentFingerFeatureMsg.fingerFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerFeatureDirectionRange.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹方向_特征方向范围", "", latentFingerFeatureMsg.fingerFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 2))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹方向_特征方向范围", "int", latentFingerFeatureMsg.fingerFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerCenterPointFeatureXCoordinate.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征X坐标", "int", latentFingerFeatureMsg.fingerCenterPointFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征X坐标", "", latentFingerFeatureMsg.fingerCenterPointFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerCenterPointFeatureYCoordinate.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征Y坐标", "int", latentFingerFeatureMsg.fingerCenterPointFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征Y坐标", "", latentFingerFeatureMsg.fingerCenterPointFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerCenterPointFeatureCoordinateRange.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征坐标范围", "int", latentFingerFeatureMsg.fingerCenterPointFeatureCoordinateRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征坐标范围", "", latentFingerFeatureMsg.fingerCenterPointFeatureCoordinateRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerCenterPointFeatureDirection.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征方向", "int", latentFingerFeatureMsg.fingerCenterPointFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征方向", "", latentFingerFeatureMsg.fingerCenterPointFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerCenterPointFeatureDirectionRange.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征方向范围", "int", latentFingerFeatureMsg.fingerCenterPointFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征方向范围", "", latentFingerFeatureMsg.fingerCenterPointFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 2))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerCenterPointFeatureReliabilityLevel.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征可靠度", "int", latentFingerFeatureMsg.fingerCenterPointFeatureReliabilityLevel.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹中心点_特征可靠度", "", latentFingerFeatureMsg.fingerCenterPointFeatureReliabilityLevel.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 1))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerSlaveCenterFeatureXCoordinate.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征X坐标", "int", latentFingerFeatureMsg.fingerSlaveCenterFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征X坐标", "", latentFingerFeatureMsg.fingerSlaveCenterFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerSlaveCenterFeatureYCoordinate.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征Y坐标", "int", latentFingerFeatureMsg.fingerSlaveCenterFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征Y坐标", "", latentFingerFeatureMsg.fingerSlaveCenterFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerSlaveCenterFeatureCoordinateRange.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征坐标范围", "int", latentFingerFeatureMsg.fingerSlaveCenterFeatureCoordinateRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征坐标范围", "", latentFingerFeatureMsg.fingerSlaveCenterFeatureCoordinateRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerSlaveCenterFeatureDirection.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征方向", "int", latentFingerFeatureMsg.fingerSlaveCenterFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征方向", "", latentFingerFeatureMsg.fingerSlaveCenterFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerSlaveCenterFeatureDirectionRange.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征方向范围", "int", latentFingerFeatureMsg.fingerSlaveCenterFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征方向范围", "", latentFingerFeatureMsg.fingerSlaveCenterFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 2))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerSlaveCenterFeatureReliabilityLevel.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征可靠度", "int", latentFingerFeatureMsg.fingerSlaveCenterFeatureReliabilityLevel.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹副中心_特征可靠度", "", latentFingerFeatureMsg.fingerSlaveCenterFeatureReliabilityLevel.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 1))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerLeftTriangleFeatureXCoordinate.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征X坐标", "int", latentFingerFeatureMsg.fingerLeftTriangleFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征X坐标", "", latentFingerFeatureMsg.fingerLeftTriangleFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerLeftTriangleFeatureYCoordinate.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征Y坐标", "int", latentFingerFeatureMsg.fingerLeftTriangleFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征Y坐标", "", latentFingerFeatureMsg.fingerLeftTriangleFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerLeftTriangleFeatureCoordinateRange.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征坐标范围", "int", latentFingerFeatureMsg.fingerLeftTriangleFeatureCoordinateRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征坐标范围", "", latentFingerFeatureMsg.fingerLeftTriangleFeatureCoordinateRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerLeftTriangleFeatureDirection.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征方向", "int", latentFingerFeatureMsg.fingerLeftTriangleFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征方向", "", latentFingerFeatureMsg.fingerLeftTriangleFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerLeftTriangleFeatureDirectionRange.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征方向范围", "int", latentFingerFeatureMsg.fingerLeftTriangleFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征方向范围", "", latentFingerFeatureMsg.fingerLeftTriangleFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 2))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerLeftTriangleFeatureReliabilityLevel.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征可靠度", "int", latentFingerFeatureMsg.fingerLeftTriangleFeatureReliabilityLevel.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹左三角_特征可靠度", "", latentFingerFeatureMsg.fingerLeftTriangleFeatureReliabilityLevel.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 1))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerRightTriangleFeatureXCoordinate.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征X坐标", "int", latentFingerFeatureMsg.fingerRightTriangleFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征X坐标", "", latentFingerFeatureMsg.fingerRightTriangleFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerRightTriangleFeatureYCoordinate.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征Y坐标", "int", latentFingerFeatureMsg.fingerRightTriangleFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征Y坐标", "", latentFingerFeatureMsg.fingerRightTriangleFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerRightTriangleFeatureCoordinateRange.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征坐标范围", "int", latentFingerFeatureMsg.fingerRightTriangleFeatureCoordinateRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征坐标范围", "", latentFingerFeatureMsg.fingerRightTriangleFeatureCoordinateRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerRightTriangleFeatureDirection.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征方向", "int", latentFingerFeatureMsg.fingerRightTriangleFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征方向", "", latentFingerFeatureMsg.fingerRightTriangleFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerRightTriangleFeatureDirectionRange.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征方向范围", "int", latentFingerFeatureMsg.fingerRightTriangleFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征方向范围", "", latentFingerFeatureMsg.fingerRightTriangleFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 2))
              }
              if (StringUtils.isNotEmpty(latentFingerFeatureMsg.fingerRightTriangleFeatureReliabilityLevel.toString)) {
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征可靠度", "int", latentFingerFeatureMsg.fingerRightTriangleFeatureReliabilityLevel.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹右三角_特征可靠度", "", latentFingerFeatureMsg.fingerRightTriangleFeatureReliabilityLevel.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 1))
              }

              if (latentFingerFeatureMsg.latentMinutiaSet.latentMinutia != null && latentFingerFeatureMsg.latentMinutiaSet.latentMinutia.length > 0) {
                latentFingerFeatureMsg.latentMinutiaSet.latentMinutia.foreach{
                  latentFingerFeatureMsgMinutia =>
                    lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹特征点_特征X坐标", "int", latentFingerFeatureMsgMinutia.fingerFeaturePointXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                    lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹特征点_特征X坐标", "", latentFingerFeatureMsgMinutia.fingerFeaturePointXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 6))
                    lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹特征点_特征Y坐标", "int", latentFingerFeatureMsgMinutia.fingerFeaturePointYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                    lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹特征点_特征Y坐标", "", latentFingerFeatureMsgMinutia.fingerFeaturePointYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 6))
                    lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹特征点_特征方向", "int", latentFingerFeatureMsgMinutia.fingerFeaturePointDirection.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                    lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹特征点_特征方向", "", latentFingerFeatureMsgMinutia.fingerFeaturePointDirection.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 3))
                    lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹特征点_特征质量", "int", latentFingerFeatureMsgMinutia.fingerFeaturePointQuality.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                    lpFingerResult.add(FPT5ValidUtil.valid("现场指纹_指纹特征点_特征质量", "", latentFingerFeatureMsgMinutia.fingerFeaturePointQuality.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
                }
              }

          }
        }
        val trimList: util.List[String] = stringListTrim(lpFingerResult)
        if (trimList.size > 0) {
          result.add("==================现场指纹信息【" + latentFingerImageMsg.originalSystemLatentFingerPalmId + "】==================")
          result.addAll(stringListTrim(trimList))
        }
      }
    }
    val latentPalms = fPT5File.get.latentPackage.head.latentPalms
    if(latentPalms !=null && latentPalms.length >0 ){
      latentPalms.foreach{
        latentPalm =>
          val lpPlamResult: util.List[String] = new util.ArrayList[String]
          val latentPalmImageMsg = latentPalm.latentPalmImageMsg
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_现场指掌纹编号", "", latentPalmImageMsg.latentPalmId, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 30))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_现场物证编号", "type_xcwzbh", latentPalmImageMsg.latentPalmPhysicalId, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_现场指掌纹遗留部位", "", latentPalmImageMsg.latentPalmLeftPostion, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 30))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_尸体指掌纹_判断标识", "", latentPalmImageMsg.latentPalmCorpseJudgeIdentify, FPT5ValidUtil.VALIDTYPE_STRING, 1))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_乳突线颜色代码", "code_rtxysdm", latentPalmImageMsg.latentPalmMastoidProcessLineColorCode, FPT5ValidUtil.VALIDTYPE_CODE))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_指掌纹比对状态代码", "code_zzwbdztdm", latentPalmImageMsg.latentPalmComparisonStatusCode, FPT5ValidUtil.VALIDTYPE_CODE))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌位分析_简要情况", "type_xczhw_zhwfx_jyqk", latentPalmImageMsg.latentPalmPostionAnalysisBriefly, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_图像水平方向长度", "int", latentPalmImageMsg.latentPalmImageHorizontalDirectionLength.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_图像水平方向长度", "", latentPalmImageMsg.latentPalmImageHorizontalDirectionLength.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 6))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_图像垂直方向长度", "int", latentPalmImageMsg.latentPalmImageVerticalDirectionLength.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_图像垂直方向长度", "", latentPalmImageMsg.latentPalmImageVerticalDirectionLength.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 6))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_图像分辨率", "int", latentPalmImageMsg.latentPalmImageRatio.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_图像分辨率", "", latentPalmImageMsg.latentPalmImageRatio.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 6))
          lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_图像压缩方法描述", "type_xczw_txysffms", latentPalmImageMsg.latentPalmImageCompressMethodDescript, FPT5ValidUtil.VALIDTYPE_REGEX))
          val latentPalmFeatureMsgs = latentPalm.latentPalmFeatureMsg
          if(latentPalmFeatureMsgs != null && latentPalmFeatureMsgs.length > 0){
            latentPalmFeatureMsgs.foreach{
              latentPalmFeatureMsg =>
                lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_现场指掌纹编号", "", latentPalmFeatureMsg.latentPalmNo, FPT5ValidUtil.VALIDTYPE_STRING, -1, 1, 30))
                lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_现场物证编号", "type_xcwzbh", latentPalmFeatureMsg.latentPalmPhysicalId, FPT5ValidUtil.VALIDTYPE_REGEX))
                lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_特征组合标识符", "", latentPalmFeatureMsg.latentPalmFeatureGroupIdentifier, FPT5ValidUtil.VALIDTYPE_STRING, 15))
                lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_特征组合描述信息", "", latentPalmFeatureMsg.latentPalmFeatureDscriptInfo, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 64))
                lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_指掌纹特征提取方式代码", "", latentPalmFeatureMsg.latentPalmFeatureExtractMethodCode, FPT5ValidUtil.VALIDTYPE_STRING, 1))
                lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_指掌纹比对状态代码", "", latentPalmFeatureMsg.latentPalmComparisonStatusCode, FPT5ValidUtil.VALIDTYPE_STRING, 1))
                lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌位分析_简要情况", "type_xczhw_zhwfx_jyqk", latentPalmFeatureMsg.latentPalmAnalysisBrief, FPT5ValidUtil.VALIDTYPE_REGEX))
                val latentPalmCoreLikePatterns = latentPalmFeatureMsg.latentPalmCoreLikePatternSet.latentPalmCoreLikePattern
                if (latentPalmCoreLikePatterns != null && latentPalmCoreLikePatterns.length > 0) {
                  latentPalmCoreLikePatterns.foreach{
                    latentPalmCoreLikePattern =>
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征X坐标", "int", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征X坐标", "", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征Y坐标", "int", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征Y坐标", "", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征坐标范围", "int", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureCoordinateRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征坐标范围", "", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureCoordinateRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征方向", "int", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征方向", "", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征方向范围", "int", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征方向范围", "", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 2))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征质量", "int", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureQuality.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹折返点_特征质量", "", latentPalmCoreLikePattern.latentPalmRetracingPointFeatureQuality.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
                  }
                }
                val latentPalmDeltas = latentPalmFeatureMsg.latentPalmDeltaSet.latentPalmDelta
                if (latentPalmDeltas != null && latentPalmDeltas.length > 0) {
                  latentPalmDeltas.foreach{
                    latentPalmDelta =>
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征X坐标", "int", latentPalmDelta.latentPalmTrianglePointFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征X坐标", "", latentPalmDelta.latentPalmTrianglePointFeatureXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征Y坐标", "int", latentPalmDelta.latentPalmTrianglePointFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征Y坐标", "", latentPalmDelta.latentPalmTrianglePointFeatureYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征坐标范围", "int", latentPalmDelta.latentPalmTrianglePointFeatureRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征坐标范围", "", latentPalmDelta.latentPalmTrianglePointFeatureRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_掌纹三角位置类型代码", "code_zhwsjwzlx", latentPalmDelta.palmTrianglePostionTypeCode, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_掌纹三角位置类型代码", "", latentPalmDelta.palmTrianglePostionTypeCode, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 2))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征质量", "int", latentPalmDelta.latentPalmTrianglePointFeatureQuality.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征质量", "", latentPalmDelta.latentPalmTrianglePointFeatureQuality.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
                      val latentPalmDeltaDirections = latentPalmDelta.latentPalmDeltaDirection
                      if (latentPalmDeltaDirections != null && latentPalmDeltaDirections.length > 0) {
                        latentPalmDeltaDirections.foreach{
                          latentPalmDeltaDirection =>
                            lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征方向", "int", latentPalmDeltaDirection.latentPalmTrianglePointFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                            lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征方向", "", latentPalmDeltaDirection.latentPalmTrianglePointFeatureDirection.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
                            lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征方向范围", "int", latentPalmDeltaDirection.latentPalmTrianglePointFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                            lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹三角点_特征方向范围", "", latentPalmDeltaDirection.latentPalmTrianglePointFeatureDirectionRange.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 2))
                        }
                      }
                  }
                }
                val latentPalmMinutias = latentPalmFeatureMsg.latentPalmMinutiaSet.latentPalmMinutia
                if (latentPalmMinutias != null && latentPalmMinutias.length > 0) {
                  latentPalmMinutias.foreach{
                    latentPalmMinutia =>
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹特征点_特征X坐标", "int", latentPalmMinutia.fingerFeaturePointXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹特征点_特征X坐标", "", latentPalmMinutia.fingerFeaturePointXCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹特征点_特征Y坐标", "int", latentPalmMinutia.fingerFeaturePointYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹特征点_特征Y坐标", "", latentPalmMinutia.fingerFeaturePointYCoordinate.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 6))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹特征点_特征方向", "int", latentPalmMinutia.fingerFeaturePointDirection.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹特征点_特征方向", "", latentPalmMinutia.fingerFeaturePointDirection.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹特征点_特征质量", "int", latentPalmMinutia.fingerFeaturePointQuality.toString, FPT5ValidUtil.VALIDTYPE_REGEX))
                      lpPlamResult.add(FPT5ValidUtil.valid("现场掌纹_掌纹特征点_特征质量", "", latentPalmMinutia.fingerFeaturePointQuality.toString, FPT5ValidUtil.VALIDTYPE_STRING, -1, 0, 3))
                  }
                }
                val trimList: util.List[String] = stringListTrim(lpPlamResult)
                if (trimList.size > 0) {
                  result.add("==================现场掌纹信息【" + latentPalmImageMsg.latentPalmId + "】==================")
                  result.addAll(stringListTrim(trimList))
                }
                
            }
          }
          
      }
    }
    JavaConversions.asScalaBuffer(stringListTrim(result))

  }
}
