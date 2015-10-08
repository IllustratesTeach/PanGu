package nirvana.hall.api

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.api.config.HallApiConfig
import org.apache.tapestry5.ioc.annotations.Symbol

/**
 * hall api module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-04-02
 */
object HallApiModule {
  def buildHallApiConfig(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-api.xml")
    XmlLoader.parseXML[HallApiConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/api/api.xsd")))
  }
}
