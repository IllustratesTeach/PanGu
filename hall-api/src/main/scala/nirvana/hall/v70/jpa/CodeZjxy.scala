package nirvana.hall.v70.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * CodeZjxy generated by hall orm 
 */
object CodeZjxy extends ActiveRecordInstance[CodeZjxy]

@Entity
@Table(name = "CODE_ZJXY"
)
class CodeZjxy extends ActiveRecord {


  @Id
  @Column(name = "CODE", unique = true, nullable = false, length = 2)
  var code: java.lang.String = _
  @Column(name = "NAME", length = 60)
  var name: java.lang.String = _
  @Column(name = "DELETE_FLAG", length = 1)
  var deleteFlag: java.lang.String = _
  @Column(name = "ORD")
  var ord: java.lang.Long = _
  @Column(name = "REMARK", length = 90)
  var remark: java.lang.String = _


  def this(code: java.lang.String) {
    this()
    this.code = code
  }

  def this(code: java.lang.String, name: java.lang.String, deleteFlag: java.lang.String, ord: java.lang.Long, remark: java.lang.String) {
    this()
    this.code = code
    this.name = name
    this.deleteFlag = deleteFlag
    this.ord = ord
    this.remark = remark
  }


}


