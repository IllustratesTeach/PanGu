package nirvana.hall.api.entities

import scalikejdbc._

case class GafisGatherTypeNode(
  pkId: String,
  typeId: String,
  nodeId: Option[String] = None,
  nodeOrd: Option[Long] = None,
  isSkip: Option[Short] = None,
  departId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherTypeNode.autoSession): GafisGatherTypeNode = GafisGatherTypeNode.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherTypeNode.autoSession): Unit = GafisGatherTypeNode.destroy(this)(session)

}


object GafisGatherTypeNode extends SQLSyntaxSupport[GafisGatherTypeNode] {

  override val tableName = "GAFIS_GATHER_TYPE_NODE"

  override val columns = Seq("PK_ID", "TYPE_ID", "NODE_ID", "NODE_ORD", "IS_SKIP", "DEPART_ID")

  def apply(ggtn: SyntaxProvider[GafisGatherTypeNode])(rs: WrappedResultSet): GafisGatherTypeNode = apply(ggtn.resultName)(rs)
  def apply(ggtn: ResultName[GafisGatherTypeNode])(rs: WrappedResultSet): GafisGatherTypeNode = new GafisGatherTypeNode(
    pkId = rs.get(ggtn.pkId),
    typeId = rs.get(ggtn.typeId),
    nodeId = rs.get(ggtn.nodeId),
    nodeOrd = rs.get(ggtn.nodeOrd),
    isSkip = rs.get(ggtn.isSkip),
    departId = rs.get(ggtn.departId)
  )

  val ggtn = GafisGatherTypeNode.syntax("ggtn")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisGatherTypeNode] = {
    withSQL {
      select.from(GafisGatherTypeNode as ggtn).where.eq(ggtn.pkId, pkId)
    }.map(GafisGatherTypeNode(ggtn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherTypeNode] = {
    withSQL(select.from(GafisGatherTypeNode as ggtn)).map(GafisGatherTypeNode(ggtn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherTypeNode as ggtn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherTypeNode] = {
    withSQL {
      select.from(GafisGatherTypeNode as ggtn).where.append(where)
    }.map(GafisGatherTypeNode(ggtn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherTypeNode] = {
    withSQL {
      select.from(GafisGatherTypeNode as ggtn).where.append(where)
    }.map(GafisGatherTypeNode(ggtn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherTypeNode as ggtn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    typeId: String,
    nodeId: Option[String] = None,
    nodeOrd: Option[Long] = None,
    isSkip: Option[Short] = None,
    departId: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherTypeNode = {
    withSQL {
      insert.into(GafisGatherTypeNode).columns(
        column.pkId,
        column.typeId,
        column.nodeId,
        column.nodeOrd,
        column.isSkip,
        column.departId
      ).values(
        pkId,
        typeId,
        nodeId,
        nodeOrd,
        isSkip,
        departId
      )
    }.update.apply()

    GafisGatherTypeNode(
      pkId = pkId,
      typeId = typeId,
      nodeId = nodeId,
      nodeOrd = nodeOrd,
      isSkip = isSkip,
      departId = departId)
  }

  def save(entity: GafisGatherTypeNode)(implicit session: DBSession = autoSession): GafisGatherTypeNode = {
    withSQL {
      update(GafisGatherTypeNode).set(
        column.pkId -> entity.pkId,
        column.typeId -> entity.typeId,
        column.nodeId -> entity.nodeId,
        column.nodeOrd -> entity.nodeOrd,
        column.isSkip -> entity.isSkip,
        column.departId -> entity.departId
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherTypeNode)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherTypeNode).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
