package nirvana.hall.api.internal

import java.io.OutputStream
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.google.protobuf.ExtensionRegistry
import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.services.{ProtobufRequestGlobal, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, ResponseStatus, BaseResponse}
import org.apache.tapestry5.ioc.internal.util.InternalUtils
import org.apache.tapestry5.ioc.services.PerthreadManager
import org.apache.tapestry5.services._

import scala.util.control.NonFatal

/**
 * 实现基于http servlet的protobuf消息过滤
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-05-21
 */
class ProtobufServletFilter(protobufHandler: ProtobufRequestHandler,
                            extensionRegistry: ExtensionRegistry,
                            protobufRequestGlobal: ProtobufRequestGlobal,
                            perthreadManager: PerthreadManager)
    extends HttpServletRequestFilter with LoggerSupport {
  private val PROTOBUF_HEADER = "X-Hall-Request"
  private val PROTOBUF_ERROR = "X-Hall-Error"
  private val SUOWEN_TOKEN_HEADER="Hall-Token"
  override def service(request: HttpServletRequest, response: HttpServletResponse, handler: HttpServletRequestHandler): Boolean = {
    val header = request.getHeader(PROTOBUF_HEADER)
    if (header != null) {
      val responseBuilder = BaseResponse.newBuilder()
      responseBuilder.setStatus(ResponseStatus.OK)
      try {
        val baseRequest = BaseRequest.getDefaultInstance.getParserForType.parseFrom(request.getInputStream, extensionRegistry)
        val token = baseRequest.getToken
        protobufRequestGlobal.store(token)
        //val baseRequest = BaseRequest.newBuilder().mergeFrom(request.getInputStream, extensionRegistry).build()
        protobufHandler.handle(baseRequest, responseBuilder)
      }
      catch {
        case NonFatal(e) =>
          error(e.getMessage, e)
          responseBuilder.setStatus(ResponseStatus.FAIL)
          responseBuilder.setMessage(e.toString)
      }

      //ouput protobuf stream
      var os: OutputStream = null
      try {
        response.setContentType(HallApiConstants.PROTOBUF_CONTEXT)

        response.setHeader("Access-Control-Expose-Headers",PROTOBUF_ERROR+","+SUOWEN_TOKEN_HEADER)
        response.setHeader("Access-Control-Allow-Origin","*")
        response.setHeader(SUOWEN_TOKEN_HEADER,protobufRequestGlobal.token())

        val baseResponse = responseBuilder.build()
        if (baseResponse.getStatus == ResponseStatus.FAIL) {
          response.setHeader(PROTOBUF_ERROR, ResponseStatus.FAIL.toString)
        }
        os = response.getOutputStream
        responseBuilder.build().writeTo(os)
        os.close()
        os = null
      }
      finally {
        InternalUtils.close(os)
        //清空当前线程所有东西
        perthreadManager.cleanup()
      }

      true
    }
    else {
      handler.service(request, response)
    }
  }
}
