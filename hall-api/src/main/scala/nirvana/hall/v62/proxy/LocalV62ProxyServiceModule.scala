package nirvana.hall.v62.proxy

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.config.ProxyServerConfig
import nirvana.hall.v62.proxy.filter.{GAFIS_RMTLIB_LPSVR_ServerFilter, GAFIS_RMTLIB_TPSVR_ServerFilter}
import org.apache.tapestry5.ioc.annotations.{Contribute, ServiceId, Startup, Symbol}
import org.apache.tapestry5.ioc.services.{PipelineBuilder, RegistryShutdownHub}
import org.apache.tapestry5.ioc.{OrderedConfiguration, ServiceBinder}
import org.slf4j.Logger

/**
 * Created by songpeng on 16/5/5.
 */
object LocalV62ProxyServiceModule {
  def bind(binder:ServiceBinder): Unit ={

    binder.bind(classOf[GBASE_ITEMPKG_OPSTRUCTHandler], classOf[GBASE_ITEMPKG_OPSTRUCTHandler])
  }
  def buildProxyServerConfig(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-v62-proxy.xml")
    XmlLoader.parseXML[ProxyServerConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v62/proxy/proxy.xsd")))
  }
  @Startup
  def startProxyServer(config: ProxyServerConfig, hub:RegistryShutdownHub, handler: GBASE_ITEMPKG_OPSTRUCTHandler): Unit ={
      val server = new TxProxyServer(config,handler)
      server.start(hub)
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
//    configuration.addInstance("AncientUserFilter", classOf[AncientUserFilter])
    configuration.addInstance("GAFIS_RMTLIB_TPSVR_ServerFilter", classOf[GAFIS_RMTLIB_TPSVR_ServerFilter])
//    configuration.addInstance("GAFIS_PARAMADM_ServerFilter", classOf[GAFIS_PARAMADM_ServerFilter])
//    configuration.addInstance("GAFIS_RMTLIB_DBSYS_ServerFilter", classOf[GAFIS_RMTLIB_DBSYS_ServerFilter])
    configuration.addInstance("GAFIS_RMTLIB_LPSVR_ServerFilter", classOf[GAFIS_RMTLIB_LPSVR_ServerFilter])




    //默认Filter，放在最后
//    configuration.addInstance("AncientDefaultFilter", classOf[AncientDefaultFilter], "after:*")
  }
}
