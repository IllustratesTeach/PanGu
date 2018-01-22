package nirvana.hall.v70.internal.adapter.gz.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence._

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * GafisCasePalmMnt generated by hall orm 
 */
object GafisCasePalmMnt extends ActiveRecordInstance[GafisCasePalmMnt]

@Entity
@Table(name = "GAFIS_CASE_PALM_MNT"
)
class GafisCasePalmMnt extends ActiveRecord {


  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "PALM_ID", length = 25)
  var palmId: java.lang.String = _
  @Column(name = "CAPTURE_METHOD", length = 1)
  var captureMethod: java.lang.String = _
  @Column(name = "PALM_MNT")
  var palmMnt: Array[Byte] = _
  @Column(name = "PALM_RIDGE")
  var palmRidge: Array[Byte] = _
  @Column(name = "IS_MAIN_MNT", length = 1)
  var isMainMnt: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MODIFIEDTIME", length = 23)
  var modifiedtime: java.util.Date = _
  @Column(name = "MODIFIEDPSN", length = 32)
  var modifiedpsn: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "INPUTTIME", length = 23)
  var inputtime: java.util.Date = _
  @Column(name = "INPUTPSN", length = 32)
  var inputpsn: java.lang.String = _
  @Column(name = "PALM_MNT_NOSQL_ID", length = 10)
  var palmMntNosqlId: java.lang.String = _
  @Column(name = "PALM_RIDGE_NOSQL_ID", length = 10)
  var palmRidgeNosqlId: java.lang.String = _
  @Column(name = "DELETAG", length = 1)
  var deletag: java.lang.String = _
  @Column(name = "MNT_COMBINATION_CODE", length = 15)
  var mntCombinationCode: java.lang.String = _
  @Column(name = "MNT_COMBINATION_MESSAGE", length = 128)
  var mntCombinationMessage: java.lang.String = _

  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }

  def this(pkId: java.lang.String, palmId: java.lang.String, captureMethod: java.lang.String, palmMnt: Array[Byte], palmRidge: Array[Byte], isMainMnt: java.lang.String, modifiedtime: java.util.Date, modifiedpsn: java.lang.String, inputtime: java.util.Date, inputpsn: java.lang.String, palmMntNosqlId: java.lang.String, palmRidgeNosqlId: java.lang.String, deletag:java.lang.String,mntCombinationCode: java.lang.String, mntCombinationMessage: java.lang.String) {
    this()
    this.pkId = pkId
    this.palmId = palmId
    this.captureMethod = captureMethod
    this.palmMnt = palmMnt
    this.palmRidge = palmRidge
    this.isMainMnt = isMainMnt
    this.modifiedtime = modifiedtime
    this.modifiedpsn = modifiedpsn
    this.inputtime = inputtime
    this.inputpsn = inputpsn
    this.palmMntNosqlId = palmMntNosqlId
    this.palmRidgeNosqlId = palmRidgeNosqlId
    this.deletag = deletag
    this.mntCombinationCode = mntCombinationCode
    this.mntCombinationMessage = mntCombinationMessage
  }


}

