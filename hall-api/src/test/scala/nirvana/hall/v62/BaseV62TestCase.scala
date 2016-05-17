package nirvana.hall.v62

import monad.support.services.XmlLoader
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{After, Before}

import scala.io.Source
import scala.reflect._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
class BaseV62TestCase {
  private var registry:Registry = _
  protected def getService[T:ClassTag]:T={
    registry.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
  @Before
  def setup: Unit ={
    val modules = Seq[String](
       "nirvana.hall.v62.LocalV62ServiceModule",
      "nirvana.hall.v62.TestV62Module"
    ).map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
  }
  @After
  def down: Unit ={
    registry.shutdown()
  }

  def createFacade:V62Facade ={
    val config = new HallV62Config
    config.host = "10.1.6.119"
    config.port = 6798
    config.user = "afisadmin"
    config.password=""

    new V62Facade
  }
}

object TestV62Module {
  def buildHallV62Config={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-v62.xml"),"utf8").mkString
    XmlLoader.parseXML[HallV62Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v62/v62.xsd")))
  }
}