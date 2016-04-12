package nirvana.hall.matcher

import nirvana.hall.support.services.WebHttpClientUtils
import nirvana.protocol.MatchProgressProto.MatchProgressResponse.MatchProgressStatus
import nirvana.protocol.MatchProgressProto.{MatchProgressResponse, MatchProgressRequest}
import nirvana.protocol.MatchTaskQueryProto.{MatchTaskQueryRequest, MatchTaskQueryResponse}
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}
import org.junit.{Assert, Test}


/**
 * Created by songpeng on 16/4/5.
 */
class SyncDataTest {

  @Test
  def testSyncData(): Unit ={
    val url = "http://192.168.0.108:9003/syncData"
//      val url = "http://127.0.0.1:8080/nirvana-web/syncData"
    val request = SyncDataRequest.newBuilder()
    request.setSize(100)
    request.setSyncDataType(SyncDataType.TEMPLATE_FINGER)
    request.setTimestamp(1)
    val response = SyncDataResponse.newBuilder()
    WebHttpClientUtils.call(url, request.build(), response)
    val count1 = response.getSyncDataCount
    val maxSeq1 = response.getSyncDataList.get(count1-1).getTimestamp
    println("count:"+ count1 + " max seq:"+ maxSeq1)

    request.setTimestamp(response.getSyncDataBuilderList.get(response.getSyncDataCount-1).getTimestamp)
    response.clear()
    WebHttpClientUtils.call(url, request.build(), response)
    val count2 = response.getSyncDataCount
    val maxSeq2 = response.getSyncDataList.get(count2-1).getTimestamp
    println("count:"+ count2 + " max seq:"+ maxSeq2)

    request.setSize(200)
    request.setTimestamp(1)
    response.clear()
    WebHttpClientUtils.call(url, request.build(), response)
    val count3 = response.getSyncDataCount
    val maxSeq3 = response.getSyncDataList.get(count3-1).getTimestamp
    println("count:"+ count3 + " max seq:"+ maxSeq3)

    Assert.assertEquals(count1+ count2, count3)
    Assert.assertEquals(maxSeq2, maxSeq3)

  }

  @Test
  def testGetMatchTask(): Unit ={
    val url = "http://192.168.0.108:9003/getMatchTask"
    val request = MatchTaskQueryRequest.newBuilder()
    request.setSize(1)
    val response = MatchTaskQueryResponse.newBuilder()
    WebHttpClientUtils.call(url, request.build(), response)
    println(response.getMatchTaskCount)
  }

  @Test
  def testPutMatchProgress: Unit ={
    val url = "http://192.168.0.108:9003/putMatchProgress"
    val request = MatchProgressRequest.newBuilder()
    request.setMatchId("214")
    request.setProgress(100)
    val response = MatchProgressResponse.newBuilder()
    WebHttpClientUtils.call(url, request.build(), response)
    Assert.assertEquals(MatchProgressStatus.OK, response.getStatus)
  }
}
