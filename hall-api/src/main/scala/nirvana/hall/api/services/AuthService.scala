package nirvana.hall.api.services

import nirvana.hall.api.jpa.OnlineUser
import org.springframework.transaction.annotation.Transactional

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
  def refreshToken(token: String): Option[OnlineUser]

  /**
   * login system.
   * @param name login name
   * @return token
   */
  @Transactional
  def login(name: String): String
  @Transactional
  def logout(token: String): Unit
}
