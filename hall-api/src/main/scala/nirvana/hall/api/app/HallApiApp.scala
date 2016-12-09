// Copyright 2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package nirvana.hall.api.app

import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.support.services.{JettyServerSupport, SystemEnvDetectorSupport}
import nirvana.hall.api.jni.JniLoader
import nirvana.hall.api.{HallApiConstants, HallApiModule, HallApiSymbols}
import org.slf4j.LoggerFactory

/**
 * nirvana registry application
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-01-16
 */
object HallApiApp
  extends JettyServerSupport
  with GlobalLoggerConfigurationSupport
  with SystemEnvDetectorSupport
  with BootstrapTextSupport {
  def main(args: Array[String]) {

    val serverHome = System.getProperty(MonadCoreSymbols.SERVER_HOME, "support")
    System.setProperty(MonadCoreSymbols.SERVER_HOME, serverHome)
    val config = HallApiModule.buildHallApiConfig(serverHome)
    configLogger(config.logFile, "API", "egf", "nirvana.hall")

    JniLoader.loadJniLibrary(serverHome,config.logFile)

    val logger = LoggerFactory getLogger getClass
    logger.info("starting hall api server ....")
    val classes = List[Class[_]](
      //v70
//      Class.forName("nirvana.hall.v70.LocalV70Module"),
//      Class.forName("nirvana.hall.v70.LocalV70ServiceModule"),
//      Class.forName("stark.activerecord.StarkActiveRecordModule"),
//      Class.forName("nirvana.hall.v70.LocalDataSourceModule"),
      //v62
//      Class.forName("nirvana.hall.v62.LocalV62Module"),
//      Class.forName("nirvana.hall.v62.LocalV62ServiceModule"),
//      Class.forName("stark.activerecord.StarkActiveRecordModule"),
//      Class.forName("nirvana.hall.v62.LocalV62DataSourceModule"),
//      Class.forName("nirvana.hall.v62.proxy.LocalV62ProxyServiceModule"),
      //公共配置
      Class.forName("monad.core.LocalMonadCoreModule"),
      Class.forName("monad.rpc.LocalRpcModule"),
      Class.forName("monad.rpc.LocalRpcServerModule"),
      Class.forName("nirvana.hall.support.LocalProtobufWebModule"),
      Class.forName("nirvana.hall.api.LocalProtobufModule"),
//      Class.forName("nirvana.hall.api.LocalApiWebServiceModule"),

      Class.forName("nirvana.hall.api.LocalApiServiceModule"),
      Class.forName("nirvana.hall.api.HallApiModule")
    )

    /*
    load other module from system property.
    */
    val extraModules = System.getProperty(HallApiSymbols.API_EXTRA_MODULE_CLASS)
    logger.info("extraModules: "+extraModules)
    val finalClasses =  if(extraModules!= null)
      extraModules.split(",").map(Class.forName) ++:  classes
    else classes

    startServer(config.web, "nirvana.hall.api", finalClasses: _*)
    val version = readVersionNumber("META-INF/maven/nirvana/hall-api/version.properties")
    printTextWithNative(logger, HallApiConstants.HALL_TEXT_LOGO, "api@" + config.web.bind, version, 0)
    logger.info("hall api server started")

    join()
  }
}
