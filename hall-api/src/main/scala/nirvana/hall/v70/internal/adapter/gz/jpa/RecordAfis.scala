package nirvana.hall.v70.internal.adapter.gz.jpa

import javax.persistence.{Column, _}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by yuchen on 2017/2/6.
  */
object RecordAfis extends ActiveRecordInstance[RecordAfis]

@Entity
@Table(name = "RECORD_AFIS"
)
class RecordAfis extends ActiveRecord {

  @Id
  @Column(name = "UUID")
  var uuid: java.lang.String = _
  @Column(name = "CARD_ID")
  var card_Id: java.lang.String = _
  @Column(name = "TYPE")
  var type_id: java.lang.String = _
  @Column(name = "IP_SOURCE")
  var ip_source: java.lang.String = _
  @Column(name = "INSERT_DATE")
  var insert_date: java.util.Date = _
  @Column(name = "STATUS")
  var status: java.lang.String = _
  @Column(name = "IS_SEND")
  var is_send: java.lang.String = _

  def this(uuid: java.lang.String, card_Id: java.lang.String
           , type_id: java.lang.String, ip_source: java.lang.String
           , insert_date: java.util.Date
           , status: java.lang.String, is_send: java.lang.String) {
    this()
    this.uuid = uuid
    this.card_Id = card_Id
    this.type_id = type_id
    this.ip_source = ip_source
    this.insert_date = insert_date
    this.status = status
    this.is_send = is_send
  }


}
