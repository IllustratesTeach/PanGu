package nirvana.hall.spark.services

import java.sql
import java.text.{ParsePosition, SimpleDateFormat}
import java.util.Date

import nirvana.hall.c.services.gfpt4lib.FPT3File.{FingerLData, Logic2Rec, Logic3Rec}
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic03Rec, Logic02Rec}
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.{GAFISIMAGEHEADSTRUCT, GAFISIMAGESTRUCT}

/**
  * Created by wangjue on 2016/7/27.
  */
object FptPropertiesConverter {

  def fpt3ToPersonConvert(fpt3 : Logic3Rec, fptPath : String = ""): PersonConvert ={
    val person = new PersonConvert
    person.personId = fpt3.personId
    person.cardId = fpt3.cardId
    person.name = fpt3.personName
    person.aliasName = fpt3.alias
    person.sexCode = fpt3.gender
    val birthday = fpt3.birthday
    if (!birthday.isEmpty)
      person.birthday = convertString2Date(birthday,"yyyy/MM/dd")
    else
      person.birthday = null
    person.idCardNo = fpt3.idCardNo
    person.door = fpt3.door
    person.doorDetail = fpt3.doorDetail
    person.address = fpt3.address
    person.addressDetail = fpt3.addressDetail
    person.personCategory = fpt3.category
    person.caseClasses = fpt3.caseClass1Code
    person.gatherDepartCode = fpt3.gatherUnitCode
    person.gatherDepartName = fpt3.gatherUnitName
    val gatherDate = fpt3.gatherDate
    if (!gatherDate.isEmpty)
      person.gatherDate = convertString2Date(gatherDate,"yyyy/MM/dd")
    else
      person.gatherDate = null
    person.remark = fpt3.remark

    person.fptPath = fptPath
    person
  }

  def fpt4ToPersonConvert(fpt4 : Logic02Rec, fptPath : String): PersonConvert ={
    val person = new PersonConvert
    person.personId = fpt4.personId
    person.cardId = fpt4.cardId
    person.name = fpt4.personName
    person.aliasName = fpt4.alias
    person.sexCode = fpt4.gender
    val birthday = fpt4.birthday
    if (!birthday.isEmpty)
      person.birthday = convertString2Date(birthday,"yyyy/MM/dd")
    else
      person.birthday = null
    person.idCardNo = fpt4.idCardNo
    person.door = fpt4.door
    person.doorDetail = fpt4.doorDetail
    person.address = fpt4.address
    person.addressDetail = fpt4.addressDetail
    person.personCategory = fpt4.category
    person.caseClasses = fpt4.caseClass1Code
    person.gatherDepartCode = fpt4.gatherUnitCode
    person.gatherDepartName = fpt4.gatherUnitName
    val gatherDate = fpt4.gatherDate
    if (!gatherDate.isEmpty)
      person.gatherDate = convertString2Date(gatherDate,"yyyy/MM/dd")
    else
      person.gatherDate = null
    person.remark = fpt4.remark
    person.nativePlaceCode = fpt4.nativeplace
    person.nationCode = fpt4.nation
    person.assistLevel = fpt4.assistLevel
    person.assistBonus = fpt4.bonus
    person.assistPurpose = fpt4.assistPurpose
    person.assistRefPerson = fpt4.relatedPersonId
    person.assistRefCase = fpt4.relatedCaseId
    person.assistValidate = fpt4.assistTimeLimit
    person.assistExplain = fpt4.assistAskingInfo
    person.assistDeptCode = fpt4.assistUnitCode
    person.assistDeptName = fpt4.assistUnitName
    val assistDate = fpt4.assistDate
    if (!assistDate.isEmpty)
      person.assistDate = convertString2Date(assistDate,"yyyy/MM/dd")
    else
      person.assistDate = null
    person.assistContacts = fpt4.contact
    person.assistNumber = fpt4.contactPhone
    person.assistApproval = fpt4.approver
    person.assistSign = fpt4.isAssist
    person.fptPath = fptPath

    if (!fpt4.portraitData.isEmpty) {
      val portrait = new PortraitConvert
      portrait.personId = fpt4.personId
      portrait.fgp = "1"
      portrait.gatherData = fpt4.portraitData
      person.portrait = portrait
    }

    person
  }

  def fptFingerDataToTemplateFingerConvert(personId : String, fgp : String, groupId : String, lobType : String, gafisImg : GAFISIMAGESTRUCT, path : String): TemplateFingerConvert = {
    val finger = new TemplateFingerConvert
    finger.personId = personId
    finger.fgp = fgp
    finger.fgpCase = gafisImg.stHead.bIsPlain.toString
    finger.groupId = groupId
    finger.lobType = lobType
    finger.gatherData = gafisImg.toByteArray()
    finger.path = path
    finger
  }

  def fpt3ToLatentCaseConvert(lp : Logic2Rec): LatentCaseConvert ={
    val latentCase = new LatentCaseConvert
    latentCase.caseId = lp.caseId
    latentCase.cardId = lp.cardId
    latentCase.caseClassCode = lp.caseClass1Code
    latentCase.caseOccurDate = convertString2Date(lp.occurDate,"yyyy/MM/dd")
    latentCase.assistLevel = lp.assistLevel
    latentCase.caseOccurPlace = lp.occurPlace
    latentCase.extractUnitCode = lp.extractUnitCode
    latentCase.extractUnitName = lp.extractUnitName
    latentCase.extractor = lp.extractor
    latentCase.suspiciousAreaCode = lp.suspiciousArea1Code
    latentCase.amount = lp.amount
    latentCase.remark = lp.remark

    latentCase
  }



  def fpt4ToLatentCaseConvert(lp : Logic03Rec): LatentCaseConvert ={
    val latentCase = new LatentCaseConvert
    latentCase.caseId = lp.caseId
    latentCase.cardId = lp.cardId
    latentCase.caseClassCode = lp.caseClass1Code
    latentCase.caseOccurDate = convertString2Date(lp.occurDate,"yyyy/MM/dd")
    latentCase.assistLevel = lp.assistLevel
    latentCase.caseOccurPlace = lp.occurPlace
    latentCase.extractUnitCode = lp.extractUnitCode
    latentCase.extractUnitName = lp.extractUnitName
    latentCase.extractor = lp.extractor
    latentCase.suspiciousAreaCode = lp.suspiciousArea1Code
    latentCase.amount = lp.amount
    latentCase.caseBriefDetail = lp.caseBriefDetail
    /**-fpt4-**/
    latentCase.caseOccurPlaceCode = lp.occurPlaceCode
    latentCase.isMurder = lp.isMurder
    latentCase.caseStatus = lp.caseStatus
    latentCase.extractDate = convertString2Date(lp.extractDate,"yyyy/MM/dd")
    latentCase.assistBonus = lp.bonus
    latentCase.assistUnitCode = lp.assistUnitCode
    latentCase.assistUnitName = lp.assistUnitName
    latentCase.assistDate = lp.assistDate
    latentCase.assistRevokeSign = lp.isRevoke

    latentCase
  }


  def fpt3ToLatentFingerConvert(lData : FingerLData, fptPath : String, caseId : String, cardId : String ,seqNo : String): LatentFingerConvert = {
    val latentFinger = new LatentFingerConvert
    latentFinger.caseId = caseId
    latentFinger.seqNo = seqNo
    latentFinger.fingerId = cardId
    latentFinger.remainPlace = lData.remainPlace
    latentFinger.fgp = lData.fgp
    latentFinger.ridgeColor = lData.ridgeColor
    latentFinger.mittensBegNo = lData.mittensBegNo
    latentFinger.mittensEndNo = lData.mittensEndNo
    latentFinger.imgData = lData.imgData
    latentFinger.fptPath = fptPath

    latentFinger
  }

  def fpt4ToLatentFingerConvert(lData : nirvana.hall.c.services.gfpt4lib.FPT4File.FingerLData, fptPath : String, caseId : String, cardId : String, seqNo : String): LatentFingerConvert = {
    val latentFinger = new LatentFingerConvert
    latentFinger.caseId = caseId
    latentFinger.seqNo = seqNo
    latentFinger.fingerId = cardId
    latentFinger.remainPlace = lData.remainPlace
    latentFinger.fgp = lData.fgp
    latentFinger.ridgeColor = lData.ridgeColor
    latentFinger.mittensBegNo = lData.mittensBegNo
    latentFinger.mittensEndNo = lData.mittensEndNo

    val image = fpt4code.FPTFingerLDataToGafisImage(lData)
    latentFinger.imgData = image.toByteArray()

    latentFinger.isCorpse = lData.isCorpse
    latentFinger.corpseNo = lData.corpseNo
    latentFinger.fptPath = fptPath

    latentFinger
  }

  def fptToLatentFingerFeatureConvert(fingerId : String, feature : Array[Byte], captureMethod : String) : LatentFingerFeatureConvert = {
    val latentFingerFeature = new LatentFingerFeatureConvert
    latentFingerFeature.fingerId = fingerId
    latentFingerFeature.fingerMnt = feature
    if (!captureMethod.isEmpty)
      latentFingerFeature.captureMethod = captureMethod
    latentFingerFeature
  }

  class PersonConvert extends Serializable{
    var personId : String = _
    var cardId : String = _
    var name : String = _
    var aliasName : String = _
    var sexCode : String = _
    var birthday : java.sql.Date = _
    var idCardNo : String = _
    var door : String = _
    var doorDetail : String = _
    var address : String = _
    var addressDetail : String = _
    var personCategory : String = _
    var caseClasses : String = _
    var gatherDepartCode : String = _
    var gatherDepartName : String = _
    var gatherUserName : String = _
    var gatherDate : java.sql.Date = _
    var remark : String = _
    /**-fpt4-**/
    var nativePlaceCode : String = _
    var nationCode : String = _
    var assistLevel : String = _
    var assistBonus : String = _
    var assistPurpose : String = _
    var assistRefPerson : String = _
    var assistRefCase : String = _
    var assistValidate : String = _
    var assistExplain : String = _
    var assistDeptCode : String = _
    var assistDeptName : String = _
    var assistDate : java.sql.Date = _
    var assistContacts : String = _
    var assistNumber : String = _
    var assistApproval : String = _
    var assistSign : String = _

    val isFingerRepeat : String = "0"
    val dataSource : Int = 5
    val fingerShowStatus : Int = 1
    val delTag : String = "1"

    var fptPath : String = _

    var portrait : PortraitConvert = new PortraitConvert
  }

  class PortraitConvert extends Serializable{
    var personId : String = _
    var gatherData : Array[Byte] = _
    var fgp : String = _
    var delTag : String = "1"
  }

  class TemplateFingerConvert extends Serializable{
    var personId : String = _
    var fgp : String = _
    var fgpCase : String = _
    var groupId : String = _
    var lobType : String = _
    var mainPattern : String = _
    var vicePattern : String = _
    var gatherData : Array[Byte] = _
    var path : String = _

    //idCard finger info
    var name : String = _
    var idCardNO : String = _
    var idCardID : String = _
  }

  /***  LATENT ***/
  class LatentCaseConvert extends Serializable{
    var caseId : String = _
    var cardId : String = _
    var caseClassCode : String = _
    var caseOccurDate : java.sql.Date = _
    var assistLevel : String = _
    var caseOccurPlace : String = _
    var extractUnitCode : String = _
    var extractUnitName : String =  _
    var extractor : String = _
    var suspiciousAreaCode : String = _
    var amount : String = _
    var remark : String = _
    var fingerCount : Integer = _
    var sendFingerCount : Integer = _

    /**-fpt4-**/
    var caseBriefDetail : String = _
    var caseOccurPlaceCode : String = _
    var isMurder : String = _
    var assistBonus : String = _
    var extractDate : java.sql.Date = _
    var assistUnitCode : String = _
    var assistUnitName : String = _
    var assistDate : String = _
    var assistSign : String = _
    var assistRevokeSign : String = _
    var caseStatus : String = _

    var latentFingers : List[LatentFingerConvert] = _
  }

  class LatentFingerConvert extends Serializable{
    var seqNo : String = _
    var fingerId : String = _
    var caseId : String = _
    var remainPlace : String = _
    var fgp : String = _
    var ridgeColor : String = _
    var mittensBegNo : String = _
    var mittensEndNo : String = _
    var extractMethod : String = _
    var pattern : String = _
    var feature : String = _
    var imgDataLength : Integer = _
    var imgData : Array[Byte] = _

    /**-fpt4-**/
    var isCorpse : String = _
    var corpseNo : String = _
    var isAssist : String = _
    var matchStatus : String = _

    var dataIn : String = _
    var fptPath : String =_

    var LatentFingerFeatures : List[LatentFingerFeatureConvert] = _
  }

  class LatentFingerFeatureConvert extends Serializable{
    var fingerId : String = _
    var captureMethod : String = "M"
    var fingerMnt : Array[Byte] = _
    var fingerRidge : Array[Byte] = _
    var isMainMnt : String = "1"
  }

/**
   * 将字符串转为Date
  *
  * @param str
   * @param format
   * @return
   */
  def convertString2Date(str: String, format: String): java.sql.Date = {
    if (str.isEmpty) return null
    var strDate = str
    if (str.indexOf("-")<0 && str.indexOf("/")<0) {
     strDate = str.substring(0,4)+"/"+str.substring(4,6)+"/"+str.substring(6)
    }
    var date: Date = null
    if (str != null && format != null) {
      try {
        val formatter = new SimpleDateFormat(format)
        date = formatter.parse(strDate,new ParsePosition(0))
      } catch {
        case e: Exception =>
          e.printStackTrace()
      }
    }
    if (date == null) null
    else new sql.Date(date.getTime)
  }
}
