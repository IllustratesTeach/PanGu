package nirvana.hall.api.entities

import scalikejdbc._

case class Table214Inputpsn(
  inputpsn: Option[String] = None,
  personid: String,
  gatherTypeId: Option[String] = None) {

  def save()(implicit session: DBSession = Table214Inputpsn.autoSession): Table214Inputpsn = Table214Inputpsn.save(this)(session)

  def destroy()(implicit session: DBSession = Table214Inputpsn.autoSession): Unit = Table214Inputpsn.destroy(this)(session)

}


object Table214Inputpsn extends SQLSyntaxSupport[Table214Inputpsn] {

  override val tableName = "TABLE_214_INPUTPSN"

  override val columns = Seq("INPUTPSN", "PERSONID", "GATHER_TYPE_ID")

  def apply(ti: SyntaxProvider[Table214Inputpsn])(rs: WrappedResultSet): Table214Inputpsn = apply(ti.resultName)(rs)
  def apply(ti: ResultName[Table214Inputpsn])(rs: WrappedResultSet): Table214Inputpsn = new Table214Inputpsn(
    inputpsn = rs.get(ti.inputpsn),
    personid = rs.get(ti.personid),
    gatherTypeId = rs.get(ti.gatherTypeId)
  )

  val ti = Table214Inputpsn.syntax("ti")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(inputpsn: Option[String], personid: String, gatherTypeId: Option[String])(implicit session: DBSession = autoSession): Option[Table214Inputpsn] = {
    withSQL {
      select.from(Table214Inputpsn as ti).where.eq(ti.inputpsn, inputpsn).and.eq(ti.personid, personid).and.eq(ti.gatherTypeId, gatherTypeId)
    }.map(Table214Inputpsn(ti.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Table214Inputpsn] = {
    withSQL(select.from(Table214Inputpsn as ti)).map(Table214Inputpsn(ti.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Table214Inputpsn as ti)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Table214Inputpsn] = {
    withSQL {
      select.from(Table214Inputpsn as ti).where.append(where)
    }.map(Table214Inputpsn(ti.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Table214Inputpsn] = {
    withSQL {
      select.from(Table214Inputpsn as ti).where.append(where)
    }.map(Table214Inputpsn(ti.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Table214Inputpsn as ti).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    inputpsn: Option[String] = None,
    personid: String,
    gatherTypeId: Option[String] = None)(implicit session: DBSession = autoSession): Table214Inputpsn = {
    withSQL {
      insert.into(Table214Inputpsn).columns(
        column.inputpsn,
        column.personid,
        column.gatherTypeId
      ).values(
        inputpsn,
        personid,
        gatherTypeId
      )
    }.update.apply()

    Table214Inputpsn(
      inputpsn = inputpsn,
      personid = personid,
      gatherTypeId = gatherTypeId)
  }

  def save(entity: Table214Inputpsn)(implicit session: DBSession = autoSession): Table214Inputpsn = {
    withSQL {
      update(Table214Inputpsn).set(
        column.inputpsn -> entity.inputpsn,
        column.personid -> entity.personid,
        column.gatherTypeId -> entity.gatherTypeId
      ).where.eq(column.inputpsn, entity.inputpsn).and.eq(column.personid, entity.personid).and.eq(column.gatherTypeId, entity.gatherTypeId)
    }.update.apply()
    entity
  }

  def destroy(entity: Table214Inputpsn)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Table214Inputpsn).where.eq(column.inputpsn, entity.inputpsn).and.eq(column.personid, entity.personid).and.eq(column.gatherTypeId, entity.gatherTypeId) }.update.apply()
  }

}
