package nirvana.hall.api.internal

import monad.support.services.MonadException
import nirvana.hall.api.entities.{User, OnlineUser}
import nirvana.hall.api.services.{AuthService, HallExceptionCode, ProtobufRequestGlobal}
import org.apache.tapestry5.ioc.ScopeConstants
import org.apache.tapestry5.ioc.annotations.Scope
import org.apache.tapestry5.ioc.internal.util.InternalUtils
import scalikejdbc._

/**
 * protobuf request global holder
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
@Scope(ScopeConstants.PERTHREAD)
class ProtobufRequestGlobalImpl(authService: AuthService) extends ProtobufRequestGlobal {
  private var _onlineUser: Option[OnlineUser] = None
  private var _currentUser: Option[User] = None
  private var _token:String = null

  override def token(): String = _token

  override def store(token: String): Unit = {
    if (InternalUtils.isNonBlank(token)) {
      _onlineUser = authService.refreshToken(token)
      _onlineUser match {
        case Some(ou) =>
          _currentUser = User.findBy(sqls.eq(User.column.login, ou.login))
        case None =>
        //do nothing
      }
    }

    _token = token
  }
  override def logout(): Unit = {
    _onlineUser = None
    _currentUser = None
  }

  override def userId: Int = _currentUser.
    getOrElse(throw new MonadException("user not login", HallExceptionCode.NOT_LOGIN))
    .id

  override def isLogin: Boolean = currentUser.isDefined

  override def currentUser: Option[User] = _currentUser

}
