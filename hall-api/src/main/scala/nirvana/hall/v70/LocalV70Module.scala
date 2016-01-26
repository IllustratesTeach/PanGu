package nirvana.hall.v70

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.v70.config.HallV70Config
import org.apache.tapestry5.ioc.Configuration
import org.apache.tapestry5.ioc.annotations.Symbol

/**
 * Created by songpeng on 16/1/25.
 */
object LocalV70Module {
  def buildHallV70Config(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-v70.xml")
    XmlLoader.parseXML[HallV70Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v70/v70.xsd")))
  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.v70.jpa")
  }
}
