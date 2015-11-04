package nirvana.hall.api.internal.protobuf.sys

import nirvana.hall.api.internal.BaseServiceTestSupport
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.sys.SyncDictProto.{SyncDictResponse, DictType, SyncDictRequest}
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/11/4.
 */
class SyncDictRequestFilterTest extends BaseServiceTestSupport{

  @Test
  def test_syncDict(): Unit ={
    val syncDictRequest = SyncDictRequest.newBuilder()
    syncDictRequest.setDictType(DictType.CODE_MZ)

    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(101)
    val protobufResponse = BaseResponse.newBuilder()

    protobufRequest.setExtension(SyncDictRequest.cmd, syncDictRequest.build())
    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertTrue(protobufResponse.hasExtension(SyncDictResponse.cmd))
    Assert.assertTrue(protobufResponse.getExtension(SyncDictResponse.cmd).getSyncDataCount > 0)

  }
}
