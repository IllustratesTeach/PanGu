package nirvana.hall.v70.jpa

/**
  * Created by Administrator on 2017/5/31.
  */

import javax.persistence.{Column, Entity, Id, Table, Temporal, TemporalType}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * GafisCase generated by hall orm
  */
object GafisCaseBak extends ActiveRecordInstance[GafisCaseBak]

@Entity
@Table(name = "GAFIS_CASE_BAK"
)
class GafisCaseBak extends ActiveRecord {
  @Id
  @Column(name = "CASE_ID", unique = true, nullable = false, length = 23)
  var caseId: java.lang.String = _
  @Column(name = "CARD_ID", length = 23)
  var cardId: java.lang.String = _
  @Column(name = "CASE_CLASS_CODE", length = 6)
  var caseClassCode: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CASE_OCCUR_DATE", length = 23)
  var caseOccurDate: java.util.Date = _
  @Column(name = "CASE_OCCUR_PLACE_CODE", length = 6)
  var caseOccurPlaceCode: java.lang.String = _
  @Column(name = "CASE_OCCUR_PLACE_DETAIL", length = 70)
  var caseOccurPlaceDetail: java.lang.String = _
  @Column(name = "CASE_BRIEF_DETAIL", length = 1000)
  var caseBriefDetail: java.lang.String = _
  @Column(name = "IS_MURDER", length = 1)
  var isMurder: java.lang.String = _
  @Column(name = "AMOUNT", length = 16)
  var amount: java.lang.String = _
  @Column(name = "EXTRACT_UNIT_CODE", length = 12)
  var extractUnitCode: java.lang.String = _
  @Column(name = "EXTRACT_UNIT_NAME", length = 90)
  var extractUnitName: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "EXTRACT_DATE", length = 23)
  var extractDate: java.util.Date = _
  @Column(name = "EXTRACTOR", length = 30)
  var extractor: java.lang.String = _
  @Column(name = "SUSPICIOUS_AREA_CODE", length = 6)
  var suspiciousAreaCode: java.lang.String = _
  @Column(name = "CASE_STATE", length = 2)
  var caseState: java.lang.String = _
  @Column(name = "CASE_NATURE", length = 3)
  var caseNature: java.lang.String = _
  @Column(name = "REMARK", length = 1000)
  var remark: java.lang.String = _
  @Column(name = "INPUTPSN", length = 32)
  var inputpsn: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "INPUTTIME", length = 23)
  var inputtime: java.util.Date = _
  @Column(name = "MODIFIEDPSN", length = 32)
  var modifiedpsn: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MODIFIEDTIME", length = 23)
  var modifiedtime: java.util.Date = _
  @Column(name = "DELETAG", length = 1)
  var deletag: java.lang.String = _
  @Column(name = "BROKEN_STATUS")
  var brokenStatus: java.lang.Short = _
  @Column(name = "CASE_SOURCE", length = 1)
  var caseSource: java.lang.String = _
  @Column(name = "CREATE_UNIT_CODE", length = 12)
  var createUnitCode: java.lang.String = _
  @Column(name = "ASSIST_LEVEL", length = 1)
  var assistLevel: java.lang.String = _
  @Column(name = "CASE_NATURE_OLD", length = 60)
  var caseNatureOld: java.lang.String = _
  @Column(name = "IS_CHECKED", length = 1)
  var isChecked: java.lang.Character = _
  @Column(name = "FPT_EXTRACT_UNIT_CODE", length = 12)
  var fptExtractUnitCode: java.lang.String = _
  @Column(name = "FPT_EXTRACT_UNIT_NAME", length = 90)
  var fptExtractUnitName: java.lang.String = _
  @Column(name = "SID")
  var sid: java.lang.Long = _
  @Column(name = "ASSIST_BONUS", length = 6)
  var assistBonus: java.lang.String = _
  @Column(name = "ASSIST_DEPT_CODE", length = 12)
  var assistDeptCode: java.lang.String = _
  @Column(name = "ASSIST_DEPT_NAME", length = 70)
  var assistDeptName: java.lang.String = _
  @Column(name = "ASSIST_DATE", length = 8)
  var assistDate: java.lang.String = _
  @Column(name = "ASSIST_SIGN", length = 1)
  var assistSign: java.lang.String = _
  @Column(name = "ASSIST_REVOKE_SIGN", length = 1)
  var assistRevokeSign: java.lang.String = _
  @Column(name = "CS_NO", length = 23)
  var csNo: java.lang.String = _
  @Column(name = "PSIS_NO", length = 23)
  var psisNo: java.lang.String = _


  def this(caseId: java.lang.String) {
    this()
    this.caseId = caseId
  }

  def this(caseId: java.lang.String, cardId: java.lang.String, caseClassCode: java.lang.String, caseOccurDate: java.util.Date, caseOccurPlaceCode: java.lang.String, caseOccurPlaceDetail: java.lang.String, caseBriefDetail: java.lang.String, isMurder: java.lang.String, amount: java.lang.String, extractUnitCode: java.lang.String, extractUnitName: java.lang.String, extractDate: java.util.Date, extractor: java.lang.String, suspiciousAreaCode: java.lang.String, caseState: java.lang.String, caseNature: java.lang.String, remark: java.lang.String, inputpsn: java.lang.String, inputtime: java.util.Date, modifiedpsn: java.lang.String, modifiedtime: java.util.Date, deletag: java.lang.String, brokenStatus: java.lang.Short, caseSource: java.lang.String, createUnitCode: java.lang.String, assistLevel: java.lang.String, caseNatureOld: java.lang.String, isChecked: java.lang.Character, fptExtractUnitCode: java.lang.String, fptExtractUnitName: java.lang.String, sid: java.lang.Long, assistBonus: java.lang.String, assistDeptCode: java.lang.String, assistDeptName: java.lang.String, assistDate: java.lang.String, assistSign: java.lang.String, assistRevokeSign: java.lang.String, csNo: java.lang.String, psisNo: java.lang.String) {
    this()
    this.caseId = caseId
    this.cardId = cardId
    this.caseClassCode = caseClassCode
    this.caseOccurDate = caseOccurDate
    this.caseOccurPlaceCode = caseOccurPlaceCode
    this.caseOccurPlaceDetail = caseOccurPlaceDetail
    this.caseBriefDetail = caseBriefDetail
    this.isMurder = isMurder
    this.amount = amount
    this.extractUnitCode = extractUnitCode
    this.extractUnitName = extractUnitName
    this.extractDate = extractDate
    this.extractor = extractor
    this.suspiciousAreaCode = suspiciousAreaCode
    this.caseState = caseState
    this.caseNature = caseNature
    this.remark = remark
    this.inputpsn = inputpsn
    this.inputtime = inputtime
    this.modifiedpsn = modifiedpsn
    this.modifiedtime = modifiedtime
    this.deletag = deletag
    this.brokenStatus = brokenStatus
    this.caseSource = caseSource
    this.createUnitCode = createUnitCode
    this.assistLevel = assistLevel
    this.caseNatureOld = caseNatureOld
    this.isChecked = isChecked
    this.fptExtractUnitCode = fptExtractUnitCode
    this.fptExtractUnitName = fptExtractUnitName
    this.sid = sid
    this.assistBonus = assistBonus
    this.assistDeptCode = assistDeptCode
    this.assistDeptName = assistDeptName
    this.assistDate = assistDate
    this.assistSign = assistSign
    this.assistRevokeSign = assistRevokeSign
    this.csNo = csNo
    this.psisNo = psisNo
  }

  def this(gafisCase: GafisCase) {
    this()
    this.caseId = gafisCase.caseId
    this.cardId = gafisCase.cardId
    this.caseClassCode = gafisCase.caseClassCode
    this.caseOccurDate = gafisCase.caseOccurDate
    this.caseOccurPlaceCode = gafisCase.caseOccurPlaceCode
    this.caseOccurPlaceDetail = gafisCase.caseOccurPlaceDetail
    this.caseBriefDetail = gafisCase.caseBriefDetail
    this.isMurder = gafisCase.isMurder
    this.amount = gafisCase.amount
    this.extractUnitCode = gafisCase.extractUnitCode
    this.extractUnitName = gafisCase.extractUnitName
    this.extractDate = gafisCase.extractDate
    this.extractor = gafisCase.extractor
    this.suspiciousAreaCode = gafisCase.suspiciousAreaCode
    this.caseState = gafisCase.caseState
    this.caseNature = gafisCase.caseNature
    this.remark = gafisCase.remark
    this.inputpsn = gafisCase.inputpsn
    this.inputtime = gafisCase.inputtime
    this.modifiedpsn = gafisCase.modifiedpsn
    this.modifiedtime = gafisCase.modifiedtime
    this.deletag = gafisCase.deletag
    this.brokenStatus = gafisCase.brokenStatus
    this.caseSource = gafisCase.caseSource
    this.createUnitCode = gafisCase.createUnitCode
    this.assistLevel = gafisCase.assistLevel
    this.caseNatureOld = gafisCase.caseNatureOld
    this.isChecked = gafisCase.isChecked
    this.fptExtractUnitCode = gafisCase.fptExtractUnitCode
    this.fptExtractUnitName = gafisCase.fptExtractUnitName
    this.sid = gafisCase.sid
    this.assistBonus = gafisCase.assistBonus
    this.assistDeptCode = gafisCase.assistDeptCode
    this.assistDeptName = gafisCase.assistDeptName
    this.assistDate = gafisCase.assistDate
    this.assistSign = gafisCase.assistSign
    this.assistRevokeSign = gafisCase.assistRevokeSign
    this.csNo = gafisCase.csNo
    this.psisNo = gafisCase.psisNo
  }
}