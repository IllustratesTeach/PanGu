package nirvana.hall.v70.internal.filter.sys

import monad.rpc.protocol.CommandProto.BaseCommand
import nirvana.hall.api.internal.BaseServiceTestSupport
import nirvana.hall.v70.services.sync.SyncDictService
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.DictProto.DictType
import nirvana.hall.protocol.sys.SyncDictProto.{SyncDictRequest, SyncDictResponse}
import nirvana.hall.v70.services.sys.DictService
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
    val protobufRequest = BaseCommand.newBuilder().setTaskId(1)
    val protobufResponse = BaseCommand.newBuilder()

    protobufRequest.setExtension(SyncDictRequest.cmd, syncDictRequest.build())
    handler.handle(protobufRequest.build(), protobufResponse)

    Assert.assertTrue(protobufResponse.hasExtension(SyncDictResponse.cmd))
    Assert.assertTrue(protobufResponse.getExtension(SyncDictResponse.cmd).getDictDataCount > 0)

    val syncDictservice = registry.getService(classOf[SyncDictService])
    val dictService = registry.getService(classOf[DictService])
    val request = SyncDictRequest.newBuilder()
    DictType.values().foreach{ f =>
      request.setDictType(f)
      protobufRequest.setExtension(SyncDictRequest.cmd, request.build())
//      WebAppClientUtils.call(url, request.build(), response)
      handler.handle(protobufRequest.build(), protobufResponse)
      syncDictservice.syncDict(f, dictService.findAllDict(f))
    }
  }
}
