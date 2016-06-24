package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchRelationGetRequest
import nirvana.hall.protocol.fpt.TypeDefinitionProto.MatchType
import nirvana.hall.v62.BaseV62TestCase
import org.junit.Test

/**
 * Created by songpeng on 16/6/21.
 */
class MatchRelationFilterTest extends BaseV62TestCase{

  @Test
  def test_MatchRelationGet(): Unit ={

    val service = getService[MatchRelationService]
    val request = MatchRelationGetRequest.newBuilder()
    request.setCardId("3702000000002016000400")
    request.setMatchType(MatchType.FINGER_TT)
    service.getMatchRelation(request.build())

  }

}
