package nirvana.hall.webservice.app

import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.support.services.{JettyServerSupport, SystemEnvDetectorSupport}
import nirvana.hall.support.HallSupportConstants
import nirvana.hall.webservice.HallWebserviceModule
import org.slf4j.LoggerFactory

/**
  * Created by songpeng on 2017/4/24.
  */
object HallWebserviceApp
  extends JettyServerSupport
    with GlobalLoggerConfigurationSupport
    with SystemEnvDetectorSupport
    with BootstrapTextSupport {

  def main(args: Array[String]) {
    val serverHome = System.getProperty(MonadCoreSymbols.SERVER_HOME, "support")
    System.setProperty(MonadCoreSymbols.SERVER_HOME, serverHome)
    val config = HallWebserviceModule.buildHallWebserviceConfig(serverHome)
    configLogger(config.logFile, "webservice", "egf", "nirvana.hall")

    val logger = LoggerFactory getLogger getClass
    logger.info("starting hall webservice server ....")

    val classes = List[Class[_]](
      Class.forName("stark.webservice.StarkWebServiceModule")
    )

    startServer(config.web, "nirvana.hall.webservice", classes: _*)
    val version = readVersionNumber("META-INF/maven/nirvana/hall-webservice/version.properties")
    printTextWithNative(logger, HallSupportConstants.HALL_TEXT_LOGO, "webservice@" + config.web.bind, version, 0)
    logger.info("hall webservice server started")

    join()
  }
}
