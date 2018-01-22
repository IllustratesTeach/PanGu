package nirvana.hall.v70.internal.adapter.gz.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by Administrator on 2017/10/17.
  */
object HallHitinfoRecord extends ActiveRecordInstance[HallHitinfoRecord]

@Entity
@Table(name = "Hall_Hitinfo_Record")
class HallHitinfoRecord extends ActiveRecord {


  @Id
  @Column(name = "UUID", unique = true, nullable = false, length = 32)
  var uuid: java.lang.String = _
  @Column(name = "CODE", length = 23)
  var code: java.lang.String = _
  @Column(name = "CODE_TYPE", length = 1)
  var codetype: java.lang.String = _
  @Column(name = "INSERTDATE", length = 23)
  var insertdate: java.util.Date = _
  @Column(name = "STATUS", length = 1)
  var status: java.lang.String = _

  def this(uuid: java.lang.String) {
    this()
    this.uuid = uuid
  }

  def this(uuid: java.lang.String, code: java.lang.String, codetype: java.lang.String, insertdate: java.util.Date, status: java.lang.String) {
    this()
    this.uuid = uuid
    this.code = code
    this.codetype = codetype
    this.insertdate = insertdate
    this.status = status
  }
}
