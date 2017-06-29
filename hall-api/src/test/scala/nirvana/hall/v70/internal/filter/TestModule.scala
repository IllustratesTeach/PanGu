package nirvana.hall.v70.internal.filter

import monad.support.services.XmlLoader
import nirvana.hall.v70.config.HallV70Config
import org.apache.tapestry5.ioc.Configuration

import scala.io.Source

/**
 * Created by songpeng on 15/11/16.
 */
object TestModule {
  def buildHallV70Config={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-v70.xml"),"utf8").mkString
    XmlLoader.parseXML[HallV70Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v70/v70.xsd")))
  }

  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.v70.jpa")
  }
//  def bind(binder: ServiceBinder): Unit = {
//    binder.bind(classOf[RpcHttpClient],classOf[RpcHttpClientImpl]).withId("RpcHttpClient")
//  }
}
