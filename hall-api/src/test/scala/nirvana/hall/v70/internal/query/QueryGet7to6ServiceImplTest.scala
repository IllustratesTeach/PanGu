package nirvana.hall.v70.internal.query

import nirvana.hall.v70.internal.BaseV70TestCase
import nirvana.hall.v70.services.query.QueryGet7to6Service
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/12/28.
 */
class QueryGet7to6ServiceImplTest extends BaseV70TestCase{
  @Test
  def test_getQuery(): Unit = {
    val service = getService[QueryGet7to6Service]

    val task = service.getGafisNormalqueryQueryqueMatching();

    Assert.assertNotNull(task)

    service.getQueryAndSaveMatchResult(task.get)

  }
}
