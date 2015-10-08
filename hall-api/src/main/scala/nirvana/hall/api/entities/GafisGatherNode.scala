package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherNode(
  pkId: String,
  nodeCode: Option[String] = None,
  nodeName: Option[String] = None,
  nodeRequest: String,
  deleteFlag: Option[Long] = None,
  createUserId: String,
  createDatetime: DateTime,
  updateUserId: Option[String] = None,
  updateDatetime: Option[DateTime] = None,
  nodeImg: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherNode.autoSession): GafisGatherNode = GafisGatherNode.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherNode.autoSession): Unit = GafisGatherNode.destroy(this)(session)

}


object GafisGatherNode extends SQLSyntaxSupport[GafisGatherNode] {

  override val tableName = "GAFIS_GATHER_NODE"

  override val columns = Seq("PK_ID", "NODE_CODE", "NODE_NAME", "NODE_REQUEST", "DELETE_FLAG", "CREATE_USER_ID", "CREATE_DATETIME", "UPDATE_USER_ID", "UPDATE_DATETIME", "NODE_IMG")

  def apply(ggn: SyntaxProvider[GafisGatherNode])(rs: WrappedResultSet): GafisGatherNode = apply(ggn.resultName)(rs)
  def apply(ggn: ResultName[GafisGatherNode])(rs: WrappedResultSet): GafisGatherNode = new GafisGatherNode(
    pkId = rs.get(ggn.pkId),
    nodeCode = rs.get(ggn.nodeCode),
    nodeName = rs.get(ggn.nodeName),
    nodeRequest = rs.get(ggn.nodeRequest),
    deleteFlag = rs.get(ggn.deleteFlag),
    createUserId = rs.get(ggn.createUserId),
    createDatetime = rs.get(ggn.createDatetime),
    updateUserId = rs.get(ggn.updateUserId),
    updateDatetime = rs.get(ggn.updateDatetime),
    nodeImg = rs.get(ggn.nodeImg)
  )

  val ggn = GafisGatherNode.syntax("ggn")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, nodeCode: Option[String], nodeName: Option[String], nodeRequest: String, deleteFlag: Option[Long], createUserId: String, createDatetime: DateTime, updateUserId: Option[String], updateDatetime: Option[DateTime], nodeImg: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherNode] = {
    withSQL {
      select.from(GafisGatherNode as ggn).where.eq(ggn.pkId, pkId).and.eq(ggn.nodeCode, nodeCode).and.eq(ggn.nodeName, nodeName).and.eq(ggn.nodeRequest, nodeRequest).and.eq(ggn.deleteFlag, deleteFlag).and.eq(ggn.createUserId, createUserId).and.eq(ggn.createDatetime, createDatetime).and.eq(ggn.updateUserId, updateUserId).and.eq(ggn.updateDatetime, updateDatetime).and.eq(ggn.nodeImg, nodeImg)
    }.map(GafisGatherNode(ggn.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherNode] = {
    withSQL(select.from(GafisGatherNode as ggn)).map(GafisGatherNode(ggn.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherNode as ggn)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherNode] = {
    withSQL {
      select.from(GafisGatherNode as ggn).where.append(where)
    }.map(GafisGatherNode(ggn.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherNode] = {
    withSQL {
      select.from(GafisGatherNode as ggn).where.append(where)
    }.map(GafisGatherNode(ggn.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherNode as ggn).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    nodeCode: Option[String] = None,
    nodeName: Option[String] = None,
    nodeRequest: String,
    deleteFlag: Option[Long] = None,
    createUserId: String,
    createDatetime: DateTime,
    updateUserId: Option[String] = None,
    updateDatetime: Option[DateTime] = None,
    nodeImg: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherNode = {
    withSQL {
      insert.into(GafisGatherNode).columns(
        column.pkId,
        column.nodeCode,
        column.nodeName,
        column.nodeRequest,
        column.deleteFlag,
        column.createUserId,
        column.createDatetime,
        column.updateUserId,
        column.updateDatetime,
        column.nodeImg
      ).values(
        pkId,
        nodeCode,
        nodeName,
        nodeRequest,
        deleteFlag,
        createUserId,
        createDatetime,
        updateUserId,
        updateDatetime,
        nodeImg
      )
    }.update.apply()

    GafisGatherNode(
      pkId = pkId,
      nodeCode = nodeCode,
      nodeName = nodeName,
      nodeRequest = nodeRequest,
      deleteFlag = deleteFlag,
      createUserId = createUserId,
      createDatetime = createDatetime,
      updateUserId = updateUserId,
      updateDatetime = updateDatetime,
      nodeImg = nodeImg)
  }

  def save(entity: GafisGatherNode)(implicit session: DBSession = autoSession): GafisGatherNode = {
    withSQL {
      update(GafisGatherNode).set(
        column.pkId -> entity.pkId,
        column.nodeCode -> entity.nodeCode,
        column.nodeName -> entity.nodeName,
        column.nodeRequest -> entity.nodeRequest,
        column.deleteFlag -> entity.deleteFlag,
        column.createUserId -> entity.createUserId,
        column.createDatetime -> entity.createDatetime,
        column.updateUserId -> entity.updateUserId,
        column.updateDatetime -> entity.updateDatetime,
        column.nodeImg -> entity.nodeImg
      ).where.eq(column.pkId, entity.pkId).and.eq(column.nodeCode, entity.nodeCode).and.eq(column.nodeName, entity.nodeName).and.eq(column.nodeRequest, entity.nodeRequest).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.nodeImg, entity.nodeImg)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherNode)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherNode).where.eq(column.pkId, entity.pkId).and.eq(column.nodeCode, entity.nodeCode).and.eq(column.nodeName, entity.nodeName).and.eq(column.nodeRequest, entity.nodeRequest).and.eq(column.deleteFlag, entity.deleteFlag).and.eq(column.createUserId, entity.createUserId).and.eq(column.createDatetime, entity.createDatetime).and.eq(column.updateUserId, entity.updateUserId).and.eq(column.updateDatetime, entity.updateDatetime).and.eq(column.nodeImg, entity.nodeImg) }.update.apply()
  }

}
