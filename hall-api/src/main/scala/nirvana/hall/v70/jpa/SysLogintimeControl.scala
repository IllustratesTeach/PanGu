package nirvana.hall.v70.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence.{Column, Entity, Id, Table, Temporal, TemporalType}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * SysLogintimeControl generated by hall orm 
 */
object SysLogintimeControl extends ActiveRecordInstance[SysLogintimeControl]

@Entity
@Table(name = "SYS_LOGINTIME_CONTROL"
)
class SysLogintimeControl extends ActiveRecord {


  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "CONTROL_NAME", length = 20)
  var controlName: java.lang.String = _
  @Column(name = "CONTROL_RULE", length = 1)
  var controlRule: java.lang.String = _
  @Column(name = "START_TIME", length = 20)
  var startTime: java.lang.String = _
  @Column(name = "END_TIME", length = 20)
  var endTime: java.lang.String = _
  @Column(name = "CREATE_USER", length = 32)
  var createUser: java.lang.String = _
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATE_TIME", length = 23)
  var createTime: java.util.Date = _


  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }

  def this(pkId: java.lang.String, controlName: java.lang.String, controlRule: java.lang.String, startTime: java.lang.String, endTime: java.lang.String, createUser: java.lang.String, createTime: java.util.Date) {
    this()
    this.pkId = pkId
    this.controlName = controlName
    this.controlRule = controlRule
    this.startTime = startTime
    this.endTime = endTime
    this.createUser = createUser
    this.createTime = createTime
  }


}


