package nirvana.hall.extractor.app

import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.support.services.{SystemEnvDetectorSupport, TapestryIocContainerSupport}
import nirvana.hall.extractor.NirvanaHallExtractorModule
import nirvana.hall.extractor.jni.JniLoader
import org.slf4j.LoggerFactory

/**
 * nirvana hall extractor application
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
object NirvanaHallExtractorApp
  extends TapestryIocContainerSupport
  with GlobalLoggerConfigurationSupport
  with SystemEnvDetectorSupport
  with BootstrapTextSupport {
    def main(args: Array[String]) {
      val serverHome = System.getProperty(MonadCoreSymbols.SERVER_HOME, "support")
      System.setProperty(MonadCoreSymbols.SERVER_HOME, serverHome)
      val config = NirvanaHallExtractorModule.buildHallImageConfig(serverHome)
      configLogger(config.logFile, "EXTRACTOR", "egf", "nirvana","monad")
      JniLoader.loadJniLibrary(serverHome,config.logFile)

      val logger = LoggerFactory getLogger getClass
      logger.info("Starting extractor server ....")
      val classes = List[Class[_]](
        Class.forName("monad.core.LocalMonadCoreModule"),
        Class.forName("monad.rpc.LocalRpcModule"),
        Class.forName("monad.rpc.LocalRpcClientModule"),
        Class.forName("monad.rpc.LocalRpcServerModule"),


        Class.forName("nirvana.hall.extractor.LocalHallExtractorModule"),
        Class.forName("nirvana.hall.extractor.NirvanaHallExtractorModule")
      )
      startUpContainer(classes: _*)
      val version = readVersionNumber("META-INF/maven/nirvana/hall-extractor/version.properties")
      printTextWithNative(logger, HALL_TEXT_LOGO, "extractor@" + config.rpc.bind, version, "1.1")
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
