package nirvana.hall.api.internal.filter

import com.google.protobuf.ExtensionRegistry
import nirvana.hall.protocol.api.QueryProto
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse}
import nirvana.hall.support.internal.RpcHttpClientImpl
import org.junit.Test

/**
  * Created by songpeng on 2016/12/6.
  */
class QueryFilterTest {

  val url = "http://hzfree.uttcare.com:8088"
  val registry = ExtensionRegistry.newInstance()
  QueryProto.registerAllExtensions(registry)
  val httpClient = new RpcHttpClientImpl(registry)

  @Test
  def test_getQuery: Unit ={
    val request = QueryGetRequest.newBuilder()
    request.setOraSid(504)
    val baseResponse = httpClient.call(url, QueryGetRequest.cmd, request.build())
    val response = baseResponse.getExtension(QueryGetResponse.cmd)
    println(response)
  }

}
