package nirvana.hall.image.app

import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.support.services.{SystemEnvDetectorSupport, TapestryIocContainerSupport}
import nirvana.hall.image.NirvanaHallImageModule
import nirvana.hall.image.jni.JniLoader
import org.slf4j.LoggerFactory

/**
 * nirvana hall image application
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
object NirvanaHallImageApp
  extends TapestryIocContainerSupport
  with GlobalLoggerConfigurationSupport
  with SystemEnvDetectorSupport
  with BootstrapTextSupport {
    def main(args: Array[String]) {
      val serverHome = System.getProperty(MonadCoreSymbols.SERVER_HOME, "support")
      System.setProperty(MonadCoreSymbols.SERVER_HOME, serverHome)
      val config = NirvanaHallImageModule.buildHallImageConfig(serverHome)
      configLogger(config.logFile, "IMG", "egf", "nirvana","monad")
      JniLoader.loadJniLibrary(serverHome,config.logFile)

      val logger = LoggerFactory getLogger getClass
      logger.info("Starting image server ....")
      val classes = List[Class[_]](
        Class.forName("monad.core.LocalMonadCoreModule"),
        Class.forName("monad.rpc.LocalRpcModule"),
        Class.forName("monad.rpc.LocalRpcClientModule"),
        Class.forName("monad.rpc.LocalRpcServerModule"),


        Class.forName("nirvana.hall.image.LocalHallImageModule"),
        Class.forName("nirvana.hall.image.NirvanaHallImageModule")
      )
      startUpContainer(classes: _*)
      val version = readVersionNumber("META-INF/maven/nirvana/hall-image/version.properties")
      printTextWithNative(logger, HALL_TEXT_LOGO, "image@" + config.rpc.bind, version, "1.1")
      logger.info("image server started")

      join()
    }
  final val HALL_TEXT_LOGO = """ #
   __ _____   __   __
  / // / _ | / /  / /
 / _  / __ |/ /__/ /__ module : |@@|red %s|@#
/_//_/_/ |_/____/____/ version: |@@|yellow %s|@
                             """.replaceAll("#", "@|green ")
}
