package nirvana.hall.api.internal.query

import nirvana.hall.api.services.query.Query7to6Service
import org.apache.tapestry5.ioc.RegistryBuilder
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/12/28.
 */
class Query7to6ServiceImplTest {
  val modules = Seq[String](
    "nirvana.hall.api.LocalDataSourceModule",
    "nirvana.hall.api.LocalProtobufModule",
    "nirvana.hall.api.LocalApiServiceModule",
    "nirvana.hall.api.LocalApiSyncModule",
    "nirvana.hall.orm.HallOrmModule",
    "nirvana.hall.api.internal.JpaTestModule",
    "nirvana.hall.v62.LocalV62ServiceModule",
    "nirvana.hall.v62.internal.filter.TestModule"
    ).map(Class.forName)
  val registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
  @Test
  def test_sendQuery(): Unit ={
    val service = registry.getService(classOf[Query7to6Service])

    val task = service.getMatchTask

    Assert.assertNotNull(task)

    service.sendQuery(task.get);

  }
}
