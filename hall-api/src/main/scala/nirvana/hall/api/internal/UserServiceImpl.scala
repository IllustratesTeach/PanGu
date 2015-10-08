package nirvana.hall.api.internal

import nirvana.hall.api.entities.User
import nirvana.hall.api.services.{AuthService, UserService}
import org.apache.commons.codec.digest.DigestUtils
import scalikejdbc._

/**
 * user service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
class UserServiceImpl(authService: AuthService) extends UserService {
  override def existsLoginName(loginName: String): Boolean = {
    User.countBy(sqls.eq(User.column.login, loginName)) > 0
  }
  override def login(loginName: String, password: String): (Option[User], Option[String]) = {
    val c = User.column
    val passwordEncrypted = DigestUtils.md5Hex(password)
    val userOpt = User.findBy(sqls.eq(c.login, loginName).and.eq(c.password, passwordEncrypted))
    var tokenOpt: Option[String] = None
    userOpt.foreach(x => tokenOpt = Some(authService.login(loginName)))

    (userOpt, tokenOpt)
  }
}
