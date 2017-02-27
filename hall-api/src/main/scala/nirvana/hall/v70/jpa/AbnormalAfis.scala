package nirvana.hall.v70.jpa

import javax.persistence.{Column, SequenceGenerator, _}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by yuchen on 2017/2/6.
  */
object AbnormalAfis extends ActiveRecordInstance[AbnormalAfis]

@Entity
@Table(name = "ABNORMAL_AFIS"
)
class AbnormalAfis extends ActiveRecord {

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_ID_ABNORMAL_AFIS")
  @SequenceGenerator(name="SEQ_ID_ABNORMAL_AFIS", sequenceName="SEQ_ID_ABNORMAL_AFIS")
  @Column(name = "ID", unique = true, nullable = false)
  var Id: java.lang.Integer = _
  @Column(name = "UUID")
  var uuid: java.lang.String = _
  @Column(name = "CARD_ID")
  var card_Id: java.lang.String = _
  @Column(name = "INSERT_DATE")
  var insert_date: java.util.Date = _
  @Column(name = "ABN_CONTENT")
  var abn_content: java.lang.String = _

  def this(uuid: java.lang.String, card_Id: java.lang.String
           , insert_date: java.util.Date, abn_content: java.lang.String) {
    this()
    this.uuid = uuid
    this.card_Id = card_Id
    this.insert_date = insert_date
    this.abn_content = abn_content
  }


}
