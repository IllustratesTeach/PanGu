package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.v62.DelCaseProto.DelCaseRequest
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 15/11/15.
  */
class DelCaseFilterTest {
   protected var registry:Registry = _

   @Test
   def test_request(): Unit ={
     val modules = Seq[String](
       "nirvana.hall.api.LocalProtobufModule",
       "nirvana.hall.v62.LocalV62Module",
       "nirvana.hall.v62.internal.filter.TestModule").map(Class.forName)
     registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

     val requestBuilder = DelCaseRequest.newBuilder()
     requestBuilder.setCaseId("123456")
     val handler = registry.getService(classOf[ProtobufRequestHandler])
     val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
     protobufRequest.setExtension(DelCaseRequest.cmd, requestBuilder.build())
     val protobufResponse = BaseResponse.newBuilder()

     handler.handle(protobufRequest.build(), protobufResponse)

     Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
   }

 }
