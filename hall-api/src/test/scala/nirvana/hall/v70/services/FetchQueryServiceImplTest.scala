package nirvana.hall.v70.services

import nirvana.hall.api.services.sync.FetchQueryService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 16/8/26.
 */
class FetchQueryServiceImplTest extends BaseV70TestCase{

  @Test
  def test_fetchMatchTask: Unit ={
    val service = getService[FetchQueryService]
    /*val matchTask = service.fetchMatchTask(0, 1)
    Assert.assertEquals(matchTask.size, 1)*/
  }

  /*@Test*/
  def test_getMatchResultByQueryid: Unit ={
    val service = getService[FetchQueryService]
/*    val matchResult = service.getMatchResultByQueryid(1)
    println(matchResult.get.getCandidateNum)*/
  }
}
