package nirvana.hall.api.internal.ancientry

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{Executors, ThreadFactory}

import org.jboss.netty.bootstrap.ClientBootstrap
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.channel._
import org.jboss.netty.channel.socket.nio.{NioClientSocketChannelFactory, NioWorkerPool}
import org.jboss.netty.handler.codec.oneone.{OneToOneDecoder, OneToOneEncoder}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
object Client {
  private val executor = Executors.newFixedThreadPool(6, new ThreadFactory {
    private val seq = new AtomicInteger(0)

    override def newThread(r: Runnable): Thread = {
      val thread = new Thread(r)
      thread.setName("rpc-client %s".format(seq.incrementAndGet()))

      thread
    }
  })
  def main(args:Array[String]): Unit ={
    val channelFactory = new NioClientSocketChannelFactory(executor, 1, new NioWorkerPool(executor, 5))

    val bootstrap = new ClientBootstrap(channelFactory)
    // config
    // @see org.jboss.netty.channel.socket.SocketChannelConfig
    bootstrap.setOption("keepAlive", true)
    bootstrap.setOption("tcpNoDelay", true)
    bootstrap.setOption("connectTimeoutMillis", 10000)
    //val executionHandler = new ExecutionHandler(executor,false,true)
    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      def getPipeline = {
        val pipeline = Channels.pipeline()
        pipeline.addLast("handler", new OneToOneEncoder(){
          override def encode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
            val buffer = ctx.getChannel.getConfig.getBufferFactory.getBuffer(192)
            //val buffer = ChannelBuffers.buffer(192)
            buffer.writeInt(192)
            buffer.writeShort(6)
            buffer.writeShort(1)
            buffer.writeBytes(ByteBuffer.allocate(8).put("G@xucg$".getBytes).array())
            buffer.writeBytes(ByteBuffer.allocate(16).put("afisadmin".getBytes).array())
            buffer.writeBytes(ByteBuffer.allocate(16))
            buffer.writeInt(0)
            buffer.writeShort(105)
            buffer.writeShort(476)
            buffer.writeInt(0)
            buffer.writeInt(0)
            buffer.writeBytes(ByteBuffer.allocate(16))
            buffer.writeByte(0)
            buffer.writeByte(0)
            buffer.writeShort(0)

            buffer.writeInt(0) //json length

            buffer.writeInt(0)
            buffer.writeShort(0)
            buffer.writeShort(0)
            buffer.writeBytes(ByteBuffer.allocate(8))
            buffer.writeBytes(ByteBuffer.allocate(24))
            buffer.writeBytes(ByteBuffer.allocate(64))

            //buffer.writeInt(1)
            val array = buffer.array();
            println("length:"+array.length)

            return buffer

          }
        })
        pipeline.addLast("upstream",new OneToOneDecoder {
          override def decode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
            val buf: ChannelBuffer = msg.asInstanceOf[ChannelBuffer]
            if(buf.readableBytes() < 96){
              println("bytes length:"+buf.readableBytes())
              return null;
            }
            println("final length:"+buf.readableBytes())

            println(buf.getInt(20))
            /*
            val result = buf.array()
            println("result[0]:"+result(0))
            val bufferLen = buf.capacity()
            val len = buf.getInt(0)

            println(len)
            */
            "asdf"
          }

        })
        pipeline.addLast("exception",new SimpleChannelUpstreamHandler(){
          override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent): Unit = {
            e.getCause.printStackTrace()
          }
        })

        pipeline
      }
    })

    val channelFuture = bootstrap.connect(new InetSocketAddress("10.1.6.119",6898))
    channelFuture.await()
    if(channelFuture.isSuccess){
      println("conneced")
    }else{
      println("fail")
    }

    channelFuture.getChannel.write("adsf").await()
    //channelFuture.getChannel.write("adsf").await()
    //channelFuture.getChannel.write("adsf").await()
  }
  def convertIntAsTwoBytes(i:Int): Unit ={

  }
}
