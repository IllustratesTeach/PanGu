package nirvana.hall.webservice.internal.survey.gz

import java.io.File
import java.util.Date
import javax.activation.{DataHandler, FileDataSource}

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.fpt.FPT5Utils
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt5lib.{FPT5File, LlHitResultPackage, LtHitResultPackage}
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import nirvana.hall.webservice.survey.gz.client.FPT50HandprintServiceService
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

import scala.collection.mutable.ArrayBuffer


/**
  * Created by ssj on 2017/11/21.
  */
class SendHitServiceCronService(hallWebserviceConfig: HallWebserviceConfig,
                                surveyRecordService: SurveyRecordService,
                                fPT5Service: FPT5Service) extends LoggerSupport{
  val url = hallWebserviceConfig.handprintService.url
  val targetNamespace = hallWebserviceConfig.handprintService.targetNamespace
  val userID = hallWebserviceConfig.handprintService.user
  val passwordStr = hallWebserviceConfig.handprintService.password
  val password = DigestUtils.md5Hex(passwordStr)

  val fpt50handprintServiceService = new FPT50HandprintServiceService
  val fpt50handprintServicePort = fpt50handprintServiceService.getFPT50HandprintServicePort

  final var BATCH_SIZE = 20

  /**
    * 定时器，调用海鑫现勘接口
    * 定时发送比中上报，导出和上报功能（4.0上报现勘  5.0上报刑专）
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.handprintService.cron != null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          try {
            info("begin SendHitServiceCronService Cron")
            doWork
            info("end SendHitServiceCronService Cron")
          } catch {
            case e: Exception =>
              error("SendHitServiceCronService-error:{},currentTime:{}"
                , ExceptionUtil.getStackTraceInfo(e), DateConverter.convertDate2String(new Date, Constant.DATETIME_FORMAT)
              )
              surveyRecordService.saveSurveyLogRecord("","","","","",ExceptionUtil.getStackTraceInfo(e))
          }
        }
      })
    }
  }
  def doWork {
    var hitLTPackage: LtHitResultPackage = null
    var hitLLPackage: LlHitResultPackage = null
    var path: String = null
    surveyRecordService.getSurveyHit(BATCH_SIZE).foreach {
      hitResult =>
        info("开始比中上报方法")
        if(surveyRecordService.isSurvey(hitResult.get("xcwzbh").get.toString)){
          info("比中队列现场物证编号："+ hitResult.get("xcwzbh").get.toString)
          path = hallWebserviceConfig.localHitResultPath + "/" + hitResult.get("orasid").get.toString + ".fptx"
         //LT比中关系导出上报
          if(hitResult.get("queryType").get.toString.equals("2")){
            hitLTPackage = fPT5Service.getLTHitResultPackage(hitResult.get("orasid").get.toString)
            val hitLTPackageSeq = new ArrayBuffer[LtHitResultPackage]
            hitLTPackageSeq += hitLTPackage
            val hitLTFPTFile =  getFPT5HitLTPackage(hitLTPackageSeq)               //创建LT FPT5 比中文件
            val xmlStr = XmlLoader.toXml(hitLTFPTFile,"utf-8")
            try{
              XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/LTHitResult.xsd"))
                , basePath = "/nirvana/hall/fpt5/")
            }catch {
              case e : Exception =>
                surveyRecordService.saveSurveyLogRecord("SendLTHitService"
                  ,Constant.EMPTY
                  ,hitResult.get("xcwzbh").get.toString
                  ,Constant.EMPTY
                  ,Constant.EMPTY
                  ,e.getMessage)
                FileUtils.writeByteArrayToFile(new File(path+ ".error"), xmlStr.getBytes())   //保存比中fptx文件
            }
            sendLTHitResult(xmlStr ,path ,hitResult.get("uuid").get.toString ,hitResult.get("kno").get.toString)
          }
          //LL比中关系导出上报
          if(hitResult.get("queryType").get.toString.equals("3")){
            hitLLPackage = fPT5Service.getLlHitResultPackage(hitResult.get("orasid").get.toString)
            val hitLLPackageSeq = new ArrayBuffer[LlHitResultPackage]
            hitLLPackageSeq += hitLLPackage
            val hitLLFPTFile =  getFPT5HitLLPackage(hitLLPackageSeq)              //创建LT FPT5 比中文件
            val xmlStr = XmlLoader.toXml(hitLLFPTFile,"utf-8")
            try{
              XmlLoader.parseXML[FPT5File](xmlStr, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/LLHitResult.xsd")) //比中关系xsd校验
                , basePath = "/nirvana/hall/fpt5/")
            }catch {
              case e : Exception =>
                surveyRecordService.saveSurveyLogRecord("SendLLHitService"
                  ,Constant.EMPTY
                  ,hitResult.get("xcwzbh").get.toString
                  ,Constant.EMPTY
                  ,Constant.EMPTY
                  ,e.getMessage)
                FileUtils.writeByteArrayToFile(new File(path+ ".error"), xmlStr.getBytes())   //保存比中fptx文件
            }
            sendLLHitResult(xmlStr ,path ,hitResult.get("uuid").get.toString ,hitResult.get("kno").get.toString)
          }
        }
    }
  }

  /**
    * 保存比中关系fptx及zip，上报比中关系，保存返回结果并更新SURVEY_HITRESULT_RECORD状态
    * @param xmlStr
    * @param path
    * @param uuid
    * @param kno
    */
  private def sendLLHitResult(xmlStr : String ,path : String ,uuid :String ,kno : String) :Unit = {
    FileUtils.writeByteArrayToFile(new File(path), xmlStr.getBytes())   //保存比中fptx文件
    FPT5Utils.zipFile(new File(path), path + ".zip")                     //比中关系压缩.fptx.zip
//    val zipFileInputStream = new FileInputStream(path + ".zip")
    val matchcode = fpt50handprintServicePort.sendLLHitResult(userID       //比中关系上报
      ,password
      ,kno
      ,new DataHandler(new FileDataSource(path + ".zip")))
    info(kno+ "比中关系上报返回结果：" +matchcode)
    surveyRecordService.updateSurveyHitState(matchcode ,uuid)

    surveyRecordService.saveSurveyLogRecord(Constant.FBMatchCondition
      ,kno
      ,Constant.EMPTY
      ,CommonUtil.appendParam("userID:"+userID
        ,"password:"+password
        ,"xckybh:"+ kno
        ,"hitpath:"+ path)
      ,matchcode
      ,Constant.EMPTY)
  }

  /**
    * 保存比中关系fptx及zip，上报比中关系，保存返回结果并更新SURVEY_HITRESULT_RECORD状态
    * @param xmlStr
    * @param path
    * @param uuid
    * @param kno
    */
  private def sendLTHitResult(xmlStr : String ,path : String ,uuid :String ,kno : String) :Unit = {
    FileUtils.writeByteArrayToFile(new File(path), xmlStr.getBytes())   //保存比中fptx文件
    FPT5Utils.zipFile(new File(path), path + ".zip")                     //比中关系压缩.fptx.zip
//    val zipFileInputStream = new FileInputStream(path + ".zip")
    val matchcode = fpt50handprintServicePort.sendLTHitResult(userID       //比中关系上报
      ,password
      ,kno
      ,new DataHandler(new FileDataSource(path + ".zip")))
    info(kno+ "比中关系上报返回结果：" +matchcode)
    surveyRecordService.updateSurveyHitState(matchcode ,uuid)

    surveyRecordService.saveSurveyLogRecord(Constant.FBMatchCondition
      ,kno
      ,Constant.EMPTY
      ,CommonUtil.appendParam("userID:"+userID
        ,"password:"+password
        ,"xckybh:"+ kno
        ,"hitpath:"+ path)
      ,matchcode
      ,Constant.EMPTY)
  }

  private def getFPT5HitLTPackage(hitLTPackageSeq : Seq[LtHitResultPackage]) : FPT5File = {
    val fPT5File = new FPT5File
    fPT5File.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    fPT5File.packageHead.sendUnitCode = "520000050000"
    fPT5File.packageHead.sendUnitName = "贵州省公安厅刑事侦查总队"
    fPT5File.packageHead.sendPersonName = "system"
    fPT5File.packageHead.sendPersonIdCard = "000000000000000000"
    fPT5File.packageHead.sendPersonTel = "000"
    fPT5File.packageHead.sendUnitSystemType = "1900"
    fPT5File.ltHitResultPackage = hitLTPackageSeq.toArray
    fPT5File
  }

  private def getFPT5HitLLPackage(hitLLPackageSeq : Seq[LlHitResultPackage]) : FPT5File = {
    val fPT5File = new FPT5File
    fPT5File.packageHead.originSystem = fPT5File.AFIS_SYSTEM
    fPT5File.packageHead.sendUnitCode = "520000050000"
    fPT5File.packageHead.sendUnitName = "贵州省公安厅刑事侦查总队"
    fPT5File.packageHead.sendPersonName = "system"
    fPT5File.packageHead.sendPersonIdCard = "000000000000000000"
    fPT5File.packageHead.sendPersonTel = "000"
    fPT5File.packageHead.sendUnitSystemType = "1900"
    fPT5File.llHitResultPackage = hitLLPackageSeq.toArray
    fPT5File
  }
}
