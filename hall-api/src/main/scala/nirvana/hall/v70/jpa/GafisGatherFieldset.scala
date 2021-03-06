package nirvana.hall.v70.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * GafisGatherFieldset generated by hall orm 
 */
object GafisGatherFieldset extends ActiveRecordInstance[GafisGatherFieldset]

@Entity
@Table(name = "GAFIS_GATHER_FIELDSET"
)
class GafisGatherFieldset extends ActiveRecord {


  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "NODE_ID", length = 32)
  var nodeId: java.lang.String = _
  @Column(name = "FIELD_NAME", length = 100)
  var fieldName: java.lang.String = _
  @Column(name = "FIELD", length = 32)
  var field: java.lang.String = _
  @Column(name = "RULE", length = 3000)
  var rule: java.lang.String = _


  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }

  def this(pkId: java.lang.String, nodeId: java.lang.String, fieldName: java.lang.String, field: java.lang.String, rule: java.lang.String) {
    this()
    this.pkId = pkId
    this.nodeId = nodeId
    this.fieldName = fieldName
    this.field = field
    this.rule = rule
  }


}


