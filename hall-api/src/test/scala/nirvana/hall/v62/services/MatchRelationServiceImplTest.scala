package nirvana.hall.v62.services

import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchRelationGetRequest
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.BaseV62TestCase
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 16/9/8.
 */
class MatchRelationServiceImplTest extends BaseV62TestCase{
  @Test
  def test_MatchRelationGet(): Unit ={
    val service = getService[MatchRelationService]
    val request = MatchRelationGetRequest.newBuilder()
    request.setCardId("P3702000000002015129996")
    request.setMatchType(MatchType.FINGER_TT)
    val relation = service.getMatchRelation(request.build())
    println(relation.getMatchStatus)
    println(relation)

  }
  @Test
  def test_MatchRelationGetOfBreakCase(): Unit ={
    val service = getService[MatchRelationService]
    val request = MatchRelationGetRequest.newBuilder()
    request.setCardId("1234567890")
    request.setMatchType(MatchType.FINGER_TL)
    val relation = service.getMatchRelation(request.build())
    println(relation.getMatchStatus)
    println(relation)

  }

  @Test
  def test_isExist(): Unit ={
    val service = getService[MatchRelationService]
    Assert.assertEquals(service.isExist("test0329094724000000001"
                      ,Option("21")),true)
  }

  @Test
  def test_getLogic04Rec: Unit ={
    val service = getService[MatchRelationService]
    val logic04Recs = service.getLogic04Rec("1234567890", false)
    assert(logic04Recs.nonEmpty)
    val logic04Recs2 = service.getLogic04Rec("12345601", true)
    assert(logic04Recs2.nonEmpty)
  }

  @Test
  def test_getLogic05Rec: Unit ={
    val service = getService[MatchRelationService]
    val logic05Recs = service.getLogic05Rec("1234567890")
    assert(logic05Recs.nonEmpty)
  }

  @Test
  def test_getLogic06Rec: Unit ={
    val service = getService[MatchRelationService]
    val logic06Recs = service.getLogic06Rec("12345601")
    assert(logic06Recs.nonEmpty)
  }
}
