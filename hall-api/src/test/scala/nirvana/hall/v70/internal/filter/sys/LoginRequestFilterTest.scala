package nirvana.hall.v70.internal.filter.sys

import nirvana.hall.api.internal.BaseServiceTestSupport
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{ResponseStatus, BaseResponse, BaseRequest}
import nirvana.hall.protocol.sys.LoginProto.{LoginResponse, LoginRequest}
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-26
 */
class LoginRequestFilterTest extends BaseServiceTestSupport{
  @Test
  def test_login: Unit ={
    val loginRequest = LoginRequest.newBuilder()
    loginRequest.setLogin("jcai")
    loginRequest.setPassword("password")


    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(101)
    protobufRequest.setExtension(LoginRequest.cmd, loginRequest.build())
    val protobufResponse = BaseResponse.newBuilder()
    protobufResponse.setStatus(ResponseStatus.OK)
    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertTrue(protobufResponse.hasExtension(LoginResponse.cmd))
    handler.handle(protobufRequest.build(), protobufResponse)
    Assert.assertTrue(protobufResponse.hasExtension(LoginResponse.cmd))

  }
}
