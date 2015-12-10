package nirvana.hall.v62.internal.filter.qry

import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.v62.qry.QueryProto.QuerySendRequest
import org.apache.tapestry5.ioc.{RegistryBuilder, Registry}
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/12/9.
 */
class QueryFilterTest {
  private val modules = Seq[String](
    "nirvana.hall.api.LocalProtobufModule",
    "nirvana.hall.v62.LocalV62ServiceModule",
    "nirvana.hall.v62.internal.filter.TestModule").map(Class.forName)
  protected var registry:Registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

  @Test
  def test_sendQuery: Unit ={
    val requestBuilder = QuerySendRequest.newBuilder()
    val matchTask = requestBuilder.addMatchTaskBuilder()
    matchTask.setMatchId("P3702000000002015129996")
    matchTask.setMatchType(MatchType.FINGER_TT)
    matchTask.setPriority(1)
    matchTask.setScoreThreshold(50)
    matchTask.setObjectId(1)

    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(QuerySendRequest.cmd, requestBuilder.build())
    val protobufResponse = BaseResponse.newBuilder()

    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
  }
}
