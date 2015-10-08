package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class TransferMethod(
  id: Int,
  userId: Int,
  createdTime: Option[Int] = None,
  updatedTime: Option[Int] = None,
  name: Option[String] = None) {

  def save()(implicit session: DBSession = TransferMethod.autoSession): TransferMethod = TransferMethod.save(this)(session)

  def destroy()(implicit session: DBSession = TransferMethod.autoSession): Unit = TransferMethod.destroy(this)(session)

}


object TransferMethod extends SQLSyntaxSupport[TransferMethod] {

  override val tableName = "TRANSFER_METHOD"

  override val columns = Seq("ID", "USER_ID", "CREATED_TIME", "UPDATED_TIME", "NAME")

  def apply(tm: SyntaxProvider[TransferMethod])(rs: WrappedResultSet): TransferMethod = apply(tm.resultName)(rs)
  def apply(tm: ResultName[TransferMethod])(rs: WrappedResultSet): TransferMethod = new TransferMethod(
    id = rs.get(tm.id),
    userId = rs.get(tm.userId),
    createdTime = rs.get(tm.createdTime),
    updatedTime = rs.get(tm.updatedTime),
    name = rs.get(tm.name)
  )

  val tm = TransferMethod.syntax("tm")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TransferMethod] = {
    withSQL {
      select.from(TransferMethod as tm).where.eq(tm.id, id)
    }.map(TransferMethod(tm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TransferMethod] = {
    withSQL(select.from(TransferMethod as tm)).map(TransferMethod(tm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TransferMethod as tm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TransferMethod] = {
    withSQL {
      select.from(TransferMethod as tm).where.append(where)
    }.map(TransferMethod(tm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TransferMethod] = {
    withSQL {
      select.from(TransferMethod as tm).where.append(where)
    }.map(TransferMethod(tm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TransferMethod as tm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    createdTime: Option[Int] = None,
    updatedTime: Option[Int] = None,
    name: Option[String] = None)(implicit session: DBSession = autoSession): TransferMethod = {
    val generatedKey = withSQL {
      insert.into(TransferMethod).columns(
        column.userId,
        column.createdTime,
        column.updatedTime,
        column.name
      ).values(
        userId,
        createdTime,
        updatedTime,
        name
      )
    }.updateAndReturnGeneratedKey.apply()

    TransferMethod(
      id = generatedKey.toInt,
      userId = userId,
      createdTime = createdTime,
      updatedTime = updatedTime,
      name = name)
  }

  def save(entity: TransferMethod)(implicit session: DBSession = autoSession): TransferMethod = {
    withSQL {
      update(TransferMethod).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.createdTime -> entity.createdTime,
        column.updatedTime -> entity.updatedTime,
        column.name -> entity.name
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TransferMethod)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TransferMethod).where.eq(column.id, entity.id) }.update.apply()
  }

}
