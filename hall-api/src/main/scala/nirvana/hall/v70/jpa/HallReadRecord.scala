package nirvana.hall.v70.jpa

import java.util.{Date, UUID}
import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by yuchen on 2017/8/10.
  */
object HallReadRecord extends ActiveRecordInstance[HallReadRecord]


@Entity
@Table(name = "HALL_READ_RECORD"
)
class HallReadRecord extends ActiveRecord{
  @Id
  @Column(name = "UUID", unique = true, nullable = false,length = 32)
  var uuid: java.lang.String = _

  @Column(name = "ISSYNCCANDLIST", nullable = false)
  var issynccandlist: java.lang.String = _

  @Column(name = "CREATETIME", nullable = false)
  var createtime:java.util.Date = _

  @Column(name = "ORASID",nullable = false,length = 32)
  var orasid:java.lang.String = _

  def this(orasid:java.lang.String){
    this()
    this.uuid = UUID.randomUUID().toString.replace("-","")
    this.issynccandlist = "0"
    this.createtime = new Date
    this.orasid = orasid
  }
}
