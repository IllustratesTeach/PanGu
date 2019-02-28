package nirvana.hall.api.jpa.njtxserver

// Generated Feb 27, 2019 5:23:16 PM by Stark Activerecord generator 4.3.1.Final


import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.ActiveRecord
import stark.activerecord.services.ActiveRecordInstance

/**
  * TxLog generated by stark activerecord generator
  */
object TxLog extends ActiveRecordInstance[TxLog]

@Entity
@Table(name = "TX_LOG")
class TxLog extends ActiveRecord {

  @Id
  @Column(name = "id", nullable = false, length = 32)
  var id: java.lang.String = _
  @Column(name = "levels", nullable = false)
  var levels: Int = _
  @Column(name = "staion_code", nullable = false, length = 12)
  var staionCode: java.lang.String = _
  @Column(name = "card_no", length = 22)
  var cardNo: java.lang.String = _
  @Column(name = "work_type")
  var workType: java.lang.Integer = _
  @Column(name = "init_time", length = 10)
  var initTime: java.util.Date = _


  def this(id: java.lang.String) {
    this()
    this.id = id
  }

  def this(id: java.lang.String, levels: Int, staionCode: java.lang.String, cardNo: java.lang.String, workType: java.lang.Integer, initTime: java.util.Date) {
    this()
    this.id = id
    this.levels = levels
    this.staionCode = staionCode
    this.cardNo = cardNo
    this.workType = workType
    this.initTime = initTime
  }
}

