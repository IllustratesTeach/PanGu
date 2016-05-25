package nirvana.hall.v62.internal.proxy

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.grmtlib.grmtpkg
import nirvana.hall.v62.services.ChannelOperator
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}
import org.jboss.netty.channel.{ChannelStateEvent, Channel, ChannelHandlerContext}
import org.jboss.netty.handler.codec.frame.FrameDecoder
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder

import scala.concurrent.{Await, Promise}
import scala.concurrent.duration._

import scala.reflect._

/**
  * 支持同步调用
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-24
  */
class BlockFrameDecoder extends FrameDecoder with LoggerSupport with gitempkg with grmtpkg{
  @volatile
  private var frameLengthOpt:Option[Int] = None
  private var framePromiseOpt:Option[Promise[ChannelBuffer]] = None
  private var channelOperator:ChannelOperator  = null

  class NettyServerChannel(channel:Channel) extends ChannelOperator {
    override def receiveByteArray(len: Int): ChannelBuffer = {
      frameLengthOpt = Some(len)
      val promise = Promise[ChannelBuffer]()
      framePromiseOpt = Some(promise)
      //TODO 秒数做成可配置
      Await.result(promise.future,30 seconds)
    }
    override def writeByteArray[R <: AncientData:ClassTag](data: Array[Byte], offset: Int, length: Int): R={
      val buffer = ChannelBuffers.wrappedBuffer(data,offset,length)
      //TODO 秒做成配置
      channel.write(buffer)//.await(30,TimeUnit.SECONDS)
      receive[R]()
    }
    override def getServerInfo: String = channel.getRemoteAddress.toString
  }

  override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent): Unit = {
    channelOperator = new NettyServerChannel(ctx.getChannel)
    super.channelConnected(ctx, e)
  }

  override def decode(ctx: ChannelHandlerContext, channel: Channel, buffer: ChannelBuffer): AnyRef = {
    def readBytes(dataLength:Int): Option[ChannelBuffer]={
      if(buffer.readableBytes() >= dataLength){
        Some(buffer.readBytes(dataLength))
      }else None
    }

    frameLengthOpt match{
      case Some(dataLength)=>
        readBytes(dataLength).orNull
      case None if buffer.readableBytes() >= 4 =>  //说明是第一次接受数据
        buffer.markReaderIndex()
        val dataLength = buffer.readInt()
        if(dataLength <= 0)
          throw new IllegalArgumentException("data length is zero!")
        frameLengthOpt = Some(dataLength)
        if(dataLength == 192) //TODO 使用常量，刚好等于 GNETREQUESTHEADOBJECT的长度
          buffer.resetReaderIndex()
        else {
          //向通讯服务器发送数据
          val dataLengthBuffer = buffer.factory().getBuffer(4)
          dataLengthBuffer.writeInt(1)
          channel.write(dataLengthBuffer)
        }

        readBytes(dataLength).orNull
      case other=>
    }

    null
  }
  class AncientDecoder extends OneToOneDecoder{
    override def decode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
      msg match{
        case buffer:ChannelBuffer =>
          framePromiseOpt match {
            case Some(promise) =>
              promise.success(buffer)
            case None =>
              //TODO 使用常量
              val pkg = if (buffer.readableBytes() == 192) {
                val pstPkg = GBASE_ITEMPKG_New
                GAFIS_PKG_AddRmtRequest(pstPkg, new GNETREQUESTHEADOBJECT().fromStreamReader(buffer))
                pstPkg
              } else {
                new GBASE_ITEMPKG_OPSTRUCT().fromStreamReader(buffer)
              }
              //TODO 处理业务逻辑
          }
          null
        case other=>
          other.asInstanceOf[AnyRef]
      }
    }
  }
}
