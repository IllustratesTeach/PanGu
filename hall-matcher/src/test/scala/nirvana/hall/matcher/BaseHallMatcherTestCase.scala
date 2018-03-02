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
      Class.forName("nirvana.hall.matcher.HallMatcherDataSourceModule")
    )
    val config = TestHallMatcherModule.buildHallMatcherConfig()
    val extraClasses = config.module match {
      case "gz" => Class.forName(HallMatcherSymobls.SERVICE_MODULE_GZ)
      case "daku" => Class.forName(HallMatcherSymobls.SERVICE_MODULE_DAKU)
      case "sh" => Class.forName(HallMatcherSymobls.SERVICE_MODULE_SH)
      case "gafis6" => Class.forName(HallMatcherSymobls.SERVICE_MODULE_GAFIS6)
      case "nj" => Class.forName(HallMatcherSymobls.SERVICE_MODULE_NJ)
      case other =>
        throw new UnsupportedOperationException(" module "+other.toString+" unsupported")
    }
    registry = RegistryBuilder.buildAndStartupRegistry(classes :+ extraClasses : _*)
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