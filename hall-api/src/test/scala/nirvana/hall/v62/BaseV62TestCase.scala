package nirvana.hall.v62

import javax.persistence.EntityManagerFactory

import com.google.protobuf.ExtensionRegistry
import monad.rpc.services.ProtobufExtensionRegistryConfiger
import monad.support.services.XmlLoader
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.AuthServiceImpl
import nirvana.hall.api.internal.fpt.FPTServiceImpl
import nirvana.hall.api.services.AuthService
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.services.V62ServerAddress
import org.apache.tapestry5.ioc.annotations.EagerLoad
import org.apache.tapestry5.ioc.{Configuration, Registry, RegistryBuilder, ServiceBinder}
import org.junit.{After, Before}
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.support.TransactionSynchronizationManager

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
      "stark.activerecord.StarkActiveRecordModule",
      "nirvana.hall.api.LocalProtobufModule",
      "nirvana.hall.v62.LocalV62ServiceModule",
      "nirvana.hall.v62.LocalV62DataSourceModule",
      //LiaoNing
//      "nirvana.hall.v62.LocalV62LiaoNingServiceModule",
//      "nirvana.hall.api.LocalApiWebServiceModule",
      "nirvana.hall.v62.TestV62Module"
    ).map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
    val entityManagerFactory= getService[EntityManagerFactory]
    val emHolder= new EntityManagerHolder(entityManagerFactory.createEntityManager())
    TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder)
  }
  @After
  def down: Unit ={
    val emf: EntityManagerFactory = registry.getService(classOf[EntityManagerFactory])
    val emHolder: EntityManagerHolder = TransactionSynchronizationManager.unbindResource(emf).asInstanceOf[EntityManagerHolder]
    EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager)

    registry.shutdown()
  }

  def createFacade:V62Facade = {
    val config = new HallV62Config
    config.appServer.host = "192.168.0.214"
    config.appServer.port = 6898
    config.appServer.user = "afisadmin"
    config.appServer.password=""
    new V62Facade(config)
  }
  def executeInContext[T](function: => T): Unit ={
    val config = new HallV62Config
    config.appServer.host = "192.168.0.214"
    config.appServer.port = 6898
    config.appServer.user = "afisadmin"
    config.appServer.password=""

    val passwordOpt = if(config.appServer.password.isEmpty) None else Some(config.appServer.password)

    val address = V62ServerAddress(
      config.appServer.host,
      config.appServer.port,
      config.appServer.connectionTimeoutSecs,
      config.appServer.readTimeoutSecs,
      config.appServer.user,
      passwordOpt)
    V62Facade.serverContext.withValue(address){
     function
    }
  }
}

object TestV62Module {
  def buildHallV62Config={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-v62.xml"),"utf8").mkString
    XmlLoader.parseXML[HallV62Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v62/v62.xsd")))
  }
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[AuthService], classOf[AuthServiceImpl])
    binder.bind(classOf[FPTService], classOf[FPTServiceImpl])
  }
  def buildHallApiConfig={
    new HallApiConfig
  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.api.jpa")
  }
  @EagerLoad
  def buildProtobufRegistroy(configruation: java.util.Collection[ProtobufExtensionRegistryConfiger]) = {
    val registry = ExtensionRegistry.newInstance()
    val it = configruation.iterator()
    while (it.hasNext)
      it.next().config(registry)

    registry
  }
}