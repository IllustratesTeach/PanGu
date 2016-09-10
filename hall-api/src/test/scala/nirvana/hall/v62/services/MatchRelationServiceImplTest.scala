package nirvana.hall.v62.services

import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchRelationGetRequest
import nirvana.hall.protocol.fpt.TypeDefinitionProto.MatchType
import nirvana.hall.v62.BaseV62TestCase
import org.junit.Test

/**
 * Created by songpeng on 16/9/8.
 */
class MatchRelationServiceImplTest extends BaseV62TestCase{
  @Test
  def test_MatchRelationGet(): Unit ={

    val service = getService[MatchRelationService]
    val request = MatchRelationGetRequest.newBuilder()
    request.setCardId("1100000000002016033334")
    request.setMatchType(MatchType.FINGER_TT)
    val relation = service.getMatchRelation(request.build())
    println(relation.getMatchStatus)

  }
}
