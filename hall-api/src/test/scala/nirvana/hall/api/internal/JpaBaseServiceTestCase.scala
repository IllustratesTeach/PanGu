package nirvana.hall.api.internal

import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.jdbc.TransactionManagement
import nirvana.hall.api.services.{ProtobufRequestGlobal, UserService}
import org.apache.tapestry5.ioc.{Configuration, Registry, RegistryBuilder}
import org.junit.{After, Before}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
trait JpaBaseServiceTestSupport extends TransactionManagement {
  protected var registry: Registry = _
  protected var token: String = _
  @Before
  def setup: Unit = {
    val modules = Seq[String](
      "nirvana.hall.api.LocalDataSourceModule",
      "nirvana.hall.orm.HallOrmModule",
      "nirvana.hall.api.LocalProtobufModule",
      "nirvana.hall.api.LocalApiServiceModule",
      "nirvana.hall.api.internal.JpaTestModule").map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
    //新建用户

    val userService = registry.getService(classOf[UserService])
    userService.testCreateUser("jcai","password")
    /*
    val registryRequest = RegistryRequest.newBuilder()
    registryRequest.setLogin("jcai")
    registryRequest.setPassword("password")
    registryRequest.setCorporateName("freedom")
    registryRequest.setIndustry(123)
    userService.registry(registryRequest.build())
    */


    /*
    //OpenSession In Thread
    val entityManagerFactory= registry.getService(classOf[EntityManagerFactory])
    val em = registry.getService(classOf[EntityManager])
    val emHolder= new EntityManagerHolder(em)
    TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder)
    */

    token = userService.login("jcai", "password")._2.get
  }

  @After
  def close: Unit = {
    if (registry != null)
      registry.shutdown()
  }

  protected def login(): Unit = {
    registry.getService(classOf[ProtobufRequestGlobal]).store(token)
  }

  protected def logout(): Unit = {
    registry.getService(classOf[ProtobufRequestGlobal]).logout()
  }
}
object JpaTestModule {
  def buildHallApiConfig() = {
    val config = new HallApiConfig
    config.api.db.driver = "org.h2.Driver"
    config.api.db.user = "sa"
    config.api.db.url = "jdbc:h2:mem:db"

    config
  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.api.jpa")
  }
}
