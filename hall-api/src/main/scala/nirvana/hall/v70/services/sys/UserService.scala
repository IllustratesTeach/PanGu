package nirvana.hall.v70.services.sys

import nirvana.hall.v70.jpa.SysUser
import org.springframework.transaction.annotation.Transactional

/**
 * 用户相关的服务类
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
trait UserService {
  @Transactional
  def testCreateUser(login: String, password: String)

  def login(loginName: String, password: String): (Option[SysUser], Option[String])
  def existsLoginName(loginName: String): Boolean
}
