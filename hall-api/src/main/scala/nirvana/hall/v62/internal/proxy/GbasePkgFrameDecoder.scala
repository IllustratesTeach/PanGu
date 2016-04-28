package nirvana.hall.v62.internal.proxy

import java.util.concurrent.atomic.AtomicBoolean

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.channel.{Channel, ChannelHandlerContext}
import org.jboss.netty.handler.codec.frame.FrameDecoder
import org.jboss.netty.handler.codec.oneone.{OneToOneEncoder, OneToOneDecoder}

/**
  * 自动解析 GBASE_ITEMPKG_OPSTRUCT 的总长度
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
class GbasePkgFrameDecoder extends FrameDecoder{
  private var lengthOpt:Option[Int] = None
  private val sendingFlag = new AtomicBoolean(false)
  private var waitingPkg:GBASE_ITEMPKG_OPSTRUCT = null
  @throws(classOf[Exception])
  protected def decode(ctx: ChannelHandlerContext, channel: Channel, buffer: ChannelBuffer): AnyRef = {
    if(sendingFlag.get){
      if(buffer.readableBytes() >= 4){ //读取客户端响应的数据长度
        val length = buffer.readInt
        if(length <0){
          throw new IllegalStateException("server return data length less than zero")
        }
        val pkgBuffer = channel.getConfig.getBufferFactory.getBuffer(waitingPkg.getDataSize)
        waitingPkg.writeToStreamWriter(pkgBuffer)
        channel.write(pkgBuffer)
        sendingFlag.set(false)
        waitingPkg = null
      }
      return null
    }
    lengthOpt match{
      case Some(dataLength)=>
        if(buffer.readableBytes() >= dataLength){
          return buffer.readBytes(dataLength)
        }
      case None if buffer.readableBytes() >= 4=>
        val dataLength = buffer.readInt()
        lengthOpt = Some(dataLength)
        if(dataLength <= 0)
          throw new IllegalArgumentException("data length is zero!")

        //向通讯服务器发送数据
        val dataLengthBuffer = buffer.factory().getBuffer(4)
        dataLengthBuffer.writeInt(dataLength)
        channel.write(dataLengthBuffer)

        if(buffer.readableBytes() >= dataLength){
          lengthOpt = None //清空之前的操作数据
          return buffer.readBytes(dataLength)
        }
      case other=>
    }

    null
  }
  class GbasePkgDecoder extends OneToOneDecoder{
    override def decode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
      msg match{
        case buffer:ChannelBuffer =>
          new GBASE_ITEMPKG_OPSTRUCT().fromStreamReader(buffer)
        case other=>
          other.asInstanceOf[AnyRef]
      }
    }
  }
  class GbasePkgEncoder extends OneToOneEncoder{
    override def encode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
      msg match{
        case pkg:GBASE_ITEMPKG_OPSTRUCT =>
          sendingFlag.set(true)
          waitingPkg = pkg
          //先发四个长度
          val buffer = channel.getConfig.getBufferFactory.getBuffer(4)
          buffer.writeInt(pkg.getDataSize)

          buffer
        case other=>
          other.asInstanceOf[AnyRef]
      }
    }
  }
}
