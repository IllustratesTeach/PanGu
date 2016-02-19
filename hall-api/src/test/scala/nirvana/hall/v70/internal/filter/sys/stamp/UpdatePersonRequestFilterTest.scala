package nirvana.hall.v70.internal.filter.sys.stamp

import monad.rpc.protocol.CommandProto.{CommandStatus, BaseCommand}
import org.junit.{Assert, Test}
import nirvana.hall.api.internal.BaseServiceTestSupport
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.stamp.UpdatePersonProto.{UpdatePersonResponse, UpdatePersonRequest}

/**
 * Created by wangjue on 2015/11/9.
 */
class UpdatePersonRequestFilterTest extends BaseServiceTestSupport {

  @Test
  def test_update: Unit ={
    val updateRequest = UpdatePersonRequest.newBuilder()
    updateRequest.setPersonInfo("")


    val handler = registry.getService(classOf[ProtobufRequestHandler])
    val protobufRequest = BaseCommand.newBuilder().setTaskId(1)
    protobufRequest.setExtension(UpdatePersonRequest.cmd, updateRequest.build())
    val protobufResponse = BaseCommand.newBuilder()
    protobufResponse.setStatus(CommandStatus.OK)
    handler.handle(protobufRequest.build(), protobufResponse)

    val personInfo = protobufResponse.getExtension(UpdatePersonResponse.cmd)

    Assert.assertTrue(personInfo != null)

  }
}
