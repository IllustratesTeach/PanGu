package nirvana.hall.webservice

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.webservice.config.HallWebserviceConfig
import org.apache.tapestry5.ioc.annotations.Symbol

/**
  * Created by yuchen on 2017/9/27.
  */
object HallWebserviceSymbol {
  def buildHallWebserviceConfig(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-webservice.xml")
    XmlLoader.parseXML[HallWebserviceConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/webservice/webservice.xsd")))
  }
}
