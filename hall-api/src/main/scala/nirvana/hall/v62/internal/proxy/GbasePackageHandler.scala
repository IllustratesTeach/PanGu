package nirvana.hall.v62.internal.proxy

import monad.support.services.{LoggerSupport, MonadException}
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.grmtlib.grmtpkg
import nirvana.hall.v62.services.HallV62ExceptionCode.FAIL_TO_FIND_PROCESSOR
import org.jboss.netty.channel._

/**
  * 此handler每个连接是一个实例
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
class GbasePackageHandler(handler: GbaseItemPkgHandler,config:HallV62Config) extends SimpleChannelUpstreamHandler
  with grmtpkg with gitempkg with LoggerSupport{
  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
    val pkg = e.getMessage.asInstanceOf[GBASE_ITEMPKG_OPSTRUCT]
    if(pkg.head.nDataLen != pkg.getDataSize){
      throw new MonadException("invalidate package len(r):%s!=len(h)%s".format(pkg.getDataSize,pkg.head.nDataLen),FAIL_TO_FIND_PROCESSOR)
    }

    val requestOpt = GAFIS_PKG_GetRmtRequest(pkg)
    if(requestOpt.isEmpty) {
      throw new IllegalStateException("Missing Request Item")
    }
    val request = requestOpt.get
    val opClass = request.nOpClass
    val opCode = request.nOpCode
    info("[{}] username:{} opClass {} opCode:{} ",ctx.getChannel.getRemoteAddress,request.szUserName,opClass,opCode)

    //绑定当前线程使用的channel
    ChannelThreadContext.channelContext.withValue(ctx.getChannel){
      //绑定当前线程使用的v62配置
      V62Facade.withConfigurationServer(config){
        handler.handle(request, pkg)
      }
    }
  }
  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent): Unit = {
    e.getCause match {
      case me: MonadException =>
        error("server exception,client:{} msg:{}", e.getChannel.getRemoteAddress, me.toString)
      case other =>
        error("server exception,client:" + e.getChannel.getRemoteAddress, other)
    }

    //服务器上发生异常，则关闭此channel
    e.getChannel.close()
  }
}
object ChannelThreadContext{
  //DSL Context using DynamicVariable method
  private [proxy] val channelContext = new scala.util.DynamicVariable[Channel](null)
}
