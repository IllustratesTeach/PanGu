package nirvana.hall.v70.internal.filter.sys.stamp

import nirvana.hall.protocol.sys.stamp.QueryBasePersonProto.{QueryBasePersonResponse, QueryBasePersonRequest}
import org.junit.{Assert, Test}
import nirvana.hall.api.internal.BaseServiceTestSupport
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{ResponseStatus, BaseResponse, BaseRequest}
import nirvana.hall.protocol.sys.stamp.QueryPersonProto.{QueryPersonResponse}

/**
 * Created by wangjue on 2015/11/2.
 */
class QueryPersonRequestFilterTest extends BaseServiceTestSupport {

  @Test
  def test_query: Unit ={
    val queryRequest = QueryBasePersonRequest.newBuilder()
    queryRequest.setPersonid("CS520201511050001")


    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(QueryBasePersonRequest.cmd, queryRequest.build())
    val protobufResponse = BaseResponse.newBuilder()
    protobufResponse.setStatus(ResponseStatus.OK)
    handler.handle(protobufRequest.build(), protobufResponse)
    val personInfo = protobufResponse.getExtension(QueryBasePersonResponse.cmd)

    Assert.assertTrue(personInfo != null)

  }
}
