package nirvana.hall.v70.internal.txserver.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2019/3/4
  */
object SevenReceiveRecord extends ActiveRecordInstance[SevenReceiveRecord]

@Entity
@Table(name="SEVEN_RECEIVE_RECORD")
class SevenReceiveRecord extends ActiveRecord{
  @Id
  @Column(name = "id", nullable=false, length=32)
  var id:java.lang.String = _
  @Column(name = "station_code")
  var stationCode :java.lang.String = _
  @Column(name = "flag")
  var flag:java.lang.Integer = _
  @Column(name = "status")
  var status:java.lang.String = _
  @Column(name = "cardid")
  var cardId:java.lang.String = _
  @Column(name = "input_time")
  var inputTime:java.util.Date =  _

  def this(id:java.lang.String) {
    this()
    this.id = id
  }

  def this(id:java.lang.String
           ,stationCode:java.lang.String
           ,flag:java.lang.Integer
           ,status:java.lang.String
           ,cardId:java.lang.String
           ,inputTime:java.util.Date){
    this()
    this.id = id
    this.stationCode = stationCode
    this.flag = flag
    this.status = status
    this.cardId = cardId
    this.inputTime = inputTime
  }
}
