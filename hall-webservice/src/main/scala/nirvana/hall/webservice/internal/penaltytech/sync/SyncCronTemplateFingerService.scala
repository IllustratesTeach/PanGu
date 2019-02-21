package nirvana.hall.webservice.internal.penaltytech.sync

import java.util
import java.util.{Date, UUID}

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.{JSON, JSONArray, JSONObject, TypeReference}
import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.gacodetb.GAFIS_CODE_ENTRYSTRUCT
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.penaltytech.{PenaltyTechConstant, V62ServiceSupport, WebHttpClientUtils}
import nirvana.hall.webservice.internal.penaltytech.jpa.{GafisCronConfig, LogRecord}
import nirvana.hall.webservice.internal.penaltytech.vo.TPCardInfo
import nirvana.hall.webservice.penaltytech.GafisEncryptUtil
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/12/7
  */
class SyncCronTemplateFingerService(v62Facade: V62Facade
                                    , hallWebserviceConfig: HallWebserviceConfig
                                    , v62ServiceSupport: V62ServiceSupport
                                    , tPCardService: TPCardService) extends LoggerSupport {

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    if (hallWebserviceConfig.penaltyTechService.cron_tp != null) {

      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.penaltyTechService.cron_tp, StartAtDelay), "SyncCronTemplateFingerService", new Runnable {
        override def run(): Unit = {
          try {
            doWork
          } catch {
            case ex: Exception =>
              error("SyncCronTemplateFingerService:" + ex.getMessage)
          }
        }
      })
    }
  }

  private def doWork: Unit = {
    var cardId = ""
    try {
      info("SyncCronTemplateFingerService-start")
      val startTime = GafisCronConfig.where(GafisCronConfig.typ === PenaltyTechConstant.TEMPLATE_FINGER)
        .and(GafisCronConfig.deleteFlag === PenaltyTechConstant.NOT_DELETE).head.startTime
      //val currentTime = DateConverter.convertDate2String(new Date(), "yyyy-MM-dd HH:mm:ss")
      val endTime = hallWebserviceConfig.penaltyTechService.tp_endTime
      info("SyncCronTemplateFingerService-startTime{},currentTime{}", startTime, endTime)
      info("SyncCronTemplateFingerService-CardList:{}", v62ServiceSupport.getTemplateCardIdList(DateConverter.convertDate2String(startTime, "yyyy-MM-dd HH:mm:ss"), endTime).size)
      v62ServiceSupport.getTemplateCardIdList(DateConverter.convertDate2String(startTime, "yyyy-MM-dd HH:mm:ss"), endTime).foreach {
        t =>
          try{
            cardId = t
            info("创建捺印对象-卡号{}", cardId)
            var cardList = new util.ArrayList[JSONObject]()
            //val json = getTPCardInfo(cardId).getJSONString.replaceAll("\\n","")
            val json = getTPCardInfo(cardId)
            cardList.add(json.getJSONObject)
            info("请求中间库")
            //WebHttpClientUtils.call(hallWebserviceConfig.penaltyTechService.data_transport_url + "addtpcard",GafisEncryptUtil.encrypt(json))
            val resultJsonArray = WebHttpClientUtils.call(hallWebserviceConfig.penaltyTechService.data_transport_url + "addtpcard", GafisEncryptUtil.encrypt(JSON.toJSONString(cardList, SerializerFeature.NotWriteDefaultValue)))
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
                , PenaltyTechConstant.TEMPLATE_FINGER.toString
                , new Date()
                , flag
                , errorMsg).save()
            }
          }catch {
          case ex:Exception =>

        }
      }
      GafisCronConfig.update.set(startTime = DateConverter.convertString2Date(endTime, "yyyy-MM-dd HH:mm:ss"), inputTime = new Date()).where(GafisCronConfig.typ === PenaltyTechConstant.TEMPLATE_FINGER).execute
    } catch {
      case ex: Exception =>
        new LogRecord(UUID.randomUUID().toString.replace("-", "")
          , cardId
          , PenaltyTechConstant.TEMPLATE_FINGER.toString
          , new Date()
          , PenaltyTechConstant.FAIL
          , ExceptionUtil.getStackTraceInfo(ex)).save()
    }
  }

  private def getTPCardInfo(cardId: String): TPCardInfo = {
    /**
      * 通过民族代码获得民族名称
      *
      * @param raceCode 民族代码
      * @return
      */
    def getRaceNameByRaceCode(raceCode: String, tableName: String): String = {
      val codeEntryStruct = new GAFIS_CODE_ENTRYSTRUCT
      codeEntryStruct.szCode = raceCode
      v62Facade.NET_GAFIS_CODETB_INFO(21.toShort
        , 205.toShort
        , tableName.getBytes
        , codeEntryStruct
        , gnopcode.OP_CODETABLE_GET)
      new String(codeEntryStruct.szName, "GBK").trim
    }

    /**
      * 通过国籍代码获得国籍名称
      *
      * @param nationCode 民族代码
      * @return
      */
    def getNationNameByNationCode(nationCode: String, tableName: String): String = {
      val codeEntryStruct = new GAFIS_CODE_ENTRYSTRUCT
      codeEntryStruct.szCode = nationCode
      v62Facade.NET_GAFIS_CODETB_INFO(21.toShort
        , 215.toShort
        , tableName.getBytes
        , codeEntryStruct
        , gnopcode.OP_CODETABLE_GET)
      new String(codeEntryStruct.szName, "GBK").trim
    }


    val tpCard = tPCardService.getTPCard(cardId)
    val tPCardInfo = new TPCardInfo
    tPCardInfo.cardId = tpCard.getStrCardID
    tPCardInfo.personId = tpCard.getStrMisPersonID
    tPCardInfo.personType = tpCard.getText.getStrPersonType
    tPCardInfo.name = tpCard.getText.getStrName
    tPCardInfo.alias = tpCard.getText.getStrAliasName
    tPCardInfo.nameSpell = tpCard.getText.getStrSpellName
    tPCardInfo.sex = tpCard.getText.getNSex
    tPCardInfo.birthday = tpCard.getText.getStrBirthDate
    tPCardInfo.idCard = tpCard.getText.getStrIdentityNum //tpCard.getText.getStrCertifID
    tPCardInfo.domicilePlaces = tpCard.getText.getStrHuKouPlaceCode
    tPCardInfo.domicilePlacesAddress = tpCard.getText.getStrHuKouPlaceTail
    tPCardInfo.addressCode = tpCard.getText.getStrAddrCode
    tPCardInfo.address = tpCard.getText.getStrAddr
    if (tpCard.getText.getStrNation.nonEmpty) {
      tPCardInfo.nationality = getNationNameByNationCode(tpCard.getText.getStrNation, "Code_Nationality")
    }else{
      tPCardInfo.nationality = tpCard.getText.getStrNation
    }

    if (tpCard.getText.getStrRace.nonEmpty) {
      tPCardInfo.nation = getRaceNameByRaceCode(tpCard.getText.getStrRace, "Code_RaceTable")
    } else {
      tPCardInfo.nation = tpCard.getText.getStrRace
    }
    tPCardInfo.gatherUnit = tpCard.getText.getStrPrintUnitCode
    tPCardInfo.gather = tpCard.getText.getStrPrinter
    tPCardInfo.gatherDate = tpCard.getText.getStrPrintDate
    tPCardInfo.fingerPalmType = "1"
    tPCardInfo.inputTime = tpCard.getAdmData.getCreateDatetime
    tPCardInfo.inputPerson = tpCard.getAdmData.getCreator
    tPCardInfo.inputUnit = tpCard.getAdmData.getCreateUnitCode
    tPCardInfo.modifyTime = tpCard.getAdmData.getUpdateDatetime
    tPCardInfo.modifyPerson = tpCard.getAdmData.getUpdator
    tPCardInfo.modifyUnit = tpCard.getAdmData.getUpdateUnitCode
    tPCardInfo.deleteFlag = "1"
    tPCardInfo
  }
}
