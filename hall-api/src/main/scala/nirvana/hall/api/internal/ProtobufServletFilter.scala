package nirvana.hall.api.internal

import java.io.OutputStream
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.google.protobuf.ExtensionRegistry
import monad.rpc.protocol.CommandProto.{BaseCommand, CommandStatus}
import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.services.{ProtobufRequestGlobal, ProtobufRequestHandler}
import nirvana.hall.v62.services.GafisException
import org.apache.tapestry5.ioc.internal.util.InternalUtils
import org.apache.tapestry5.ioc.services.PerthreadManager
import org.apache.tapestry5.services._

import scala.util.control.NonFatal

/**
 * 实现基于http servlet的protobuf消息过滤
  *
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
  private val HALL_TOKEN_HEADER="Hall-Token"
  override def service(request: HttpServletRequest, response: HttpServletResponse, handler: HttpServletRequestHandler): Boolean = {
    val header = request.getHeader(PROTOBUF_HEADER)
    if (header != null) {
      val responseBuilder = BaseCommand.newBuilder()
      responseBuilder.setStatus(CommandStatus.OK)
      try {
        val baseRequest = BaseCommand.getDefaultInstance.getParserForType.parseFrom(request.getInputStream, extensionRegistry)
        // TODO 暂时取消登录验证
//        val token = baseRequest.getToken
//        protobufRequestGlobal.store(token)
        //val baseRequest = BaseRequest.newBuilder().mergeFrom(request.getInputStream, extensionRegistry).build()
        protobufHandler.handle(baseRequest, responseBuilder)
      }
      catch {
        case NonFatal(e) =>
          e match{
            case ge:GafisException=>
              error(ge.getFullMessage, e)
            case _ =>
              error(e.getMessage, e)
          }
          responseBuilder.setStatus(CommandStatus.FAIL)
          responseBuilder.setMsg(e.toString)
      }

      //ouput protobuf stream
      var os: OutputStream = null
      try {
        response.setContentType(HallApiConstants.PROTOBUF_CONTEXT)

        response.setHeader("Access-Control-Expose-Headers",PROTOBUF_ERROR+","+HALL_TOKEN_HEADER)
        response.setHeader("Access-Control-Allow-Origin","*")
        response.setHeader(HALL_TOKEN_HEADER,protobufRequestGlobal.token())

        val baseResponse = responseBuilder.build()
        if (baseResponse.getStatus == CommandStatus.FAIL) {
          response.setHeader(PROTOBUF_ERROR, CommandStatus.FAIL.toString)
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
