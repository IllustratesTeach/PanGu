package nirvana.hall.extractor

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.extractor.config.HallExtractorConfig
import org.apache.tapestry5.ioc.annotations.Symbol

/**
 * extractor module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-09
 */
object NirvanaHallExtractorModule {
  def buildHallImageConfig(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-extractor.xml")
    XmlLoader.parseXML[HallExtractorConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/extractor/extractor.xsd")))
  }
}
