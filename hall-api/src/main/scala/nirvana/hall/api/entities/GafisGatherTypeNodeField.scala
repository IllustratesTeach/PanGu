package nirvana.hall.api.entities

import scalikejdbc._

case class GafisGatherTypeNodeField(
  pkId: String,
  typeId: Option[String] = None,
  nodeId: Option[String] = None,
  fieldId: Option[String] = None,
  required: Option[Short] = None,
  departId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherTypeNodeField.autoSession): GafisGatherTypeNodeField = GafisGatherTypeNodeField.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherTypeNodeField.autoSession): Unit = GafisGatherTypeNodeField.destroy(this)(session)

}


object GafisGatherTypeNodeField extends SQLSyntaxSupport[GafisGatherTypeNodeField] {

  override val tableName = "GAFIS_GATHER_TYPE_NODE_FIELD"

  override val columns = Seq("PK_ID", "TYPE_ID", "NODE_ID", "FIELD_ID", "REQUIRED", "DEPART_ID")

  def apply(ggtnf: SyntaxProvider[GafisGatherTypeNodeField])(rs: WrappedResultSet): GafisGatherTypeNodeField = apply(ggtnf.resultName)(rs)
  def apply(ggtnf: ResultName[GafisGatherTypeNodeField])(rs: WrappedResultSet): GafisGatherTypeNodeField = new GafisGatherTypeNodeField(
    pkId = rs.get(ggtnf.pkId),
    typeId = rs.get(ggtnf.typeId),
    nodeId = rs.get(ggtnf.nodeId),
    fieldId = rs.get(ggtnf.fieldId),
    required = rs.get(ggtnf.required),
    departId = rs.get(ggtnf.departId)
  )

  val ggtnf = GafisGatherTypeNodeField.syntax("ggtnf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisGatherTypeNodeField] = {
    withSQL {
      select.from(GafisGatherTypeNodeField as ggtnf).where.eq(ggtnf.pkId, pkId)
    }.map(GafisGatherTypeNodeField(ggtnf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherTypeNodeField] = {
    withSQL(select.from(GafisGatherTypeNodeField as ggtnf)).map(GafisGatherTypeNodeField(ggtnf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherTypeNodeField as ggtnf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherTypeNodeField] = {
    withSQL {
      select.from(GafisGatherTypeNodeField as ggtnf).where.append(where)
    }.map(GafisGatherTypeNodeField(ggtnf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherTypeNodeField] = {
    withSQL {
      select.from(GafisGatherTypeNodeField as ggtnf).where.append(where)
    }.map(GafisGatherTypeNodeField(ggtnf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherTypeNodeField as ggtnf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    typeId: Option[String] = None,
    nodeId: Option[String] = None,
    fieldId: Option[String] = None,
    required: Option[Short] = None,
    departId: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherTypeNodeField = {
    withSQL {
      insert.into(GafisGatherTypeNodeField).columns(
        column.pkId,
        column.typeId,
        column.nodeId,
        column.fieldId,
        column.required,
        column.departId
      ).values(
        pkId,
        typeId,
        nodeId,
        fieldId,
        required,
        departId
      )
    }.update.apply()

    GafisGatherTypeNodeField(
      pkId = pkId,
      typeId = typeId,
      nodeId = nodeId,
      fieldId = fieldId,
      required = required,
      departId = departId)
  }

  def save(entity: GafisGatherTypeNodeField)(implicit session: DBSession = autoSession): GafisGatherTypeNodeField = {
    withSQL {
      update(GafisGatherTypeNodeField).set(
        column.pkId -> entity.pkId,
        column.typeId -> entity.typeId,
        column.nodeId -> entity.nodeId,
        column.fieldId -> entity.fieldId,
        column.required -> entity.required,
        column.departId -> entity.departId
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherTypeNodeField)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherTypeNodeField).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
