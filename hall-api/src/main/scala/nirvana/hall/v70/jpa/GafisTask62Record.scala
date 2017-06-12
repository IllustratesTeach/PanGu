package nirvana.hall.v70.jpa

import java.util.Date
import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by win-20161010 on 2017/6/12.
  */
object GafisTask62Record extends ActiveRecordInstance[GafisTask62Record]


@Entity
@Table(name = "Gafis_Task62Record")
class GafisTask62Record extends ActiveRecord {
  @Id
  @Column(name = "UUID", unique = true, nullable = false, length = 32)
  var id: java.lang.String = _
  @Column(name = "QUERYID", length = 32)
  var queryid: java.lang.String = _
  @Column(name = "ORASID", length = 32)
  var orasid: java.lang.String = _
  @Column(name = "ISSYNCCANDLIST", length = 1)
  var isSyncCandList: java.lang.String = _
  @Column(name = "CREATETIME")
  var createtime: java.util.Date = _
  @Column(name = "UPDATETIME")
  var updatetime: java.util.Date = _

  def this(id: java.lang.String, queryid: java.lang.String
           , orasid: java.lang.String, isSyncCandList: java.lang.String) {
    this()
    this.id = id
    this.queryid = queryid
    this.orasid = orasid
    this.createtime = new Date
    this.isSyncCandList = isSyncCandList
  }
}
