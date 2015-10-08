package nirvana.hall.api.entities

import scalikejdbc._

case class GafisGatherFieldset(
  pkId: String,
  nodeId: Option[String] = None,
  fieldName: Option[String] = None,
  field: Option[String] = None,
  rule: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherFieldset.autoSession): GafisGatherFieldset = GafisGatherFieldset.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherFieldset.autoSession): Unit = GafisGatherFieldset.destroy(this)(session)

}


object GafisGatherFieldset extends SQLSyntaxSupport[GafisGatherFieldset] {

  override val tableName = "GAFIS_GATHER_FIELDSET"

  override val columns = Seq("PK_ID", "NODE_ID", "FIELD_NAME", "FIELD", "RULE")

  def apply(ggf: SyntaxProvider[GafisGatherFieldset])(rs: WrappedResultSet): GafisGatherFieldset = apply(ggf.resultName)(rs)
  def apply(ggf: ResultName[GafisGatherFieldset])(rs: WrappedResultSet): GafisGatherFieldset = new GafisGatherFieldset(
    pkId = rs.get(ggf.pkId),
    nodeId = rs.get(ggf.nodeId),
    fieldName = rs.get(ggf.fieldName),
    field = rs.get(ggf.selectDynamic("field")),
    rule = rs.get(ggf.rule)
  )

  val ggf = GafisGatherFieldset.syntax("ggf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, nodeId: Option[String], fieldName: Option[String], field: Option[String], rule: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherFieldset] = {
    withSQL {
      select.from(GafisGatherFieldset as ggf).where.eq(ggf.pkId, pkId).and.eq(ggf.nodeId, nodeId).and.eq(ggf.fieldName, fieldName).and.eq(ggf.selectDynamic("field"), field).and.eq(ggf.rule, rule)
    }.map(GafisGatherFieldset(ggf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherFieldset] = {
    withSQL(select.from(GafisGatherFieldset as ggf)).map(GafisGatherFieldset(ggf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherFieldset as ggf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherFieldset] = {
    withSQL {
      select.from(GafisGatherFieldset as ggf).where.append(where)
    }.map(GafisGatherFieldset(ggf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherFieldset] = {
    withSQL {
      select.from(GafisGatherFieldset as ggf).where.append(where)
    }.map(GafisGatherFieldset(ggf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherFieldset as ggf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    nodeId: Option[String] = None,
    fieldName: Option[String] = None,
    field: Option[String] = None,
    rule: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherFieldset = {
    withSQL {
      insert.into(GafisGatherFieldset).columns(
        column.pkId,
        column.nodeId,
        column.fieldName,
        column.selectDynamic("field"),
        column.rule
      ).values(
        pkId,
        nodeId,
        fieldName,
        field,
        rule
      )
    }.update.apply()

    GafisGatherFieldset(
      pkId = pkId,
      nodeId = nodeId,
      fieldName = fieldName,
      field = field,
      rule = rule)
  }

  def save(entity: GafisGatherFieldset)(implicit session: DBSession = autoSession): GafisGatherFieldset = {
    withSQL {
      update(GafisGatherFieldset).set(
        column.pkId -> entity.pkId,
        column.nodeId -> entity.nodeId,
        column.fieldName -> entity.fieldName,
        column.selectDynamic("field") -> entity.field,
        column.rule -> entity.rule
      ).where.eq(column.pkId, entity.pkId).and.eq(column.nodeId, entity.nodeId).and.eq(column.fieldName, entity.fieldName).and.eq(column.selectDynamic("field"), entity.field).and.eq(column.rule, entity.rule)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherFieldset)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherFieldset).where.eq(column.pkId, entity.pkId).and.eq(column.nodeId, entity.nodeId).and.eq(column.fieldName, entity.fieldName).and.eq(column.selectDynamic("field"), entity.field).and.eq(column.rule, entity.rule) }.update.apply()
  }

}
