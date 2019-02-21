package nirvana.hall.webservice.internal.penaltytech.sync

import java.util.{Date, UUID}

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject, TypeReference}
import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.{CaseInfoService, LPCardService}
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.gacodetb.GAFIS_CODE_ENTRYSTRUCT
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.penaltytech.{PenaltyTechConstant, V62ServiceSupport, WebHttpClientUtils}
import nirvana.hall.webservice.internal.penaltytech.jpa.{GafisCronConfig, LogRecord}
import nirvana.hall.webservice.internal.penaltytech.vo.LPCardInfo
import nirvana.hall.webservice.penaltytech.GafisEncryptUtil
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/12/7
  */
class SyncCronLatentFingerService(v62Facade: V62Facade
                                  , hallWebserviceConfig: HallWebserviceConfig
                                  , v62ServiceSupport: V62ServiceSupport
                                  , lPCardService: LPCardService
                                  , caseInfoService: CaseInfoService) extends LoggerSupport {
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    if (hallWebserviceConfig.penaltyTechService.cron_lp != null) {

      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.penaltyTechService.cron_lp, StartAtDelay), "SyncCronLatentFingerService", new Runnable {
        override def run(): Unit = {
          try {
            doWork
          } catch {
            case ex: Exception =>
              error("SyncCronLatentFingerService:" + ex.getMessage)
          }
        }
      })
    }
  }

  def doWork: Unit = {
    var cardId = ""
    try {
      info("SyncCronLatentFingerService-start")
      val startTime = GafisCronConfig.where(GafisCronConfig.typ === PenaltyTechConstant.LATENT_FINGER)
        .and(GafisCronConfig.deleteFlag === PenaltyTechConstant.NOT_DELETE).head.startTime
      //val currentTime = DateConverter.convertDate2String(new Date(), "yyyy-MM-dd HH:mm:ss")
      val currentTime = hallWebserviceConfig.penaltyTechService.lp_endTime
      info("SyncCronLatentFingerService-startTime{},currentTime{}", startTime, currentTime)
      info("SyncCronLatentFingerService-count:{}", v62ServiceSupport.getLatentCardList(DateConverter.convertDate2String(startTime, "yyyy-MM-dd HH:mm:ss"), currentTime).size)
      v62ServiceSupport.getLatentCardList(DateConverter.convertDate2String(startTime, "yyyy-MM-dd HH:mm:ss"), currentTime).foreach {
        t =>
          try {
            cardId = t
            info("创建现场对象-卡号{}", cardId)
            var jsonArray = new JSONArray()
            val json = getLPCardInfo(cardId)
            jsonArray.add(json.getJSONObject)
            info("请求中间库")
            val resultJsonArray = WebHttpClientUtils.call(hallWebserviceConfig.penaltyTechService.data_transport_url + "addlpcard", GafisEncryptUtil.encrypt(jsonArray.toJSONString.replace("\\n", "")))
            info("返回中间库请求信息")
            val results = JSON.parseArray(resultJsonArray).iterator()
            while (results.hasNext) {
              val result = results.next().asInstanceOf[JSONObject]
              var flag = PenaltyTechConstant.SUCCESS
              var errorMsg = ""
              result.getString("flag") match {
                case "0" => flag = PenaltyTechConstant.FAIL; errorMsg = result.getString("errormsg")
                case "1" =>
              }
              new LogRecord(UUID.randomUUID().toString.replace("-", "")
                , result.getString("cardid")
                , PenaltyTechConstant.LATENT_FINGER.toString
                , new Date()
                , flag
                , errorMsg).save()
            }
          } catch {
            case ex: Exception =>
              new LogRecord(UUID.randomUUID().toString.replace("-", "")
                , cardId
                , PenaltyTechConstant.LATENT_FINGER.toString
                , new Date()
                , PenaltyTechConstant.FAIL
                , ExceptionUtil.getStackTraceInfo(ex)).save()
          }
      }
      GafisCronConfig.update.set(startTime = DateConverter.convertString2Date(currentTime, "yyyy-MM-dd HH:mm:ss"), inputTime = new Date()).where(GafisCronConfig.typ === PenaltyTechConstant.LATENT_FINGER).execute
    } catch {
      case ex: Exception =>
        new LogRecord(UUID.randomUUID().toString.replace("-", "")
          , cardId
          , PenaltyTechConstant.LATENT_FINGER.toString
          , new Date()
          , PenaltyTechConstant.FAIL
          , ExceptionUtil.getStackTraceInfo(ex)).save()
    }
  }

  private def getLPCardInfo(cardId: String): LPCardInfo = {

    /**
      * 通过案件类别代码获得案件类别名称
      *
      * @param caseTypeCode 案件类别代码
      * @return
      */
    def getCaseTypeNameByCaseTypeCode(caseTypeCode: String, tableName: String): String = {
      val codeEntryStruct = new GAFIS_CODE_ENTRYSTRUCT
      codeEntryStruct.szCode = caseTypeCode
      v62Facade.NET_GAFIS_CODETB_INFO(21.toShort
        , 202.toShort
        , tableName.getBytes
        , codeEntryStruct
        , gnopcode.OP_CODETABLE_GET)
      new String(codeEntryStruct.szName, "GBK").trim
    }
    val lpCard = lPCardService.getLPCard(cardId)
    val caseInfo = caseInfoService.getCaseInfo(v62ServiceSupport.getCaseIdByFingerId(cardId))
    val lpCardInfo = new LPCardInfo
    lpCardInfo.fingerId = lpCard.getStrCardID
    lpCardInfo.physicalCode = lpCard.getStrPhysicalId
    lpCardInfo.caseCode = caseInfo.getStrJingZongCaseId
    lpCardInfo.surveyCode = caseInfo.getStrSurveyId
    lpCardInfo.inCaseCode = caseInfo.getStrCaseID + "A"
    lpCardInfo.warningInstanceCode = ""
    lpCardInfo.fingerNo = 1 //TODO
    lpCardInfo.fingerPalmType = "1"
    lpCardInfo.occurDate = caseInfo.getText.getStrCaseOccurDate
    lpCardInfo.occurAddressCode = caseInfo.getText.getStrCaseOccurPlaceCode
    lpCardInfo.occurAddress = caseInfo.getText.getStrCaseOccurPlace
    if (caseInfo.getText.hasStrCaseType1 && caseInfo.getText.getStrCaseType1.nonEmpty && caseInfo.getText.getStrCaseType1.trim != "") {
      lpCardInfo.caseType = getCaseTypeNameByCaseTypeCode(caseInfo.getText.getStrCaseType1, "Code_CaseClassTable")
    } else {
      lpCardInfo.caseType = ""
    }
    lpCardInfo.briefDetails = caseInfo.getText.getStrComment
    lpCardInfo.extractUnit = caseInfo.getText.getStrExtractUnitCode
    lpCardInfo.extractUser = caseInfo.getText.getStrExtractor
    lpCardInfo.extractTime = caseInfo.getText.getStrExtractDate
    lpCardInfo.leftoverLocation = lpCard.getText.getStrRemainPlace
    lpCardInfo.inputTime = lpCard.getAdmData.getCreateDatetime
    lpCardInfo.inputPerson = lpCard.getAdmData.getCreator
    lpCardInfo.inputUnit = lpCard.getAdmData.getCreateUnitCode
    lpCardInfo.modifyTime = lpCard.getAdmData.getUpdateDatetime
    lpCardInfo.modifyPerson = lpCard.getAdmData.getUpdator
    lpCardInfo.modifyUnit = lpCard.getAdmData.getUpdateUnitCode
    lpCardInfo.deleteFlag = "1"
    lpCardInfo
  }


}
