package nirvana.hall.support.internal

import java.io.OutputStream
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

import com.google.protobuf.ExtensionRegistry
import com.google.protobuf.GeneratedMessage.GeneratedExtension
import monad.rpc.protocol.CommandProto
import monad.rpc.protocol.CommandProto.{BaseCommand, CommandStatus}
import monad.rpc.services.{CommandResponse, RpcServerMessageHandler}
import monad.support.services.LoggerSupport
import nirvana.hall.support.HallSupportConstants
import org.apache.tapestry5.ioc.internal.util.InternalUtils
import org.apache.tapestry5.services.{HttpServletRequestFilter, HttpServletRequestHandler}
import org.jboss.netty.channel.ChannelFuture

import scala.util.control.NonFatal

/**
 * protobuf rpc server message servlet filter
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-13
 */
class ProtobufRpcServerMessageServletFilter(serverMessageHandler:RpcServerMessageHandler,extensionRegistry: ExtensionRegistry)
  extends HttpServletRequestFilter with LoggerSupport{
  override def service(request: HttpServletRequest, response: HttpServletResponse, handler: HttpServletRequestHandler): Boolean = {
    try {
      val header = request.getHeader(HallSupportConstants.HTTP_PROTOBUF_HEADER)
      if (header != null) {
        val responseBuilder = BaseCommand.newBuilder()
        responseBuilder.setStatus(CommandStatus.OK)
        var baseRequest: BaseCommand = null
        try {
          baseRequest = CommandProto.BaseCommand
            .getDefaultInstance
            .getParserForType
            .parseFrom(request.getInputStream, extensionRegistry)
          val commandResponse = new CommandResponse {
            override def writeMessage[T](commandRequest: BaseCommand, extension: GeneratedExtension[BaseCommand, T], value: T): ChannelFuture = {
              responseBuilder.setExtension(extension, value).setTaskId(commandRequest.getTaskId).setStatus(CommandStatus.OK)
              null
            }

            override def writeErrorMessage[T](commandRequest: BaseCommand, message: String): ChannelFuture = {
              responseBuilder.setTaskId(commandRequest.getTaskId).setStatus(CommandStatus.FAIL).setMsg(message)
              null
            }
          }
          val result = serverMessageHandler.handle(baseRequest, commandResponse)
          if (!result) {
            responseBuilder.setStatus(CommandStatus.FAIL)
            responseBuilder.setMsg("message not handled !")
          }
        } catch {
          case NonFatal(e) =>
            error(e.getMessage, e)
            responseBuilder.setStatus(CommandStatus.FAIL)
            if(e.getMessage != null)
              responseBuilder.setMsg(e.getMessage)
            else
              responseBuilder.setMsg(e.toString)
        }
        if (!responseBuilder.hasTaskId) {

          if (baseRequest == null)
            responseBuilder.setTaskId(-1L)
          else
            responseBuilder.setTaskId(baseRequest.getTaskId)
        }

        writeProtobufMessage(response, responseBuilder.build())

        true
      } else {
        handler.service(request, response)
      }
    }catch{
      case NonFatal(e) =>
        error(e.toString,e)
        throw e
    }
  }
  private def writeProtobufMessage(response:HttpServletResponse,responseCommand:BaseCommand): Unit ={
    //ouput protobuf stream
    var os: OutputStream = null
    try {
      response.setContentType(PROTOBUF_CONTEXT)

      os = response.getOutputStream
      responseCommand.writeTo(os)
      os.close()
      os = null
    }
    finally {
      InternalUtils.close(os)
      //清空当前线程所有东西
      //perthreadManager.cleanup()
    }
  }
  final val PROTOBUF_CONTEXT = "application/protobuf"
}
