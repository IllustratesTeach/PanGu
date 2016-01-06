package nirvana.hall.api.internal

import nirvana.hall.api.config.{HallApiConfig}
import nirvana.hall.api.internal.jdbc.TransactionManagement
import nirvana.hall.api.services.{ProtobufRequestGlobal, UserService}
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{After, Before}
import org.springframework.transaction.PlatformTransactionManager

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
trait BaseServiceTestSupport extends TransactionManagement {
  protected var registry: Registry = _
  protected var token: String = _
  @Before
  def setup: Unit = {
    val modules = Seq[String](
      "nirvana.hall.api.LocalDataSourceModule",
      "nirvana.hall.api.LocalProtobufModule",
      "nirvana.hall.api.LocalApiServiceModule",
      "nirvana.hall.api.internal.TestModule").map(Class.forName)
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

    token = userService.login("jcai", "password")._2.get

    setupTransactionManager(registry.getService(classOf[PlatformTransactionManager]))
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
object TestModule {
  def buildHallApiConfig() = {
    val config = new HallApiConfig
    config.api.db.driver = "org.h2.Driver"
    config.api.db.user = "sa"
    config.api.db.url = "jdbc:h2:file:target/hall/db"

    config
  }
}
