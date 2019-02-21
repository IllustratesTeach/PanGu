package nirvana.hall.v70.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by win-20161010 on 2017/6/27.
  */
object HallPkIdBreakId extends ActiveRecordInstance[HallPkIdBreakId]


@Entity
@Table(name = "HALL_PKID_BREAKID")
class HallPkIdBreakId extends ActiveRecord {
  @Id
  @Column(name = "PKID", unique = true, nullable = false, length = 32)
  var pkid: java.lang.String = _
  @Column(name = "BREAKID", length = 24)
  var breakid: java.lang.String = _

  def this(pkid: java.lang.String, breakid: java.lang.String) {
    this()
    this.pkid = pkid
    this.breakid = breakid
  }
}
