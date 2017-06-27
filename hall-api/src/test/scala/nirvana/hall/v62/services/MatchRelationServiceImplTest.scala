package nirvana.hall.v62.services

import nirvana.hall.api.services.{ExceptRelationService, MatchRelationService}
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
  def test_exportMatchRelation(): Unit ={

    val service = getService[ExceptRelationService]
    val relation = service.exportMatchRelation("0","453")
    println("asa")

  }

  @Test
  def test_isExist(): Unit ={
    val service = getService[MatchRelationService]
    Assert.assertEquals(service.isExist("test0329094724000000001"
                      ,Option("21")),true)
  }

}
