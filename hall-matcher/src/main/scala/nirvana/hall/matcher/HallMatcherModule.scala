package nirvana.hall.matcher

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.matcher.config.HallMatcherConfig
import org.apache.tapestry5.ioc.annotations.Symbol

/**
 * Created by songpeng on 16/3/25.
 */
object HallMatcherModule {

 def buildHallMatcherConfig(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) ={
   val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-matcher.xml")
   XmlLoader.parseXML[HallMatcherConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/matcher/matcher.xsd")))
 }
}
