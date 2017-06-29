package nirvana.hall.api.jpa

import javax.persistence.{Column, Id, Table, Entity}

import stark.activerecord.services.{ActiveRecordInstance, ActiveRecord}

/**
 * hall数据同步实体类，记录抓取方同步配置
 */
object HallFetchConfig extends ActiveRecordInstance[HallFetchConfig]
@Entity
@Table(name = "HALL_FETCH_CONFIG")
class HallFetchConfig extends ActiveRecord {
  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "NAME", length = 100)
  var name: java.lang.String = _
  @Column(name = "URL", length = 100)
  var url: java.lang.String = _
  @Column(name = "TYP", length = 32)
  var typ: java.lang.String = _
  @Column(name = "DBID", length = 32)
  var dbid: java.lang.String = _
  @Column(name = "DEST_DBID", length = 32)
  var destDbid: java.lang.String = _
  @Column(name = "WRITE_STRATEGY", length = 1000)
  var writeStrategy: java.lang.String = _
  @Column(name = "CONFIG", length = 1000)
  var config: java.lang.String = _
  @Column(name = "SEQ")
  var seq: java.lang.Long = _
  @Column(name = "DELETAG", length = 1)
  var deletag: java.lang.String = _

  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }

  def this(pkId: java.lang.String, name: java.lang.String, url: java.lang.String, typ: java.lang.String, dbid: java.lang.String, destDbid: java.lang.String, writeStrategy: java.lang.String,config: java.lang.String, seq: java.lang.Long, deletag: java.lang.String){
    this()
    this.pkId = pkId
    this.url = url
    this.name = name
    this.typ = typ
    this.dbid = dbid
    this.destDbid = destDbid
    this.writeStrategy = writeStrategy
    this.config = config
    this.seq = seq
    this.deletag = deletag
  }
}
