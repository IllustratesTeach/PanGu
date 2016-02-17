package nirvana.hall.v70.internal.filter.sys.stamp

import org.junit.{Assert, Test}
import nirvana.hall.api.internal.BaseServiceTestSupport
import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{ResponseStatus, BaseResponse, BaseRequest}
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
    val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
    protobufRequest.setExtension(UpdatePersonRequest.cmd, updateRequest.build())
    val protobufResponse = BaseResponse.newBuilder()
    protobufResponse.setStatus(ResponseStatus.OK)
    handler.handle(protobufRequest.build(), protobufResponse)

    val personInfo = protobufResponse.getExtension(UpdatePersonResponse.cmd)

    Assert.assertTrue(personInfo != null)

  }
}
