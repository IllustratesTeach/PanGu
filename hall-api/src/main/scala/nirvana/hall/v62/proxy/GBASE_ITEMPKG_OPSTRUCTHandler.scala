package nirvana.hall.v62.proxy

import monad.support.services.{LoggerSupport, MonadException}
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}
import nirvana.hall.v62.internal.c.grmtlib.{grmtcsr, grmtpkg}
import nirvana.hall.v62.proxy.filter.NettyChannelOperator
import nirvana.hall.v62.services.HallV62ExceptionCode.FAIL_TO_FIND_PROCESSOR
import nirvana.hall.v62.services.{ChannelOperator, V62ServerAddress}
import org.jboss.netty.channel._

/**
  * 此handler每个连接是一个实例
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
class GBASE_ITEMPKG_OPSTRUCTHandler(handler: GbaseItemPkgHandler) extends SimpleChannelUpstreamHandler
  with grmtpkg with gitempkg with grmtcsr with gnetcsr with reqansop with AncientClientSupport with LoggerSupport{
  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
    val msg = e.getMessage
    val channel = ctx.getChannel
    val attachment = channel.getAttachment.asInstanceOf[ChannelAttachment]
    msg match{
      case pkg:GBASE_ITEMPKG_OPSTRUCT =>
        if(pkg.head.nDataLen != pkg.getDataSize){
          throw new MonadException("invalidate package len(r):%s!=len(h)%s".format(pkg.getDataSize,pkg.head.nDataLen),FAIL_TO_FIND_PROCESSOR)
        }
        val request = GAFIS_PKG_GetRmtRequest(pkg).getOrElse(throw new IllegalStateException("Missing Request Item"))
        val opClass = request.nOpClass
        val opCode = request.nOpCode
        attachment.opClass = opClass
        attachment.opCode = opCode
        info("[{}] <-- username:{} opClass {} opCode:{},direct:{} --> [{}] ",ctx.getChannel.getRemoteAddress,request.szUserName,opClass,opCode,attachment.directRequest,attachment.outboundChannel.getRemoteAddress)

        //绑定当前线程使用的channel
        ChannelThreadContext.channelContext.withValue(channel) {
          /**
            * 非direct的模式，且被捕获
            * 在6.2中direct模式就是代理模式，所以直接进行转发
            */
          val outbound = attachment.outboundChannel
          if (!attachment.directRequest && hanleInExceptionCaught(request, pkg)) {
            debug("[{}] <-- username:{} opClass {} opCode:{},direct:{} --> [{}] handled",ctx.getChannel.getRemoteAddress,request.szUserName,opClass,opCode,attachment.directRequest,attachment.outboundChannel.getRemoteAddress)
            attachment.status = RequestHandled
            //如果被hall进行处理,则关掉代理的通讯服务器连接
            TxProxyInboundHandler.closeOnFlush(outbound)
          } else {
            debug("[{}] <-- username:{} opClass {} opCode:{},direct:{} --> [{}] not handled",ctx.getChannel.getRemoteAddress,request.szUserName,opClass,opCode,attachment.directRequest,attachment.outboundChannel.getRemoteAddress)
            attachment.status = RequestNotHandled
            if (attachment.directRequest) {
              outbound.write(request)
            } else {
              outbound.write(pkg)
            }
          }
        }
      case other =>
        attachment.status = RequestNotHandled
        attachment.outboundChannel.write(e.getMessage)
    }
  }
  private def hanleInExceptionCaught(request:GNETREQUESTHEADOBJECT,pkg:GBASE_ITEMPKG_OPSTRUCT): Boolean ={
    try{
      handler.handle(request,pkg)
    }catch{
      case e:Throwable =>
        error(e.getMessage,e)
        //服务器上发生异常，则关闭此channel
        GAFIS_NETCSR_SendRemoteErrStruct(e)
        true
    }
  }
  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent): Unit = {
    e.getCause match {
      case me: MonadException =>
        error("server exception,client:{} msg:{}", e.getChannel.getRemoteAddress, me.toString)
      case other =>
        error("server exception,client:" + e.getChannel.getRemoteAddress, other)
    }
    //e.getChannel.close()
  }
  override def serverAddress: V62ServerAddress = {throw new UnsupportedOperationException}
  /**
    * execute in channel
    */
  override def executeInChannel[T](channelOperator: (ChannelOperator) => T): T = {
    val channel = ChannelThreadContext.channelContext.value
    channelOperator(new NettyChannelOperator(channel))
  }
}
object ChannelThreadContext{
  //DSL Context using DynamicVariable method
  private [proxy] val channelContext = new scala.util.DynamicVariable[Channel](null)
}
