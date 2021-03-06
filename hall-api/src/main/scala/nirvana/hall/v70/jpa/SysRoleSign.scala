package nirvana.hall.v70.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * SysRoleSign generated by hall orm 
 */
object SysRoleSign extends ActiveRecordInstance[SysRoleSign]

@Entity
@Table(name = "SYS_ROLE_SIGN"
)
class SysRoleSign extends ActiveRecord {


  @Id
  @Column(name = "CODE", unique = true, nullable = false, length = 20)
  var code: java.lang.String = _
  @Column(name = "NAME", length = 300)
  var name: java.lang.String = _
  @Column(name = "DELETE_FLAG", length = 1)
  var deleteFlag: java.lang.String = _
  @Column(name = "REMARK", length = 90)
  var remark: java.lang.String = _
  @Column(name = "ORD")
  var ord: java.lang.Long = _


  def this(code: java.lang.String) {
    this()
    this.code = code
  }

  def this(code: java.lang.String, name: java.lang.String, deleteFlag: java.lang.String, remark: java.lang.String, ord: java.lang.Long) {
    this()
    this.code = code
    this.name = name
    this.deleteFlag = deleteFlag
    this.remark = remark
    this.ord = ord
  }


}


