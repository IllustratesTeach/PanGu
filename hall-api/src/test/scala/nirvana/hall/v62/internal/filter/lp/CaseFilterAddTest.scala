package nirvana.hall.v62.internal.filter.lp

import nirvana.hall.api.services.ProtobufRequestHandler
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.v62.lp.CaseAddProto.CaseAddRequest
import org.apache.tapestry5.ioc.{Registry, RegistryBuilder}
import org.junit.{Assert, Test}

/**
  * Created by songpeng on 15/11/15.
  */
class CaseFilterAddTest {
   protected var registry:Registry = _

   @Test
   def test_request(): Unit ={
     val modules = Seq[String](
       "nirvana.hall.api.LocalProtobufModule",
       "nirvana.hall.v62.LocalV62Module",
       "nirvana.hall.v62.internal.filter.TestModule").map(Class.forName)
     registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

     val requestBuilder = CaseAddRequest.newBuilder()
     val caseInfo = requestBuilder.getCaseBuilder
     caseInfo.setStrCaseID("123456")
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
     textBuilder.setStrCaseType1("010000")
     textBuilder.setStrComment("comment")
     textBuilder.setStrExtractDate("20151111")
     textBuilder.setStrExtractUnitCode("520000")
     textBuilder.setStrExtractUnitName("gui zhou")
     textBuilder.setStrMoneyLost("100")
     textBuilder.setStrPremium("1000")
     textBuilder.setStrSuspArea1Code("520100")

     val handler = registry.getService(classOf[ProtobufRequestHandler])
     val protobufRequest = BaseRequest.newBuilder().setToken("asdf").setVersion(102)
     protobufRequest.setExtension(CaseAddRequest.cmd, requestBuilder.build())
     val protobufResponse = BaseResponse.newBuilder()

     handler.handle(protobufRequest.build(), protobufResponse)

     Assert.assertEquals(ResponseStatus.OK,protobufResponse.getStatus)
   }

 }
