package nirvana.hall.v62.services

import nirvana.hall.api.services.QueryService
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_ITEMSTRUCT
import nirvana.hall.c.services.gloclib.gqrycond.GAFIS_QRYPARAM
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.BaseV62TestCase
import nirvana.hall.v62.internal.QueryServiceImpl
import org.jboss.netty.buffer.ChannelBuffers
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
      val queryResult = service.findSimpleQuery(20,Some("((KeyID LIKE '123'))"),10)
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

  @Test
  def test_getMatchResult: Unit ={
    val service = getService[QueryService]
    val matchResult = service.getMatchResult(854).get
    Assert.assertTrue(matchResult.getCandidateNum > 0)
  }

  @Test
  def test_sendQuery: Unit ={
    val matchTask = MatchTask.newBuilder()
    matchTask.setMatchId("1234567890")
    matchTask.setMatchType(NirvanaTypeDefinition.MatchType.FINGER_TT)
    matchTask.setPriority(1)
    matchTask.setScoreThreshold(50)
    matchTask.setObjectId(1)

    val service = getService[QueryService]
    val oraSid = service.sendQuery(matchTask.build())
    Assert.assertTrue(oraSid > 0)
  }

  @Test
  def sendQueryByCardIdAndMatchType: Unit ={
    val service = getService[QueryService]
    val oraSid = service.sendQueryByCardIdAndMatchType("1234567890", MatchType.FINGER_TT)
    Assert.assertTrue(oraSid > 0)
  }

  @Test
  def test_getQuery: Unit ={
    val service = getService[QueryService]
    val matchResult = service.getMatchResult(1)
    Assert.assertNotNull(matchResult)
  }

  @Test
  def test_getGAQUERYSTRUCT: Unit ={
    val service = getService[QueryService]
    val query = service.getGAQUERYSTRUCT(2)
    Assert.assertNotNull(query)
    val param = new GAFIS_QRYPARAM()
    val item = new GBASE_ITEMPKG_ITEMSTRUCT()
    val qryCondData = query.pstQryCond_Data
    val arr = new Array[Byte](qryCondData.length - 64)
    System.arraycopy(qryCondData, 64, arr, 0, qryCondData.length -64)
    println(qryCondData.length)
    val buffer = ChannelBuffers.buffer(qryCondData.length)
    buffer.writeBytes(qryCondData)
    param.fromStreamReader(buffer)
    item.fromByteArray(arr)
    println(item.stHead.nItemLen)
    println(new String(item.bnRes))
  }

  @Test
  def test_getStatusBySid: Unit ={
    val service = getService[QueryService]
    val statusId = service.getStatusBySid(327)
    Assert.assertEquals(7,statusId)
  }

}
