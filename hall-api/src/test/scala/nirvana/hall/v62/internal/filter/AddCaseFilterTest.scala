package nirvana.hall.v62.internal.filter

import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.v62.AddCaseProto.AddCaseRequest
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 15/11/15.
  */
class AddCaseFilterTest {
   protected var registry:Registry = _

   @Test
   def test_request(): Unit ={
     val modules = Seq[String](
       "nirvana.hall.api.LocalProtobufModule",
       "nirvana.hall.v62.LocalV62Module",
       "nirvana.hall.v62.internal.filter.TestModule").map(Class.forName)
     registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

     val requestBuilder = AddCaseRequest.newBuilder()
     val caseInfo = requestBuilder.getCaseBuilder
     caseInfo.setStrCaseID(System.currentTimeMillis().toString)
     caseInfo.setNCaseFingerCount(0)
     caseInfo.setNSendFingerCount(0)
     val textBuilder = caseInfo.getTextBuilder
     textBuilder.setStrCaseOccurDate("20151111")
     textBuilder.setBPersonKilled(false)
     textBuilder.setNCancelFlag(1)
     textBuilder.setNCaseState(1)
     textBuilder.setNSuperviseLevel(1)
     textBuilder.setNXieChaState(1)
     textBuilder.setStrCaseOccurPlace("贵阳市")
     textBuilder.setStrCaseOccurPlaceCode("520000")
     textBuilder.setStrCaseType1("")
     textBuilder.setStrCaseType2("")
     textBuilder.setStrCaseType3("")
     textBuilder.setStrComment("comment")
     textBuilder.setStrExtractDate("20151111")
     textBuilder.setStrExtractUnitCode("520000")
     textBuilder.setStrExtractUnitName("gui zhou")
     textBuilder.setStrMoneyLost("100")
     textBuilder.setStrPremium("premium")
     textBuilder.setStrSuspArea1Code("")

     val handler = registry.getService(classOf[ProtobufRequestHandler])
     val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
     protobufRequest.setExtension(AddCaseRequest.cmd, requestBuilder.build())
     val protobufResponse = BaseResponse.newBuilder()

     handler.handle(protobufRequest.build(), protobufResponse)

     Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
   }

 }
