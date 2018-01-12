package nirvana.hall.v70.gz.sys

import nirvana.hall.api.services.AuthService
import nirvana.hall.c.services.gfpt5lib.currentUserMessage
import nirvana.hall.v70.gz.jpa.{SysDepart, SysUser}
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

  //新增FPT5.0头文件用户信息
  def getFPT5CurrentUserMessage(userId:String):currentUserMessage = {
    val currentUserMessage = new currentUserMessage()
    val user = SysUser.findOption(userId).get
    val depart = SysDepart.findOption(user.departCode).get
    currentUserMessage.sendPersonIdCard = user.idcard
    currentUserMessage.sendPersonName = user.trueName
    currentUserMessage.sendUnitCode = user.departCode
    currentUserMessage.sendUnitName = depart.name
    currentUserMessage.sendPersonTel = user.phone
    currentUserMessage
  }
}
