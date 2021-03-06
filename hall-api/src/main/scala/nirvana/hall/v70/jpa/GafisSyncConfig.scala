package nirvana.hall.v70.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * SyncGafis6 generated by hall orm 
 */
object GafisSyncConfig extends ActiveRecordInstance[GafisSyncConfig]

@Entity
@Table(name = "GAFIS_SYNC_CONFIG")
class GafisSyncConfig extends ActiveRecord {

  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "NAME", length = 100)
  var name: java.lang.String = _
  @Column(name = "URL", length = 100)
  var url: java.lang.String = _
  @Column(name = "CONFIG", length = 100)
  var config: java.lang.String = _
  @Column(name = "TIMESTAMP")
  var timestamp: java.lang.Long = _
  @Column(name = "DELETAG", length = 1)
  var deletag: java.lang.String = _

  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }

  def this(pkId: java.lang.String,name: java.lang.String, url: java.lang.String, config: java.lang.String, timestamp: java.lang.Long, deletag: java.lang.String){
    this()
    this.pkId = pkId
    this.url = url
    this.name = name
    this.config = config
    this.timestamp = timestamp
    this.deletag = deletag
  }
}


