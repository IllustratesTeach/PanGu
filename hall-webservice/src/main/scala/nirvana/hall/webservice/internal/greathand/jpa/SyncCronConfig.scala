package nirvana.hall.webservice.internal.greathand.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}


object SyncCronConfig extends ActiveRecordInstance[SyncCronConfig]

@Entity
@Table(name="Gafis_Cron_Config")
class SyncCronConfig extends ActiveRecord{
  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "start_time")
  var startTime: java.util.Date = _
  @Column(name = "TYP", length = 32)
  var typ: java.lang.String = _
  @Column(name = "update_time")
  var inputTime: java.util.Date = _
  @Column(name = "delete_flag")
  var deleteFlag:java.lang.String =_

  def this(pkId:java.lang.String){
    this()
    this.pkId = pkId
  }

  def this(pkId:java.lang.String
           ,startTime:java.util.Date
           ,typ:java.lang.String
           ,inputTime:java.util.Date
          ,deleteFlag:java.lang.String){
    this()
    this.pkId = pkId
    this.startTime = startTime
    this.typ = typ
    this.inputTime = inputTime
    this.deleteFlag = deleteFlag
  }
}
