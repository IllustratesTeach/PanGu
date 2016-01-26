package nirvana.hall.v70.internal.query

import nirvana.hall.v70.services.query.QueryGet7to6Service
import org.apache.tapestry5.ioc.RegistryBuilder
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/12/28.
 */
class QueryGet7to6ServiceImplTest {
  val modules = Seq[String](
    "nirvana.hall.api.LocalProtobufModule",
    "nirvana.hall.v70.LocalDataSourceModule",
    "nirvana.hall.v70.LocalV70ServiceModule",
    "nirvana.hall.orm.HallOrmModule",
    "nirvana.hall.api.internal.JpaTestModule",
    "nirvana.hall.v62.LocalV62ServiceModule",
    "nirvana.hall.v62.internal.filter.TestModule"
  ).map(Class.forName)
  val registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

  @Test
  def test_getQuery(): Unit = {
    val service = registry.getService(classOf[QueryGet7to6Service])

    val task = service.getGafisNormalqueryQueryqueMatching();

    Assert.assertNotNull(task)

    service.getQueryAndSaveMatchResult(task.get)

  }
}
