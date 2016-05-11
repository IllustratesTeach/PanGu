package nirvana.hall.api.internal

import monad.support.services.MonadException
import nirvana.hall.api.services.{AuthService, HallExceptionCode, ProtobufRequestGlobal}
import nirvana.hall.v70.jpa.{SysUser, OnlineUser}
import org.apache.tapestry5.ioc.ScopeConstants
import org.apache.tapestry5.ioc.annotations.Scope
import org.apache.tapestry5.ioc.internal.util.InternalUtils

/**
 * protobuf request global holder
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
@Scope(ScopeConstants.PERTHREAD)
class ProtobufRequestGlobalImpl(authService: AuthService) extends ProtobufRequestGlobal {
  private var _onlineUser: Option[OnlineUser] = None
  private var _currentUser: Option[SysUser] = None
  private var _token:String = null

  override def token(): String = _token

  override def store(token: String): Unit = {
    if (InternalUtils.isNonBlank(token)) {
      _onlineUser = authService.refreshToken(token)
      _onlineUser match {
        case Some(ou) =>
          _currentUser = SysUser.find_by_loginName(ou.login).headOption
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

  override def userId: String = _currentUser.
    getOrElse(throw new MonadException("user not login", HallExceptionCode.NOT_LOGIN))
    .pkId

  override def isLogin: Boolean = currentUser.isDefined

  override def currentUser: Option[SysUser] = _currentUser

}
