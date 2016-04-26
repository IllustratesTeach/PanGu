package nirvana.hall.matcher

import nirvana.hall.support.services.WebHttpClientUtils
import nirvana.protocol.MatchProgressProto.MatchProgressResponse.MatchProgressStatus
import nirvana.protocol.MatchProgressProto.{MatchProgressRequest, MatchProgressResponse}
import nirvana.protocol.MatchResult.MatchResultResponse.MatchResultResponseStatus
import nirvana.protocol.MatchResult.{MatchResultRequest, MatchResultResponse}
import nirvana.protocol.MatchTaskQueryProto.{MatchTaskQueryRequest, MatchTaskQueryResponse}
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}
import org.junit.{Assert, Test}


/**
 * Created by songpeng on 16/4/5.
 */
class SyncDataTest {

  val url = "http://10.1.6.247:9003"
  @Test
  def testSyncData(): Unit ={
     val syncUrl = url+"/syncData"
    val request = SyncDataRequest.newBuilder()
    val response = SyncDataResponse.newBuilder()
    request.setSize(100)
    request.setSyncDataType(SyncDataType.TEMPLATE_FINGER)
    var timestamp:Long = 0
    var total = 0
    (1 to 3).foreach{i=>
      request.setTimestamp(timestamp)
      response.clear()
      WebHttpClientUtils.call(syncUrl, request.build(), response)
      val count = response.getSyncDataCount
      total += count
      timestamp = response.getSyncDataList.get(count-1).getTimestamp
      println("count:"+ count + " timestamp:"+ timestamp)
    }
    response.clear()
    request.setTimestamp(0)
    request.setSize(total)
    WebHttpClientUtils.call(syncUrl, request.build(), response)
    val count = response.getSyncDataCount
    val maxSeq = response.getSyncDataList.get(count-1).getTimestamp
    println("count:"+ count + " max seq:"+ maxSeq)

    Assert.assertEquals(total, count)
    Assert.assertEquals(maxSeq, timestamp)

  }

  @Test
  def testGetMatchTask(): Unit ={
    val getMatchTaskUrl = url + "/getMatchTaskQuery"
    val request = MatchTaskQueryRequest.newBuilder()
    request.setSize(1)
    val response = MatchTaskQueryResponse.newBuilder()
    WebHttpClientUtils.call(getMatchTaskUrl, request.build(), response)
    println(response.getMatchTaskCount)
  }

  @Test
  def testPutMatchProgress: Unit ={
    val putMatchProgressUrl = url + "/putMatchProgress"
    val request = MatchProgressRequest.newBuilder()
    request.setMatchId("214")
    request.setProgress(100)
    val response = MatchProgressResponse.newBuilder()
    WebHttpClientUtils.call(putMatchProgressUrl, request.build(), response)
    Assert.assertEquals(MatchProgressStatus.OK, response.getStatus)
  }

  @Test
  def testPutMatchResult: Unit ={
    val putMatchResultUrl = url + "/putMatchResult"
    val request = MatchResultRequest.newBuilder()
    request.setCandidateNum(1)
    request.setMatchId("1")
    request.setMaxScore(100)
    request.setRecordNumMatched(1000)
    request.setTimeElapsed(1000)
    request.setStatus(MatchResultRequest.MatcherStatus.newBuilder().setCode(0).setMsg(""))
    val cand = request.addCandidateResultBuilder
    cand.setObjectId(1)
    cand.setPos(1)
    cand.setScore(100)
    val response = MatchResultResponse.newBuilder()
    WebHttpClientUtils.call(putMatchResultUrl, request.build(), response)
    Assert.assertEquals(MatchResultResponseStatus.OK, response.getStatus)

  }
}
