package nirvana.hall.v70.internal.adapter.common.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
  * Created by Administrator on 2017/4/1.
  */
object GafisLogicDbRule extends ActiveRecordInstance[GafisLogicDbRule]

@Entity
@Table(name = "GAFIS_LOGIC_DB_RULE"
)
class GafisLogicDbRule extends ActiveRecord {


  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "LOGIC_CODE", length = 32)
  var logicCode: java.lang.String = _
  @Column(name = "LOGIC_NAME", length = 32)
  var logicName: java.lang.String = _
  @Column(name = "LOGIC_CATEGORY", length = 1)
  var logicCategory: java.lang.String = _
  @Column(name = "LOGIC_REMARK", length = 60)
  var logicRemark: java.lang.String = _
  @Column(name = "LOGIC_RULE", length = 60)
  var logicRule: java.lang.String = _

  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }

  def this(pkId: java.lang.String, logicCode: java.lang.String, logicName: java.lang.String, logicCategory: java.lang.String, logicDeltag: java.lang.String, logicRemark: java.lang.String, logicRule: java.lang.String) {
    this()
    this.pkId = pkId
    this.logicCode = logicCode
    this.logicName = logicName
    this.logicCategory = logicCategory
    this.logicRemark = logicRemark
    this.logicRule = logicRule
  }
}


