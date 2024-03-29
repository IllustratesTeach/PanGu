package nirvana.hall.v70.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence._

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * GafisGatherPortrait generated by hall orm 
 */
object GafisGatherPortrait extends ActiveRecordInstance[GafisGatherPortrait]

@Entity
@Table(name = "GAFIS_GATHER_PORTRAIT"
)
class GafisGatherPortrait extends ActiveRecord {


  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "PERSONID", length = 32)
  var personid: java.lang.String = _
  @Column(name = "FGP", nullable = false, length = 1)
  var fgp: java.lang.String = _
  @Lob
  @Column(name = "GATHER_DATA", nullable = false)
  var gatherData: Array[Byte] = _
  @Column(name = "INPUTPSN", length = 32)
  var inputpsn: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "INPUTTIME", nullable = false, length = 23)
  var inputtime: java.util.Date = _
  @Column(name = "MODIFIEDPSN", length = 32)
  var modifiedpsn: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MODIFIEDTIME", length = 23)
  var modifiedtime: java.util.Date = _
  @Column(name = "DELETAG", length = 1)
  var deletag: java.lang.String = _
  @Column(name = "GATHERDATANOSQLID", length = 10)
  var gatherdatanosqlid: java.lang.String = _


  def this(pkId: java.lang.String, fgp: java.lang.String, gatherData: Array[Byte], inputtime: java.util.Date) {
    this()
    this.pkId = pkId
    this.fgp = fgp
    this.gatherData = gatherData
    this.inputtime = inputtime
  }

  def this(pkId: java.lang.String, personid: java.lang.String, fgp: java.lang.String, gatherData: Array[Byte], inputpsn: java.lang.String, inputtime: java.util.Date, modifiedpsn: java.lang.String, modifiedtime: java.util.Date, deletag: java.lang.String, gatherdatanosqlid: java.lang.String) {
    this()
    this.pkId = pkId
    this.personid = personid
    this.fgp = fgp
    this.gatherData = gatherData
    this.inputpsn = inputpsn
    this.inputtime = inputtime
    this.modifiedpsn = modifiedpsn
    this.modifiedtime = modifiedtime
    this.deletag = deletag
    this.gatherdatanosqlid = gatherdatanosqlid
  }


}


