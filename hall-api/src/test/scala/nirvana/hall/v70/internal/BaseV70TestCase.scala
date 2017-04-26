package nirvana.hall.v70.internal

import javax.persistence.EntityManagerFactory

import com.google.protobuf.ExtensionRegistry
import monad.rpc.services.ProtobufExtensionRegistryConfiger
import monad.support.services.XmlLoader
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.AuthServiceImpl
import nirvana.hall.api.services.AuthService
import nirvana.hall.v70.config.HallV70Config
import org.apache.tapestry5.ioc.annotations.EagerLoad
import org.apache.tapestry5.ioc.{Configuration, Registry, RegistryBuilder, ServiceBinder}
import org.junit.{After, Before}
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.support.TransactionSynchronizationManager

import scala.io.Source
import scala.reflect._

/**
 * Created by songpeng on 16/2/23.
 */
class BaseV70TestCase {
  private var registry:Registry = _
  protected def getService[T:ClassTag]:T={
    registry.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
  @Before
  def setup: Unit ={
    val modules = Seq[String](
      "stark.activerecord.StarkActiveRecordModule",
      "nirvana.hall.v70.LocalV70ServiceModule",
      "nirvana.hall.v70.LocalDataSourceModule",
      "nirvana.hall.api.LocalProtobufModule",
      "nirvana.hall.v70.internal.TestV70Module"
    ).map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
    //OpenSession In Thread
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
}

object TestV70Module{
  def buildHallV70Config={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-v70.xml"),"utf8").mkString
    XmlLoader.parseXML[HallV70Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v70/v70.xsd")))
  }
  def buildHallApiConfig={
    new HallApiConfig
  }
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[AuthService], classOf[AuthServiceImpl])
  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.v70.jpa")
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
