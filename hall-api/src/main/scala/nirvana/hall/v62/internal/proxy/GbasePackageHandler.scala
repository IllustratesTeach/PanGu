package nirvana.hall.v62.internal.proxy

import monad.support.services.{LoggerSupport, MonadException}
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import org.jboss.netty.channel.{ChannelHandlerContext, ExceptionEvent, MessageEvent, SimpleChannelUpstreamHandler}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
class GbasePackageHandler extends SimpleChannelUpstreamHandler with LoggerSupport{
  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
    val pkg = e.getMessage.asInstanceOf[GBASE_ITEMPKG_OPSTRUCT]
    //TODO 实现业务逻辑
    debug("pakcage received")


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
