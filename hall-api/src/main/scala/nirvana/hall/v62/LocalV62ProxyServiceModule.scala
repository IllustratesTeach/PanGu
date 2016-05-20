package nirvana.hall.v62

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.proxy.filter.{AncientDefaultFilter, AncientUserFilter}
import nirvana.hall.v62.internal.proxy.{GbaseProxyServer, GbaseItemPkgFilter, GbaseItemPkgHandler, GbasePackageHandler}
import org.apache.tapestry5.ioc.annotations.{Startup, Contribute, ServiceId}
import org.apache.tapestry5.ioc.services.{RegistryShutdownHub, PipelineBuilder}
import org.apache.tapestry5.ioc.{OrderedConfiguration, ServiceBinder}
import org.slf4j.Logger

/**
 * Created by songpeng on 16/5/5.
 */
object LocalV62ProxyServiceModule {
  def bind(binder:ServiceBinder): Unit ={

    binder.bind(classOf[GbasePackageHandler], classOf[GbasePackageHandler])
  }
  @Startup
  def startProxyServer(rpcBindSupport: HallV62Config,hub:RegistryShutdownHub,handler: GbasePackageHandler): Unit ={
    if(rpcBindSupport.rpc!=null && rpcBindSupport.rpc.bind.length >0){
      val server = new GbaseProxyServer(rpcBindSupport,handler)
      server.start(hub)
    }
  }
  @ServiceId("GbaseItemPkgHandler")
  def buildGbaseItemPkgHandler(pipelineBuilder: PipelineBuilder, logger: Logger,
                               configuration: java.util.List[GbaseItemPkgFilter])
  : GbaseItemPkgHandler = {
    val terminator = new GbaseItemPkgHandler {
      override def handle(request: GNETREQUESTHEADOBJECT, pkg: GBASE_ITEMPKG_OPSTRUCT): Boolean = false
    }
    pipelineBuilder.build(logger, classOf[GbaseItemPkgHandler], classOf[GbaseItemPkgFilter], configuration, terminator)
  }
  @Contribute(classOf[GbaseItemPkgHandler])
  def provideSegGbaseItemPkgMessageHandler(configuration: OrderedConfiguration[GbaseItemPkgFilter]) {
    configuration.addInstance("AncientUserFilter", classOf[AncientUserFilter])

    //默认Filter，放在最后
    configuration.addInstance("AncientDefaultFilter", classOf[AncientDefaultFilter], "after:*")
  }
}
