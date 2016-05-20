package nirvana.hall.v62.internal

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import monad.support.services.LoggerSupport
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.V62QueryTableSupport
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
  with V62QueryTableSupport
  with DataSyncSupport
  with ganetqry
  with ganetdbp
  with ganetlp
  with gnetflib
  with nettable
  with gnetfunc
  with netpmadm
  with netmisc
  with AncientClientSupport
  with reqansop
  with LoggerSupport{
  override def serverAddress: V62ServerAddress = {
    val address = V62Facade.serverContext.value
    if(address == null)
      throw new IllegalStateException("can't find v62 server address from context")

    address
  }
}
object V62Facade{
  //http请求头标示
  val X_V62_HOST_HEAD     ="X-V62-HOST"
  val X_V62_PORT_HEAD     ="X-V62-PORT"
  val X_V62_USER_HEAD     ="X-V62-USER"
  val X_V62_PASSWORD_HEAD ="X-V62-PASS"

  /**
    * 动态获取服务器的地址，通过动态变量的上下文进行获取
    */
  private[v62] val serverContext = new scala.util.DynamicVariable[V62ServerAddress](null)
  def withConfigurationServer[T](config:HallV62Config)(function: =>T)={
    val address = V62ServerAddress(config.host,
      config.port,
      config.connectionTimeoutSecs,
      config.readTimeoutSecs,
      config.user,
      Option(config.password))
    serverContext.withValue(address){
      function
    }
  }
  /**
    * 针对v62的服务器端请求，自动添加请求的上下文
    * NOTICE: 此filter的位置需要放置在protobuf的前端
    *
    * 如果前端的请求指定了服务器相关信息，则采用指定的信息进行连接
 *
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
      val host = request.getHeader(X_V62_HOST_HEAD)
      val v62Address =
        if(host == null){
          address
        }else{
          val port = getHeader(request,X_V62_PORT_HEAD,config.port.toString).toInt
          val user = getHeader(request,X_V62_USER_HEAD,config.user)
          val passOpt = Option(getHeader(request,X_V62_PASSWORD_HEAD,config.password))
          V62ServerAddress(host,port,config.connectionTimeoutSecs,config.readTimeoutSecs,user,passOpt)
        }
      serverContext.withValue(v62Address){
        handler.service(request,response)
      }
    }
    private def getHeader(request:HttpServletRequest,name:String,defaultValue:String):String={
      val value = request.getHeader(name)
      if(value == null ) defaultValue else value
    }
  }
}
