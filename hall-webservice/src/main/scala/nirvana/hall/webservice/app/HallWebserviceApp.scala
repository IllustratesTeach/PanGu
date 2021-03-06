package nirvana.hall.webservice.app

import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.support.services.{JettyServerSupport, SystemEnvDetectorSupport}
import nirvana.hall.extractor.jni.JniLoader
import nirvana.hall.support.HallSupportConstants
import nirvana.hall.webservice.{HallWebserviceConstants, HallWebserviceSymbol}
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
    val config = HallWebserviceSymbol.buildHallWebserviceConfig(serverHome)
    configLogger(config.logFile, "webservice", "egf", "nirvana.hall")
    //加载提取特征JNI
    JniLoader.loadJniLibrary(serverHome,config.logFile)

    val logger = LoggerFactory getLogger getClass
    logger.info("starting hall webservice server ....")

    val classes = List[Class[_]](
      //v62
      Class.forName("nirvana.hall.v62.LocalV62Module"),
      Class.forName("nirvana.hall.v62.LocalV62ServiceModule"),
      Class.forName("stark.activerecord.StarkActiveRecordModule"),
      Class.forName("nirvana.hall.v62.LocalV62DataSourceModule"),
      //v70
//      Class.forName("nirvana.hall.v70.LocalV70Module"),
//      Class.forName("nirvana.hall.v70.LocalV70ServiceModule"),
//      Class.forName("stark.activerecord.StarkActiveRecordModule"),
//      Class.forName("nirvana.hall.v70.LocalDataSourceModule"),

      //v70贵州现勘
//     Class.forName("nirvana.hall.v70.gz.LocalV70Module"),
//     Class.forName("nirvana.hall.v70.gz.LocalV70ServiceModule"),
//     Class.forName("nirvana.hall.v70.gz.LocalDataSourceModule"),
//     Class.forName("stark.activerecord.StarkActiveRecordModule"),
//     Class.forName("nirvana.hall.webservice.HallWebserviceSurveyModule"),

      Class.forName("nirvana.hall.webservice.HallWebserviceSurvey62Module"),

      //公用
      Class.forName("monad.rpc.LocalRpcModule"),
      Class.forName("nirvana.hall.api.LocalProtobufModule"),
//      Class.forName("stark.webservice.StarkWebServiceModule"), //Tapestry会自动加载StarkWebServiceModule
      Class.forName("nirvana.hall.webservice.HallWebserviceSymbol"),
      Class.forName("nirvana.hall.webservice.HallWebserviceModule")


    )

    //加载额外module
    val extraModules = System.getProperty(HallWebserviceConstants.EXTRA_MODULE_CLASS)
    logger.info("extraModules: "+extraModules)
    val finalClasses =  if(extraModules!= null)
      extraModules.split(",").map(Class.forName) ++:  classes
    else classes

    startServer(config.web, "nirvana.hall.webservice", finalClasses: _*)
    val version = readVersionNumber("META-INF/maven/nirvana/hall-webservice/version.properties")
    printTextWithNative(logger, HallSupportConstants.HALL_TEXT_LOGO, "webservice@" + config.web.bind, version, 0)
    logger.info("hall webservice server started")

    join()
  }
}
