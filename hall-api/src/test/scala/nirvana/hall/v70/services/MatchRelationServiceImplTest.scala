package nirvana.hall.v70.services

import java.io.FileOutputStream

import junit.framework.Assert
import nirvana.hall.api.services.MatchRelationService
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchRelationGetRequest
import nirvana.hall.protocol.fpt.TypeDefinitionProto.MatchType
import nirvana.hall.v70.internal.BaseV70TestCase
import org.junit.Test

/**
  * Created by songpeng on 16/11/26.
  */
class MatchRelationServiceImplTest extends BaseV70TestCase{

  @Test
  def test_getMatchRelationTT: Unit ={
    val service = getService[MatchRelationService]
    val requestBuilder = MatchRelationGetRequest.newBuilder()
    requestBuilder.setCardId("P3702000000002015109995")
    requestBuilder.setMatchType(MatchType.FINGER_TT)

    val matchRelation = service.getMatchRelation(requestBuilder.build())
    Assert.assertNotNull(matchRelation)
    println(matchRelation)
  }
  @Test
  def test_getMatchRelationTL: Unit ={
    val service = getService[MatchRelationService]
    val requestBuilder = MatchRelationGetRequest.newBuilder()
    requestBuilder.setCardId("R3702000000002015000004")
    requestBuilder.setMatchType(MatchType.FINGER_TL)

    val matchRelation = service.getMatchRelation(requestBuilder.build())
    Assert.assertNotNull(matchRelation)
    println(matchRelation)
  }
  @Test
  def test_getMatchRelationLT: Unit ={
    val service = getService[MatchRelationService]
    val requestBuilder = MatchRelationGetRequest.newBuilder()
    requestBuilder.setCardId("A333333332222222222222202")
    requestBuilder.setMatchType(MatchType.FINGER_LT)

    val matchRelation = service.getMatchRelation(requestBuilder.build())
    Assert.assertNotNull(matchRelation)
    println(matchRelation)
  }


  @Test
  def test_exportMatchRelation(): Unit ={

    val service = getService[MatchRelationService]
    val relation = service.exportMatchRelation("123","9536") //9536 tt //11017 tl //10816 lt //6322 ll

    val fileOutPutStream = new FileOutputStream("D://123.fpt")
    relation.writeTo(fileOutPutStream)
    fileOutPutStream.flush()
    fileOutPutStream.close()

  }
}
