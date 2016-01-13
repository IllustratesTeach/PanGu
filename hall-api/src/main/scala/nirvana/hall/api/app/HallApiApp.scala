// Copyright 2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package nirvana.hall.api.app

import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.support.services.{JettyServerSupport, SystemEnvDetectorSupport}
import nirvana.hall.api.{HallApiConstants, HallApiModule}
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

    val logger = LoggerFactory getLogger getClass
    logger.info("starting hall api server ....")

    val classes = List[Class[_]](
//      Class.forName("monad.core.ProtobufProcessorModule"),
      Class.forName("nirvana.hall.api.LocalDataSourceModule"),
      Class.forName("nirvana.hall.api.LocalProtobufModule"),
      Class.forName("nirvana.hall.api.LocalApiWebModule"),
      Class.forName("nirvana.hall.api.LocalApiServiceModule"),
      Class.forName("nirvana.hall.api.LocalApiSyncModule"),
      Class.forName("nirvana.hall.v62.LocalV62ServiceModule"),
      Class.forName("nirvana.hall.v62.LocalV62Module"),
      Class.forName("nirvana.hall.orm.HallOrmModule"),
      Class.forName("nirvana.hall.api.HallApiModule"))
    startServer(config.web, "nirvana.hall.api", classes: _*)
    val version = readVersionNumber("META-INF/maven/nirvana/hall-api/version.properties")
    printTextWithNative(logger, HallApiConstants.HALL_TEXT_LOGO, "api@" + config.web.bind, version, 0)
    logger.info("hall api server started")

    join()
  }
}
