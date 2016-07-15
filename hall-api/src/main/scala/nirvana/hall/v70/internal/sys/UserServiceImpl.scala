package nirvana.hall.v70.internal.sys

import nirvana.hall.api.services.AuthService
import nirvana.hall.v70.jpa.SysUser
import nirvana.hall.v70.services.sys.UserService
import org.apache.commons.codec.digest.DigestUtils

/**
 * user service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
class UserServiceImpl(authService: AuthService) extends UserService {

  override def testCreateUser(login: String, password: String): Unit = {
    //SysUser.create("uuid","jcai",Some(DigestUtils.md5Hex(password)))
    //SysUser.create("uuid","jcai",Some(DigestUtils.md5Hex(password)))
    val user = new SysUser()
    user.pkId = "UUID"
    user.loginName = "jcai"
    user.password = DigestUtils.md5Hex(password)

    user.save()
  }

  override def existsLoginName(loginName: String): Boolean = {
    SysUser.where(SysUser.loginName === loginName).limit(1).nonEmpty
  }
  override def login(loginName: String, password: String): (Option[SysUser], Option[String]) = {

    val passwordEncrypted = DigestUtils.md5Hex(password)
    val userOpt = SysUser.find_by_loginName_and_password(loginName,passwordEncrypted).headOption

    var tokenOpt: Option[String] = None
    userOpt.foreach(x => tokenOpt = Some(authService.login(loginName)))

    (userOpt, tokenOpt)
  }

  /**
   * 根据登录名查找用户
   * @param loginName
   * @return
   */
  override def findSysUserByLoginName(loginName: String): Option[SysUser] = {
    SysUser.where(SysUser.loginName === loginName).limit(1).headOption
  }
}
