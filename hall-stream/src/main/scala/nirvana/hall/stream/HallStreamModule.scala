package nirvana.hall.stream

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.stream.config.NirvanaHallStreamConfig
import org.apache.tapestry5.ioc.annotations.Symbol

/**
 * hall stream module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
object HallStreamModule {
  def buildHallExtractConfig(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-stream.xml")
    XmlLoader.parseXML[NirvanaHallStreamConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/stream/stream.xsd")))
  }
}
