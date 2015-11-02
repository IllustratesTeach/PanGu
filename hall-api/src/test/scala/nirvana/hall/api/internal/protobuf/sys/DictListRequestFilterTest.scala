package nirvana.hall.api.internal.protobuf.sys

import nirvana.hall.api.internal.BaseServiceTestSupport
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseResponse, BaseRequest}
import nirvana.hall.protocol.sys.DictListProto.{DictListResponse, DictListRequest}
import nirvana.hall.protocol.sys.DictListProto.DictListRequest.DictType
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/10/28.
 */
class DictListRequestFilterTest extends BaseServiceTestSupport{

  @Test
  def test_dictList: Unit = {

    val dictListRequest = DictListRequest.newBuilder()
    dictListRequest.setDictType(DictType.XB)

    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(101)
    protobufRequest.setExtension(DictListRequest.cmd, dictListRequest.build())

    val protobufResponse = BaseResponse.newBuilder()
    handler.handle(protobufRequest.build(), protobufResponse)

    val response = protobufResponse.getExtension(DictListResponse.cmd)
    println(response.getDictListCount)

  }
}
