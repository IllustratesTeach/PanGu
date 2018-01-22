package nirvana.hall.v70.internal.adapter.nj.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence._

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * GafisGatherPalm generated by hall orm 
 */
object GafisGatherPalm extends ActiveRecordInstance[GafisGatherPalm]

@Entity
@Table(name = "GAFIS_GATHER_PALM"
)
class GafisGatherPalm extends ActiveRecord {

  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "PERSON_ID", length = 32)
  var personId: java.lang.String = _
  @Column(name = "FGP", nullable = false)
  var fgp: Short = _
  @Column(name = "GROUP_ID")
  var groupId: java.lang.Short = _
  @Column(name = "LOBTYPE", nullable = false)
  var lobtype: Short = _
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
  @Column(name = "SEQ")
  var seq: java.lang.Long = _
  @Column(name = "PALM_DATA_NOSQL_ID", length = 10)
  var palmDataNosqlId: java.lang.String = _


  def this(pkId: java.lang.String, fgp: Short, lobtype: Short, gatherData: Array[Byte], inputtime: java.util.Date) {
    this()
    this.pkId = pkId
    this.fgp = fgp
    this.lobtype = lobtype
    this.gatherData = gatherData
    this.inputtime = inputtime
  }

  def this(pkId: java.lang.String, personId: java.lang.String, fgp: Short, groupId: java.lang.Short, lobtype: Short, gatherData: Array[Byte], inputpsn: java.lang.String, inputtime: java.util.Date, modifiedpsn: java.lang.String, modifiedtime: java.util.Date, seq: java.lang.Long, palmDataNosqlId: java.lang.String) {
    this()
    this.pkId = pkId
    this.personId = personId
    this.fgp = fgp
    this.groupId = groupId
    this.lobtype = lobtype
    this.gatherData = gatherData
    this.inputpsn = inputpsn
    this.inputtime = inputtime
    this.modifiedpsn = modifiedpsn
    this.modifiedtime = modifiedtime
    this.seq = seq
    this.palmDataNosqlId = palmDataNosqlId
  }


}

