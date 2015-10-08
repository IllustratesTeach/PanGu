package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisExtractMntTask(
  pkId: String,
  dataId: Option[String] = None,
  tlFlag: Option[String] = None,
  fpFlag: Option[String] = None,
  isExtractRidge: Option[String] = None,
  status: Option[String] = None,
  failMessage: Option[String] = None,
  inputtime: Option[DateTime] = None,
  finishtime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisExtractMntTask.autoSession): GafisExtractMntTask = GafisExtractMntTask.save(this)(session)

  def destroy()(implicit session: DBSession = GafisExtractMntTask.autoSession): Unit = GafisExtractMntTask.destroy(this)(session)

}


object GafisExtractMntTask extends SQLSyntaxSupport[GafisExtractMntTask] {

  override val tableName = "GAFIS_EXTRACT_MNT_TASK"

  override val columns = Seq("PK_ID", "DATA_ID", "TL_FLAG", "FP_FLAG", "IS_EXTRACT_RIDGE", "STATUS", "FAIL_MESSAGE", "INPUTTIME", "FINISHTIME")

  def apply(gemt: SyntaxProvider[GafisExtractMntTask])(rs: WrappedResultSet): GafisExtractMntTask = apply(gemt.resultName)(rs)
  def apply(gemt: ResultName[GafisExtractMntTask])(rs: WrappedResultSet): GafisExtractMntTask = new GafisExtractMntTask(
    pkId = rs.get(gemt.pkId),
    dataId = rs.get(gemt.dataId),
    tlFlag = rs.get(gemt.tlFlag),
    fpFlag = rs.get(gemt.fpFlag),
    isExtractRidge = rs.get(gemt.isExtractRidge),
    status = rs.get(gemt.status),
    failMessage = rs.get(gemt.failMessage),
    inputtime = rs.get(gemt.inputtime),
    finishtime = rs.get(gemt.finishtime)
  )

  val gemt = GafisExtractMntTask.syntax("gemt")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisExtractMntTask] = {
    withSQL {
      select.from(GafisExtractMntTask as gemt).where.eq(gemt.pkId, pkId)
    }.map(GafisExtractMntTask(gemt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisExtractMntTask] = {
    withSQL(select.from(GafisExtractMntTask as gemt)).map(GafisExtractMntTask(gemt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisExtractMntTask as gemt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisExtractMntTask] = {
    withSQL {
      select.from(GafisExtractMntTask as gemt).where.append(where)
    }.map(GafisExtractMntTask(gemt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisExtractMntTask] = {
    withSQL {
      select.from(GafisExtractMntTask as gemt).where.append(where)
    }.map(GafisExtractMntTask(gemt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisExtractMntTask as gemt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    dataId: Option[String] = None,
    tlFlag: Option[String] = None,
    fpFlag: Option[String] = None,
    isExtractRidge: Option[String] = None,
    status: Option[String] = None,
    failMessage: Option[String] = None,
    inputtime: Option[DateTime] = None,
    finishtime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisExtractMntTask = {
    withSQL {
      insert.into(GafisExtractMntTask).columns(
        column.pkId,
        column.dataId,
        column.tlFlag,
        column.fpFlag,
        column.isExtractRidge,
        column.status,
        column.failMessage,
        column.inputtime,
        column.finishtime
      ).values(
        pkId,
        dataId,
        tlFlag,
        fpFlag,
        isExtractRidge,
        status,
        failMessage,
        inputtime,
        finishtime
      )
    }.update.apply()

    GafisExtractMntTask(
      pkId = pkId,
      dataId = dataId,
      tlFlag = tlFlag,
      fpFlag = fpFlag,
      isExtractRidge = isExtractRidge,
      status = status,
      failMessage = failMessage,
      inputtime = inputtime,
      finishtime = finishtime)
  }

  def save(entity: GafisExtractMntTask)(implicit session: DBSession = autoSession): GafisExtractMntTask = {
    withSQL {
      update(GafisExtractMntTask).set(
        column.pkId -> entity.pkId,
        column.dataId -> entity.dataId,
        column.tlFlag -> entity.tlFlag,
        column.fpFlag -> entity.fpFlag,
        column.isExtractRidge -> entity.isExtractRidge,
        column.status -> entity.status,
        column.failMessage -> entity.failMessage,
        column.inputtime -> entity.inputtime,
        column.finishtime -> entity.finishtime
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisExtractMntTask)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisExtractMntTask).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}
