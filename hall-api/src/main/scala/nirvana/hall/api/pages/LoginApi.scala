package nirvana.hall.api.pages

import javax.inject.Inject
import javax.servlet.http.HttpServletRequest

import com.google.protobuf.ExtensionRegistry
import monad.core.services.ProtobufProcessor
import nirvana.hall.protocol.sys.CommonProto.BaseRequest
import nirvana.hall.protocol.sys.LoginProto.{LoginResponse, LoginRequest}
import org.slf4j.Logger

import scala.util.control.NonFatal

/**
 * login api page
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-04-02
 */
class LoginApi {
  @Inject
  private var request: HttpServletRequest = _
  @Inject
  private var processor: ProtobufProcessor[LoginRequest, LoginResponse] = _
  @Inject
  private var logger: Logger = _

  def onActivate(): LoginResponse = {
    try {
      val extensionRegistry: ExtensionRegistry = null;
      //val request:BaseRequest = BaseRequest.getDefaultInstance.getParserForType.parseFrom(request.getInputStream,extensionRegistry)
      val baseRequest = BaseRequest.newBuilder().mergeFrom(request.getInputStream, extensionRegistry)
      val loginRequest = baseRequest.getExtension(LoginRequest.cmd);
      val loginRequestBuilder = LoginRequest.newBuilder().mergeFrom(request.getInputStream, extensionRegistry)
      processor.process(loginRequestBuilder.build())
    }
    catch {
      case NonFatal(e) =>
        logger.warn(e.getMessage)
        val responseBuilder = LoginResponse.newBuilder()

        //responseBuilder.setStatus(LoginStatus.FAIL)
        //responseBuilder.setMessage(e.getMessage)
        responseBuilder.build()
    }
  }
}
