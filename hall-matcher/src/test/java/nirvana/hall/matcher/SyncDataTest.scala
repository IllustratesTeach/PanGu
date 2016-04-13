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
//    val url = "http://10.1.6.247:9003/syncData"
      val url = "http://127.0.0.1:8080/nirvana-web/syncData"
    val request = SyncDataRequest.newBuilder()
    val response = SyncDataResponse.newBuilder()
    request.setSize(100)
    request.setSyncDataType(SyncDataType.LATENT_FINGER)
    var timestamp:Long = 0
    var total = 0
    (1 to 3).foreach{i=>
      request.setTimestamp(timestamp)
      response.clear()
      WebHttpClientUtils.call(url, request.build(), response)
      val count = response.getSyncDataCount
      total += count
      timestamp = response.getSyncDataList.get(count-1).getTimestamp
      println("count:"+ count + " timestamp:"+ timestamp)
    }
    response.clear()
    request.setTimestamp(0)
    request.setSize(total)
    WebHttpClientUtils.call(url, request.build(), response)
    val count = response.getSyncDataCount
    val maxSeq = response.getSyncDataList.get(count-1).getTimestamp
    println("count:"+ count + " max seq:"+ maxSeq)

    Assert.assertEquals(total, count)
    Assert.assertEquals(maxSeq, timestamp)

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
