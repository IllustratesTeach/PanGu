package nirvana.hall.v70.internal.adapter.common.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * Created by songpeng on 16/6/28.
 */
object RemoteQueryConfig extends ActiveRecordInstance[RemoteQueryConfig]

@Entity
@Table(name = "REMOTE_QUERY_CONFIG")
class RemoteQueryConfig extends ActiveRecord{

  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "NAME", length = 100)
  var name: java.lang.String = _
  @Column(name = "URL", length = 100)
  var url: java.lang.String = _
  @Column(name = "CONFIG", length = 100)
  var config: java.lang.String = _
  @Column(name = "DELETAG", length = 1)
  var deletag: java.lang.String = _

  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }

  def this(pkId: java.lang.String, name: java.lang.String, url: java.lang.String, config: java.lang.String, deletag: java.lang.String){
    this()
    this.pkId = pkId
    this.url = url
    this.name = name
    this.config = config
  }
}
