package nirvana.hall.api.services

import nirvana.hall.api.entities.User

/**
 * protobuf request global
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
trait ProtobufRequestGlobal {
  def token(): String;

  def store(token: String)
  def isLogin: Boolean
  def currentUser: Option[User]
  def userId: Int
  def logout()
}
