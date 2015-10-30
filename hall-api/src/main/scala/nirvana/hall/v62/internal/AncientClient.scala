package nirvana.hall.v62.internal

import java.net.InetSocketAddress
import java.util.concurrent._
import java.util.concurrent.atomic.AtomicInteger

import monad.support.services.{LoggerSupport, MonadException}
import nirvana.hall.api.services.HallExceptionCode.FAIL_TO_FIND_CHANNEL
import org.jboss.netty.bootstrap.ClientBootstrap
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.channel._
import org.jboss.netty.channel.socket.nio.{NioClientSocketChannelFactory, NioWorkerPool}
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder

import scala.annotation.tailrec
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
object AncientClient extends LoggerSupport{
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
    //val executionHandler = new ExecutionHandler(executor,false,true)
    /*
    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      def getPipeline = {
        val pipeline = Channels.pipeline()
        pipeline.addLast("ancient-data-encoder", new AncientDataEncoder)
        pipeline.addLast("exception",new SimpleChannelUpstreamHandler(){
          override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent): Unit = {
            error(e.getCause.toString,e.getCause)
          }
        })
        pipeline
      }
    })
    */
  }
  def createClient(host:String,port:Int,concurrent:Int=5): AncientClient={
    new AncientClient(host,port,concurrent)
  }
  def main(args:Array[String]): Unit={
    val client = createClient("10.1.6.119",6898,1)
    client.executeInChannel{channel=>
      val header = new RequestHeader
      //header.nIP="10.1.1.1"
      header.szUserName="afisadmin"
      header.nOpClass = 105
      header.nOpCode= 476
      val response = channel.writeMessage[ResponseHeader](header)
      println(response.nReturnValue)
    }
  }
}
class AncientDataEncoder extends OneToOneEncoder with LoggerSupport{
  override def encode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
    msg match {
      case data: AncientData =>
        debug("data sent")
        val buffer = ctx.getChannel.getConfig.getBufferFactory.getBuffer(data.getDataSize)
        data.writeToChannelBuffer(buffer)
        buffer
      case other =>
        other.asInstanceOf[AnyRef]
    }
  }
}

//channel pool
class AncientClient(host:String,port:Int,concurrent:Int) extends LoggerSupport{
  private val pool = new ArrayBlockingQueue[ChannelHolder](concurrent)
  private val count = new AtomicInteger(0)

  @tailrec
  private def fillPool(): Unit ={
    val countValue = count.get()
    //using cas to control concurrent request
    if(count.get() < concurrent && count.compareAndSet(countValue,countValue+1)) {

      val holder =  new ChannelHolder(new AtomicInteger(0))
      //val executionHandler = new ExecutionHandler(executor,false,true)
      AncientClient.bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
        def getPipeline = {
          val pipeline = Channels.pipeline()
          pipeline.addLast("ancient-data-encoder", new AncientDataEncoder)
          pipeline.addLast("handler",holder)
          pipeline
        }
      })
      val channelFuture = AncientClient.bootstrap.connect(new InetSocketAddress(host, port))
      if (channelFuture.await(10, TimeUnit.SECONDS)) {
        if(!pool.offer(holder,10,TimeUnit.SECONDS)){
          holder.decRef() //关掉当前的channel
          throw new MonadException("overtime 10s to offer channel",FAIL_TO_FIND_CHANNEL)
        }
        channelFuture.getChannel.getCloseFuture.addListener(new ChannelFutureListener {
          override def operationComplete(future: ChannelFuture): Unit = {
            holder.decRef()
          }
        })
      }
    }

    //fill pool until pool is full
    if(count.get() < concurrent)
      fillPool()
  }
  def executeInChannel[T](action:ChannelOperator=>T): T={
    fillPool()
    val channelHolder = pool.poll(5,TimeUnit.SECONDS)
    if(channelHolder == null){
      throw new MonadException("overtime 5s to find channel",FAIL_TO_FIND_CHANNEL)
    }
    try{
      channelHolder.incRef()
      action(channelHolder)
    }finally{
      channelHolder.decRef()
      if(!pool.offer(channelHolder,10,TimeUnit.SECONDS)){
        channelHolder.decRef() //closing the channel if not put pool
      }
    }
  }
  //hold the tcp channel
  class ChannelHolder(ref:AtomicInteger) extends SimpleChannelUpstreamHandler with LoggerSupport with ChannelOperator{
    var channel: Channel = _
    var dataReceiver:Promise[AncientData] = _
    var dataInstance:AncientData  =  _
    override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent): Unit = {
      channel = ctx.getChannel
      info("connected!!")
    }


    override def writeMessage[R <: AncientData](data: AncientData*)(implicit manifest: Manifest[R]): R = {
      dataInstance = manifest.runtimeClass.newInstance().asInstanceOf[AncientData]
      dataReceiver = Promise[AncientData]()
      data.foreach(channel.write)
      Await.result(dataReceiver.future,Duration("10s")).asInstanceOf[R]
    }

    override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
      info("msg:{} received,dataSize:{}",e.getMessage,dataInstance.getDataSize)
      e.getMessage match{
        case buffer:ChannelBuffer =>
          if(buffer.readableBytes() >= dataInstance.getDataSize){
            dataInstance.fromChannelBuffer(buffer)
            dataReceiver.success(dataInstance)
          }
        case other=>
          //donothing
      }

    }


    override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent): Unit = {
      error(e.getCause.toString,e.getCause)
    }


    def incRef(): Unit = {
      if (ref.get() != 0)
        ref.incrementAndGet()
    }

    @tailrec
    final def decRef(): Unit = {
      val refCount = ref.get() - 1
      if (refCount != 0)
        if (ref.compareAndSet(refCount, refCount - 1)) {
          if (refCount - 1 == 0) {
            //need to  closing channel
            pool.remove(this)
            count.decrementAndGet()
            if (channel!= null && channel.isOpen)
              channel.close()
          }
        } else {
          decRef()
        }
    }
  }
}


/**
 * channel operator trait.
 * use it to write message and receive message
 */
trait ChannelOperator{
  def writeMessage[R <: AncientData](data:AncientData*)(implicit manifest: Manifest[R]): R
}

