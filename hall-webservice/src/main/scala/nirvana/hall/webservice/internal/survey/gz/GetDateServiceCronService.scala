package nirvana.hall.webservice.internal.survey.gz

import java.io.{ByteArrayInputStream, File, FileInputStream}
import java.util.Date

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.fpt.FPT5Utils
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.AncientData.AncientDataException
import nirvana.hall.c.services.gfpt4lib.FPTFile.FPTParseException
import nirvana.hall.c.services.gfpt5lib.FPT5File
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import nirvana.hall.webservice.survey.gz.client.FPT50HandprintServiceService
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.apache.tools.zip.ZipFile

import scala.io.Source

/**
  * Created by ssj on 2017/11/13.
  */
class GetDateServiceCronService(hallWebserviceConfig: HallWebserviceConfig,
                                surveyRecordService: SurveyRecordService,
                                fPT5Service: FPT5Service) extends LoggerSupport{
  val BATCH_SIZE=10

  val url = hallWebserviceConfig.handprintService.url
  val targetNamespace = hallWebserviceConfig.handprintService.targetNamespace
  val userID = hallWebserviceConfig.handprintService.user
  val passwordStr = hallWebserviceConfig.handprintService.password
  val password = DigestUtils.md5Hex(passwordStr)

  val fpt50handprintServiceService = new FPT50HandprintServiceService()
  val fpt50handprintServicePort = fpt50handprintServiceService.getFPT50HandprintServicePort

  /**
    * 定时器，调用海鑫现勘接口
    * 获取现勘记录表中的 文字信息 和 指掌纹数据 和 接警编号
    * 1 判断是否有 案件编号 使用获取案事件编号接口 getCaseNo
    * 2 如果有案事件编号则调用获取文字信息的接口 getOriginalData 传入参数 T，然后将获取的数据入库，现勘记录表
    * 根据现勘号，一个一个获取指掌纹数据。直接入库操作，更新指掌纹记录表和现勘记录表
    * 如果没有则跳出当前定时
    * 3 入库成功后，调用获取接警编号接口，存入oracle case表中并更新记录表
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if (hallWebserviceConfig.handprintService.cron != null) {
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          try{
            info("begin GetDateServiceCronService Cron")
            doWork
            info("end GetDateServiceCronService Cron")
          }catch{
            case ex:Exception =>
              error("GetDateServiceCronService-error:{},currentTime:{}"
                ,ExceptionUtil.getStackTraceInfo(ex),DateConverter.convertDate2String(new Date,Constant.DATETIME_FORMAT)
              )
          }
        }
      })
    }
  }



  def doWork {
    val xkCodeList = surveyRecordService.getSurveyRecordbyState(Constant.INIT, BATCH_SIZE)
    info("现堪号列表长度：------"+xkCodeList.length)
    xkCodeList.foreach {
      kNo =>
        if(checkCaseNo(kNo)){
          //获取现场物证编号列表
          surveyRecordService.getWZBHbyKno(kNo).foreach {
            m =>
              if(getPFTPackage( m.get("xcwzbh").get.toString ,m.get("uuid").get.toString)){  //获取数据包并保存数据，判断数据保存是否正常并继续获取接警编号
                getReceptionNo(kNo)
              }
          }
        }
    }
  }

  /**
    * 判断是否有案件编号
    * @param kNo
    * return 有案件编号返回true ； 没有案件编号返回false
    */
  private def checkCaseNo(kNo:String):Boolean= {
    var bStr = false
    val caseNo = fpt50handprintServicePort.getCaseNo(userID,password,kNo)
    surveyRecordService.saveSurveyLogRecord (Constant.GET_CASE_NO
      , kNo
      , Constant.EMPTY
      , CommonUtil.appendParam ("userID:" + userID, "password:" + password, "xckybh:" + kNo)
      , caseNo
      , Constant.EMPTY)
    info("现堪编号："+kNo+"-----查询的案件编号："+caseNo)
    if (! CommonUtil.isNullOrEmpty (caseNo) ) {
      bStr = true
    }else{
      surveyRecordService.updateSurveyRecordStateByKno(Constant.SURVEY_CODE_KNO_FAIL,kNo)
    }
    bStr
  }

  private def getPFTPackage(xcwzbh:String ,uuid : String): Boolean ={
    var bStr = false
    val fpt = fpt50handprintServicePort.getFingerPrint(userID,password,xcwzbh)
    val path = hallWebserviceConfig.localLatentPath+"/" + xcwzbh
    surveyRecordService.saveSurveyLogRecord(Constant.GET_ORIGINAL_DATA
                                            ,Constant.EMPTY
                                            ,xcwzbh
                                            ,CommonUtil.appendParam("userID:" + userID
                                                                  , "password:" + password
                                                                  , "xcwzbh:" + xcwzbh)
                                            ,path
                                            ,Constant.EMPTY)
    //判断返回数据包是否为空
    if(IOUtils.toByteArray(fpt.getInputStream).length>0){
//      if(hallWebserviceConfig.saveFPTFlag.equals("1")){
        FileUtils.writeByteArrayToFile(new File(path+".zip"), IOUtils.toByteArray(fpt.getInputStream))
//      }
      FPT5Utils.unzipFile(new ZipFile(path+".zip"),path +".xml")
      val fileInputStream = new FileInputStream(path + ".xml")
      val content = Source.fromInputStream(fileInputStream).mkString
      val fpt5File = XmlLoader.parseXML[FPT5File](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/fpt5/latent.xsd")), basePath= "/nirvana/hall/fpt5/")
      try{
        for(i <- 0 until fpt5File.latentPackage.size){
          fPT5Service.addLatentPackage(fpt5File.latentPackage(i))
          surveyRecordService.updateRecordStateByXCWZBH(Constant.SNO_SUCCESS,uuid)
          sendFBUseCondition(xcwzbh,Constant.FPT_PARSE_SUCCESS,"")
        }
        bStr = true
      }catch {
        case ex:AncientDataException =>
          sendFBUseCondition(xcwzbh,Constant.FPT_PARSE_FAIL,ExceptionUtil.getStackTraceInfo(ex))
        case exx:FPTParseException =>
          sendFBUseCondition(xcwzbh,Constant.FPT_PARSE_FAIL,ExceptionUtil.getStackTraceInfo(exx))
      }
    }else{
      surveyRecordService.updateRecordStateByXCWZBH(Constant.SURVEY_CODE_CASEID_REPEAT,uuid)
      info("返回空数据包！")
      bStr = false
    }
    bStr
  }

  /**
    *  获得接警编号
    * @param kNo
    */
  private def getReceptionNo(kNo:String) = {
    val receptionNO = fpt50handprintServicePort.getReceptionNo(userID,password,kNo)
    surveyRecordService.saveSurveyLogRecord(Constant.GET_RECEPTION_NO,kNo
      ,Constant.EMPTY
      ,CommonUtil.appendParam("userID:"+userID,"password:"+password,"xckybh:"+kNo)
      ,receptionNO,Constant.EMPTY)
    if(CommonUtil.isNullOrEmpty(receptionNO)){
      surveyRecordService.updateRecordStateByKno(Constant.SURVEY_CODE_CASEID_ERROR,kNo)
    }else{
      surveyRecordService.updateCasePeception(receptionNO,kNo)
      surveyRecordService.updateRecordStateByKno(Constant.SURVEY_CODE_CASEID_SUCCESS,kNo)
    }
  }

  private def sendFBUseCondition(xcwzbh:String,resultType:String,exceptionInfo:String): Unit ={
    val responses = fpt50handprintServicePort.sendFBUseCondition(userID,password,xcwzbh,resultType)
    info("callFBUseCondition返回信息：---"+responses)
    surveyRecordService.saveSurveyLogRecord(Constant.FBUSECONDITION
      ,Constant.EMPTY
      ,xcwzbh
      ,CommonUtil.appendParam("userID:" + userID
        , "password:" + password
        , "xcwzbh:" + xcwzbh
        ,"resultType:"+ resultType)
      ,responses
      ,exceptionInfo)
  }
}



