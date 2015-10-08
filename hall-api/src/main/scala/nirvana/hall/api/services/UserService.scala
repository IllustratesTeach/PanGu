package nirvana.hall.api.services

import nirvana.hall.api.entities.User

/**
 * 用户相关的服务类
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
trait UserService {
  def login(loginName: String, password: String): (Option[User], Option[String])
  def existsLoginName(loginName: String): Boolean
}
