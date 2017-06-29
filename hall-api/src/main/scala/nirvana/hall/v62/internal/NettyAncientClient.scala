package nirvana.hall.v62.internal

import java.net.InetSocketAddress
import java.util.concurrent.{Executors, TimeUnit, TimeoutException}

import nirvana.hall.c.services.AncientData
import nirvana.hall.v62.services.{AncientClient, ChannelOperator}
import org.jboss.netty.bootstrap.ClientBootstrap
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}
import org.jboss.netty.channel._
import org.jboss.netty.channel.socket.oio.OioClientSocketChannelFactory
import org.jboss.netty.handler.queue.BlockingReadHandler

import scala.annotation.tailrec
import scala.reflect.ClassTag

/**
  * netty ancient client
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-06-22
  */
private[internal] object NettyAncientClient{
  val bootstrap = new ClientBootstrap(
    new OioClientSocketChannelFactory(Executors.newCachedThreadPool())
  )
  bootstrap.setOption("keepAlive", true)
  bootstrap.setOption("tcpNoDelay", true)
}
class NettyAncientClient(host:String,port:Int,connectionTimeoutSecs:Int,readTimeoutSecs:Int) extends AncientClient{
  override def executeInChannel[T](action: (ChannelOperator) => T): T = {
    val reader = new BlockingReadHandler[ChannelBuffer](){
      override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
        e.getMessage match{
          case buffer:ChannelBuffer=>
//            println(buffer.readableBytes())
            getQueue.put(e)
          case other=>
            //discard some message
//            println("other",other)
        }
      }
    }
    NettyAncientClient.bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      def getPipeline = {
        val pipeline = Channels.pipeline()
        pipeline.addLast("handler", reader)
        pipeline
      }
    })
    val channelFuture = NettyAncientClient.bootstrap.connect(new InetSocketAddress(host, port))
    if(channelFuture.await(connectionTimeoutSecs,TimeUnit.SECONDS)){
      try {
        val operator = new NettyChannelOperator(channelFuture.getChannel, reader)
        action(operator)
      }finally{
        //关闭连接
        channelFuture.getChannel.close()
      }
    }else{
      throw new TimeoutException("timeout[%s]s to connect %s:%s".format(connectionTimeoutSecs,host,port))
    }
  }
  private class NettyChannelOperator(channel:Channel,reader:BlockingReadHandler[ChannelBuffer]) extends ChannelOperator{
    protected var cumulation: ChannelBuffer = null
    /**
      * 得到服务器信息
      *
      * @return
      */
    override def getServerInfo: String = channel.getRemoteAddress.toString

    override def receiveByteArray(len: Int): ChannelBuffer = {
      def readBufferFromCumulation: ChannelBuffer= {
        var result:ChannelBuffer = null
        //如果累积的字节数达到了需要的字节数,则进行读取操作
        if(cumulation != null && cumulation.readableBytes()>=len) {
          result = cumulation.readBytes(len)
          //如果可读的字节数为0了,则把累计值置为0
          if (cumulation.readableBytes() == 0)
            cumulation = null
        }

        result
      }
      @tailrec
      def loopRead:ChannelBuffer =
      {
        var result:ChannelBuffer = null
        //先从累计值中读取
        if(cumulation != null && cumulation.readableBytes()>=len) {
          result = readBufferFromCumulation
//          println("f:",cumulation,result)
        }
        //读取数据
        if(result == null){
          val buffer = reader.read(readTimeoutSecs, TimeUnit.SECONDS)
          if(cumulation == null)
            cumulation = buffer
          else if(buffer != null)
            cumulation = ChannelBuffers.wrappedBuffer(cumulation,buffer)

          result = readBufferFromCumulation
//          println("s:",cumulation,result)
        }

        if(result == null) loopRead else result
      }
      loopRead
    }

    /**
      * write byte array to channel
      *
      * @param data   data be sent
      * @param offset byte array offset
      * @param length data length
      * @tparam R return type
      * @return data from server
      */
    override def writeByteArray[R <: AncientData : ClassTag](data: Array[Byte], offset: Int, length: Int): R = {
      channel.write(channel.getConfig.getBufferFactory.getBuffer(data,offset,length))
      receive[R]()
    }
  }
}
