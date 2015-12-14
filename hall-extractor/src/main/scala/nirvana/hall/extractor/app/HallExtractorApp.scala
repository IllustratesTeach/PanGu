package nirvana.hall.extractor.app

import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.support.services.{JettyServerSupport, SystemEnvDetectorSupport}
import nirvana.hall.extractor.NirvanaHallExtractorModule
import nirvana.hall.extractor.jni.JniLoader
import org.slf4j.LoggerFactory

/**
 * nirvana hall extractor application
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
object HallExtractorApp
  extends JettyServerSupport
  with GlobalLoggerConfigurationSupport
  with SystemEnvDetectorSupport
  with BootstrapTextSupport {
    def main(args: Array[String]) {
      val serverHome = System.getProperty(MonadCoreSymbols.SERVER_HOME, "support")
      System.setProperty(MonadCoreSymbols.SERVER_HOME, serverHome)
      val config = NirvanaHallExtractorModule.buildHallExtractConfig(serverHome)
      configLogger(config.logFile, "EXTRACTOR", "egf", "nirvana","monad")
      JniLoader.loadJniLibrary(serverHome,config.logFile)

      val logger = LoggerFactory getLogger getClass
      logger.info("Starting extractor server ....")
      val classes = List[Class[_]](
        Class.forName("monad.core.LocalMonadCoreModule"),
        Class.forName("monad.rpc.LocalRpcModule"),
        Class.forName("monad.rpc.LocalRpcServerModule"),

        Class.forName("nirvana.hall.protobuf.LocalProtobufWebModule"),

        Class.forName("nirvana.hall.extractor.LocalHallExtractorModule"),
        Class.forName("nirvana.hall.extractor.NirvanaHallExtractorModule")
      )
      startServer(config.web, "nirvana.hall.extract", classes: _*)
      val version = readVersionNumber("META-INF/maven/nirvana/hall-extractor/version.properties")
      printTextWithNative(logger, HALL_TEXT_LOGO, "extractor rpc@" + config.rpc.bind + "web@"+config.web.bind, version, "1.1")
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
