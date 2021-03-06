package nirvana.hall.v70.jpa

;
// Generated Jan 6, 2016 6:08:10 PM by hall orm generator 4.3.1.Final


import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance}

/**
 * GafisGatherTypeNodeField generated by hall orm 
 */
object GafisGatherTypeNodeField extends ActiveRecordInstance[GafisGatherTypeNodeField]

@Entity
@Table(name = "GAFIS_GATHER_TYPE_NODE_FIELD"
)
class GafisGatherTypeNodeField extends ActiveRecord {


  @Id
  @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
  var pkId: java.lang.String = _
  @Column(name = "TYPE_ID", length = 32)
  var typeId: java.lang.String = _
  @Column(name = "NODE_ID", length = 32)
  var nodeId: java.lang.String = _
  @Column(name = "FIELD_ID", length = 32)
  var fieldId: java.lang.String = _
  @Column(name = "REQUIRED")
  var required: java.lang.Short = _
  @Column(name = "DEPART_ID", length = 32)
  var departId: java.lang.String = _


  def this(pkId: java.lang.String) {
    this()
    this.pkId = pkId
  }

  def this(pkId: java.lang.String, typeId: java.lang.String, nodeId: java.lang.String, fieldId: java.lang.String, required: java.lang.Short, departId: java.lang.String) {
    this()
    this.pkId = pkId
    this.typeId = typeId
    this.nodeId = nodeId
    this.fieldId = fieldId
    this.required = required
    this.departId = departId
  }


}


