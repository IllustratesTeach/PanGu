package nirvana.hall.v62.internal.proxy

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.grmtlib.grmtpkg
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.channel.{Channel, ChannelHandlerContext}
import org.jboss.netty.handler.codec.frame.FrameDecoder
import org.jboss.netty.handler.codec.oneone.{OneToOneDecoder, OneToOneEncoder}

import scala.concurrent.{ExecutionContext, Promise}

/**
  * 自动解析 GBASE_ITEMPKG_OPSTRUCT 的总长度
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
@deprecated
class GbasePkgFrameDecoder extends FrameDecoder with LoggerSupport with gitempkg with grmtpkg{
  private val DIRECT_REQUEST_LENGTH=192
  private implicit val executionContext = ExecutionContext.global
  private var lengthOpt:Option[Int] = None
  private var nextPromiseOpt:Option[Promise[ChannelBuffer]] = None
  @throws(classOf[Exception])
  protected def decode(ctx: ChannelHandlerContext, channel: Channel, buffer: ChannelBuffer): AnyRef = {
    lengthOpt match{
      case Some(dataLength)=>
//        debug("readable length:{} dataLength:{}",buffer.readableBytes(),dataLength)
        if(buffer.readableBytes() >= dataLength){
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
        if(dataLength == DIRECT_REQUEST_LENGTH) {
          buffer.resetReaderIndex()
        }
        else {
          //向通讯服务器发送数据
          val dataLengthBuffer = buffer.factory().getBuffer(4)
          dataLengthBuffer.writeInt(1)
          channel.write(dataLengthBuffer)
        }

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
          if(buffer.readableBytes() == DIRECT_REQUEST_LENGTH) {
            val pstPkg = GBASE_ITEMPKG_New
            val pReq = new GNETREQUESTHEADOBJECT().fromStreamReader(buffer)
            GAFIS_PKG_AddRmtRequest(pstPkg,pReq)
            //192的忽略
            error("===========> direct data for opClass:"+pReq.nOpClass+" opCode:"+pReq.nOpCode)
            pstPkg
          }else{
            new GBASE_ITEMPKG_OPSTRUCT().fromStreamReader(buffer)
          }
        case other=>
          other.asInstanceOf[AnyRef]
      }
    }
  }
  class GbasePkgEncoder extends OneToOneEncoder{
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
          }
          lengthOpt = Some(4)
          nextPromiseOpt = Some(promise)

          buffer
        case ancientData:AncientData =>
          val buffer = channel.getConfig.getBufferFactory.getBuffer(ancientData.getDataSize)
          ancientData.writeToStreamWriter(buffer)

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
