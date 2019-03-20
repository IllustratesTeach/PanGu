package nirvana.hall.v70.internal.adapter.common.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence._

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * GafisFingerAudit
 */
object GafisFingerAudit extends ActiveRecordInstance[GafisFingerAudit]

@Entity
@Table(name = "GAFIS_FINGER_AUDIT")
class GafisFingerAudit extends ActiveRecord {


  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "PERSON_ID", length = 32)
  var personId: java.lang.String = _
  @Column(name = "FGP", length = 2)
  var fgp: java.lang.String = _
  @Column(name = "FGP_CASE", length = 1)
  var fgpCase: java.lang.String = _
  @Column(name = "NUM", length = 2)
  var num: java.lang.Integer = _
  @Column(name = "STATUS", length = 1)
  var status: java.lang.String = _
  @Column(name = "DESCRIPTION", length = 300)
  var description: java.lang.String = _
  @Column(name = "AUDITOR", length = 90)
  var auditor: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="AUDIT_TIME", length=8)
  var auditTime:java.util.Date =  _


  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }

  def this(pkId: java.lang.String, personId: java.lang.String, fgp: java.lang.String, fgpCase: java.lang.String, num: java.lang.Integer, status: java.lang.String, description: java.lang.String, auditor: java.lang.String, auditTime: java.util.Date) {
    this()
    this.pkId = pkId
    this.personId = personId
    this.fgp = fgp
    this.fgpCase = fgpCase
    this.num = num
    this.status = status
    this.description = description
    this.auditor = auditor
    this.auditTime = auditTime
  }


}



