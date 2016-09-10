package nirvana.hall.v70.services

import nirvana.hall.api.services.QueryService
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

/**
 * Created by songpeng on 16/8/31.
 */
class QueryServiceImplTest extends BaseV70TestCase{

  @Test
  def test_getMatchResult: Unit ={
    val service = getService[QueryService]
    val matchResult = service.getMatchResult(1099)
    println(matchResult.get.getCandidateNum)

  }

}
