package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.v62.GetCaseProto.GetCaseRequest
import nirvana.hall.protocol.v62.GetCaseProto.GetCaseResponse
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 15/11/15.
  */
class GetCaseFilterTest {
   protected var registry:Registry = _

   @Test
   def test_request(): Unit ={
     val modules = Seq[String](
       "nirvana.hall.api.LocalProtobufModule",
       "nirvana.hall.v62.LocalV62Module",
       "nirvana.hall.v62.internal.filter.TestModule").map(Class.forName)
     registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

     val requestBuilder = GetCaseRequest.newBuilder()
     requestBuilder.setCaseId("123456")
     val handler = registry.getService(classOf[ProtobufRequestHandler])
     val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
     protobufRequest.setExtension(GetCaseRequest.cmd, requestBuilder.build())
     val protobufResponse = BaseResponse.newBuilder()

     handler.handle(protobufRequest.build(), protobufResponse)

     Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
     Assert.assertNotNull(protobufResponse.getExtension(GetCaseResponse.cmd).getCase.getText)
   }

 }
