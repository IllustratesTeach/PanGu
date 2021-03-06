package nirvana.hall.v70.internal.adapter.common.jpa

// Generated 2017-10-23 12:04:36 by Stark Activerecord generator 4.3.1.Final


import javax.persistence._

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance};

/**
  * GafisPerson generated by stark activerecord generator
  */
object GafisPerson extends ActiveRecordInstance[GafisPerson]

@Entity
@Table(name = "GAFIS_PERSON"
)
class GafisPerson extends ActiveRecord {


  @Id
  @Column(name = "PERSONID", unique = true, nullable = false, length = 23)
  var personid: java.lang.String = _
  @Column(name = "IDCARDNO", length = 18)
  var idcardno: java.lang.String = _
  @Column(name = "NAME", length = 60)
  var name: java.lang.String = _
  @Column(name = "SPELLNAME", length = 100)
  var spellname: java.lang.String = _
  @Column(name = "ALIASNAME", length = 60)
  var aliasname: java.lang.String = _
  @Column(name = "ALIASSPELL", length = 60)
  var aliasspell: java.lang.String = _
  @Column(name = "SEX_CODE", length = 1)
  var sexCode: java.lang.String = _
  @Column(name = "NATIVEPLACE_CODE", length = 6)
  var nativeplaceCode: java.lang.String = _
  @Column(name = "NATION_CODE", length = 2)
  var nationCode: java.lang.String = _
  @Temporal(TemporalType.DATE)
  @Column(name = "BIRTHDAYST", length = 8)
  var birthdayst: java.util.Date = _
  @Column(name = "DOOR", length = 6)
  var door: java.lang.String = _
  @Column(name = "DOORDETAIL", length = 90)
  var doordetail: java.lang.String = _
  @Column(name = "ADDRESS", length = 6)
  var address: java.lang.String = _
  @Column(name = "ADDRESSDETAIL", length = 90)
  var addressdetail: java.lang.String = _
  @Column(name = "GATHER_ORG_CODE", length = 12)
  var gatherOrgCode: java.lang.String = _
  @Column(name = "IPADDRESS", length = 15)
  var ipaddress: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "GATHER_DATE", length = 14)
  var gatherDate: java.util.Date = _
  @Column(name = "GATHER_TYPE_ID", length = 32)
  var gatherTypeId: java.lang.String = _
  @Column(name = "STATUS", length = 2)
  var status: java.lang.String = _
  @Column(name = "FINGERREPEATNO", length = 32)
  var fingerrepeatno: java.lang.String = _
  @Column(name = "ANNEX", length = 1500)
  var annex: java.lang.String = _
  @Column(name = "INPUTPSN", length = 32)
  var inputpsn: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "INPUTTIME", length = 14)
  var inputtime: java.util.Date = _
  @Column(name = "MODIFIEDPSN", length = 32)
  var modifiedpsn: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MODIFIEDTIME", length = 14)
  var modifiedtime: java.util.Date = _
  @Column(name = "DELETAG", length = 1)
  var deletag: java.lang.String = _
  @Column(name = "SCHEDULE", length = 10)
  var schedule: java.lang.String = _
  @Column(name = "APPROVAL", length = 1)
  var approval: java.lang.String = _
  @Column(name = "PERSON_CATEGORY", length = 3)
  var personCategory: java.lang.String = _
  @Column(name = "AUDITOR", length = 32)
  var auditor: java.lang.String = _
  @Temporal(TemporalType.DATE)
  @Column(name = "AUDITEDTIME", length = 8)
  var auditedtime: java.util.Date = _
  @Column(name = "GATHER_FINGER_MODE", length = 1)
  var gatherFingerMode: java.lang.String = _
  @Column(name = "CASE_CLASSES", length = 30)
  var caseClasses: java.lang.String = _
  @Column(name = "GATHER_FINGER_NUM")
  var gatherFingerNum: java.lang.Long = _
  @Column(name = "FINGER_REMARK", length = 300)
  var fingerRemark: java.lang.String = _
  @Column(name = "GATHERDEPARTCODE", length = 50)
  var gatherdepartcode: java.lang.String = _
  @Column(name = "GATHERUSERID", length = 50)
  var gatheruserid: java.lang.String = _
  @Temporal(TemporalType.DATE)
  @Column(name = "GATHER_FINGER_TIME", length = 8)
  var gatherFingerTime: java.util.Date = _
  @Column(name = "CASE_BRIEF_CONTENTS", length = 1500)
  var caseBriefContents: java.lang.String = _
  @Column(name = "DATA_SOURCES", precision = 2, scale = 0)
  var dataSources: java.lang.Long = _
  @Column(name = "CITY_CODE", length = 6)
  var cityCode: java.lang.String = _
  @Column(name = "SID")
  var sid: java.lang.Long = _
  @Column(name = "SEQ")
  var seq: java.lang.Long = _
  @Column(name = "CARDID", length = 23)
  var cardid: java.lang.String = _
  @Column(name = "RECORDMARK", length = 1)
  var recordmark: java.lang.String = _
  @Column(name = "RECORDSITUATION", length = 3072)
  var recordsituation: java.lang.String = _
  @Column(name = "ASSIST_LEVEL", length = 1)
  var assistLevel: java.lang.String = _
  @Column(name = "ASSIST_BONUS", length = 6)
  var assistBonus: java.lang.String = _
  @Column(name = "ASSIST_PURPOSE", length = 5)
  var assistPurpose: java.lang.String = _
  @Column(name = "ASSIST_REF_PERSON", length = 23)
  var assistRefPerson: java.lang.String = _
  @Column(name = "ASSIST_REF_CASE", length = 23)
  var assistRefCase: java.lang.String = _
  @Column(name = "ASSIST_VALID_DATE", length = 1)
  var assistValidDate: java.lang.String = _
  @Column(name = "ASSIST_EXPLAIN", length = 512)
  var assistExplain: java.lang.String = _
  @Column(name = "ASSIST_DEPT_CODE", length = 12)
  var assistDeptCode: java.lang.String = _
  @Column(name = "ASSIST_DEPT_NAME", length = 70)
  var assistDeptName: java.lang.String = _
  @Column(name = "ASSIST_DATE", length = 10)
  var assistDate: java.lang.String = _
  @Column(name = "ASSIST_CONTACTS", length = 30)
  var assistContacts: java.lang.String = _
  @Column(name = "ASSIST_NUMBER", length = 40)
  var assistNumber: java.lang.String = _
  @Column(name = "ASSIST_APPROVAL", length = 30)
  var assistApproval: java.lang.String = _
  @Column(name = "ASSIST_SIGN", length = 1)
  var assistSign: java.lang.String = _
  @Column(name = "GATHERDEPARTNAME", length = 100)
  var gatherdepartname: java.lang.String = _
  @Column(name = "GATHERUSERNAME", length = 100)
  var gatherusername: java.lang.String = _
  @Column(name = "CERTIFICATETYPE", length = 3)
  var certificatetype: java.lang.String = _
  @Column(name = "CERTIFICATEID", length = 90)
  var certificateid: java.lang.String = _
  @Column(name = "PERSON_TYPE", length = 3)
  var personType: java.lang.String = _
  @Column(name = "CASE_CLASSES2", length = 30)
  var caseClasses2: java.lang.String = _
  @Column(name = "CASE_CLASSES3", length = 30)
  var caseClasses3: java.lang.String = _
  @Column(name = "PSIS_NO", length = 32)
  var psisNo: java.lang.String = _
  @Column(name = "REMARK")
  var remark: java.lang.String = _
  @Column(name = "CASE_FINGER_GROUP_NO", length = 25)
  var caseFingerGroupNo: java.lang.String = _
  @Column(name = "THAN_STATE_TT", length = 1)
  var thanStateTt: java.lang.String = _
  @Column(name = "THAN_STATE_TL", length = 1)
  var thanStateTl: java.lang.String = _
  @Column(name = "THAN_STATE_LT", length = 1)
  var thanStateLt: java.lang.String = _
  @Column(name="INPUT_USERNAME", length=60)
  var inputUsername:java.lang.String =  _
  @Column(name="MODIFY_USERNAME", length=60)
  var modifyUsername:java.lang.String =  _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="TLDATE", length=14)
  var tldate:java.util.Date =  _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="TTDATE", length=14)
  var ttdate:java.util.Date =  _
  @Column(name="MODIFY_UNIT_CODE", length=50)
  var modifyUnitCode:java.lang.String =  _
  @Column(name="MIC_UPDATE_USERNAME", length=60)
  var micUpdateUsername:java.lang.String =  _
  @Column(name="MIC_UPDATE_USERID", length=32)
  var micUpdateUserid:java.lang.String =  _
  @Column(name="MIC_UPDATE_UNITCODE", length=16)
  var micUpdateUnitcode:java.lang.String =  _
  @Column(name="TLCOUNT", length=18)
  var tlcount:java.lang.String  =  _
  @Column(name="TTCOUNT", length=18)
  var ttcount:java.lang.String =  _
  @Column(name="EDIT_COUNT", length=18)
  var editCount:java.lang.String =  _
  @Column(name="TL_USERNAME", length=60)
  var tlUsername:java.lang.String =  _
  @Column(name="TT_USERNAME", length=60)
  var ttUsername:java.lang.String =  _
  @Column(name="TL_USER_ID", length=32)
  var tlUserId:java.lang.String =  _
  @Column(name="TT_USER_ID", length=32)
  var ttUserId:java.lang.String =  _


  def this(personid: java.lang.String) {
    this()
    this.personid = personid
  }

  def this(personid: java.lang.String, idcardno: java.lang.String, name: java.lang.String, spellname: java.lang.String, aliasname: java.lang.String, aliasspell: java.lang.String, sexCode: java.lang.String, nativeplaceCode: java.lang.String, nationCode: java.lang.String, birthdayst: java.util.Date, door: java.lang.String, doordetail: java.lang.String, address: java.lang.String, addressdetail: java.lang.String, gatherOrgCode: java.lang.String, ipaddress: java.lang.String, gatherDate: java.util.Date, gatherTypeId: java.lang.String, status: java.lang.String, fingerrepeatno: java.lang.String, annex: java.lang.String, inputpsn: java.lang.String, inputtime: java.util.Date, modifiedpsn: java.lang.String, modifiedtime: java.util.Date, deletag: java.lang.String, schedule: java.lang.String, approval: java.lang.String, personCategory: java.lang.String, auditor: java.lang.String, auditedtime: java.util.Date, gatherFingerMode: java.lang.String, caseClasses: java.lang.String, gatherFingerNum: java.lang.Long, fingerRemark: java.lang.String, gatherdepartcode: java.lang.String, gatheruserid: java.lang.String, gatherFingerTime: java.util.Date, caseBriefContents: java.lang.String, dataSources: java.lang.Long, cityCode: java.lang.String, sid: java.lang.Long, seq: java.lang.Long, cardid: java.lang.String, recordmark: java.lang.String, recordsituation: java.lang.String, assistLevel: java.lang.String, assistBonus: java.lang.String, assistPurpose: java.lang.String, assistRefPerson: java.lang.String, assistRefCase: java.lang.String, assistValidDate: java.lang.String, assistExplain: java.lang.String, assistDeptCode: java.lang.String, assistDeptName: java.lang.String, assistDate: java.lang.String, assistContacts: java.lang.String, assistNumber: java.lang.String, assistApproval: java.lang.String, assistSign: java.lang.String, gatherdepartname: java.lang.String, gatherusername: java.lang.String, certificatetype: java.lang.String, certificateid: java.lang.String, personType: java.lang.String, caseClasses2: java.lang.String, caseClasses3: java.lang.String, psisNo: java.lang.String, remark: java.lang.String, caseFingerGroupNo: java.lang.String, thanStateTt: java.lang.String, thanStateTl: java.lang.String, thanStateLt: java.lang.String ,inputUsername:java.lang.String, modifyUsername:java.lang.String, tldate:java.util.Date, ttdate:java.util.Date, modifyUnitCode:java.lang.String, micUpdateUsername:java.lang.String, micUpdateUserid:java.lang.String, micUpdateUnitcode:java.lang.String, tlcount:java.lang.String, ttcount:java.lang.String, editCount:java.lang.String, tlUsername:java.lang.String, ttUsername:java.lang.String, tlUserId:java.lang.String, ttUserId:java.lang.String) {
    this()
    this.personid = personid
    this.idcardno = idcardno
    this.name = name
    this.spellname = spellname
    this.aliasname = aliasname
    this.aliasspell = aliasspell
    this.sexCode = sexCode
    this.nativeplaceCode = nativeplaceCode
    this.nationCode = nationCode
    this.birthdayst = birthdayst
    this.door = door
    this.doordetail = doordetail
    this.address = address
    this.addressdetail = addressdetail
    this.gatherOrgCode = gatherOrgCode
    this.ipaddress = ipaddress
    this.gatherDate = gatherDate
    this.gatherTypeId = gatherTypeId
    this.status = status
    this.fingerrepeatno = fingerrepeatno
    this.annex = annex
    this.inputpsn = inputpsn
    this.inputtime = inputtime
    this.modifiedpsn = modifiedpsn
    this.modifiedtime = modifiedtime
    this.deletag = deletag
    this.schedule = schedule
    this.approval = approval
    this.personCategory = personCategory
    this.auditor = auditor
    this.auditedtime = auditedtime
    this.gatherFingerMode = gatherFingerMode
    this.caseClasses = caseClasses
    this.gatherFingerNum = gatherFingerNum
    this.fingerRemark = fingerRemark
    this.gatherdepartcode = gatherdepartcode
    this.gatheruserid = gatheruserid
    this.gatherFingerTime = gatherFingerTime
    this.caseBriefContents = caseBriefContents
    this.dataSources = dataSources
    this.cityCode = cityCode
    this.sid = sid
    this.seq = seq
    this.cardid = cardid
    this.recordmark = recordmark
    this.recordsituation = recordsituation
    this.assistLevel = assistLevel
    this.assistBonus = assistBonus
    this.assistPurpose = assistPurpose
    this.assistRefPerson = assistRefPerson
    this.assistRefCase = assistRefCase
    this.assistValidDate = assistValidDate
    this.assistExplain = assistExplain
    this.assistDeptCode = assistDeptCode
    this.assistDeptName = assistDeptName
    this.assistDate = assistDate
    this.assistContacts = assistContacts
    this.assistNumber = assistNumber
    this.assistApproval = assistApproval
    this.assistSign = assistSign
    this.gatherdepartname = gatherdepartname
    this.gatherusername = gatherusername
    this.certificatetype = certificatetype
    this.certificateid = certificateid
    this.personType = personType
    this.caseClasses2 = caseClasses2
    this.caseClasses3 = caseClasses3
    this.psisNo = psisNo
    this.remark = remark
    this.caseFingerGroupNo = caseFingerGroupNo
    this.thanStateTt = thanStateTt
    this.thanStateTl = thanStateTl
    this.thanStateLt = thanStateLt
    this.inputUsername = inputUsername
    this.modifyUsername = modifyUsername
    this.tldate = tldate
    this.ttdate = ttdate
    this.modifyUnitCode = modifyUnitCode
    this.micUpdateUsername = micUpdateUsername
    this.micUpdateUserid = micUpdateUserid
    this.micUpdateUnitcode = micUpdateUnitcode
    this.tlcount = tlcount
    this.ttcount = ttcount
    this.editCount = editCount
    this.tlUsername = tlUsername
    this.ttUsername = ttUsername
    this.tlUserId = tlUserId
    this.ttUserId = ttUserId
  }


}


