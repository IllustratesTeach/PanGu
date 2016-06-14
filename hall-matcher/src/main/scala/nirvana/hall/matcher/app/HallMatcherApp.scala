package nirvana.hall.matcher.app

import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.support.services.{JettyServerSupport, SystemEnvDetectorSupport}
import nirvana.hall.extractor.jni.JniLoader
import nirvana.hall.matcher.{HallMatcherSymobls, HallMatcherConstants, HallMatcherModule}
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
    JniLoader.loadJniLibrary(serverHome,config.logFile)

    val logger = LoggerFactory getLogger getClass
    logger.info("starting hall matcher server ....")
    val classes = List[Class[_]](
      Class.forName("nirvana.hall.matcher.HallMatcherModule"),
      Class.forName("nirvana.hall.matcher.HallMatcherDataSourceModule")
    )

    logger.info("module: "+ config.module)
    val extraClasses = config.module match {
      case "gz" => Class.forName(HallMatcherSymobls.SERVICE_MODULE_GZ)
      case "daku" => Class.forName(HallMatcherSymobls.SERVICE_MODULE_DAKU)
      case "sh" => Class.forName(HallMatcherSymobls.SERVICE_MODULE_SH)
      case "gafis6" => Class.forName(HallMatcherSymobls.SERVICE_MODULE_GAFIS6)
      case other =>
        throw new UnsupportedOperationException(" module "+other.toString+" unsupported")
    }
    startServer(config.web, "nirvana.hall.matcher", classes :+ extraClasses : _*)
    val version = readVersionNumber("META-INF/maven/nirvana/hall-matcher/version.properties")
    printTextWithNative(logger, HallMatcherConstants.NIRVANA_TEXT_LOGO, "web@" + config.web.bind, version, 0)
    logger.info("hall matcher server started")

    join()

  }

}
