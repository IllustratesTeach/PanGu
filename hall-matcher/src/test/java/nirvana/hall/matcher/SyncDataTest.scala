package nirvana.hall.matcher

import nirvana.hall.support.services.WebHttpClientUtils
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}

/**
 * Created by songpeng on 16/4/5.
 */
object SyncDataTest {
  val url = "http://127.0.0.1:9003/syncData"
//  val url = "http://127.0.0.1:8080/nirvana-web/syncData"

  def main(args: Array[String]) {
    val request = SyncDataRequest.newBuilder()
    request.setSize(100)
    request.setSyncDataType(SyncDataType.TEMPLATE_FINGER)
    request.setTimestamp(1)
    val response = SyncDataResponse.newBuilder()

    WebHttpClientUtils.call(url, request.build(), response)
    println(response.getSyncDataCount)
  }
}
