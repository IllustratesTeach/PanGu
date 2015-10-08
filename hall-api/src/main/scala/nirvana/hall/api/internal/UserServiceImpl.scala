package nirvana.hall.api.internal

import nirvana.hall.api.entities.SysUser
import nirvana.hall.api.services.{AuthService, UserService}
import org.apache.commons.codec.digest.DigestUtils
import scalikejdbc._

/**
 * user service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
class UserServiceImpl(authService: AuthService) extends UserService {

  override def testCreateUser(login: String, password: String): Unit = {
    SysUser.create("uuid","jcai",Some(DigestUtils.md5Hex(password)))
  }

  override def existsLoginName(loginName: String): Boolean = {
    SysUser.countBy(sqls.eq(SysUser.column.loginName, loginName)) > 0
  }
  override def login(loginName: String, password: String): (Option[SysUser], Option[String]) = {
    val c = SysUser.column
    val passwordEncrypted = DigestUtils.md5Hex(password)
    val userOpt = SysUser.findBy(sqls.eq(c.loginName, loginName).and.eq(c.password, passwordEncrypted))
    var tokenOpt: Option[String] = None
    userOpt.foreach(x => tokenOpt = Some(authService.login(loginName)))

    (userOpt, tokenOpt)
  }
}
