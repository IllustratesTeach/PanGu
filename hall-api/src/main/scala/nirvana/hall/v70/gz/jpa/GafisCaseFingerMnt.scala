package nirvana.hall.v70.gz.jpa

;
// Generated 2017-6-29 16:56:26 by Stark Activerecord generator 4.3.1.Final


import javax.persistence._

import stark.activerecord.services.ActiveRecord
import stark.activerecord.services.ActiveRecordInstance;

/**
  * GafisCaseFingerMnt generated by stark activerecord generator
  */
object GafisCaseFingerMnt extends ActiveRecordInstance[GafisCaseFingerMnt]

@Entity
@Table(name = "GAFIS_CASE_FINGER_MNT"
)
class GafisCaseFingerMnt extends ActiveRecord {


  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "FINGER_ID", length = 25)
  var fingerId: java.lang.String = _
  @Column(name = "CAPTURE_METHOD", length = 1)
  var captureMethod: java.lang.String = _
  @Lob
  @Column(name = "FINGER_MNT")
  var fingerMnt: Array[Byte] = _
  @Lob
  @Column(name = "FINGER_RIDGE")
  var fingerRidge: Array[Byte] = _
  @Column(name = "IS_MAIN_MNT", length = 1)
  var isMainMnt: java.lang.String = _
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
  @Column(name = "FINGER_MNT_NOSQL_ID", length = 10)
  var fingerMntNosqlId: java.lang.String = _
  @Column(name = "FINGER_RIDGE_NOSQL_ID", length = 10)
  var fingerRidgeNosqlId: java.lang.String = _


  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }

  def this(pkId: java.lang.String, fingerId: java.lang.String, captureMethod: java.lang.String, fingerMnt: Array[Byte], fingerRidge: Array[Byte], isMainMnt: java.lang.String, modifiedtime: java.util.Date, modifiedpsn: java.lang.String, inputtime: java.util.Date, inputpsn: java.lang.String, deletag: java.lang.String, fingerMntNosqlId: java.lang.String, fingerRidgeNosqlId: java.lang.String) {
    this()
    this.pkId = pkId
    this.fingerId = fingerId
    this.captureMethod = captureMethod
    this.fingerMnt = fingerMnt
    this.fingerRidge = fingerRidge
    this.isMainMnt = isMainMnt
    this.modifiedtime = modifiedtime
    this.modifiedpsn = modifiedpsn
    this.inputtime = inputtime
    this.inputpsn = inputpsn
    this.deletag = deletag
    this.fingerMntNosqlId = fingerMntNosqlId
    this.fingerRidgeNosqlId = fingerRidgeNosqlId
  }


}


