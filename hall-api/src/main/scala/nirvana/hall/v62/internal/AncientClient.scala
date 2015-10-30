package nirvana.hall.v62.internal

import java.net.InetSocketAddress
import java.util.concurrent._
import java.util.concurrent.atomic.AtomicInteger

import monad.support.services.{LoggerSupport, MonadException}
import nirvana.hall.api.services.HallExceptionCode.FAIL_TO_FIND_CHANNEL
import nirvana.hall.v62.AncientConstants
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
  }
  def createClient(host:String,port:Int,concurrent:Int=5): AncientClient={
    new AncientClient(host,port,concurrent)
  }
  def main(args:Array[String]): Unit={
    val client = createClient("10.1.6.119",6898,1)
    client.executeInChannel{channel=>
      val header = new RequestHeader
      header.szUserName="afisadmin"
      header.nOpClass = 105
      header.nOpCode= 476
      header.nDBID = 20
      header.nTableID = 2

      header.bnData1=1
      header.bnData2 = 48
      header.bnData3 = 10

      val response = channel.writeMessage[ResponseHeader](header)
      println("header sent,then return code:{}",response.nReturnValue)

      channel.writeMessage[NoneResponse]("3702022014000002".getBytes(),header.bnData2)

      val pos = 0 until 10 map(x=>(x+1).asInstanceOf[Byte])
      channel.writeMessage[NoneResponse](pos)

      val queryStruct = new QueryStruct
      //fill simple data
      queryStruct.stSimpQry.nQueryType = AncientConstants.TTQUERY.asInstanceOf[Byte]
      queryStruct.stSimpQry.nPriority = 1
      queryStruct.stSimpQry.nFlag = 1
      queryStruct.stSimpQry.stSrcDB.nDBID = 1
      queryStruct.stSimpQry.stSrcDB.nTableID= 2
      queryStruct.stSimpQry.stDestDB.apply(0).nDBID = 1
      queryStruct.stSimpQry.stDestDB.apply(0).nTableID= 2
      queryStruct.stSimpQry.tSubmitTime.tDate.tYear = 115
      queryStruct.stSimpQry.tSubmitTime.tDate.tMonth = 9
      queryStruct.stSimpQry.tSubmitTime.tDate.tDay = 30
      queryStruct.stSimpQry.nDestDBCount = 1


      queryStruct.nItemFlagA = 64


      val response2 = channel.writeMessage[ResponseHeader](queryStruct)
      println("query struct sent,then return code:{}",response2.nReturnValue)
    }
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


    override def writeMessage[R <: AncientData](data: Any*)(implicit manifest: Manifest[R]): R = {
      dataInstance = manifest.runtimeClass.newInstance().asInstanceOf[AncientData]
      if(dataInstance.isInstanceOf[NoneResponse]) {
        data.foreach(channel.write)
        dataInstance.asInstanceOf[R]
      }else{
        dataReceiver = Promise[AncientData]()
        data.foreach(channel.write)
        Await.result(dataReceiver.future,Duration("10s")).asInstanceOf[R]
      }
    }


    override def writeMessage[R <: AncientData](data: Array[Byte], offset: Int, length: Int)(implicit manifest: Manifest[R]): R = {
      val result = new Array[Byte](length)
      val finalLength = math.min(length,data.length-offset)
      System.arraycopy(data,offset,result,offset,finalLength)

      writeMessage(result)
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
      ctx.getChannel.close()
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
  def writeMessage[R <: AncientData](data:Any*)(implicit manifest: Manifest[R]): R
  def writeMessage[R <: AncientData](data:Array[Byte],offset:Int=0,length:Int = -1)(implicit manifest: Manifest[R]): R
}

