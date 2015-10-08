package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class OnlineUser(
  login: String,
  loginTime: Int,
  latestTime: Int,
  token: String) {

  def save()(implicit session: DBSession = OnlineUser.autoSession): OnlineUser = OnlineUser.save(this)(session)

  def destroy()(implicit session: DBSession = OnlineUser.autoSession): Unit = OnlineUser.destroy(this)(session)

}


object OnlineUser extends SQLSyntaxSupport[OnlineUser] {

  override val tableName = "ONLINE_USER"

  override val columns = Seq("LOGIN", "LOGIN_TIME", "LATEST_TIME", "TOKEN")

  def apply(ou: SyntaxProvider[OnlineUser])(rs: WrappedResultSet): OnlineUser = apply(ou.resultName)(rs)
  def apply(ou: ResultName[OnlineUser])(rs: WrappedResultSet): OnlineUser = new OnlineUser(
    login = rs.get(ou.login),
    loginTime = rs.get(ou.loginTime),
    latestTime = rs.get(ou.latestTime),
    token = rs.get(ou.token)
  )

  val ou = OnlineUser.syntax("ou")

 override def autoSession = AutoSpringDataSourceSession()

  def find(login: String, loginTime: Int, latestTime: Int, token: String)(implicit session: DBSession = autoSession): Option[OnlineUser] = {
    withSQL {
      select.from(OnlineUser as ou).where.eq(ou.login, login).and.eq(ou.loginTime, loginTime).and.eq(ou.latestTime, latestTime).and.eq(ou.token, token)
    }.map(OnlineUser(ou.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[OnlineUser] = {
    withSQL(select.from(OnlineUser as ou)).map(OnlineUser(ou.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(OnlineUser as ou)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[OnlineUser] = {
    withSQL {
      select.from(OnlineUser as ou).where.append(where)
    }.map(OnlineUser(ou.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[OnlineUser] = {
    withSQL {
      select.from(OnlineUser as ou).where.append(where)
    }.map(OnlineUser(ou.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(OnlineUser as ou).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    login: String,
    loginTime: Int,
    latestTime: Int,
    token: String)(implicit session: DBSession = autoSession): OnlineUser = {
    withSQL {
      insert.into(OnlineUser).columns(
        column.login,
        column.loginTime,
        column.latestTime,
        column.token
      ).values(
        login,
        loginTime,
        latestTime,
        token
      )
    }.update.apply()

    OnlineUser(
      login = login,
      loginTime = loginTime,
      latestTime = latestTime,
      token = token)
  }

  def save(entity: OnlineUser)(implicit session: DBSession = autoSession): OnlineUser = {
    withSQL {
      update(OnlineUser).set(
        column.login -> entity.login,
        column.loginTime -> entity.loginTime,
        column.latestTime -> entity.latestTime,
        column.token -> entity.token
      ).where.eq(column.login, entity.login).and.eq(column.loginTime, entity.loginTime).and.eq(column.latestTime, entity.latestTime).and.eq(column.token, entity.token)
    }.update.apply()
    entity
  }

  def destroy(entity: OnlineUser)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(OnlineUser).where.eq(column.login, entity.login).and.eq(column.loginTime, entity.loginTime).and.eq(column.latestTime, entity.latestTime).and.eq(column.token, entity.token) }.update.apply()
  }

}
