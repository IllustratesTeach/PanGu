package nirvana.hall.v62.internal

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import monad.support.services.LoggerSupport
import nirvana.hall.v62.config.{V62ServerConfig, HallV62Config}
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
class V62Facade(defaultConfig:HallV62Config)
  extends gnetcsr
  with V62QueryTableSupport
  with DataSyncSupport
  with gnetsurvey
  with gnetblob
  with ganetqry
  with ganetdbp
  with ganetlp
  with ganettp
  with ganetlog
  with gnetflib
  with nettable
  with gnetfunc
  with netpmadm
  with netmisc
  with ganetuser
  with gnetcode
  with AncientClientSupport
  with reqansop
  with LoggerSupport{
  override def serverAddress: V62ServerAddress = {
    var address = V62Facade.serverContext.value
    if(address == null) {
      address = V62ServerAddress(
        defaultConfig.appServer.host,
        defaultConfig.appServer.port,
        defaultConfig.appServer.connectionTimeoutSecs,
        defaultConfig.appServer.readTimeoutSecs,
        defaultConfig.appServer.user,
        Option(defaultConfig.appServer.password))
      info("using default config server to request [{}:{}]",address.host,address.port)
    }else{
      info("using context server to request [{}:{}]",address.host,address.port)
    }
    address
  }
}
object V62Facade{
  //http请求头标示
  val X_V62_HOST_HEAD     ="X-V62-HOST"
  val X_V62_PORT_HEAD     ="X-V62-PORT"
  val X_V62_USER_HEAD     ="X-V62-USER"
  val X_V62_PASSWORD_HEAD ="X-V62-PASS"

  //tableid
  val TID_TPCARDINFO:Short = 2
  val TID_LATFINGER:Short  = 2
  val TID_LATPALM:Short    = 3
  val TID_CASE:Short       = 4
  val TID_QUERYQUE:Short   = 2
  val TID_BREAKCASE:Short  = 4 //比中信息admin_breakcase
  val TID_PERSONINFO:Short = 3 //重卡信息normaltp_personinfo
  val TID_LPGROUP:Short    = 9 //现场
  val TID_USER:Short       = 2

  //现勘接口记录表
  val TID_SURVEYRECORD:Short = 391
  val TID_SURVEYCONFIG:Short = 392
  val TID_SURVEYHITRESULTRECORD:Short = 393

  //默认管理员库
  val DBID_ADMIN_DEFAULT: Short = 1
  //默认捺印库
  val DBID_TP_DEFAULT: Short = 1
  //默认现场库
  val DBID_LP_DEFAULT: Short = 2
  //默认查询库
  val DBID_QRY_DEFAULT: Short = 20
  //现勘数据库
  val DBID_SURVEY: Short = 21

  /**
    * 动态获取服务器的地址，通过动态变量的上下文进行获取
    */
  private[v62] val serverContext = new scala.util.DynamicVariable[V62ServerAddress](null)
  def withConfigurationServer[T](config:V62ServerConfig)(function: =>T)={
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
    * @see ProtobufServletFilter
    * @see LocalV62ServiceModule
    * @see LocalApiWebModule
    */
  class AutoSetupServerContextFilter(config:HallV62Config) extends HttpServletRequestFilter {

    override def service(request: HttpServletRequest, response: HttpServletResponse, handler: HttpServletRequestHandler): Boolean = {
      val host = request.getHeader(X_V62_HOST_HEAD)
        if(host == null){
          withConfigurationServer(config.appServer) {
            handler.service(request, response)
          }
        }else{
          val port = getHeader(request,X_V62_PORT_HEAD,config.appServer.port.toString).toInt
          val user = getHeader(request,X_V62_USER_HEAD,config.appServer.user)
          val passOpt = Option(getHeader(request,X_V62_PASSWORD_HEAD,config.appServer.password))
          val address = V62ServerAddress(host,port,config.appServer.connectionTimeoutSecs,config.appServer.readTimeoutSecs,user,passOpt)
          serverContext.withValue(address){
            handler.service(request,response)
          }
        }
    }
    private def getHeader(request:HttpServletRequest,name:String,defaultValue:String):String={
      val value = request.getHeader(name)
      if(value == null ) defaultValue else value
    }
  }
}
