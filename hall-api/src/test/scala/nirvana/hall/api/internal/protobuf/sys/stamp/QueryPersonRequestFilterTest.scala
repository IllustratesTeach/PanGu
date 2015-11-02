package nirvana.hall.api.internal.protobuf.sys.stamp

import org.junit.{Assert, Test}
import nirvana.hall.api.internal.BaseServiceTestSupport
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{ResponseStatus, BaseResponse, BaseRequest}
import nirvana.hall.protocol.sys.stamp.QueryPersonProto.{QueryPersonResponse, QueryPersonRequest}

/**
 * Created by wangjue on 2015/11/2.
 */
class QueryPersonRequestFilterTest extends BaseServiceTestSupport {

  @Test
  def test_query: Unit ={
    val queryRequest = QueryPersonRequest.newBuilder()
    queryRequest.setName("")
    queryRequest.setIdcard("")
    queryRequest.setGatherDateST("")
    queryRequest.setGatherDateEN("")
    queryRequest.setStart(1)
    queryRequest.setLimit(10)


    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(QueryPersonRequest.cmd, queryRequest.build())
    val protobufResponse = BaseResponse.newBuilder()
    protobufResponse.setStatus(ResponseStatus.OK)
    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertTrue(protobufResponse.hasExtension(QueryPersonResponse.cmd))

  }
}
