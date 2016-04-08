package nirvana.hall.matcher.app

import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.support.services.{JettyServerSupport, SystemEnvDetectorSupport}
import nirvana.hall.matcher.{HallMatcherConstants, HallMatcherModule}
import org.slf4j.LoggerFactory

/**
 * Created by songpeng on 16/3/20.
 */
object HallMatcherApp extends JettyServerSupport
with GlobalLoggerConfigurationSupport
with SystemEnvDetectorSupport
with BootstrapTextSupport{
  def main(args: Array[String]) {
    val serverHome = System.getProperty(MonadCoreSymbols.SERVER_HOME, "support")
    System.setProperty(MonadCoreSymbols.SERVER_HOME, serverHome)
    val config = HallMatcherModule.buildHallMatcherConfig(serverHome)
    configLogger(config.logFile, "WEB", "egf", "hall.matcher")

    val logger = LoggerFactory getLogger getClass
    logger.info("starting hall matcher server ....")
    val classes = List[Class[_]](
      Class.forName("nirvana.hall.matcher.HallMatcherModule"),
      Class.forName("nirvana.hall.matcher.HallMatcherDataSourceModule"),
      Class.forName("nirvana.hall.matcher.HallMatcherServiceModule")
    )

    startServer(config.web, "nirvana.hall.matcher", classes: _*)
    val version = readVersionNumber("META-INF/maven/nirvana/hall-matcher/version.properties")
    printTextWithNative(logger, HallMatcherConstants.NIRVANA_TEXT_LOGO, "web@" + config.web.bind, version, 0)
    logger.info("hall matcher server started")

    join()

  }

}
