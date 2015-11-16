package nirvana.hall.v62.internal.filter

import monad.support.services.XmlLoader
import nirvana.hall.v62.config.HallV62Config

import scala.io.Source

/**
 * Created by songpeng on 15/11/16.
 */
object TestModule {
  def buildHallV62Config={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-v62.xml"),"utf8").mkString
    XmlLoader.parseXML[HallV62Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v62/v62.xsd")))
  }

}
