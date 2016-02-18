package nirvana.hall.v70.internal.query

import nirvana.hall.v70.services.query.Query7to6Service
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/12/28.
 */
class Query7to6ServiceImplTest {
  private val modules = Seq[String](
    "nirvana.hall.api.LocalProtobufModule",
    "nirvana.hall.api.LocalApiServiceModule",
    "nirvana.hall.orm.HallOrmModule",
    "nirvana.hall.v70.internal.filter.TestModule",
    "nirvana.hall.v70.LocalV70ServiceModule",
    "nirvana.hall.v70.LocalDataSourceModule"
  ).map(Class.forName)

  protected var registry:Registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
  @Test
  def test_sendQuery(): Unit ={
    val service = registry.getService(classOf[Query7to6Service])

    val task = service.getGafisNormalqueryQueryque

    Assert.assertNotNull(task)

    service.sendQuery(task.get);

  }
}
