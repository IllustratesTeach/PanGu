package nirvana.hall.api.jpa

import javax.persistence.{Column, Id, Table, Entity}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * hall同步读取配置
 */
object HallReadConfig extends ActiveRecordInstance[HallReadConfig]
@Entity
@Table(name = "HALL_READ_CONFIG")
class HallReadConfig extends ActiveRecord{
  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "NAME", length = 100)
  var name: java.lang.String = _
  @Column(name = "IP", length = 100)
  var ip: java.lang.String = _
  @Column(name = "TYP", length = 32)
  var typ: java.lang.String = _
  @Column(name = "DBID", length = 32)
  var dbid: java.lang.String = _
  @Column(name = "READ_STRATEGY", length = 1000)
  var readStrategy: java.lang.String = _
  @Column(name = "SEQ")
  var seq: java.lang.Long = _
  @Column(name = "DELETAG", length = 1)
  var deletag: java.lang.String = _

  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }
  def this(pkId: java.lang.String, name: java.lang.String, ip: java.lang.String, typ: java.lang.String, dbid: java.lang.String, readStrategy: java.lang.String, seq: java.lang.Long, deletag: java.lang.String){
    this()
    this.pkId = pkId
    this.ip = ip
    this.name = name
    this.typ = typ
    this.dbid = dbid
    this.readStrategy = readStrategy
    this.seq = seq
    this.deletag = deletag
  }
}
