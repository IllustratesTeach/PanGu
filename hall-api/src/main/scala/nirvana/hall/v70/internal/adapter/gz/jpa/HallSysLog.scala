package nirvana.hall.v70.internal.adapter.gz.jpa

import javax.persistence.{Column, _}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by yuchen on 2017/2/6.
  */
object HallSysLog extends ActiveRecordInstance[HallSysLog]

@Entity
@Table(name = "Hall_sys_log"
)
class HallSysLog extends ActiveRecord {

  @Id
  @Column(name = "UUID")
  var uuid: java.lang.String = _
  @Column(name = "CARD_ID")
  var card_Id: java.lang.String = _
  @Column(name = "INSERT_DATE")
  var insert_date: java.util.Date = _
  @Column(name = "CONTENT")
  var content: java.lang.String = _
  @Column(name = "LOG_TYPE")
  var log_type:java.lang.Integer = _
  @Column(name = "MSG")
  var msg:java.lang.String = _
  @Column(name = "SEQ")
  var seq:java.lang.String = _

  def this(uuid: java.lang.String, card_Id: java.lang.String
           , insert_date: java.util.Date, content: java.lang.String
           ,log_type: java.lang.Integer, msg: java.lang.String, seq: java.lang.String) {
    this()
    this.uuid = uuid
    this.card_Id = card_Id
    this.insert_date = insert_date
    this.content = content
    this.log_type = log_type
    this.msg = msg
    this.seq = seq
  }
}
