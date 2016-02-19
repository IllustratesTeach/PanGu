package nirvana.hall.v70.internal.filter.sys.stamp

import monad.rpc.protocol.CommandProto.{BaseCommand, CommandStatus}
import nirvana.hall.api.internal.BaseServiceTestSupport
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.stamp.QueryBasePersonProto.{QueryBasePersonRequest, QueryBasePersonResponse}
import org.junit.{Assert, Test}

/**
 * Created by wangjue on 2015/11/2.
 */
class QueryPersonRequestFilterTest extends BaseServiceTestSupport {

  @Test
  def test_query: Unit ={
    val queryRequest = QueryBasePersonRequest.newBuilder()
    queryRequest.setPersonid("CS520201511050001")


    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseCommand.newBuilder().setTaskId(1)
    protobufRequest.setExtension(QueryBasePersonRequest.cmd, queryRequest.build())
    val protobufResponse = BaseCommand.newBuilder()
    protobufResponse.setStatus(CommandStatus.OK)
    handler.handle(protobufRequest.build(), protobufResponse)
    val personInfo = protobufResponse.getExtension(QueryBasePersonResponse.cmd)

    Assert.assertTrue(personInfo != null)

  }
}
