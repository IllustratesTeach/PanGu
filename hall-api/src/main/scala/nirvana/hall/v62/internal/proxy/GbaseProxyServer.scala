package nirvana.hall.v62.internal.proxy

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import monad.support.services.{LoggerSupport, MonadException, MonadUtils}
import nirvana.hall.v62.config.V62ProxyBindSupport
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.socket.ServerSocketChannelFactory
import org.jboss.netty.channel.socket.nio.{NioClientSocketChannelFactory, NioServerSocketChannelFactory}
import org.jboss.netty.channel.{Channel, ChannelPipeline, ChannelPipelineFactory, Channels}
import org.jboss.netty.handler.timeout.ReadTimeoutHandler

import scala.util.control.NonFatal

/**
  * gbase proxy server
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
class GbaseProxyServer(rpcBindSupport:V62ProxyBindSupport,handler: GbasePackageHandler) extends LoggerSupport {
  //一个主IO，2个worker
  val ioThread = rpcBindSupport.proxy.ioThread
  val workerThread = rpcBindSupport.proxy.workerThread
  val executor = Executors.newCachedThreadPool() /*.newFixedThreadPool(ioThread + workerThread + 2, new ThreadFactory {
    private val seq = new AtomicInteger(0)

    override def newThread(r: Runnable): Thread = {
      val thread = new Thread(r)
      thread.setName("gbase-proxy-%s".format(seq.incrementAndGet()))
      thread.setDaemon(true)

      thread
    }
  })
  */

  private var channelFactory: ServerSocketChannelFactory = _
  private var bootstrap: ServerBootstrap = _
  private var serverChannel: Option[Channel] = None

  /**
    * 启动对象实例
    */
//  @PostConstruct
  def start(hub: RegistryShutdownHub) {
    val cf = new NioClientSocketChannelFactory(executor, executor);


    channelFactory = new NioServerSocketChannelFactory(executor, executor, workerThread)
    bootstrap = new ServerBootstrap(channelFactory)
    bootstrap.setOption("connectTimeoutMillis", 10000);

    bootstrap.setOption("child.tcpNoDelay", true)
    bootstrap.setOption("child.keepAlive", true)
    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      def getPipeline: ChannelPipeline = {
        val pipeline = Channels.pipeline()
        //解码
        val txHandler = new TxProxyInboundHandler(cf)
        val decoder = new txHandler.GbasePkgFrameDecoder
        pipeline.addLast("timer",new ReadTimeoutHandler(TxProxyInboundHandler.timer,30))
        pipeline.addLast("proxy", txHandler)
        pipeline.addLast("frameDecoder", decoder)
        pipeline.addLast("gbasePkgDecoder", new decoder.GbasePkgDecoder)

        //编码
        pipeline.addLast("AncientDataEncoder", new txHandler.AncientDataEncoder())
        pipeline.addLast("GbasePkgEncoder",new decoder.GbasePkgEncoder)

        //业务逻辑处理
        pipeline.addLast("handler", handler)
        pipeline
      }
    })
    serverChannel = Some(openOnce())
  }

  private def openOnce(): Channel = {
    try {
      val bindTuple = MonadUtils.parseBind(rpcBindSupport.proxy.bind)
      val address = new InetSocketAddress("0.0.0.0", bindTuple._2)
      bootstrap.bind(address)
    } catch {
      case NonFatal(e) =>
        shutdown()
        throw MonadException.wrap(e)
    }
  }

  def registryShutdownListener(hub: RegistryShutdownHub): Unit = {
    hub.addRegistryWillShutdownListener(new Runnable {
      override def run(): Unit = shutdown()
    })
  }

  /**
    * 关闭对象
    */
  def shutdown() {
    info("closing rpc server ...")
    serverChannel.foreach(_.close().awaitUninterruptibly())
    channelFactory.releaseExternalResources()
    MonadUtils.shutdownExecutor(executor, "rpc server executor service")
  }
}
