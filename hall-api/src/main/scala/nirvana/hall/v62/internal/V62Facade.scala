package nirvana.hall.v62.internal

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import monad.support.services.LoggerSupport
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gnetlib._
import nirvana.hall.v62.internal.c.grmtlib.gnetfunc
import nirvana.hall.v62.services.V62ServerAddress
import org.apache.tapestry5.services.{HttpServletRequestFilter, HttpServletRequestHandler}

/**
 * v62 facade
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
class V62Facade
  extends gnetcsr
  with DataSyncSupport
  with ganetqry
  with ganetdbp
  with ganetlp
  with gnetflib
  with nettable
  with gnetfunc
  with netmisc
  with AncientClientSupport
  with reqansop
  with LoggerSupport{
  override def serverAddress: V62ServerAddress = {
    val address = V62Facade.serverContext.value
    if(address == null)
      throw new IllegalStateException("can't find v62 server address from context")
  }
}
object V62Facade{
  /**
    * 动态获取服务器的地址，通过动态变量的上下文进行获取
    */
  private[v62] val serverContext = new scala.util.DynamicVariable[V62ServerAddress](null)
  /**
    * 针对v62的服务器端请求，自动添加请求的上下文
    * NOTICE: 此filter的位置需要放置在protobuf的前端
    * @see ProtobufServletFilter
    * @see LocalV62ServiceModule
    * @see LocalApiWebModule
    */
  class AutoSetupServerContextFilter(config:HallV62Config) extends HttpServletRequestFilter {
    //服务器的地址配置
    private val address = V62ServerAddress(config.host,
      config.port,
      config.connectionTimeoutSecs,
      config.readTimeoutSecs,
      config.user,
      Option(config.password))

    override def service(request: HttpServletRequest, response: HttpServletResponse, handler: HttpServletRequestHandler): Boolean = {
      serverContext.withValue(address){
        handler.service(request,response)
      }
    }
  }
}
