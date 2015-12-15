package nirvana.hall.stream.app

import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.support.services.{SystemEnvDetectorSupport, TapestryIocContainerSupport}
import nirvana.hall.stream.HallStreamModule
import org.slf4j.LoggerFactory

/**
 * hall stream application
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
object HallStreamApp
    extends TapestryIocContainerSupport
    with GlobalLoggerConfigurationSupport
    with SystemEnvDetectorSupport
    with BootstrapTextSupport {
    def main(args: Array[String]) {
      val serverHome = System.getProperty(MonadCoreSymbols.SERVER_HOME, "support")
      System.setProperty(MonadCoreSymbols.SERVER_HOME, serverHome)
      val config = HallStreamModule.buildHallExtractConfig(serverHome)
      configLogger(config.logFile, "EXTRACTOR", "egf", "nirvana","monad")

      val logger = LoggerFactory getLogger getClass
      logger.info("Starting extractor server ....")
      val classes = List[Class[_]](

        Class.forName("nirvana.hall.stream.LocalHallStreamModule"),
        Class.forName("nirvana.hall.stream.HallStreamModule")
      )
      startUpContainer(classes: _*)
      val version = readVersionNumber("META-INF/maven/nirvana/hall-extractor/version.properties")
      printTextWithNative(logger, HALL_TEXT_LOGO, "stream", version, "1.1")
      logger.info("extractor server started")

      join()
    }
    final val HALL_TEXT_LOGO = """ #
   __ _____   __   __
  / // / _ | / /  / /
 / _  / __ |/ /__/ /__ module : |@@|red %s|@#
/_//_/_/ |_/____/____/ version: |@@|yellow %s|@
                               """.replaceAll("#", "@|green ")
  }
