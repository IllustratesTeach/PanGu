package nirvana.hall.v62

import nirvana.hall.api.services._
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal._
import nirvana.hall.v62.internal.proxy.processor.DefaultProcessor
import nirvana.hall.v62.internal.proxy.{AncientPackageProcessorSourceImpl, GbaseProxyServer}
import nirvana.hall.v62.services.{AncientPackageProcessor, AncientPackageProcessorSource}
import org.apache.tapestry5.ioc.annotations.{Contribute, Startup}
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.apache.tapestry5.ioc.{MappedConfiguration, ServiceBinder}

/**
 * local v62 service module
 * Created by songpeng on 15/12/7.
 */
object LocalV62ServiceModule {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[V62Facade])
    binder.bind(classOf[RpcHttpClient],classOf[RpcHttpClientImpl]).withId("RpcHttpClient")
    binder.bind(classOf[TPCardService], classOf[TPCardServiceImpl])
    binder.bind(classOf[LPCardService], classOf[LPCardServiceImpl])
    binder.bind(classOf[CaseInfoService], classOf[CaseInfoServiceImpl])
    binder.bind(classOf[QueryService], classOf[QueryServiceImpl])
    binder.bind(classOf[AncientPackageProcessorSource],classOf[AncientPackageProcessorSourceImpl])
  }
  @Startup
  def startProxyServer(rpcBindSupport: HallV62Config,hub:RegistryShutdownHub,source:AncientPackageProcessorSource): Unit ={
    if(rpcBindSupport.rpc!=null && rpcBindSupport.rpc.bind.length >0){
      val server = new GbaseProxyServer(rpcBindSupport,source)
      server.start(hub)
    }
  }

  @Contribute(classOf[AncientPackageProcessorSource])
  def provideProcessor(configuration: MappedConfiguration[Short, AncientPackageProcessor]){
    /*
    configuration.addInstance(gnopcode.OP_CLASS_USER.toShort, classOf[AncientUserProcessor])
    configuration.addInstance(grmtcode.OP_CLASS_RMTLIB.toShort, classOf[RmtlibProcessor])
    */
    configuration.addInstance(0, classOf[DefaultProcessor])

  }
}
