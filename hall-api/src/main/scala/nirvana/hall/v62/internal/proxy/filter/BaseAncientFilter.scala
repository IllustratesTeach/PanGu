package nirvana.hall.v62.internal.proxy.filter

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.AncientData
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gloclib.{galocpkg, galoctp}
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}
import nirvana.hall.v62.internal.c.grmtlib.{grmtcsr, grmtpkg, grmtsvr}
import nirvana.hall.v62.internal.proxy.{ChannelThreadContext, GbaseItemPkgFilter}
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}
import nirvana.hall.v62.services.{ChannelOperator, V62ServerAddress}
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}
import org.jboss.netty.channel.Channel

import scala.concurrent.Promise
import scala.reflect.ClassTag

/**
 * Created by songpeng on 16/5/4.
 */
trait BaseAncientFilter extends GbaseItemPkgFilter with LoggerSupport
  with galocpkg
  with gitempkg
  with galoctp
  with grmtcsr
  with grmtsvr
  with grmtpkg
  with gnetcsr
  with reqansop with AncientClientSupport {

  override def serverAddress: V62ServerAddress = {throw new UnsupportedOperationException}
  /**
   * execute in channel
   */
  override def executeInChannel[T](channelOperator: (ChannelOperator) => T): T = {
    val channel = ChannelThreadContext.channelContext.value
    channelOperator(new NettyChannelOperator(channel))
  }
}
class NettyChannelOperator(channel:Channel) extends ChannelOperator {

  /**
    * 得到服务器信息
    *
    * @return
    */
  override def getServerInfo: String = channel.getRemoteAddress.toString

  /**
   * write message to channel
   *
   * @param data     data written
   * @tparam R return type
   * @return server return message object
   */
  override def writeMessage[R <: AncientData:ClassTag](data: Any*): R = {
    data foreach {
      case pkg: AncientData=>
        channel.write(pkg)
      case other =>
        throw new IllegalStateException("only support GBASE_ITEMPKG_OPSTRUCT")
    }
    new NoneResponse().asInstanceOf[R]
  }


  override def writePromise(len: Int, promise: Promise[ChannelBuffer]): Unit = {
    channel.write((len,promise))
  }

  override def receiveByteArray(len: Int): ChannelBuffer = {
    throw new UnsupportedOperationException
  }

  /**
   * write byte array to channel
   *
   * @param data     data be sent
   * @param offset   byte array offset
   * @param length   data length
   * @tparam R return type
   * @return data from server
   */
  override def writeByteArray[R <: AncientData:ClassTag](data: Array[Byte], offset: Int, length: Int): R = {
    throw new UnsupportedOperationException
  }

  override def writeByteArray[R <: AncientData:ClassTag](data: Array[Byte]): R = {
    channel.write(ChannelBuffers.wrappedBuffer(data))
    new NoneResponse().asInstanceOf[R]
  }

  override def receive[R <: AncientData : ClassTag](): R = {
    throw new UnsupportedOperationException
  }

  override def receive[R <: AncientData](target: R): R = {
    throw new UnsupportedOperationException
  }
}