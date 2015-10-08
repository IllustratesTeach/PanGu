package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class User(
  id: Int,
  login: String,
  password: String,
  corporateName: String,
  boss: String,
  linkMan: String,
  industryId: Option[Int] = None,
  createdTime: Option[Int] = None,
  updatedTime: Option[Int] = None) {

  def save()(implicit session: DBSession = User.autoSession): User = User.save(this)(session)

  def destroy()(implicit session: DBSession = User.autoSession): Unit = User.destroy(this)(session)

}


object User extends SQLSyntaxSupport[User] {

  override val tableName = "USER"

  override val columns = Seq("ID", "LOGIN", "PASSWORD", "CORPORATE_NAME", "BOSS", "LINK_MAN", "INDUSTRY_ID", "CREATED_TIME", "UPDATED_TIME")

  def apply(u: SyntaxProvider[User])(rs: WrappedResultSet): User = apply(u.resultName)(rs)
  def apply(u: ResultName[User])(rs: WrappedResultSet): User = new User(
    id = rs.get(u.id),
    login = rs.get(u.login),
    password = rs.get(u.password),
    corporateName = rs.get(u.corporateName),
    boss = rs.get(u.boss),
    linkMan = rs.get(u.linkMan),
    industryId = rs.get(u.industryId),
    createdTime = rs.get(u.createdTime),
    updatedTime = rs.get(u.updatedTime)
  )

  val u = User.syntax("u")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.eq(u.id, id)
    }.map(User(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[User] = {
    withSQL(select.from(User as u)).map(User(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(User as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(User as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    login: String,
    password: String,
    corporateName: String,
    boss: String,
    linkMan: String,
    industryId: Option[Int] = None,
    createdTime: Option[Int] = None,
    updatedTime: Option[Int] = None)(implicit session: DBSession = autoSession): User = {
    val generatedKey = withSQL {
      insert.into(User).columns(
        column.login,
        column.password,
        column.corporateName,
        column.boss,
        column.linkMan,
        column.industryId,
        column.createdTime,
        column.updatedTime
      ).values(
        login,
        password,
        corporateName,
        boss,
        linkMan,
        industryId,
        createdTime,
        updatedTime
      )
    }.updateAndReturnGeneratedKey.apply()

    User(
      id = generatedKey.toInt,
      login = login,
      password = password,
      corporateName = corporateName,
      boss = boss,
      linkMan = linkMan,
      industryId = industryId,
      createdTime = createdTime,
      updatedTime = updatedTime)
  }

  def save(entity: User)(implicit session: DBSession = autoSession): User = {
    withSQL {
      update(User).set(
        column.id -> entity.id,
        column.login -> entity.login,
        column.password -> entity.password,
        column.corporateName -> entity.corporateName,
        column.boss -> entity.boss,
        column.linkMan -> entity.linkMan,
        column.industryId -> entity.industryId,
        column.createdTime -> entity.createdTime,
        column.updatedTime -> entity.updatedTime
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: User)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(User).where.eq(column.id, entity.id) }.update.apply()
  }

}
