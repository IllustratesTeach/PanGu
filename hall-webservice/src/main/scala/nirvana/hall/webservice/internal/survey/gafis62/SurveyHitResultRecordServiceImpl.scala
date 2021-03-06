package nirvana.hall.webservice.internal.survey.gafis62

import java.io.File
import java.util.regex.Pattern
import java.util.{Date, UUID}
import javax.activation.DataHandler

import monad.support.MonadSupportConstants
import monad.support.services.XmlLoader
import nirvana.hall.api.internal.{DataConverter, DateConverter}
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib.{FPT5File, LlHitResultPackage, LtHitResultPackage, fpt5util}
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.gacodetb.GAFIS_CODE_ENTRYSTRUCT
import nirvana.hall.c.services.gloclib.gafisusr.{GAFIS_USERINFOSTRUCT, GAFIS_USERSTRUCT}
import nirvana.hall.c.services.gloclib.galoclp.{GAFIS_LPGROUPENTRY, GAFIS_LPGROUPSTRUCT}
import nirvana.hall.c.services.gloclib.survey.SURVEYHITRESULTRECORD
import nirvana.hall.protocol.api.FPTProto.CaseSource
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.{BreakInfos, gaqryqueConverter}
import nirvana.hall.v62.internal.c.gloclib.gcolnames.g_stCN
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.survey.SurveyConstant
import nirvana.hall.webservice.jpa.survey.{LogEditrecord, LogPuthitresult}
import nirvana.hall.webservice.services.survey.SurveyHitResultRecordService
import org.apache.commons.lang.StringUtils

import scala.collection.mutable.ArrayBuffer
/**
  * Created by songpeng on 2018/1/18.
  */
class SurveyHitResultRecordServiceImpl(v62Facade: V62Facade
                                       , hallWebserviceConfig: HallWebserviceConfig
                                       , v62Config: HallV62Config
                                       , fpt5Service: FPT5Service
                                       , tPCardService: TPCardService
                                       , caseInfoService:CaseInfoService
                                       , lPCardService: LPCardService) extends SurveyHitResultRecordService{
  val LATENT = true
  val NOT_LATENT = false

  val sendUnitCode = hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendUnitCode
  val sendUnitName = hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendUnitName
  val sendPersonName = hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonName
  val sendPersonIdCard = hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonIdCard
  val sendPersonTel = hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonTel

  /**
    * 添加现勘比中信息
    * @param hitResult
    */
  override def addSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD): Unit = {
    v62Facade.NET_GAFIS_SURVEYHITRESULTRECORD_ADD(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, hitResult)
  }

  /**
    * 获取现堪比中信息
    * @param hitResult
    */
  override def updateSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD): Unit = {
    v62Facade.NET_GAFIS_SURVEYHITRESULTRECORD_UPDATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, hitResult)
  }

  /**
    * 获取现勘比中信息
    * @param state 状态
    * @return
    */
  override def getSurveyHitResultRecordList(state: Byte, limit: Int): Seq[SURVEYHITRESULTRECORD] = {
    v62Facade.NET_GAFIS_SURVEYHITRESULTRECORD_GET_BY_STATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, state)
  }

  /**
    * 根据hitResult获取FPT5比对关系
    * @param hitResult SURVEYHITRESULTRECORD
    * @return
    */
  override def getDataHandlerOfLtOrLlHitResultPackage(hitResult: SURVEYHITRESULTRECORD,logPutHitResult:LogPuthitresult): Option[DataHandler] = {
    val fingerId = hitResult.szFingerID
    val hitFingerId = hitResult.szHitFingerID
    val fpt5File = new FPT5File
    hitResult.nQueryType match {
      case QueryConstants.QUERY_TYPE_LT =>
        val lTHitResultPackage = new ArrayBuffer[LtHitResultPackage]
        val ltHitResultPackage = getLTHitResultPackage(fingerId,hitFingerId,hitResult)
        ltHitResultPackage match {
          case Some(ltHitResultPackage) =>
            lTHitResultPackage += ltHitResultPackage
            fpt5File.ltHitResultPackage = lTHitResultPackage.toArray
            logPutHitResult.calltime = new Date()
            logPutHitResult.hitresultType = "LT"
            val ltHitResult = fpt5File.ltHitResultPackage.head
            logPutHitResult.xczwAsjbh = ltHitResult.latentFingerCaseId
            logPutHitResult.xczwYsxtAsjbh = ltHitResult.latentFingerOriginalSystemCaseId
            logPutHitResult.xczwXckybh = ltHitResult.latentFingerLatentSurveyId
            logPutHitResult.xczwYsxtXczzhwbh = ltHitResult.latentFingerOriginalSystemFingerId
            logPutHitResult.xczwXcwzbh = ltHitResult.latentFingerLatentPhysicalId
            logPutHitResult.xczwXczzhwkbh = ltHitResult.latentFingerCardId
            logPutHitResult.nyzwYsxtAsjxgrybh = ltHitResult.fingerPrintOriginalSystemPersonId
            logPutHitResult.nyzwJzrybh = ltHitResult.fingerPrintJingZongPersonId
            logPutHitResult.nyzwAsjxgrybh = ltHitResult.fingerPrintPersonId
            logPutHitResult.nyzwZzhwkbh = ltHitResult.fingerPrintCardId
            logPutHitResult.nyzwZzhwdm = ltHitResult.fingerPrintPostionCode
            logPutHitResult.asjfsddXzqhdm = ltHitResult.hitUnitCode.take(6).toLong
            val fileName = ltHitResultPackage.latentFingerLatentSurveyId + "-" + ltHitResultPackage.fingerPrintOriginalSystemPersonId
            logPutHitResult.fptpath = hallWebserviceConfig.localHitResultPath + File.separator + DateConverter.convertDate2String(new Date(), "yyyyMMdd") + File.separator + fileName +".fptx"
            build(fpt5File,sendUnitCode,sendUnitName,sendPersonName,sendPersonIdCard,sendPersonTel)
            val dataHandler = getZipDataHandlerOfString(XmlLoader.toXml(fpt5File,MonadSupportConstants.UTF8_ENCODING), fingerId +"-"+ hitFingerId, hallWebserviceConfig.localHitResultPath,SurveyConstant.EXPORT_FPTX_FILE)
            Option(dataHandler)
          case _ => None
        }
      case QueryConstants.QUERY_TYPE_LL =>
        val llHitResultPackage = new ArrayBuffer[LlHitResultPackage]
        val hitResultPackage = getLLHitResultPackage(fingerId,hitFingerId,hitResult)
        hitResultPackage match{
          case Some(hitResultPackage) =>
            llHitResultPackage += hitResultPackage
            fpt5File.llHitResultPackage = llHitResultPackage.toArray
            logPutHitResult.calltime = new Date()
            logPutHitResult.hitresultType = "LL"
            build(fpt5File,sendUnitCode,sendUnitName,sendPersonName,sendPersonIdCard,sendPersonTel)
            val dataHandler = getZipDataHandlerOfString(XmlLoader.toXml(fpt5File,MonadSupportConstants.UTF8_ENCODING), fingerId +"-"+ hitFingerId, hallWebserviceConfig.localHitResultPath,SurveyConstant.EXPORT_FPTX_FILE)
            Option(dataHandler)
          case _ => None
        }
      case _ => None
    }
  }

  /**
    * 获得正查比中关系包
    * @param fingerId 源案件的现场指纹卡号
    * @param hitFingerId 目标捺印的指纹卡号
    * @param hitResult 比中结果消息对象
    * @return
    */
  private def getLTHitResultPackage(fingerId:String,hitFingerId:String,hitResult: SURVEYHITRESULTRECORD): Option[LtHitResultPackage] ={
    val caseInfo = caseInfoService.getCaseInfo(getCaseIdOrTPCardIdByFingerId(fingerId,LATENT))
    if(caseInfo.getStrCaseSource == CaseSource.SURVEY_VALUE){
      val lPCard = lPCardService.getLPCard(fingerId)
      val fingerCardId = getCaseIdOrTPCardIdByFingerId(hitFingerId,NOT_LATENT)
      val tpCard = tPCardService.getTPCard(fingerCardId)
      val ltHitPkg = new LtHitResultPackage
      ltHitPkg.taskId = "%23s".format(gaqryqueConverter.convertSixByteArrayToLong(hitResult.nOraSID).toString).replace(" ","0")
      ltHitPkg.comparisonSystemTypeDescript = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
      //捺印指纹_指掌纹卡编号--nyzw_zzhwkbh  maxLength value="23"
      ltHitPkg.fingerPrintCardId = hitFingerId
      //现场指纹_案事件编号--xczw_asjbh |((A|Z)[0-9]{6}([0-9]|[A-Z]){6}[0-9]{4}(0[1-9]|1[0-2])([0-9]|[A-Z]){4}) length=23
      ltHitPkg.latentFingerCaseId = caseInfo.getStrJingZongCaseId //加校验，不处理
      //现场指纹_原始系统_案事件编号--xczw_ysxt_asjbh (A[0-9A-Z]{22}) //加校验，不处理
      ltHitPkg.latentFingerOriginalSystemCaseId = appendCaseNoHeadLetter(caseInfo.getStrCaseID)
      //现场指纹_现场勘验编号--xczw_xckybh (K[0-9]{6}([0-9]|[A-Z]){6}[0-9]{4}(0[1-9]|1[0-2])([0-9]|[A-Z]){4})|(K0000000000000000000000) //加校验，不处理
      ltHitPkg.latentFingerLatentSurveyId = caseInfo.getStrSurveyId
      //现场指纹_原始系统_现场指掌纹编号--xczw_ysxt_xczzhwbh maxLength value="30" //加校验，不处理
      ltHitPkg.latentFingerOriginalSystemFingerId = fingerId
      //现场指纹_现场物证编号--xczw_xcwzbh F[0-9]{6}([0-9]|[A-Z]){6}[0-9]{4}(0[1-9]|1[0-2])([0-9]|[A-Z]){4}11(0[1-4]|[99])[0-9]{3} //加校验，不处理
      ltHitPkg.latentFingerLatentPhysicalId = lPCard.getStrPhysicalId
      //现场指纹_现场指掌纹卡编号--xczw_xczzhwkbh maxLength value="23 //加校验，不处理
      ltHitPkg.latentFingerCardId = "" //系统自用,建议不赋值
      //捺印指纹_原始系统_案事件相关人员编号--nyzw_ysxt_asjxgrybh (R[0-9A-Z]{22}) //加校验，处理
      ltHitPkg.fingerPrintOriginalSystemPersonId = appendPersonNoHeadLetter(tpCard.getStrPersonID)
      //捺印指纹_警综人员编号--nyzw_jzrybh |(R[0-9]{6}([0-9]|[A-Z]){6}[0-9]{4}(0[1-9]|1[0-2])([0-9]|[A-Z]){4}) //加校验，如果不为空就校验，不符合要求就修改
      ltHitPkg.fingerPrintJingZongPersonId = tpCard.getStrJingZongPersonId
      //捺印指纹_案事件相关人员编号--nyzw_asjxgrybh |(P[0-9]{6}([0-9]|[A-Z]){6}[0-9]{4}(0[1-9]|1[0-2])([0-9]|[A-Z]){4}) //加校验，如果不为空就校验，不符合要求就修改
      ltHitPkg.fingerPrintPersonId = ""//appendPersonNoHeadLetter(tpCard.getStrPersonID).replace("R","P")
      //捺印指纹_指掌纹卡编号--nyzw_zzhwkbh maxLength value="23"
      ltHitPkg.fingerPrintCardId = fingerCardId
      //捺印指纹_指掌位代码--nyzw_zzhwdm
      ltHitPkg.fingerPrintPostionCode = hitFgpconvert(hitResult.nHitFgp.toString.toInt)
      ltHitPkg.fingerPrintComparisonMethodCode = fpt5util.QUERY_TYPE_LT
      //存储记录修改前后的比中关系信息
      storeModifyRecord(ltHitPkg)
      val breakInfos = getHitHistory(fingerCardId,NOT_LATENT)
      if(breakInfos.nonEmpty){
        breakInfos.get.breakRecords.filter(_.tprCardID.equals(hitFingerId)).foreach{
          t =>
            val hitUserInfo = getUserInfoStruct(t.breakUserName)
            ltHitPkg.hitUnitCode = "%-12s".format(t.breakUnitCode).replace(" ","0")
            ltHitPkg.hitUnitName = getUnitNameByUnitCode(Option(t.breakUnitCode).getOrElse(throw new Exception("breakUnitCode is null")),"Code_UnitTable")
            ltHitPkg.hitPersonName = t.breakUserName
            ltHitPkg.hitPersonIdCard = if(null != hitUserInfo.szMail && hitUserInfo.szMail.length == 18) hitUserInfo.szMail else hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonIdCard
            ltHitPkg.hitPersonTel = if(getPhoneFromSzPhone(hitUserInfo.szPhone) != "") getPhoneFromSzPhone(hitUserInfo.szPhone) else hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonTel
            ltHitPkg.hitDateTime = t.breakDateTime
            ltHitPkg.checkUnitCode = "%-12s".format(t.reCheckUnitCode).replace(" ","0")
            ltHitPkg.checkUnitName = getUnitNameByUnitCode(Option(t.reCheckUnitCode).getOrElse(throw new Exception("reCheckUnitCode is null")),"Code_UnitTable")
            ltHitPkg.checkPersonName = t.reCheckUserName
            val reCheckUserInfo = getUserInfoStruct(t.reCheckUserName)
            ltHitPkg.checkPersonIdCard = if(null != reCheckUserInfo.szMail && reCheckUserInfo.szMail.length == 18) reCheckUserInfo.szMail else  hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonIdCard
            ltHitPkg.checkPersonTel = if(getPhoneFromSzPhone(reCheckUserInfo.szPhone) != "") getPhoneFromSzPhone(reCheckUserInfo.szPhone) else  hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonTel
            ltHitPkg.checkDateTime = t.reCheckDate
            ltHitPkg.memo = ""
        }
      }
      Some(ltHitPkg)
    }else{
      None
    }
  }

  /**
    * 获得串查比中关系包
    * @param fingerId 源案件的现场指纹卡号
    * @param hitFingerId 目标案件的现场指纹卡号
    * @param hitResult 比中结果消息对象
    * @return
    */
  private def getLLHitResultPackage(fingerId:String,hitFingerId:String,hitResult: SURVEYHITRESULTRECORD): Option[LlHitResultPackage] ={
    val sourceCaseInfo = caseInfoService.getCaseInfo(getCaseIdOrTPCardIdByFingerId(fingerId,LATENT))
    if(sourceCaseInfo.getStrCaseSource == CaseSource.SURVEY_VALUE){
      val sourceLPCard = lPCardService.getLPCard(fingerId)
      val destCaseInfo = caseInfoService.getCaseInfo(getCaseIdOrTPCardIdByFingerId(hitFingerId,LATENT))
      val destLPCard = lPCardService.getLPCard(hitFingerId)
      val llHitPkg = new LlHitResultPackage
      llHitPkg.taskId = "%23s".format(gaqryqueConverter.convertSixByteArrayToLong(hitResult.nOraSID).toString).replace(" ","0")
      llHitPkg.comparisonSystemTypeDescript = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
      llHitPkg.originalSystemCaseId = appendCaseNoHeadLetter(sourceCaseInfo.getStrCaseID)
      llHitPkg.caseId = sourceCaseInfo.getStrJingZongCaseId
      llHitPkg.latentSurveyId = sourceCaseInfo.getStrSurveyId
      llHitPkg.latentPhysicalId = sourceLPCard.getStrPhysicalId
      llHitPkg.cardId = ""
      llHitPkg.resultOriginalSystemCaseId = appendCaseNoHeadLetter(destCaseInfo.getStrCaseID)
      llHitPkg.resultCaseId = destCaseInfo.getStrJingZongCaseId
      llHitPkg.resultLatentSurveyId = destCaseInfo.getStrSurveyId
      llHitPkg.resultLatentPhysicalId = destLPCard.getStrPhysicalId
      llHitPkg.resultCardId = "" //hitFingerId

      val groupName = getGroupName(hitFingerId).trim

      val keyList_data = getGAFIS_LPGROUPSTRUCT(groupName).pstKeyList_Data
      keyList_data.foreach{
        t =>
          if(t.szKey.equals(hitFingerId)){
            val hitPersonInfoAndRecheckInfo = getHitPersonInfoAndRecheckInfoForLLHitResult(t)
            llHitPkg.hitUnitCode = "%-12s".format(hitPersonInfoAndRecheckInfo.hitUnitCode).replace(" ","0")
            llHitPkg.hitUnitName = hitPersonInfoAndRecheckInfo.hitUnitName
            llHitPkg.hitPersonName = hitPersonInfoAndRecheckInfo.hitPersonName
            llHitPkg.hitPersonIdCard = if(hitPersonInfoAndRecheckInfo.hitPersonIdCard.nonEmpty && hitPersonInfoAndRecheckInfo.hitPersonIdCard.length > 17) hitPersonInfoAndRecheckInfo.hitPersonIdCard else hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonTel
            llHitPkg.hitPersonTel =  if(getPhoneFromSzPhone(hitPersonInfoAndRecheckInfo.hitPersonTel) != "") getPhoneFromSzPhone(hitPersonInfoAndRecheckInfo.hitPersonTel) else hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonTel
            llHitPkg.hitDateTime = hitPersonInfoAndRecheckInfo.hitDateTime
            llHitPkg.checkUnitCode = "%-12s".format(hitPersonInfoAndRecheckInfo.checkUnitCode).replace(" ","0")
            llHitPkg.checkUnitName = hitPersonInfoAndRecheckInfo.checkUnitName
            llHitPkg.checkPersonName = hitPersonInfoAndRecheckInfo.checkPersonName
            llHitPkg.checkPersonIdCard = if(hitPersonInfoAndRecheckInfo.checkPersonIdCard.nonEmpty && hitPersonInfoAndRecheckInfo.checkPersonIdCard.length > 17) hitPersonInfoAndRecheckInfo.checkPersonIdCard else hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonTel
            llHitPkg.checkPersonTel = if(getPhoneFromSzPhone(hitPersonInfoAndRecheckInfo.checkPersonTel) != "") getPhoneFromSzPhone(hitPersonInfoAndRecheckInfo.checkPersonTel) else hallWebserviceConfig.handprintService.surveyHitResultHeadPackageInfo.sendPersonTel
            llHitPkg.checkDateTime = hitPersonInfoAndRecheckInfo.checkDateTime
            llHitPkg.memo = ""
          }
      }
      Some(llHitPkg)
    }else{
      None
    }
  }

  /**
    * 通用查询
    * @param fingerId 指纹编号
    * @param isLatent 是否案件现场
    * @return
    */
  private def getCaseIdOrTPCardIdByFingerId(fingerId: String,isLatent:Boolean): String = {
    var db = v62Config.templateTable
    var queryVal = g_stCN.stTcID.pszName //捺印卡号
    if(isLatent){
      db = v62Config.latentTable
      queryVal = g_stCN.stLCsID.pszName //案件编号
    }
    val data = v62Facade.NET_GAFIS_COL_GetByKey(db.dbId.toShort, db.tableId.toShort, fingerId, queryVal)
    new String(data).trim
  }

  /**
    * 获取历史比中信息
    * @param cardId 卡号
    * @return
    */
  private def getHitHistory(cardId: String, isLatent: Boolean): Option[BreakInfos] = {
    var db = v62Config.templateTable
    if(isLatent){
      db = v62Config.latentTable
    }
    val data = v62Facade.NET_GAFIS_COL_GetByKey(db.dbId.toShort, db.tableId.toShort, cardId, g_stCN.stTCardText.pszHitHistory)
    if(data != null && data.length > 0){
      Option(XmlLoader.parseXML[BreakInfos](new String(data,"GBK").trim))
    }else{
      None
    }
  }

  /**
    * 通过单位代码获得单位名称
    * @param unitCode 单位代码
    * @return
    */
  private def getUnitNameByUnitCode(unitCode:String,tableName:String):String ={

     val db = v62Config.codeUnitTable
     val codeEntryStruct = new GAFIS_CODE_ENTRYSTRUCT
     codeEntryStruct.szCode = unitCode
     v62Facade.NET_GAFIS_CODETB_INFO(db.dbId.toShort
       , db.tableId.toShort
       , tableName.getBytes
       ,codeEntryStruct
       ,gnopcode.OP_CODETABLE_GET)
    new String(codeEntryStruct.szName,"GBK").trim

  }


  /**
    * 获取现场关联
    * @param cardId
    */
  private def getGroupName(cardId: String): String ={
    val data = v62Facade.NET_GAFIS_COL_GetByKey(v62Config.latentTable.dbId.toShort, v62Config.latentTable.tableId.toShort, cardId, g_stCN.stLAdm.pszGroupName)
    if(data != null && data.length > 0){
      new String(data,"GBK")
    }else{
      ""
    }
  }


  /**
    * 获取现场关联信息
    * @param groupName 串卡组号
    * @return
    */
  private def getGAFIS_LPGROUPSTRUCT(groupName: String): GAFIS_LPGROUPSTRUCT ={
    val lpGroup = new GAFIS_LPGROUPSTRUCT
    lpGroup.szGroupID = groupName
    v62Facade.NET_GAFIS_LPGROUP_Get(v62Config.latentTable.dbId.toShort, V62Facade.TID_LPGROUP, lpGroup)

    lpGroup
  }

  /**
    * 获得系统操作人的用户基本信息
    * @param userName
    * @return
    */
  private def getUserInfoStruct(userName:String): GAFIS_USERINFOSTRUCT ={
    val userStruct = new GAFIS_USERSTRUCT()
    userStruct.stInfo.szName = userName
    v62Facade.NET_GAFIS_USER_GetUserInfo(V62Facade.DBID_ADMIN_DEFAULT, V62Facade.TID_USER, userStruct)
    userStruct.stInfo
  }

  /**
    * 在生成串查比中关系的时候使用该方法
    * 通过该方法返回认定人和复合人的基本信息
    */
  case class HitPersonInfoAndRecheckInfo(hitUnitCode:String,hitUnitName:String,hitPersonName:String,hitPersonIdCard:String,hitPersonTel:String,hitDateTime:String
                                        ,checkUnitCode:String,checkUnitName:String,checkPersonName:String,checkPersonIdCard:String,checkPersonTel:String,checkDateTime:String)
  private def getHitPersonInfoAndRecheckInfoForLLHitResult(lPGROUPENTRY:GAFIS_LPGROUPENTRY): HitPersonInfoAndRecheckInfo ={
    val hitUserInfo = getUserInfoStruct(lPGROUPENTRY.szUserName)
    val unitName = getUnitNameByUnitCode(lPGROUPENTRY.szUnitCode,"Code_UnitTable")
    new HitPersonInfoAndRecheckInfo(
       lPGROUPENTRY.szUnitCode
      ,unitName
      ,hitUserInfo.szName
      ,hitUserInfo.szMail
      ,hitUserInfo.szPhone
      ,DateConverter.convertAFISDateTime2String(lPGROUPENTRY.tDateTime)
      ,lPGROUPENTRY.szUnitCode
      ,unitName
      ,hitUserInfo.szName
      ,hitUserInfo.szMail
      ,hitUserInfo.szPhone
      ,DateConverter.convertAFISDateTime2String(lPGROUPENTRY.tDateTime))
  }

  private def appendCaseNoHeadLetter(caseId:String):String = {
    if(!caseId.toUpperCase.startsWith("A")){
      val caseid = "A".concat(caseId.toUpperCase)
      caseid
    } else
      caseId.toUpperCase
  }

  private def appendPersonNoHeadLetter(personID:String):String = {
    if(!personID.toUpperCase.startsWith("R")){
      val personid = "R".concat(personID.toUpperCase)
      personid
    } else
      personID.toUpperCase
  }

  private def hitFgpconvert(nHitFgp: Int): String = {
    if(nHitFgp < 10) {
      "0"+ nHitFgp.toString
    } else if(nHitFgp > 20 && nHitFgp < 30)
      (nHitFgp-10).toString
    else
      nHitFgp.toString
  }

  private def getPhoneFromSzPhone(szPhone: String):String = {
    if(StringUtils.isNotEmpty(szPhone)){
      if(szPhone.length > 24){
        szPhone.substring(0,24).trim
      }
      else if(szPhone.length > 48){
        szPhone.substring(25,48).trim
      }else{
        ""
      }
    }else {
      ""
    }
  }


  def build(fPT5File: FPT5File,sendUnitCode:String,sendUnitName:String,sendPersonName:String,sendPersonIdCard:String,sendPersonTel:String): FPT5File ={
    fPT5File.packageHead.originSystem = "AFIS"
    fPT5File.packageHead.sendUnitCode = sendUnitCode
    fPT5File.packageHead.sendUnitName = sendUnitName
    fPT5File.packageHead.sendPersonName = sendPersonName
    fPT5File.packageHead.sendPersonIdCard = sendPersonIdCard
    fPT5File.packageHead.sendPersonTel = sendPersonTel
    fPT5File.packageHead.sendUnitSystemType = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
    fPT5File
  }
  private def storeModifyRecord(ltHitResultPackage: LtHitResultPackage): Unit = {
    val logEditRecord = new LogEditrecord()
    logEditRecord.pkId = UUID.randomUUID().toString.replace("-","")
    logEditRecord.oldXczwAsjbh = ltHitResultPackage.latentFingerCaseId
    logEditRecord.oldXczwYsxtAsjbh = ltHitResultPackage.latentFingerOriginalSystemCaseId
    logEditRecord.oldXczwXckybh = ltHitResultPackage.latentFingerLatentSurveyId
    logEditRecord.oldXczwYsxtXczzhwbh = ltHitResultPackage.latentFingerOriginalSystemFingerId
    logEditRecord.oldXczwXcwzbh = ltHitResultPackage.latentFingerLatentPhysicalId
    logEditRecord.oldXczwXczzhwkbh = ltHitResultPackage.latentFingerCardId
    logEditRecord.oldNyzwYsxtAsjxgrybh = ltHitResultPackage.fingerPrintOriginalSystemPersonId
    //校验原始系统案事件相关人员编号
    if(!Pattern.compile("R[0-9A-Z]{22}").matcher(ltHitResultPackage.fingerPrintOriginalSystemPersonId).matches()){
      ltHitResultPackage.fingerPrintOriginalSystemPersonId = ltHitResultPackage.latentFingerCaseId.replace("A","R")
      logEditRecord.newNyzwJzrybh = ltHitResultPackage.fingerPrintOriginalSystemPersonId
    }
    logEditRecord.oldNyzwJzrybh = ltHitResultPackage.fingerPrintJingZongPersonId
    //校验警综人员编号
    if(!StringUtils.equals("",ltHitResultPackage.fingerPrintJingZongPersonId)){
      val regEx = "(R[0-9]{6}([0-9]|[A-Z]){6}[0-9]{4}(0[1-9]|1[0-2])([0-9]|[A-Z]){4})"
      val pattern = Pattern.compile(regEx)
      val matcher = pattern.matcher(ltHitResultPackage.fingerPrintJingZongPersonId)
      if(!matcher.matches()){
        ltHitResultPackage.fingerPrintJingZongPersonId = ltHitResultPackage.latentFingerCaseId.replace("A","R")
        logEditRecord.newNyzwJzrybh = ltHitResultPackage.fingerPrintJingZongPersonId
      }
    }
    logEditRecord.oldNyzwAsjxgrybh = ltHitResultPackage.fingerPrintPersonId
    //校验案事件相关人员编号
    if(!StringUtils.equals("",ltHitResultPackage.fingerPrintPersonId)){
      val regEx = "(R[0-9]{6}([0-9]|[A-Z]){6}[0-9]{4}(0[1-9]|1[0-2])([0-9]|[A-Z]){4})"
      val pattern = Pattern.compile(regEx)
      val matcher = pattern.matcher(ltHitResultPackage.fingerPrintPersonId)
      if(!matcher.matches()){
        ltHitResultPackage.fingerPrintPersonId = ltHitResultPackage.latentFingerCaseId.replace("A","R")
        logEditRecord.newNyzwJzrybh = ltHitResultPackage.fingerPrintPersonId
      }
    }
    logEditRecord.oldNyzwZzhwkbh = ltHitResultPackage.fingerPrintCardId
    logEditRecord.oldNyzwZzhwdm = ltHitResultPackage.fingerPrintPostionCode
    logEditRecord.modifytime = new Date()
    logEditRecord.newXczwAsjbh = ltHitResultPackage.latentFingerCaseId
    logEditRecord.newXczwYsxtAsjbh = ltHitResultPackage.latentFingerOriginalSystemCaseId
    logEditRecord.newXczwXckybh = ltHitResultPackage.latentFingerLatentSurveyId
    logEditRecord.newXczwYsxtXczzhwbh = ltHitResultPackage.latentFingerOriginalSystemFingerId
    logEditRecord.newXczwXcwzbh = ltHitResultPackage.latentFingerLatentPhysicalId
    logEditRecord.newXczwXczzhwkbh = ltHitResultPackage.latentFingerCardId
    logEditRecord.newNyzwYsxtAsjxgrybh =ltHitResultPackage.fingerPrintOriginalSystemPersonId

    logEditRecord.newNyzwAsjxgrybh = ltHitResultPackage.fingerPrintPersonId
    logEditRecord.newNyzwZzhwkbh = ltHitResultPackage.fingerPrintCardId
    logEditRecord.newNyzwZzhwdm = ltHitResultPackage.fingerPrintPostionCode
    logEditRecord.save()
  }
}
