package nirvana.hall.v62.proxy

import java.net.InetSocketAddress
import java.nio.channels.ClosedChannelException

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.config.ProxyServerConfig
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.grmtlib.grmtpkg
import org.jboss.netty.bootstrap.ClientBootstrap
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}
import org.jboss.netty.channel.{Channel, ChannelFuture, ChannelFutureListener, ChannelHandlerContext, ChannelStateEvent, ExceptionEvent, MessageEvent, SimpleChannelUpstreamHandler}
import org.jboss.netty.channel.socket.ClientSocketChannelFactory
import org.jboss.netty.handler.codec.frame.FrameDecoder
import org.jboss.netty.handler.codec.oneone.{OneToOneDecoder, OneToOneEncoder}
import org.jboss.netty.handler.timeout.ReadTimeoutException
import org.jboss.netty.util.HashedWheelTimer

import scala.concurrent.{ExecutionContext, Promise}

/**
  * 整个代理服务器的核心部分,整个系统采取完全异步模式(比较难理解)
  *
  * 1.总体架构：
  *  活采 <-> 活采通讯服务器    <--- HALL PROXY -->   上级通讯服务器 <->应用服务器
  *                                   \
  *                                    \------>   HALL(支持6.2/7.0)
  *
  *  hall proxy起到代理功能，大部分的功能还是通过通信服务器来实现的，只是把我们需要的业务放在7.0服务器中实现.
  * 2. 整个核心程序的思想把一端收到的消息毫无保留的转发给另外一端的通信服务器
  *  处理左端网络消息的核心程序
  *     TxProxyInboundHandler  针对收到的消息进行统一处理，根据状态是否要转发给右端服务器，还是本身自己消化
  *     GBASE_ITEMPKG_OPSTRUCTFrameDecoder   针对收到的网络字节序进行处理,需要注意的是：
  *                               开始消息有两种，1.GBASE_ITEMPKG_OPSTRUCT 2.GNETREQUESTHEADOBJECT
  *     GBASE_ITEMPKG_OPSTRUCTDecoder        针对收到的网络字节变为为  GBASE_ITEMPKG_OPSTRUCT对象
  *     AncientDataEncoder    针对所有发送的数据，把对象变换为字节码
  *     GBASE_ITEMPKG_OPSTRUCTEncoder       构造GBASE_ITEMPKG_OPSTRUCT的发送方法
  *     GBASE_ITEMPKG_OPSTRUCTHandler   针对接受到的消息执行业务逻辑，如果整个包没有处理，则直接转发给右端服务器
  *                                                                      ^^^^^^^^^^^^^^
  *  处理右端网络消息的核心程序
  *     OutboundHandler       接收到右端网络的消息，直接发送给左端网络服务器
  *     AncientDataEncoder    针对所有发送的对象数据，变换为字节码
  *
  */
object TxProxyInboundHandler {
  /**
    * 通过收到的头大小来进行判断是否为代理模式
    * 见：grmtcsr#GAFIS_RMTLIB_RecvPkg
    */
  val DIRECT_REQUEST_LENGTH=192
  /** 用来做超时处理的定时器  **/
  val timer = new HashedWheelTimer
  /**
    * Closes the specified channel after all queued write requests are flushed.
    */
  private[proxy] def closeOnFlush(ch: Channel) {
    if (ch.isConnected) {
      ch.write(ChannelBuffers.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE)
    }
  }
}
/** 请求处理的状态  **/
sealed abstract class RequestHandleStatus()
/** 未被Hall处理 **/
case object RequestNotHandled extends RequestHandleStatus
/** 被Hall处理 **/
case object RequestHandled extends RequestHandleStatus
/** 当前channel中的参数，用来做判断处理 **/
case class ChannelAttachment(var status:RequestHandleStatus,outboundChannel:Channel,var directRequest:Boolean=false){
  var opClass:Int = 0
  var opCode:Int = 0

  override def toString: String = {
    "opClass:%s opCode:%s direct:%s".format(opClass,opCode,directRequest)
  }
}

class TxProxyInboundHandler(cf: ClientSocketChannelFactory,proxyConfig: ProxyServerConfig) extends SimpleChannelUpstreamHandler with LoggerSupport{
  private[proxy] final val trafficLock: AnyRef = new AnyRef
  @volatile
  private var outboundChannel: Channel = null

  override def channelOpen(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
    val inboundChannel: Channel = e.getChannel
    inboundChannel.setReadable(false)
    val cb: ClientBootstrap = new ClientBootstrap(cf)
    cb.setOption("connectTimeoutMillis", proxyConfig.backend.connectionTimeoutSecs * 1000);
    cb.getPipeline.addLast("handler", new OutboundHandler(e.getChannel))
    cb.getPipeline.addLast("encoder", new AncientDataEncoder())

    val f: ChannelFuture = cb.connect(new InetSocketAddress(proxyConfig.backend.host, proxyConfig.backend.port))
    outboundChannel = f.getChannel
    f.addListener(new ChannelFutureListener() {
      def operationComplete(future: ChannelFuture) {
        if (future.isSuccess) {
          inboundChannel.setReadable(true)
        }
        else {
          inboundChannel.close
        }
      }
    })
    inboundChannel.setAttachment(ChannelAttachment(RequestHandled,outboundChannel))
  }

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {
    val msg: ChannelBuffer = e.getMessage.asInstanceOf[ChannelBuffer]
    trafficLock synchronized {

      /**
        * 此处针对收到的数据进行处理
        * 如果是被系统处理的数据，则向上传送进行处理
        * 如果是未被处理的数据，则直接转发给右端服务器
        */
      ctx.getChannel.getAttachment match{
        case ChannelAttachment(RequestHandled,channel:Channel,_)=>
          ctx.sendUpstream(e)
        case ChannelAttachment(RequestNotHandled,outboundChannel:Channel,_)=>
          outboundChannel.write(msg)
      }
      if (!outboundChannel.isWritable) {
        e.getChannel.setReadable(false)
      }
    }
  }

  override def channelInterestChanged(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
    trafficLock synchronized {
      if (e.getChannel.isWritable) {
        if (outboundChannel != null) {
          outboundChannel.setReadable(true)
        }
      }
    }
  }

  override def channelClosed(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
    val isDirect=ctx.getChannel.getAttachment.asInstanceOf[ChannelAttachment].directRequest
//    debug("[{}](closed) <-- HALL(direct:{}) --> [{}]",e.getChannel.getRemoteAddress,isDirect,outboundChannel.getRemoteAddress)
    if (outboundChannel != null) {
      TxProxyInboundHandler.closeOnFlush(outboundChannel)
    }
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent) {
    outputException(e,ctx.getChannel.getAttachment.asInstanceOf[ChannelAttachment],ctx.getChannel,outboundChannel)
    TxProxyInboundHandler.closeOnFlush(e.getChannel)
  }

  /**
    * 转向所代理的服务器
    *
    * @param inboundChannel 请求的通道
    */
  private class OutboundHandler(inboundChannel: Channel) extends SimpleChannelUpstreamHandler {
    override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {
      val msg = e.getMessage
      trafficLock synchronized {
        msg match{
          case buffer:ChannelBuffer =>
            inboundChannel.write(msg)
        }
        if (!inboundChannel.isWritable) {
          e.getChannel.setReadable(false)
        }
      }
    }

    override def channelInterestChanged(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
      trafficLock synchronized {
        if (e.getChannel.isWritable) {
          inboundChannel.setReadable(true)
        }
      }
    }
    override def channelClosed(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
      val isDirect=inboundChannel.getAttachment.asInstanceOf[ChannelAttachment].directRequest
//      debug("[{}] <-- HALL(direct:{}) --> [{}](closed)",inboundChannel.getRemoteAddress,isDirect,outboundChannel.getRemoteAddress)
      TxProxyInboundHandler.closeOnFlush(inboundChannel)
    }
    override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent) {
      outputException(e,inboundChannel.getAttachment.asInstanceOf[ChannelAttachment],inboundChannel,ctx.getChannel)
      TxProxyInboundHandler.closeOnFlush(e.getChannel)
    }
  }
  private def outputException(e:ExceptionEvent,attachment: ChannelAttachment,inbound:Channel,outbound:Channel): Unit ={
    e.getCause match {
      case cce:ClosedChannelException=>
//        System.err.println("channel closed!")
      case ioe:java.io.IOException =>
        if(ioe.getMessage== null || ioe.getMessage != "Connection reset by peer")
          error(ioe.getMessage,ioe)
      case rte:ReadTimeoutException=>
        val isDirect=inbound.getAttachment.asInstanceOf[ChannelAttachment].directRequest
        warn("[{}] <-- HALL(opClass:{} opCode:{} direct:{}) --> [{}] .... read timeout",
          inbound.getRemoteAddress,attachment.opClass,attachment.opCode,isDirect,outbound.getRemoteAddress)
      case other=>
        error(other.getMessage,other)
    }
  }

  /**
    * 针对结构性数据进行输出操作
    */
  class AncientDataEncoder extends OneToOneEncoder{
    override def encode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
      msg match{
        case data:AncientData =>
          val buffer = ctx.getChannel.getConfig.getBufferFactory.getBuffer(data.getDataSize)
          data.writeToStreamWriter(buffer)
          buffer
        case other =>
          other.asInstanceOf[AnyRef]
      }
    }
  }


  /**
    * 得到符合GBASE_ITEMPKG_OPSTRUCT结构的数据帧
    */
  class GBASE_ITEMPKG_OPSTRUCTFrameDecoder extends FrameDecoder with LoggerSupport with gitempkg with grmtpkg{
    private implicit val executionContext = ExecutionContext.global
    private var lengthOpt:Option[Int] = None
    private var nextPromiseOpt:Option[Promise[ChannelBuffer]] = None
    @throws(classOf[Exception])
    protected def decode(ctx: ChannelHandlerContext, channel: Channel, buffer: ChannelBuffer): AnyRef = {
      val attachment = channel.getAttachment.asInstanceOf[ChannelAttachment]
      if(attachment.status == RequestNotHandled){
        val bytesNum = buffer.readableBytes()
        return buffer.readBytes(bytesNum)
      }
      lengthOpt match{
        case Some(dataLength)=>
          if(buffer.readableBytes() >= dataLength){
            lengthOpt = None
            val tmpBuffer = buffer.readBytes(dataLength)
            nextPromiseOpt match{
              case Some(promise)=>
                promise.success(tmpBuffer)
                nextPromiseOpt = None
                return null
              case None =>
                return tmpBuffer
            }
          }
        case None if buffer.readableBytes() >= 4=> //第一次读取
          buffer.markReaderIndex()
          val dataLength = buffer.readInt()
          if(dataLength <= 0)
            throw new IllegalArgumentException("data length is zero!")
          lengthOpt = Some(dataLength)
          val attachment = channel.getAttachment.asInstanceOf[ChannelAttachment]
          if(dataLength == TxProxyInboundHandler.DIRECT_REQUEST_LENGTH) {
            buffer.resetReaderIndex()
            attachment.directRequest = true
          }
          else {
            attachment.directRequest = false
            /**
              * 此处向右端服务器发送4个的字节，
              * 然后右端服务器回应4个字节给左端服务器
              * 如果后面的业务能处理此包，则右端收到的4个字节丢弃了
              * 如果后面的业务不能处理此包，则刚好模拟了正常的数据代理
              */
            val dataLengthBuffer = buffer.factory().getBuffer(4)
            dataLengthBuffer.writeInt(dataLength)
            outboundChannel.write(dataLengthBuffer)
          }

          if(buffer.readableBytes() >= dataLength){
            lengthOpt = None //清空之前的操作数据
            return buffer.readBytes(dataLength)
          }
        case other=>
          //continue read buffer ...
      }

      null
    }

    /**
      * 把收到的数据帧转换为 GBASE_ITEMPKG_OPSTRUCT结构
      */
    class GBASE_ITEMPKG_OPSTRUCTDecoder extends OneToOneDecoder{
      override def decode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
        val attachment = channel.getAttachment.asInstanceOf[ChannelAttachment]

        msg match{
          case buffer:ChannelBuffer if attachment.status == RequestNotHandled =>
            val len = buffer.readableBytes()
            buffer.readBytes(len)
          case buffer:ChannelBuffer if attachment.status == RequestHandled =>
            if(buffer.readableBytes() == TxProxyInboundHandler.DIRECT_REQUEST_LENGTH) {
              val pstPkg = GBASE_ITEMPKG_New
              val pReq = new GNETREQUESTHEADOBJECT().fromStreamReader(buffer)
              GAFIS_PKG_AddRmtRequest(pstPkg,pReq)
              pstPkg
            }else{
              new GBASE_ITEMPKG_OPSTRUCT().fromStreamReader(buffer)
            }
          case other=>
            other.asInstanceOf[AnyRef]
        }
      }
    }

    /**
      * 把收到的GBASE_ITEMPKG_OPSTRUCT结构转换为buffer
      */
    class GBASE_ITEMPKG_OPSTRUCTEncoder extends OneToOneEncoder{
      override def encode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
        msg match{
          case pkg:GBASE_ITEMPKG_OPSTRUCT =>
            //先发四个长度
            val buffer = channel.getConfig.getBufferFactory.getBuffer(4)
            buffer.writeInt(pkg.getDataSize)

            //读取四个长度的promise
            val promise = Promise[ChannelBuffer]()
            promise.future.foreach{intBuffer=>
              val pkgBuffer = channel.getConfig.getBufferFactory.getBuffer(pkg.getDataSize)
              pkg.writeToStreamWriter(pkgBuffer)
              channel.write(pkgBuffer)
              //如果发送完了报文级别的数据，则关闭连接
              TxProxyInboundHandler.closeOnFlush(channel)
            }
            lengthOpt = Some(4)
            nextPromiseOpt = Some(promise)

            buffer
          case (len:Int,promise:Promise[ChannelBuffer])=>
            lengthOpt = Some(len)
            nextPromiseOpt = Some(promise)
            null
          case other=>
            other.asInstanceOf[AnyRef]
        }
      }
    }
  }

}