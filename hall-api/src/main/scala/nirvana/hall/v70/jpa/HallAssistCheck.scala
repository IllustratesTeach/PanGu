package nirvana.hall.v70.jpa

import java.util.Date
import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by yuchen on 2017/4/21.
  */
object HallAssistCheck extends ActiveRecordInstance[HallAssistCheck]


@Entity
@Table(name = "HALL_ASSISTCHECK")
class HallAssistCheck extends ActiveRecord {
  @Id
  @Column(name = "ID", unique = true, nullable = false, length = 32)
  var id: java.lang.String = _
  @Column(name = "QUERYID", length = 32)
  var queryid: java.lang.String = _
  @Column(name = "ORASID",length = 32)
  var orasid: java.lang.String = _
  @Column(name = "CASEID",length = 32)
  var caseid: java.lang.String = _
  @Column(name = "PERSONID",length = 32)
  var personid: java.lang.String = _
  @Column(name = "CREATETIME")
  var createtime: java.util.Date = _
  @Column(name = "SOURCE",length = 4)
  var source: java.lang.String = _
  @Column(name = "STATUS")
  var status: java.lang.Integer = _

  def this(id: java.lang.String, query_id: java.lang.String
           , ora_sid: java.lang.String, case_id: java.lang.String
           , person_id: java.lang.String, source: java.lang.String, status:java.lang.Integer) {
    this()
    this.id = id
    this.queryid = query_id
    this.orasid = ora_sid
    this.caseid = case_id
    this.personid = person_id
    this.createtime = new Date
    this.source = source
    this.status = status
  }

}
