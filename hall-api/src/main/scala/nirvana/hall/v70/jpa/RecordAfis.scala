package nirvana.hall.v70.jpa

import javax.persistence.{Column, TemporalType, _}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by yuchen on 2017/2/6.
  */
object RecordAfis extends ActiveRecordInstance[RecordAfis]

@Entity
@Table(name = "RECORD_AFIS"
)
class RecordAfis extends ActiveRecord {

  /**
    * ID	N	INTEGER	N			自增id
      UUID	N	VARCHAR2(34)	N			主键，唯一键
      CARD_ID	N	VARCHAR2(34)	Y			卡号
      TYPE	N	VARCHAR2(3)	Y			类型
      IP_SOURCE	N	VARCHAR2(15)	Y			源ip
      INSERT_DATE	N	DATE	Y			插入时间
      STATUS	N	VARCHAR2(1)	Y			结果状态
      IS_SEND	N	VARCHAR2(1)	Y			是否是发送端
    */
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_ID_RECORD_AFIS")
  @SequenceGenerator(name="SEQ_ID_RECORD_AFIS", sequenceName="SEQ_ID_RECORD_AFIS")
  @Column(name = "ID", unique = true, nullable = false)
  var Id: java.lang.Integer = _
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
