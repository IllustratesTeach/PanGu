package nirvana.hall.v70.internal.adapter.gz.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by Administrator on 2017/10/17.
  */
object HallEdzRecord extends ActiveRecordInstance[HallEdzRecord]


@Entity
@Table(name = "Hall_Edz_Record")
class HallEdzRecord extends ActiveRecord {


  @Id
  @Column(name = "UUID", unique = true, nullable = false, length = 32)
  var uuid: java.lang.String = _
  @Column(name = "IDCARD", length = 18)
  var idcard: java.lang.String = _
  @Column(name = "INSERTDATE", length = 23)
  var insertdate: java.util.Date = _
  @Column(name = "STATUS", length = 1)
  var status: java.lang.String = _

  def this(uuid: java.lang.String) {
    this()
    this.uuid = uuid
  }

  def this(uuid: java.lang.String, idcard: java.lang.String, insertdate: java.util.Date, status: java.lang.String) {
    this()
    this.uuid = uuid
    this.idcard = idcard
    this.insertdate = insertdate
    this.status = status
  }
}
