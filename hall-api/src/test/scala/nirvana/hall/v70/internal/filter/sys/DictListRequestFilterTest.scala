package nirvana.hall.v70.internal.filter.sys

import monad.rpc.protocol.CommandProto.BaseCommand
import nirvana.hall.api.internal.JpaBaseServiceTestSupport
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.DictListProto.{DictListRequest, DictListResponse}
import nirvana.hall.protocol.sys.DictProto.DictType
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/11/10.
 */
class DictListRequestFilterTest extends JpaBaseServiceTestSupport{

  @Test
  def test_dictList(): Unit ={
    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseCommand.newBuilder().setTaskId(1)
    val protobufResponse = BaseCommand.newBuilder()

    val request = DictListRequest.newBuilder()
    request.setDictType(DictType.CODE_XB)
    protobufRequest.setExtension(DictListRequest.cmd, request.build())

    handler.handle(protobufRequest.build(), protobufResponse)
    val response = protobufResponse.getExtension(DictListResponse.cmd)

    Assert.assertTrue(response.getDictCount > 0)

  }
}
