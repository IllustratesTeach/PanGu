package nirvana.hall.matcher

import monad.support.services.XmlLoader
import nirvana.hall.matcher.config.HallMatcherConfig
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{After, Before}

import scala.io.Source
import scala.reflect.{ClassTag, _}

/**
  * Created by songpeng on 2017/8/17.
  */
class BaseHallMatcherTestCase {
  private var registry:Registry = _
  protected def getService[T:ClassTag]:T={
    registry.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
  @Before
  def setup: Unit ={
    val classes = List[Class[_]](
      Class.forName("nirvana.hall.matcher.TestHallMatcherModule"),
      Class.forName("nirvana.hall.matcher.HallMatcherGafis6ServiceModule"),
      Class.forName("nirvana.hall.matcher.HallMatcherDataSourceModule")
    )
    registry = RegistryBuilder.buildAndStartupRegistry(classes: _*)
  }

  @After
  def down: Unit ={
    registry.shutdown()
  }
}
object TestHallMatcherModule {
  def buildHallMatcherConfig() ={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-matcher.xml"),"utf8").mkString
    XmlLoader.parseXML[HallMatcherConfig](content, Map[String, String](), xsd = Some(getClass.getResourceAsStream("/nirvana/hall/matcher/matcher.xsd")))
  }
}