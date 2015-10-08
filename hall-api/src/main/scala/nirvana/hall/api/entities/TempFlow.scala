package nirvana.hall.api.entities

import scalikejdbc._

case class TempFlow(
  personId: Option[String] = None,
  finish: Option[Int] = None,
  total: Option[Int] = None) {

  def save()(implicit session: DBSession = TempFlow.autoSession): TempFlow = TempFlow.save(this)(session)

  def destroy()(implicit session: DBSession = TempFlow.autoSession): Unit = TempFlow.destroy(this)(session)

}


object TempFlow extends SQLSyntaxSupport[TempFlow] {

  override val tableName = "TEMP_FLOW"

  override val columns = Seq("PERSON_ID", "FINISH", "TOTAL")

  def apply(tf: SyntaxProvider[TempFlow])(rs: WrappedResultSet): TempFlow = apply(tf.resultName)(rs)
  def apply(tf: ResultName[TempFlow])(rs: WrappedResultSet): TempFlow = new TempFlow(
    personId = rs.get(tf.personId),
    finish = rs.get(tf.finish),
    total = rs.get(tf.total)
  )

  val tf = TempFlow.syntax("tf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(personId: Option[String], finish: Option[Int], total: Option[Int])(implicit session: DBSession = autoSession): Option[TempFlow] = {
    withSQL {
      select.from(TempFlow as tf).where.eq(tf.personId, personId).and.eq(tf.finish, finish).and.eq(tf.total, total)
    }.map(TempFlow(tf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TempFlow] = {
    withSQL(select.from(TempFlow as tf)).map(TempFlow(tf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TempFlow as tf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TempFlow] = {
    withSQL {
      select.from(TempFlow as tf).where.append(where)
    }.map(TempFlow(tf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TempFlow] = {
    withSQL {
      select.from(TempFlow as tf).where.append(where)
    }.map(TempFlow(tf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TempFlow as tf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    personId: Option[String] = None,
    finish: Option[Int] = None,
    total: Option[Int] = None)(implicit session: DBSession = autoSession): TempFlow = {
    withSQL {
      insert.into(TempFlow).columns(
        column.personId,
        column.finish,
        column.total
      ).values(
        personId,
        finish,
        total
      )
    }.update.apply()

    TempFlow(
      personId = personId,
      finish = finish,
      total = total)
  }

  def save(entity: TempFlow)(implicit session: DBSession = autoSession): TempFlow = {
    withSQL {
      update(TempFlow).set(
        column.personId -> entity.personId,
        column.finish -> entity.finish,
        column.total -> entity.total
      ).where.eq(column.personId, entity.personId).and.eq(column.finish, entity.finish).and.eq(column.total, entity.total)
    }.update.apply()
    entity
  }

  def destroy(entity: TempFlow)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(TempFlow).where.eq(column.personId, entity.personId).and.eq(column.finish, entity.finish).and.eq(column.total, entity.total) }.update.apply()
  }

}
