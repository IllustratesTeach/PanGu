package nirvana.hall.v62.internal.proxy.processor


import monad.support.services.LoggerSupport
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.v62.internal.{NoneResponse, AncientClientSupport}
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}
import nirvana.hall.v62.internal.c.grmtlib.{grmtcsr, grmtpkg}
import nirvana.hall.v62.internal.proxy.AncientPackageProcessorSource
import nirvana.hall.v62.services.{V62ServerAddress, AncientPackageProcessor, ChannelOperator}
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.channel.Channel

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-28
  */
trait BaseAncientProcessor
  extends AncientPackageProcessor
  with LoggerSupport
  with gitempkg
  with grmtcsr
  with grmtpkg
  with gnetcsr
  with reqansop
  with AncientClientSupport{

  override def serverAddress: V62ServerAddress = {throw new UnsupportedOperationException}

  /**
    * execute in channel
    */
  override def executeInChannel[T](channelOperator: (ChannelOperator) => T): T = {
    val channel =AncientPackageProcessorSource.channelContext.value
    channelOperator(new NettyChannelOperator(channel))
  }
}
class NettyChannelOperator(channel:Channel) extends ChannelOperator{
  /**
    * write message to channel
    *
    * @param data     data written
    * @param manifest class reflection
    * @tparam R return type
    * @return server return message object
    */
  override def writeMessage[R <: AncientData](data: Any*)(implicit manifest: Manifest[R]): R = {
    data foreach {
      case pkg:GBASE_ITEMPKG_OPSTRUCT =>
        channel.write(pkg)
      case other=>
        throw new IllegalStateException("only support GBASE_ITEMPKG_OPSTRUCT")
    }
    new NoneResponse().asInstanceOf[R]
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
    * @param manifest class reflection
    * @tparam R return type
    * @return data from server
    */
  override def writeByteArray[R <: AncientData](data: Array[Byte], offset: Int, length: Int)(implicit manifest: Manifest[R]): R = {
    throw new UnsupportedOperationException
  }
  override def writeByteArray[R <: AncientData](data: Array[Byte])(implicit manifest: Manifest[R]): R = {
    channel.write(ChannelBuffers.wrappedBuffer(data))
    new NoneResponse().asInstanceOf[R]
  }
  override def receive[R <: AncientData : ClassManifest](): R = {
    throw new UnsupportedOperationException
  }

  override def receive[R <: AncientData](target: R): R = {
    throw new UnsupportedOperationException
  }
}
