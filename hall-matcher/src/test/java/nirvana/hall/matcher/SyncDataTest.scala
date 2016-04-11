package nirvana.hall.matcher

import nirvana.hall.support.services.WebHttpClientUtils
import nirvana.protocol.MatchTaskQueryProto.{MatchTaskQueryResponse, MatchTaskQueryRequest}
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}
import org.junit.Test

/**
 * Created by songpeng on 16/4/5.
 */
class SyncDataTest {

  @Test
  def testSyncData(): Unit ={
    val url = "http://192.168.255.188:9003/syncData"
    //  val url = "http://127.0.0.1:8080/nirvana-web/syncData"
    val request = SyncDataRequest.newBuilder()
    request.setSize(10)
    request.setSyncDataType(SyncDataType.PERSON)
    request.setTimestamp(1)
    val response = SyncDataResponse.newBuilder()

    WebHttpClientUtils.call(url, request.build(), response)
    println(response.getSyncData(1).getData.size())
  }

  @Test
  def testGetMatchTask(): Unit ={
    val url = "http://192.168.255.188:9003/getMatchTask"
    val request = MatchTaskQueryRequest.newBuilder()
    request.setSize(1)
    val response = MatchTaskQueryResponse.newBuilder()
    WebHttpClientUtils.call(url, request.build(), response)
    println(response.getMatchTaskCount)
  }
}
