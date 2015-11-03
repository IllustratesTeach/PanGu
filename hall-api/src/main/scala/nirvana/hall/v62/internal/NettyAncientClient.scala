package nirvana.hall.v62.internal

import java.net.InetSocketAddress
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{TimeUnit, ThreadFactory, Executors}

import monad.support.services.{MonadException, LoggerSupport}
import nirvana.hall.api.services.HallExceptionCode.FAIL_TO_FIND_CHANNEL
import nirvana.hall.v62.services.{ChannelOperator, DynamicByteArray, AncientData}
import org.jboss.netty.bootstrap.ClientBootstrap
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.channel._
import org.jboss.netty.channel.socket.nio.{NioWorkerPool, NioClientSocketChannelFactory}
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder

import scala.concurrent.{Await, Promise}
import scala.concurrent.duration.Duration

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
object NettyAncientClient extends LoggerSupport{
  private val executor = Executors.newCachedThreadPool(new ThreadFactory {
    private val seq = new AtomicInteger(0)
    override def newThread(r: Runnable): Thread = {
      val thread = new Thread(r)
      thread.setName("ancient-client-%s".format(seq.incrementAndGet()))

      thread
    }
  })
  private var channelFactory: NioClientSocketChannelFactory = _
  private[internal] var bootstrap: ClientBootstrap = _

  initClient()

  private def initClient(): Unit ={
    channelFactory = new NioClientSocketChannelFactory(executor, 1, new NioWorkerPool(executor, 5))

    bootstrap = new ClientBootstrap(channelFactory)
    // config
    // @see org.jboss.netty.channel.socket.SocketChannelConfig
    bootstrap.setOption("keepAlive", true)
    bootstrap.setOption("tcpNoDelay", true)
    bootstrap.setOption("connectTimeoutMillis", 10000)
  }
  def shutdown():Unit={
    info("closing client factory")
    channelFactory.releaseExternalResources()
    executor.shutdown()
  }
}
class AncientDataEncoder extends OneToOneEncoder with LoggerSupport{
  override def encode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
    msg match {
      case data: AncientData =>
        val buffer = ctx.getChannel.getConfig.getBufferFactory.getBuffer(data.getDataSize)
        data.writeToChannelBuffer(buffer)
        buffer
      case arr:Array[Byte] =>
        ctx.getChannel.getConfig.getBufferFactory.getBuffer(arr,0,arr.length)
      case other =>
        other.asInstanceOf[AnyRef]
    }
  }
}


//channel pool
class NettyAncientClient(host:String,port:Int) extends LoggerSupport{
  private def connect(): ChannelHolder ={
    debug("connecting {} {} ...",host,port)
    val holder =  new ChannelHolder()
    NettyAncientClient.bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      def getPipeline = {
        val pipeline = Channels.pipeline()
        pipeline.addLast("ancient-data-encoder", new AncientDataEncoder)
        pipeline.addLast("handler",holder)
        pipeline
      }
    })
    val channelFuture = NettyAncientClient.bootstrap.connect(new InetSocketAddress(host, port))
    if (!channelFuture.await(10, TimeUnit.SECONDS)) {
      throw new MonadException("overtime 10s to offer channel",FAIL_TO_FIND_CHANNEL)
    }
    holder
  }
  def executeInChannel[T](action:ChannelOperator=>T): T={
    val channelHolder = connect()
    try{
      action(channelHolder)
    }finally{
      channelHolder.close()
    }
  }
  //hold the tcp channel
  class ChannelHolder() extends SimpleChannelUpstreamHandler with LoggerSupport with ChannelOperator{
    private var channel:Channel = _
    private var dataReceiver:Promise[AncientData] = _
    private var dataInstance:AncientData  =  _

    @volatile
    private var buffer:ChannelBuffer = _

    override def writeMessage[R <: AncientData](data: Any*)(implicit manifest: Manifest[R]): R = {
      val tmp = manifest.runtimeClass.newInstance().asInstanceOf[AncientData]
      if(tmp.isInstanceOf[NoneResponse]) {
        data.foreach(channel.write)
        tmp.asInstanceOf[R]
      }else{
        dataReceiver = Promise[AncientData]()
        dataInstance = tmp
        data.foreach(channel.write)
        Await.result(dataReceiver.future,Duration("30s")).asInstanceOf[R]
      }
    }


    override def receive[R <: AncientData]()(implicit manifest: Manifest[R]): R = {
      dataInstance = manifest.runtimeClass.newInstance().asInstanceOf[AncientData]
      internalReceive(dataInstance)
    }
    private def internalReceive[R <: AncientData](dataInstance:AncientData): R ={
      dataReceiver = Promise[AncientData]()
      if(buffer == null || buffer.readableBytes() < dataInstance.getDataSize) {
        //from message received method
        Await.result(dataReceiver.future, Duration("30s")).asInstanceOf[R]
      }else{
        dataInstance.fromChannelBuffer(buffer)
        dataInstance.asInstanceOf[R]
      }

    }
    override def receiveByteArray(len: Int): Array[Byte] = {
      val value = new Array[Byte](len)
      val dataInstance = new DynamicByteArray(value)
      internalReceive(dataInstance)
      value
    }

    override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent): Unit = {
      this.channel = ctx.getChannel
      super.channelConnected(ctx, e)
    }

    override def writeByteArray[R <: AncientData](data: Array[Byte], offset: Int, length: Int)(implicit manifest: Manifest[R]): R = {
      val result = new Array[Byte](length)
      val finalLength = math.min(length,data.length-offset)
      System.arraycopy(data,offset,result,offset,finalLength)

      writeMessage(result)
    }


    override def writeByteArray[R <: AncientData](data: Array[Byte])(implicit manifest: Manifest[R]): R = {
      writeByteArray(data,0,data.length)
    }

    override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
      debug("msg:{} received",e.getMessage)
      e.getMessage match{
        case buffer:ChannelBuffer =>
          if(dataInstance != null) {
            if (buffer.readableBytes() >= dataInstance.getDataSize) {
              dataInstance.fromChannelBuffer(buffer)
              dataReceiver.success(dataInstance)
              dataInstance = null
              dataReceiver = null
            }
          }else{
            this.buffer = buffer
          }
        case other=>
        //donothing
      }

    }


    override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent): Unit = {
      error(e.getCause.toString,e.getCause)
      ctx.getChannel.close()
    }


    final def close(): Unit = {
      if(channel != null)
        channel.close()
    }
  }
}

