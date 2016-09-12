package nirvana.hall.v62

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade.AutoSetupServerContextFilter
import org.apache.tapestry5.ioc.OrderedConfiguration
import org.apache.tapestry5.ioc.annotations.{Contribute, Symbol}
import org.apache.tapestry5.services.{HttpServletRequestFilter, HttpServletRequestHandler}

/**
 * v62 module
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-04
 */
object LocalV62Module {
  def buildHallV62Config(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-v62.xml")
    XmlLoader.parseXML[HallV62Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v62/v62.xsd")))
  }
  @Contribute(classOf[HttpServletRequestHandler])
  def provideAutoSetupServerContextFilter(configuration: OrderedConfiguration[HttpServletRequestFilter]): Unit = {
    configuration.addInstance("AutoSetupServerContext", classOf[AutoSetupServerContextFilter], "before:protobuf")
  }
}

