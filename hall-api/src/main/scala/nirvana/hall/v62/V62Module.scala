package nirvana.hall.v62

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.v62.config.HallV62Config
import org.apache.tapestry5.ioc.annotations.Symbol

/**
 * module for v62 system
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
object V62Module {
  def buildHallV62Config(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-v62.xml")
    XmlLoader.parseXML[HallV62Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v62/v62.xsd")))
  }
}
