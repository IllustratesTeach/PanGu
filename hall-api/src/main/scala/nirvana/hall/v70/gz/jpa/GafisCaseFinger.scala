package nirvana.hall.v70.gz.jpa

;
// Generated 2017-6-29 16:56:26 by Stark Activerecord generator 4.3.1.Final


import javax.persistence._

import stark.activerecord.services.ActiveRecord
import stark.activerecord.services.ActiveRecordInstance;

/**
  * GafisCaseFinger generated by stark activerecord generator
  */
object GafisCaseFinger extends ActiveRecordInstance[GafisCaseFinger]

@Entity
@Table(name = "GAFIS_CASE_FINGER"
)
class GafisCaseFinger extends ActiveRecord {


  @Id
  @Column(name = "FINGER_ID", unique = true, nullable = false, length = 25)
  var fingerId: java.lang.String = _
  @Column(name = "SEQ_NO", length = 2)
  var seqNo: java.lang.String = _
  @Column(name = "CASE_ID", length = 23)
  var caseId: java.lang.String = _
  @Column(name = "IS_CORPSE", length = 1)
  var isCorpse: java.lang.String = _
  @Column(name = "CORPSE_NO", length = 23)
  var corpseNo: java.lang.String = _
  @Column(name = "REMAIN_PLACE", length = 60)
  var remainPlace: java.lang.String = _
  @Column(name = "FGP_GROUP", precision = 3, scale = 0)
  var fgpGroup: java.lang.Short = _
  @Column(name = "FGP", length = 10)
  var fgp: java.lang.String = _
  @Column(name = "RIDGE_COLOR", length = 1)
  var ridgeColor: java.lang.String = _
  @Column(name = "PATTERN", length = 20)
  var pattern: java.lang.String = _
  @Column(name = "MITTENS_BEG_NO", length = 2)
  var mittensBegNo: java.lang.String = _
  @Column(name = "MITTENS_END_NO", length = 2)
  var mittensEndNo: java.lang.String = _
  @Column(name = "THAN_STATUS", length = 2)
  var thanStatus: java.lang.String = _
  @Column(name = "IS_ASSIST", length = 1)
  var isAssist: java.lang.String = _
  @Lob
  @Column(name = "FINGER_IMG")
  var fingerImg: Array[Byte] = _
  @Lob
  @Column(name = "FINGER_CPR")
  var fingerCpr: Array[Byte] = _
  @Column(name = "LT_COUNT")
  var ltCount: java.lang.Long = _
  @Column(name = "LL_COUNT")
  var llCount: java.lang.Long = _
  @Column(name = "LT_COUNT_MOD_MNT")
  var ltCountModMnt: java.lang.Long = _
  @Column(name = "LL_COUNT_MOD_MNT")
  var llCountModMnt: java.lang.Long = _
  @Column(name = "EDIT_COUNT")
  var editCount: java.lang.Long = _
  @Temporal(TemporalType.DATE)
  @Column(name = "LT_DATE", length = 8)
  var ltDate: java.util.Date = _
  @Temporal(TemporalType.DATE)
  @Column(name = "LL_DATE", length = 8)
  var llDate: java.util.Date = _
  @Column(name = "LT_OPERATOR", length = 32)
  var ltOperator: java.lang.String = _
  @Column(name = "LL_OPERATOR", length = 32)
  var llOperator: java.lang.String = _
  @Column(name = "LT_STATUS", length = 1)
  var ltStatus: java.lang.String = _
  @Column(name = "LL_STATUS", length = 1)
  var llStatus: java.lang.String = _
  @Column(name = "CREATOR_UNIT_CODE", length = 12)
  var creatorUnitCode: java.lang.String = _
  @Column(name = "UPDATOR_UNIT_CODE", length = 12)
  var updatorUnitCode: java.lang.String = _
  @Temporal(TemporalType.DATE)
  @Column(name = "MODIFIEDTIME", length = 8)
  var modifiedtime: java.util.Date = _
  @Column(name = "MODIFIEDPSN", length = 32)
  var modifiedpsn: java.lang.String = _
  @Temporal(TemporalType.DATE)
  @Column(name = "INPUTTIME", length = 8)
  var inputtime: java.util.Date = _
  @Column(name = "INPUTPSN", length = 32)
  var inputpsn: java.lang.String = _
  @Column(name = "DELETAG", length = 1)
  var deletag: java.lang.String = _
  @Column(name = "REMARK", length = 1000)
  var remark: java.lang.String = _
  @Column(name = "SID")
  var sid: java.lang.Long = _
  @Column(name = "MATCH_STATUS", length = 1)
  var matchStatus: java.lang.String = _
  @Column(name = "DEVELOP_METHOD", length = 2)
  var developMethod: java.lang.String = _
  @Column(name = "FINGER_CPR_NOSQL_ID", length = 10)
  var fingerCprNosqlId: java.lang.String = _
  @Column(name = "FINGER_IMG_NOSQL_ID", length = 10)
  var fingerImgNosqlId: java.lang.String = _
  @Column(name = "SEQ")
  var seq: java.lang.Long = _


  def this(fingerId: java.lang.String) {
    this()
    this.fingerId = fingerId
  }

  def this(fingerId: java.lang.String, seqNo: java.lang.String, caseId: java.lang.String, isCorpse: java.lang.String, corpseNo: java.lang.String, remainPlace: java.lang.String, fgpGroup: java.lang.Short, fgp: java.lang.String, ridgeColor: java.lang.String, pattern: java.lang.String, mittensBegNo: java.lang.String, mittensEndNo: java.lang.String, thanStatus: java.lang.String, isAssist: java.lang.String, fingerImg: Array[Byte], fingerCpr: Array[Byte], ltCount: java.lang.Long, llCount: java.lang.Long, ltCountModMnt: java.lang.Long, llCountModMnt: java.lang.Long, editCount: java.lang.Long, ltDate: java.util.Date, llDate: java.util.Date, ltOperator: java.lang.String, llOperator: java.lang.String, ltStatus: java.lang.String, llStatus: java.lang.String, creatorUnitCode: java.lang.String, updatorUnitCode: java.lang.String, modifiedtime: java.util.Date, modifiedpsn: java.lang.String, inputtime: java.util.Date, inputpsn: java.lang.String, deletag: java.lang.String, remark: java.lang.String, sid: java.lang.Long, matchStatus: java.lang.String, developMethod: java.lang.String, fingerCprNosqlId: java.lang.String, fingerImgNosqlId: java.lang.String, seq: java.lang.Long) {
    this()
    this.fingerId = fingerId
    this.seqNo = seqNo
    this.caseId = caseId
    this.isCorpse = isCorpse
    this.corpseNo = corpseNo
    this.remainPlace = remainPlace
    this.fgpGroup = fgpGroup
    this.fgp = fgp
    this.ridgeColor = ridgeColor
    this.pattern = pattern
    this.mittensBegNo = mittensBegNo
    this.mittensEndNo = mittensEndNo
    this.thanStatus = thanStatus
    this.isAssist = isAssist
    this.fingerImg = fingerImg
    this.fingerCpr = fingerCpr
    this.ltCount = ltCount
    this.llCount = llCount
    this.ltCountModMnt = ltCountModMnt
    this.llCountModMnt = llCountModMnt
    this.editCount = editCount
    this.ltDate = ltDate
    this.llDate = llDate
    this.ltOperator = ltOperator
    this.llOperator = llOperator
    this.ltStatus = ltStatus
    this.llStatus = llStatus
    this.creatorUnitCode = creatorUnitCode
    this.updatorUnitCode = updatorUnitCode
    this.modifiedtime = modifiedtime
    this.modifiedpsn = modifiedpsn
    this.inputtime = inputtime
    this.inputpsn = inputpsn
    this.deletag = deletag
    this.remark = remark
    this.sid = sid
    this.matchStatus = matchStatus
    this.developMethod = developMethod
    this.fingerCprNosqlId = fingerCprNosqlId
    this.fingerImgNosqlId = fingerImgNosqlId
    this.seq = seq
  }


}


