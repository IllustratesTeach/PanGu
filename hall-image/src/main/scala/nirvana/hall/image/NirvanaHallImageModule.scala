package nirvana.hall.image

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.image.config.HallImageConfig
import org.apache.tapestry5.ioc.annotations.Symbol

/**
 * image module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-09
 */
object NirvanaHallImageModule {
  def buildHallImageConfig(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-image.xml")
    XmlLoader.parseXML[HallImageConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/image/image.xsd")))
  }
}
