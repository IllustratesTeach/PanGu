package nirvana.hall.v62.services

import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.fpt.TypeDefinitionProto.MatchType
import nirvana.hall.v62.BaseV62TestCase
import nirvana.hall.v62.internal.QueryServiceImpl
import org.junit.{Assert, Test}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-14
  */
class QueryServiceImplTest extends BaseV62TestCase{
  @Test
  def testQuery: Unit ={
    executeInContext{

      val service  = new QueryServiceImpl(createFacade,null)
      val queryResult = service.queryMatchResultByCardId(20,2,Some("((KeyID LIKE '123'))"),10);
      println(queryResult.size)

    }
  }

  @Test
  def test_findFirstQueryResultByCardId(): Unit ={
    val service = getService[QueryService]
    service.findFirstQueryResultByCardId("123456789")
  }

  @Test
  def test_findFirstQueryStatusByCardIdAndMatchType(): Unit ={
    val service = getService[QueryService]
    val status = service.findFirstQueryStatusByCardIdAndMatchType("1234567890", MatchType.FINGER_TL)
    Assert.assertEquals(status, MatchStatus.WAITING_CHECK)
  }
}
