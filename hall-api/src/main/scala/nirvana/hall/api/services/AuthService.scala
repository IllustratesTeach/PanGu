package nirvana.hall.api.services

import nirvana.hall.api.entities.OnlineUser
import org.springframework.transaction.annotation.Transactional
import scalikejdbc.DBSession

/**
 * auth service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
trait AuthService {
  /**
   * find user name by token
   */
  @Transactional
  def refreshToken(token: String)(implicit session: DBSession = AutoSpringDataSourceSession()): Option[OnlineUser]

  /**
   * login system.
   * @param name login name
   * @return token
   */
  @Transactional
  def login(name: String)(implicit session: DBSession = AutoSpringDataSourceSession()): String
  @Transactional
  def logout(token: String)(implicit session: DBSession = AutoSpringDataSourceSession()): Unit
}
