package nirvana.hall.api.internal.protobuf.sys

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler, UserService}
import nirvana.hall.protocol.sys.CommonProto.{ResponseStatus, BaseResponse, BaseRequest}
import nirvana.hall.protocol.sys.LoginProto.{LoginResponse, LoginRequest}

/**
 * process login request
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-04-02
 */
class LoginRequestFilter(userService: UserService)
    extends ProtobufRequestFilter
    with LoggerSupport {

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(LoginRequest.cmd)) {
      val request = protobufRequest.getExtension(LoginRequest.cmd)
      debug("name:{} pass:{}", request.getLogin, request.getPassword)
      val builder = LoginResponse.newBuilder()
      val userOpt = userService.login(request.getLogin, request.getPassword)
      userOpt match {
        case (Some(user), Some(token)) =>
          builder.setCorporateName(user.corporateName)
          builder.setToken(token)
          responseBuilder.setExtension(LoginResponse.cmd, builder.build())
        case _ =>
          responseBuilder.setStatus(ResponseStatus.FAIL)
          responseBuilder.setMessage("invalid user");
      }
      true
    }
    else {
      handler.handle(protobufRequest, responseBuilder)
    }
  }
}
