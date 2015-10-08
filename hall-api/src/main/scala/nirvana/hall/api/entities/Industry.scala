package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class Industry(
  id: Int,
  name: String) {

  def save()(implicit session: DBSession = Industry.autoSession): Industry = Industry.save(this)(session)

  def destroy()(implicit session: DBSession = Industry.autoSession): Unit = Industry.destroy(this)(session)

}


object Industry extends SQLSyntaxSupport[Industry] {

  override val tableName = "INDUSTRY"

  override val columns = Seq("ID", "NAME")

  def apply(i: SyntaxProvider[Industry])(rs: WrappedResultSet): Industry = apply(i.resultName)(rs)
  def apply(i: ResultName[Industry])(rs: WrappedResultSet): Industry = new Industry(
    id = rs.get(i.id),
    name = rs.get(i.name)
  )

  val i = Industry.syntax("i")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Industry] = {
    withSQL {
      select.from(Industry as i).where.eq(i.id, id)
    }.map(Industry(i.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Industry] = {
    withSQL(select.from(Industry as i)).map(Industry(i.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Industry as i)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Industry] = {
    withSQL {
      select.from(Industry as i).where.append(where)
    }.map(Industry(i.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Industry] = {
    withSQL {
      select.from(Industry as i).where.append(where)
    }.map(Industry(i.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Industry as i).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String)(implicit session: DBSession = autoSession): Industry = {
    val generatedKey = withSQL {
      insert.into(Industry).columns(
        column.name
      ).values(
        name
      )
    }.updateAndReturnGeneratedKey.apply()

    Industry(
      id = generatedKey.toInt,
      name = name)
  }

  def save(entity: Industry)(implicit session: DBSession = autoSession): Industry = {
    withSQL {
      update(Industry).set(
        column.id -> entity.id,
        column.name -> entity.name
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Industry)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Industry).where.eq(column.id, entity.id) }.update.apply()
  }

}
