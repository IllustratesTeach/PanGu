package nirvana.hall.webservice.internal.greathand

import java.text.SimpleDateFormat
import java.util.{Date, UUID}

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.{DateConverter, ExceptionUtil}
import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.gacodetb.GAFIS_CODE_ENTRYSTRUCT
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, ImageType}
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.greathand.jpa.{PersonInfo, SyncCronConfig, SyncCronLog}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor


class SyncCronFingerDataService(v62Facade: V62Facade
                                , hallWebserviceConfig: HallWebserviceConfig
                                , tPCardService: TPCardService) extends LoggerSupport {


  final val TEMPLATE_FINGER = 1
  final val DELETED = "0" //已删除
  final val NOT_DELETE = "1"  //未删除
  final val SUCCESS = "1" //成功
  final val FAIL = "0" //失败
  final val FORCE_PASS = 1  //强制通过
  final val NORMAL_PASS = 0 //正常采集

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {

    if (hallWebserviceConfig.penaltyTechService.cron_tp != null) {

      periodicExecutor.addJob(new CronScheduleWithStartModel(hallWebserviceConfig.penaltyTechService.cron_tp, StartAtDelay), "SyncCronTemplateFingerService", new Runnable {
        override def run(): Unit = {
          try {
            doWork
          } catch {
            case ex: Exception =>
              error("SyncCronFingerDataService:" + ex.getMessage)
          }
        }
      })
    }
  }

  private def doWork: Unit = {
    var cardId = ""
    try {
      info("SyncCronFingerDataService-start")
      var startTime = SyncCronConfig.where(SyncCronConfig.typ === TEMPLATE_FINGER)
        .and(SyncCronConfig.deleteFlag === NOT_DELETE).head.startTime
      val currentTime = DateConverter.convertAFISDateTime2String2(v62Facade.NET_GAFIS_MISC_GetServerTime())
      info("SyncCronFingerDataService-startTime:{},currentTime:{}", startTime, currentTime)
      val cardIdList = getTemplateCardIdList(DateConverter.convertDate2String(startTime, "yyyyMMddHHmmss"), strFormatConvert(currentTime))
      info("SyncCronFingerDataService-CardList:{}", cardIdList.size)
      if (cardIdList.nonEmpty) {
        cardIdList.foreach {
          t =>
            try {
              cardId = t
              info("创建捺印对象-卡号{}", cardId)
              getTPCardInfo(cardId).save()
              new SyncCronLog(UUID.randomUUID().toString.replace("-", "")
                , cardId
                , TEMPLATE_FINGER.toString
                , new Date()
                , SUCCESS
                , "").save()
            } catch {
              case ex: Exception =>
                new SyncCronLog(UUID.randomUUID().toString.replace("-", "")
                  , cardId
                  , TEMPLATE_FINGER.toString
                  , new Date()
                  , FAIL
                  , ex.getMessage).save()
            }
        }
      }
      SyncCronConfig.update.set(startTime = DateConverter.convertString2Date(currentTime, "yyyy-MM-dd HH:mm:ss"), inputTime = new Date()).where(SyncCronConfig.typ === TEMPLATE_FINGER).execute
    } catch {
      case ex: Exception =>
        new SyncCronLog(UUID.randomUUID().toString.replace("-", "")
          , cardId
          , TEMPLATE_FINGER.toString
          , new Date()
          , FAIL
          , ExceptionUtil.getStackTraceInfo(ex)).save()
    }
  }

  private def getTPCardInfo(cardId: String): PersonInfo = {
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

    /**
      * 通过性别代码获取性别名称
      *
      * @param sexCode
      * @param tableName
      * @return
      */
    def getSexNameBySexCode(sexCode: String, tableName: String): String = {
      val codeEntryStruct = new GAFIS_CODE_ENTRYSTRUCT
      codeEntryStruct.szCode = sexCode
      v62Facade.NET_GAFIS_CODETB_INFO(21.toShort
        , 207.toShort
        , tableName.getBytes
        , codeEntryStruct
        , gnopcode.OP_CODETABLE_GET)
      new String(codeEntryStruct.szName, "GBK").trim
    }

    /**
      * 通过案件类别获取案件名称
      *
      * @param sexCode
      * @param tableName
      * @return
      */
    def getCaseClassNameByCaseClassCode(sexCode: String, tableName: String): String = {
      val codeEntryStruct = new GAFIS_CODE_ENTRYSTRUCT
      codeEntryStruct.szCode = sexCode
      v62Facade.NET_GAFIS_CODETB_INFO(21.toShort
        , 202.toShort
        , tableName.getBytes
        , codeEntryStruct
        , gnopcode.OP_CODETABLE_GET)
      new String(codeEntryStruct.szName, "GBK").trim
    }

    val tpCard = tPCardService.getTPCard(cardId)
    val personinfo = new PersonInfo
    personinfo.id = UUID.randomUUID().toString.replace("-", "")
    personinfo.name = tpCard.getText.getStrName
    personinfo.aliasname = tpCard.getText.getStrAliasName
    personinfo.sex = getSexNameBySexCode(tpCard.getText.getNSex.toString, "Code_SexTable")
    personinfo.birthday = tpCard.getText.getStrBirthDate
    personinfo.idcard = tpCard.getText.getStrIdentityNum
    personinfo.birthAddressCode = tpCard.getText.getStrBirthAddrCode
    personinfo.birthAddress = tpCard.getText.getStrBirthAddr
    personinfo.addressCode = tpCard.getText.getStrAddrCode
    personinfo.address = tpCard.getText.getStrAddr
    if (tpCard.getText.getStrNation.nonEmpty) {
      personinfo.nation = getNationNameByNationCode(tpCard.getText.getStrNation, "Code_Nationality")
    } else {
      personinfo.nation = tpCard.getText.getStrNation
    }
    if (tpCard.getText.getStrRace.nonEmpty) {
      personinfo.race = getRaceNameByRaceCode(tpCard.getText.getStrRace, "Code_RaceTable")
    } else {
      personinfo.race = tpCard.getText.getStrRace
    }
    personinfo.criminalRecord = if (tpCard.getText.getBHasCriminalRecord) 1 else 0
    personinfo.personid = tpCard.getStrPersonID
    personinfo.personType = tpCard.getText.getStrPersonType
    if (tpCard.getText.getStrCaseType1.nonEmpty && DictCaseClassificationCode.loadAdministrativeCode.contains(tpCard.getText.getStrCaseType1)) {
      //personinfo.caseType1 = getCaseClassNameByCaseClassCode(tpCard.getText.getStrCaseType1, "Code_CaseClassTable")
      personinfo.caseType1 = DictCaseClassificationCode.loadAdministrativeCode.getOrElse(tpCard.getText.getStrCaseType1,tpCard.getText.getStrCaseType1)
    } else {
      personinfo.caseType1 = tpCard.getText.getStrCaseType1
    }
    if (tpCard.getText.getStrCaseType2.nonEmpty && DictCaseClassificationCode.loadAdministrativeCode.contains(tpCard.getText.getStrCaseType2)) {
      //personinfo.caseType2 = getCaseClassNameByCaseClassCode(tpCard.getText.getStrCaseType2, "Code_CaseClassTable")
      personinfo.caseType2 = DictCaseClassificationCode.loadAdministrativeCode.getOrElse(tpCard.getText.getStrCaseType2,tpCard.getText.getStrCaseType2)
    } else {
      personinfo.caseType2 = tpCard.getText.getStrCaseType2
    }
    if (tpCard.getText.getStrCaseType3.nonEmpty && DictCaseClassificationCode.loadAdministrativeCode.contains(tpCard.getText.getStrCaseType3)) {
      //personinfo.caseType3 = getCaseClassNameByCaseClassCode(tpCard.getText.getStrCaseType3, "Code_CaseClassTable")
      personinfo.caseType3 =DictCaseClassificationCode.loadAdministrativeCode.getOrElse(tpCard.getText.getStrCaseType3,tpCard.getText.getStrCaseType3)
    } else {
      personinfo.caseType3 = tpCard.getText.getStrCaseType3
    }
    personinfo.printUnitCode = tpCard.getText.getStrPrintUnitCode
    personinfo.printUnitName = tpCard.getText.getStrPrintUnitName
    personinfo.printer = tpCard.getText.getStrPrinter
    personinfo.printdate = Option(tpCard.getText.getStrPrintDate).getOrElse(DateConverter.convertDate2String(new Date()))
    personinfo.printerTel = tpCard.getText.getStrPrinterPhone
    personinfo.printerIdcard = tpCard.getText.getStrPrinterIdCardNo
    personinfo.levelId = ""
    personinfo.isConvert = 0
    var strings = new Array[Int](20)
    val it = tpCard.getBlobList.iterator()
    while (it.hasNext) {
      val tpCardBlob = it.next()
      if (tpCardBlob.getType == ImageType.IMAGETYPE_FINGER) {
        if (tpCardBlob.getBPlain) {
          tpCardBlob.getFgp match {
            case FingerFgp.FINGER_R_THUMB =>
              personinfo.rmp = tpCardBlob.getStImageBytes.toByteArray
              strings(10) = getForcePassFlag(personinfo.rmp).toChar
            case FingerFgp.FINGER_R_INDEX =>
              personinfo.rsp = tpCardBlob.getStImageBytes.toByteArray
              strings(11) = getForcePassFlag(personinfo.rsp).toChar
            case FingerFgp.FINGER_R_MIDDLE =>
              personinfo.rzp = tpCardBlob.getStImageBytes.toByteArray
              strings(12) = getForcePassFlag(personinfo.rzp).toChar
            case FingerFgp.FINGER_R_RING =>
              personinfo.rhp = tpCardBlob.getStImageBytes.toByteArray
              strings(13) = getForcePassFlag(personinfo.rhp).toChar
            case FingerFgp.FINGER_R_LITTLE =>
              personinfo.rxp = tpCardBlob.getStImageBytes.toByteArray
              strings(14) = getForcePassFlag(personinfo.rxp).toChar
            case FingerFgp.FINGER_L_THUMB =>
              personinfo.lmp = tpCardBlob.getStImageBytes.toByteArray
              strings(15) = getForcePassFlag(personinfo.lmp).toChar
            case FingerFgp.FINGER_L_INDEX =>
              personinfo.lsp = tpCardBlob.getStImageBytes.toByteArray
              strings(16) = getForcePassFlag(personinfo.lsp).toChar
            case FingerFgp.FINGER_L_MIDDLE =>
              personinfo.lzp = tpCardBlob.getStImageBytes.toByteArray
              strings(17) = getForcePassFlag(personinfo.lzp).toChar
            case FingerFgp.FINGER_L_RING =>
              personinfo.lhp = tpCardBlob.getStImageBytes.toByteArray
              strings(18) = getForcePassFlag(personinfo.lhp).toChar
            case FingerFgp.FINGER_L_LITTLE =>
              personinfo.lxp = tpCardBlob.getStImageBytes.toByteArray
              strings(19) = getForcePassFlag(personinfo.lxp).toChar
          }
        } else {
          tpCardBlob.getFgp match {
            case FingerFgp.FINGER_R_THUMB =>
              personinfo.rmr = tpCardBlob.getStImageBytes.toByteArray
              strings(0) = getForcePassFlag(personinfo.rmr).toChar
            case FingerFgp.FINGER_R_INDEX =>
              personinfo.rsr = tpCardBlob.getStImageBytes.toByteArray
              strings(1) = getForcePassFlag(personinfo.rsr).toChar
            case FingerFgp.FINGER_R_MIDDLE =>
              personinfo.rzr = tpCardBlob.getStImageBytes.toByteArray
              strings(2) = getForcePassFlag(personinfo.rzr).toChar
            case FingerFgp.FINGER_R_RING =>
              personinfo.rhr = tpCardBlob.getStImageBytes.toByteArray
              strings(3) = getForcePassFlag(personinfo.rhr).toChar
            case FingerFgp.FINGER_R_LITTLE =>
              personinfo.rxr = tpCardBlob.getStImageBytes.toByteArray
              strings(4) = getForcePassFlag(personinfo.rxr).toChar
            case FingerFgp.FINGER_L_THUMB =>
              personinfo.lmr = tpCardBlob.getStImageBytes.toByteArray
              strings(5) = getForcePassFlag(personinfo.lmr).toChar
            case FingerFgp.FINGER_L_INDEX =>
              personinfo.lsr = tpCardBlob.getStImageBytes.toByteArray
              strings(6) = getForcePassFlag(personinfo.lsr).toChar
            case FingerFgp.FINGER_L_MIDDLE =>
              personinfo.lzr = tpCardBlob.getStImageBytes.toByteArray
              strings(7) = getForcePassFlag(personinfo.lzr).toChar
            case FingerFgp.FINGER_L_RING =>
              personinfo.lhr = tpCardBlob.getStImageBytes.toByteArray
              strings(8) = getForcePassFlag(personinfo.lhr).toChar
            case FingerFgp.FINGER_L_LITTLE =>
              personinfo.lxr = tpCardBlob.getStImageBytes.toByteArray
              strings(9) = getForcePassFlag(personinfo.lxr).toChar
          }
        }
      }
    }
    personinfo.idCompelPass = strings.mkString("")//strings.mkString(",").replace(",","")
    personinfo
  }


  def getTemplateCardIdList(startTime: String, endTime: String): Seq[String] = {
    println(startTime.trim+":"+endTime.trim)
    val statement = Option("((CreateTime>=Str2DateTime('%s') ) AND (CreateTime<=Str2DateTime('%s')))"
      .format(startTime.trim, endTime.trim))
    val mapper = Map("cardid" -> "szCardID")

    val result = v62Facade.queryV62Table[GTPCARDINFOSTRUCT](
      V62Facade.DBID_TP_DEFAULT,
      V62Facade.TID_TPCARDINFO,
      mapper,
      statement,
      1000000)
    result.map(t => t.szCardID)
  }

  /**
    * 强制通过
    * @param data
    * @return
    */
  def getForcePassFlag(data:Array[Byte]):Int = {
    var result:Int = 0
    val gafisimagestruct = new GAFISIMAGESTRUCT()
    if(null != data){
      val array = gafisimagestruct.fromByteArray(data)
      if(array.stHead.nQualDesc > 0){
        result = 1
      }
      result
    }
    result
  }

  private def strFormatConvert(dateTime: String): String = {
    val formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = formater.parse(dateTime)
    new SimpleDateFormat("yyyyMMddHHmmss").format(date)
  }
}
