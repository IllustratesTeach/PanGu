package nirvana.hall.api.internal.filter

import com.google.protobuf.ExtensionRegistry
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse}
import nirvana.hall.protocol.api.{HallMatchRelationProto, QueryProto, TPCardProto}
import nirvana.hall.protocol.fpt.MatchRelationProto
import nirvana.hall.protocol.fpt.TypeDefinitionProto.MatchType
import nirvana.hall.protocol.matcher.{MatchResultProto, MatchTaskQueryProto}
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.v62.internal.V62Facade
import org.junit.Test

/**
 * Created by songpeng on 16/9/10.
 */
class MatchRelationFilterTest {
//  val url = "http://127.0.0.1:8080"
  val url = "http://hzfree.uttcare.com:8088"
  val registry = ExtensionRegistry.newInstance()
  TPCardProto.registerAllExtensions(registry)
  MatchRelationProto.registerAllExtensions(registry)
  HallMatchRelationProto.registerAllExtensions(registry)
  QueryProto.registerAllExtensions(registry)
  MatchTaskQueryProto.registerAllExtensions(registry)
  MatchResultProto.registerAllExtensions(registry)
  val httpClient = new RpcHttpClientImpl(registry)
  @Test
  def test_MatchRelationGetRequest: Unit ={
    val request = MatchRelationGetRequest.newBuilder()
    request.setCardId("1100000000002016033333")
    request.setMatchType(MatchType.FINGER_LL)

    val headerMap = Map(V62Facade.X_V62_HOST_HEAD -> "10.1.7.169",
      V62Facade.X_V62_PORT_HEAD -> "6798")
    val baseResponse = httpClient.call(url, MatchRelationGetRequest.cmd, request.build(), headerMap)
    val response = baseResponse.getExtension(MatchRelationGetResponse.cmd)
    println(response.getMatchStatus)

  }

  @Test
  def test_MatchRelationGetRequestTT_v70: Unit ={
    val request = MatchRelationGetRequest.newBuilder()
    request.setCardId("000000000000000011000001")
    request.setMatchType(MatchType.FINGER_LT)

    val baseResponse = httpClient.call(url, MatchRelationGetRequest.cmd, request.build())
    val response = baseResponse.getExtension(MatchRelationGetResponse.cmd)
    println("MatchStatus:" +response.getMatchStatus)
    println(response)
  }

}
